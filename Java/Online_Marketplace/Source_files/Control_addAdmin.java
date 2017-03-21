/**
 * Application controller: add admin
 * 
 * @author Zhihao Cao
 */

public class Control_addAdmin {
	private Controller_client controller_client;
	private View_menu view_menu;
	
	public Control_addAdmin(){
		controller_client = Controller_client.getInstance();
		view_menu = new View_menu();
	}
	
	public void execute(Session session) {
		//get factory
	    Factory_abstract adminFactory = Factory_producer.getFactory("ADMIN");
		Interface_user user = adminFactory.getAdmin();
		
		controller_client.addAdmin(user, session);
		 
	    //show menu
	    view_menu.showView(session.getUser().getRoleType());
	}
}
