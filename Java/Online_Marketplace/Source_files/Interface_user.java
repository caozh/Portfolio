/**
 * User Interface
 *  -for factory use
 *  
 * @author Zhihao Cao
 */

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Interface_user extends Remote{
	//command pattern
	//customer
	Command_browse_customer browse(MVC_model_interface myModel) throws RemoteException;
	Command_loadCart loadCart(MVC_model_interface myModel) throws RemoteException;
	Command_checkOut checkOut(MVC_model_interface myModel) throws RemoteException;
	Command_viewOrder viewOrder(MVC_model_interface myModel) throws RemoteException;
	Command_updateInfo_customer updateInfo(MVC_model_interface myModel) throws RemoteException;
	
	//admin
	Command_browse_admin browseAdmin(MVC_model_interface myModel) throws RemoteException;
	Command_AddItem addItem(MVC_model_interface myModel) throws RemoteException;
	Command_RemoveItem removeItem(MVC_model_interface myModel) throws RemoteException;
	Command_updateItem updateItem(MVC_model_interface myModel) throws java.rmi.RemoteException;
	Command_addAdmin addAdmin(MVC_model_interface myModel) throws RemoteException;
	Command_updateInfo_admin updateInfoAdmin(MVC_model_interface myModel) throws RemoteException;
}
