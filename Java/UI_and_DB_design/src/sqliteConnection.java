package csci49500Project;

import java.sql.*;
import java.util.Vector;

import javax.swing.*;

public class sqliteConnection {
	static Connection conn    = null;
	static Statement  stmt    = null;
	static String     sql;
	
	public static Vector<Vector<?>> dbConnector()
	{
		//try connection
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\My documents\\workspace\\csci49500Project\\Resources\\NodeData.sqlite");											   
			conn.setAutoCommit(false);
			//try statement
			try {
			stmt=conn.createStatement();
				//try result set
				try{
					ResultSet rs = stmt.executeQuery("select * from NodeInfo");
					//try to load whole table into result Set
					try{
						ResultSetMetaData metaData;
						metaData = rs.getMetaData();
						int colNumber = metaData.getColumnCount();
						Vector<String> columnNames = new Vector<String>();
						Vector<Vector<Object>> rows        = new Vector<Vector<Object>>();
						for(int i=0; i < colNumber; i++)
						{
							columnNames.addElement(metaData.getColumnLabel(i + 1));
						}
						while(rs.next())
						{
							Vector<Object> newRow = new Vector<Object>();
						    //load raw into vector newRow
						    for (int i = 1; i <= colNumber; i++) {
						    	newRow.addElement(rs.getObject(i));
						    }
						    //load newRow into vector rows 
						    rows.addElement(newRow);
						    //(vector, vector)
						 }
						
						Vector<Vector<?>> rowAndCol = new Vector<Vector<?>>();
						//output the table data by a vector
						rowAndCol.addElement(rows);
						rowAndCol.addElement(columnNames);
						//System.out.println("row: "+rowAndCol.elementAt(0));  
						//System.out.println("col: "+rowAndCol.elementAt(1));  
						return rowAndCol;
					} finally {
						rs.close();
					}//end try to load
				} finally {
					stmt.close();
				}//end try result set
			} finally {
				conn.close();
			}//end statement
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return null;
		}//end try connection
	}// end dbConnector()
	

	public static void insert() {
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Zhihao\\workspace\\csci49500Project\\Resources\\NodeData.sqlite");
			conn.setAutoCommit(false);
			try{
				stmt = conn.createStatement();
				try{
					//DB insertion sql sentence
					sql = "INSERT INTO NodeInfo (Node_ID,Type,Version,Flag,Location) " +
								 "VALUES (20, 2, 3, 0, 32);";
					stmt.executeUpdate(sql);
					
				    conn.commit();
				    JOptionPane.showMessageDialog(null, "Insertion Successful");
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				}finally{
					stmt.close();
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e);
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}finally{
				conn.close();
			}
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}//end insert()
	
	public static void delete() {
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Zhihao\\workspace\\csci49500Project\\Resources\\NodeData.sqlite");
			conn.setAutoCommit(false);
			try{
				stmt = conn.createStatement();
				try{
					//DB deletion SQL sentence
					sql =  "DELETE from NodeInfo where Node_ID = 20;";
					stmt.executeUpdate(sql);
					
				    conn.commit();
				    JOptionPane.showMessageDialog(null, "Deletion Successful");
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				}finally{
					stmt.close();
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e);
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}finally{
				conn.close();
			}
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}//end delete()
}// end class


