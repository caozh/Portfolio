// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete factory: customer
 * 
 * @author Zhihao Cao
 */

import java.rmi.RemoteException;

public class Factory_concrete_customer implements Interface_user {

	@Override
	public Command_browse_customer browse(MVC_model_interface myModel) throws RemoteException{
		System.out.println("customer call browse()");
		Command_browse_customer command_browse_customer = new Command_browse_customer(myModel);
		return command_browse_customer;
	}

	@Override
	public Command_loadCart loadCart(MVC_model_interface myModel) throws RemoteException {
		System.out.println("customer call cart()");
		Command_loadCart command_loadCart = new Command_loadCart(myModel);
		return command_loadCart;
	}

	@Override
	public Command_viewOrder viewOrder(MVC_model_interface myModel) throws RemoteException {
		System.out.println("customer call viewOrder()");
		Command_viewOrder command_viewOrder = new Command_viewOrder(myModel);
		return command_viewOrder;
	}

	@Override
	public Command_updateInfo_customer updateInfo(MVC_model_interface myModel) throws RemoteException {
		System.out.println("customer call updateInfo()");
		Command_updateInfo_customer command_updateInfo_customer = new Command_updateInfo_customer(myModel);
		return command_updateInfo_customer;
	}

	@Override
	public Command_checkOut checkOut(MVC_model_interface myModel) throws RemoteException{
		System.out.println("customer call checkOut()");
		Command_checkOut command_checkOut = new Command_checkOut(myModel);
		return command_checkOut;
	}

	/**
	 * Admin
	 * unimplemented methods
	 */
	@Override
	public Command_browse_admin browseAdmin(MVC_model_interface myModel) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command_AddItem addItem(MVC_model_interface myModel) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command_RemoveItem removeItem(MVC_model_interface myModel) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command_updateItem updateItem(MVC_model_interface myModel) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command_addAdmin addAdmin(MVC_model_interface myModel) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command_updateInfo_admin updateInfoAdmin(MVC_model_interface myModel) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
