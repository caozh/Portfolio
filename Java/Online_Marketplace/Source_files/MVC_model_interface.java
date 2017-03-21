/**
 * RMI Interface for MVC model
 * 
 * @author Zhihao Cao
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MVC_model_interface extends Remote{
	public String processLogin(Credential credential) throws RemoteException;
	
	//customer
	@RequiresRole("customer")
	public String[] browse(Session session) 		  	  throws RemoteException;
	@RequiresRole("customer")
	public String[] loadCart(Session session)		 	  throws RemoteException;
	@RequiresRole("customer")
	public String[] checkOut(Session session) 		  	  throws RemoteException;
	@RequiresRole("customer")
	public String[] viewOrder(Session session)		  	  throws RemoteException;
	@RequiresRole("customer")
	public String[] updateInfo(Session session) 	  	  throws RemoteException;
	
	//administrator
	@RequiresRole("admin")
	public String[] browseAdmin(Session session) 	  	  throws RemoteException;
	@RequiresRole("admin")
	public String[] addItem(Session session) 		  	  throws RemoteException;
	@RequiresRole("admin")
	public String[] removeItem(Session session) 	  	  throws RemoteException;
	@RequiresRole("admin")
	public String[] updateItem(Session session) 	  	  throws RemoteException;
	@RequiresRole("admin")
	public String[] addAdmin(Session session)             throws RemoteException;
	@RequiresRole("admin")
	public String[] updateInfoAdmin(Session session) 	  throws RemoteException;
}
