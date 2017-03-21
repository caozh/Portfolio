/**
 * Application controller: menu
 * 
 * @author Zhihao Cao
 */

public class Control_menu {
	//private Controller_client controller_client;
	private View_menu view_menu;
	
	public Control_menu(){
		//controller_client = Controller_client.getInstance();
		view_menu = new View_menu();
	}
	
	public void execute(Session session) {
		String role = session.getUser().getRoleType();
		view_menu.showView(role);
	}
}
