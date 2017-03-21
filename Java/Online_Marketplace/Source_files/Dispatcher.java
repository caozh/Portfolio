// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Dispatcher
 * 
 * Dispatcher checks the associated Role for the User session
 * and forwards the request to respective application controller
 * 
 * @author Zhihao Cao
 */

public class Dispatcher {
	//concrete views
	private Control_menu control_menu;
	private Control_browse_customer control_browse_customer;
	private Control_browse_admin control_browse_admin;
	private Control_addAdmin control_addAdmin;
	
	//dispatcher constructor
	public Dispatcher(){
		control_menu = new Control_menu();
		control_browse_customer = new Control_browse_customer();
		control_browse_admin = new Control_browse_admin();
		control_addAdmin = new Control_addAdmin();
	}	
	
	//based upon the request = dispatch the view
	public void dispatch(String request, Session session) {
		if (request.equalsIgnoreCase("menu")) {
			control_menu.execute(session);
		} 
		else if (request.equalsIgnoreCase("browseCustomer")) {
			control_browse_customer.execute(session);
		} 
		else if (request.equalsIgnoreCase("browseAdmin")) {
			control_browse_admin.execute(session);
		} 
		else if (request.equalsIgnoreCase("addAdmin")) {
			control_addAdmin.execute(session);
		}
		else {
			System.out.println("dispatcher cannot recognize the request: " + request);
		}
	}
}
