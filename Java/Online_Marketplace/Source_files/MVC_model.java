/**
 * RMI Interface implementation for MVC model 
 * 
 * methods:
 * processLogin()
 * browse()
 * cart()
 * checkout()
 * viewOrder()
 * updateInfo()
 * browseAdmin()
 * addItem()
 * removeItem()
 * updateItem()
 * addAdmin()
 * updateInfoAdmin()
 * 
 * @author Zhihao Cao
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MVC_model extends UnicastRemoteObject implements MVC_model_interface{
	private static final long serialVersionUID = 1L;
	
	//constructor to create remote object
	protected MVC_model() throws RemoteException {
		super();
	}

	/**
	 * login Method
	 * 
	 * Allows users to login into the system by offering username and password.
	 * only accept two accounts at the present:
	 * Administrator:
	 * username: admin
	 * password: admin
	 * 
	 * Customer:
	 * username: josephzelo
	 * password: asdasd
	 */
	public String processLogin(Credential credential) {
		String result = null;
    	System.out.println("input username is:" + credential.getUname());
    	System.out.println("input password is:" + String.valueOf(credential.getPwd()));
        if (credential.getUname().equalsIgnoreCase("josephzelo")){
        	System.out.println("username is correct");
        	if(String.valueOf(credential.getPwd()).equalsIgnoreCase("asdasd")){
        		result = "customer";
        	} else {
        		System.out.println("password is wrong");
        	}
        } else if (credential.getUname().equalsIgnoreCase("admin")) {
        	System.out.println("username is correct");
        	if(String.valueOf(credential.getPwd()).equalsIgnoreCase("admin")){
        		result = "admin";
        	} else {
        		System.out.println("password is wrong");
        	}
        } else {
        	System.out.println("username is wrong");
        }
		return result;
	}
	
    //customer
	// Ryan: Please include usefull comments in each file.
    // FIX: display all products selling in the market place
	/**
	 * It pulls all selling products information from db,
	 * return to the customer a array of product objects.
	 */
    public String[] browse(Session session) throws RemoteException {
    	String[] items = {"a", "b"}; 
    	System.out.println("Server model invokes browse() method");
		return items;
    };
    
    /**
     * It pulls pre-saved products of a user from db,
     * return to client as a array of product objects.
     */
	public String[] loadCart(Session session) throws RemoteException{
		System.out.println("Server model invokes loadCart() method");
		return null;
		
	};
	
	/**
	 * It performs the placing order and transaction processes.
	 * It updates the product quantities, creates a record to save 
	 * products and related purchasing information. 
	 */
	public String[] checkOut(Session session) throws RemoteException {
		System.out.println("Server model invokes checkOut() method");
		return null;
	}
	
	/**
	 * It pulls the order(s) of a client from db,
	 * return to client as a array of orders.
	 */
	public String[] viewOrder(Session session) throws RemoteException{
		System.out.println("Server model invokes viewOrder() method");
		return null;
		
	};
	
	/**
	 * It allow a customer to update his/her personal information
	 * in db.
	 */
	public String[] updateInfo(Session session) throws RemoteException{
		System.out.println("Server model invokes updateInfo() method");
		return null;
		
	};
	
	//administrator
	/**
	 * It pulls all selling products information from db,
	 * return to the admin a array of product objects. 
	 */
	public String[] browseAdmin(Session session) throws RemoteException{
		System.out.println("Server model invokes browseAdmin() method");
		return null;
		
	};
	/**
	 * It allows the admin to add a product into db.
	 */
	public String[] addItem(Session session) throws RemoteException{
		System.out.println("Server model invokes addItem() method");
		return null;
		
	};
	/**
	 * It allows the admin to remove a product from db.
	 */
	public String[] removeItem(Session session) throws RemoteException{
		System.out.println("Server model invokes removeItem() method");
		return null;
		
	};
	/**
	 * It allows the admin to update a product from db.
	 */
	public String[] updateItem(Session session) throws RemoteException {
		System.out.println("Server model invokes updateItem() method");
		return null;
	}
	/**
	 * It allows the admin to add an admin account
	 */
	public String[] addAdmin(Session session) throws RemoteException {
		System.out.println("Server model invokes addAdmin() method");
		return null;
	}
	
	/**
	 * It allows tea admin to update its information 
	 */
	public String[] updateInfoAdmin(Session session) throws RemoteException {
		System.out.println("Server model invokes updateInfoAdmin() method");
		return null;
	}


}
