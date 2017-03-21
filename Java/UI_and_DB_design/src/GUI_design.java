package csci49500Project;




import java.awt.EventQueue;
import java.awt.event.*;
import java.awt.Color;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class GUI_design {

	private JFrame frame;
	private static DefaultTableModel myModel = new DefaultTableModel();
	private static JTable myTable;
	//set the table editable
	static boolean editable = false;
	// ArrayList<Integer> nodeList;
	static ArrayList<Object> nodeList = new ArrayList<Object>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_design window = new GUI_design();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} //end try
			} //end run
		});
	}//end main
	
	
	
	
	/**
	 * Create the application.
	 */
	public GUI_design() {
		initialize();
	}

	//Refresh the table when table has changes
	public void tableRefresh() {
		//myModel = sqliteConnection.dbConnector();
		myTable = makeTable();
		System.out.print("call makeTable()");
		JScrollPane sp = new JScrollPane(myTable);
		sp.setBounds(156, 29, 529, 280); 
		frame.getContentPane().add(sp);
		frame.repaint();
	}//end tableRefresh
	
	
	//make a table with check box
	public static JTable makeTable() {
		
		//editable = true;
		
		//load database data into table
		Vector<Vector<?>> rowAndCol = new Vector<Vector<?>>();
		rowAndCol = sqliteConnection.dbConnector();
		//create vectors to hold rows data and columns data
		Vector<?> rows = new Vector<Object>(rowAndCol.get(0));
		Vector<?> columnNames = new Vector<Object>(rowAndCol.get(1));
		//create table model
		myModel = new DefaultTableModel(rows,columnNames){
		private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				if (editable) return true;
				else return (column == 6);
			}
		};		
		myTable = new JTable(myModel);
		
		//Create a list to hold column data for check box
		List<Boolean> colData = new ArrayList<Boolean>(myTable.getRowCount());
		for (int i = 0; i < myTable.getRowCount(); i++) {
		      colData.add(new Boolean(false));
		}
		//add the column to model
		myModel.addColumn("Check box", colData.toArray());
		//render the check box
		TableColumn tc = myTable.getColumnModel().getColumn(6); 
		tc.setCellEditor(myTable.getDefaultEditor(Boolean.class)); 
		tc.setCellRenderer(myTable.getDefaultRenderer(Boolean.class));   
		//fix the column
		myTable.getTableHeader().setReorderingAllowed(false);
		
		//row selection
		myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//add a check box listener
		myTable.getModel().addTableModelListener(new TableModelListener() {
			//
		    public void tableChanged(TableModelEvent e) {
		    	//register the listener to check box column
		    	if(e.getColumn() == 6){
		    		int row = e.getFirstRow();
		    	    int column = e.getColumn();
		    	    //DefaultTableModel model = (DefaultTableModel)e.getSource();
		    	    //String columnName = model.getColumnName(column);
		    	    boolean checkValue = (boolean) myModel.getValueAt(row, column);
		    	    Object nodeID = myModel.getValueAt(row, 0);
		    	    //if the node is checked
		    	    if (checkValue) {
	                	//see if the list already have it
	                	if (!nodeList.contains(nodeID))
	                	{
	                		//add the node id to the list
	                		nodeList.add(nodeID);
	                		//test
	                	  	for(int i = 0; i < nodeList.size(); i++) {
	                			System.out.print((nodeList.get(i)).toString());
	                	  	}  
	                	  	System.out.println("\n");
	                	}
		    	    } //end checkvalue true
		    	    //if the node id is unchecked
		    	    if (!checkValue) {
	                	//Remove the node id from the list  
		    	    	if (nodeList.contains(nodeID))
		    	    		nodeList.remove(nodeID);
		    	    	//test
	                	for(int i = 0; i < nodeList.size(); i++) {
	                		System.out.print((nodeList.get(i)).toString());
	                	}
	                	System.out.println("\n");
	                };// end checkvalue false
	         	} //end register the listener to check box column
		      }//end tableChanged()
		});// end addTableModelListener()
		return myTable;
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 722, 524);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(10, 25, 127, 33);
		frame.getContentPane().add(btnConnect);
		
		JButton btnDetectSingle = new JButton("Detect Single");
		btnDetectSingle.setBounds(10, 111, 127, 33);
		frame.getContentPane().add(btnDetectSingle);
		
		JButton btnDetectAll = new JButton("Detect All Nodes");
		btnDetectAll.setBounds(10, 154, 127, 33);
		frame.getContentPane().add(btnDetectAll);
		
		JButton btnDissem = new JButton("Disseminate");
		btnDissem.setBounds(10, 68, 127, 33);
		frame.getContentPane().add(btnDissem);
		//btnDissem.setVisible(false);
		
		JButton btnStopDisseminate = new JButton("Stop Disseminate");
		btnStopDisseminate.setBounds(10, 197, 127, 33);
		frame.getContentPane().add(btnStopDisseminate);
		//btnStopDisseminate.setVisible(false);
		
		JButton btnAbortReprogram = new JButton("Abort Reprogram");
		btnAbortReprogram.setBounds(10, 240, 127, 33);
		frame.getContentPane().add(btnAbortReprogram);
		//btnAbortReprogram.setVisible(false);
		
		
		
		JButton btnBackMenu = new JButton("Back");
		btnBackMenu.setBounds(10, 320, 127, 33);
		frame.getContentPane().add(btnBackMenu);
		
		
		JTextArea area = new JTextArea();
		area.setDropMode(DropMode.INSERT);
		area.setEditable(false);
		area.setBounds(156,302,529,161);
		area.setBackground(Color.LIGHT_GRAY);
		area.setForeground(Color.BLACK);
		JScrollPane scroll = new JScrollPane(area);
	    scroll.setBounds(156, 319, 529, 126); 
		frame.getContentPane().add(scroll);
		
		
		//create a table with check box
		myTable = makeTable();
		myTable.setBounds(156, 29, 529, 280);
		
		//A scroll pane for table
		JScrollPane sp = new JScrollPane(myTable);
		sp.setBounds(156, 29, 529, 280); 
		frame.getContentPane().add(sp);
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		//Menu bar
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		//File
		JMenu mnFile = new JMenu("File          ");
		menuBar.add(mnFile);
		//File
		JMenu mnImportImage = new JMenu("Import image");
		mnFile.add(mnImportImage);
		//File
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);		
		//import-File
		JMenuItem mntmOpenFile = new JMenuItem("Open file");
		mnImportImage.add(mntmOpenFile);
		//Print
		JMenu mnPrint = new JMenu("Print           ");
		menuBar.add(mnPrint);
		//Print
		JMenuItem mntmPrintTheBase = new JMenuItem("Print the noed table");
		mnPrint.add(mntmPrintTheBase);
		//database
		JMenu mnDatabase = new JMenu("Database         ");
		menuBar.add(mnDatabase);		
		//Edit database
		JMenuItem mntmEditDatabase = new JMenuItem("Edit database");
		mnDatabase.add(mntmEditDatabase);
		//Help
		JMenu mnHelp = new JMenu("Help          ");
		menuBar.add(mnHelp);
		//Help
		JMenuItem mntmHelpInformation = new JMenuItem("Help information");
		mnHelp.add(mntmHelpInformation);



		//Menu actionListener
		//load image file
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				area.append("Select an image.\n");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    area.append("Selected file: " + selectedFile.getAbsolutePath()+'\n');
				}
			}
		});
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		mntmEditDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditDb editDb_window = new EditDb();
				editDb_window.setVisible(true);
				nodeList.clear();
			}
		});
		
		
		
		
		
		
		
		
		//ActionListener
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("You clicked the button");
				sqliteConnection.insert();
				tableRefresh();
				area.append("Connect\n");
			}
		});
		
		btnBackMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqliteConnection.delete();
				tableRefresh();
			}
		});
		
		btnDetectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				area.append("Detect All\n");
			}
		});
		
		btnDetectSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.append("Detect Single\n");
			}
		});
		
		btnDissem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.append("Disseminate\n");
			}
		});

		btnStopDisseminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.append("Stop Disseminate\n");
			}
		});
		
		btnAbortReprogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.append("Abort Reprogram\n");
			}
		});// end actionListener
	}//end initialize()
}//end class



