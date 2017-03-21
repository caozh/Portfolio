
// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Application controller: admin browse
 * 
 * @author Zhihao Cao
 */

public class Control_browse_admin {

	private View_menu view_menu;
	
	public Control_browse_admin() {
		view_menu = new View_menu();
	}

	public void execute(Session session) {
		 System.out.println("Admin browse function is under construction.");
		 System.out.println("====================");
		 
		//show menu
		view_menu.showView(session.getUser().getRoleType());
	}
}
