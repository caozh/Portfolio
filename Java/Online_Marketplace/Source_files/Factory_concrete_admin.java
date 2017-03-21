// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete factory: admin
 * 
 * @author Zhihao Cao
 */

import java.rmi.RemoteException;

public class Factory_concrete_admin implements Interface_user {
	
	
	@Override
	public Command_browse_admin browseAdmin(MVC_model_interface myModel) throws RemoteException {
		Command_browse_admin command_browse_admin = new Command_browse_admin(myModel);
		return command_browse_admin;
	}

	@Override
	public Command_AddItem addItem(MVC_model_interface myModel) throws RemoteException {
		Command_AddItem command_AddItem = new Command_AddItem(myModel);
		System.out.println("admin call addItem()");
		return command_AddItem;
	}

	@Override
	public Command_RemoveItem removeItem(MVC_model_interface myModel) throws RemoteException {
		Command_RemoveItem command_RemoveItem = new Command_RemoveItem(myModel);
		System.out.println("admin call removeItem()");
		return command_RemoveItem;
	}

	@Override
	public Command_updateItem updateItem(MVC_model_interface myModel) throws RemoteException {
		Command_updateItem command_updateItem = new Command_updateItem(myModel);
		System.out.println("admin call updateItem()");
		return command_updateItem;
	}

	@Override
	public Command_addAdmin addAdmin(MVC_model_interface myModel) throws RemoteException {
		System.out.println("addAdmin command created");
		Command_addAdmin command_addAdmin = new Command_addAdmin(myModel);
		return command_addAdmin;
	}

	@Override
	public Command_updateInfo_admin updateInfoAdmin(MVC_model_interface myModel) throws RemoteException {
		Command_updateInfo_admin command_updateInfo_admin = new Command_updateInfo_admin(myModel);
		System.out.println("admin call updateInfo()");
		return command_updateInfo_admin;
	}

	/**
	 * Customer
	 * unimplemented methods
	 */
	@Override
	public Command_browse_customer browse(MVC_model_interface myModel) throws RemoteException{
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command_loadCart loadCart(MVC_model_interface myModel) throws RemoteException{
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command_checkOut checkOut(MVC_model_interface myModel) throws RemoteException{
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command_viewOrder viewOrder(MVC_model_interface myModel) throws RemoteException{
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command_updateInfo_customer updateInfo(MVC_model_interface myModel) throws RemoteException{
		return null;
		// TODO Auto-generated method stub
		
	}

}
