package csci49500Project;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EditDb extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable editTable;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditDb frame = new EditDb();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the Constructor.
	 */
	public EditDb() {
		initialize();
		
	}
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 617, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		editTable = GUI_design.makeTable();
		JScrollPane sp = new JScrollPane(editTable);
		sp.setBounds(45, 40, 529, 280); 
		contentPane.add(sp);		
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnClose.setBounds(481, 340, 93, 23);
		contentPane.add(btnClose);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(362, 340, 93, 23);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(241, 340, 93, 23);
		contentPane.add(btnUpdate);
	}
}
















