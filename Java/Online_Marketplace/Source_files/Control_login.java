// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Application controller: login
 * 
 * @author Zhihao Cao
 */

public class Control_login {
	private View_login view_login;
	
	public Control_login (){
		view_login = new View_login();
	}
	
	public void execute() {
		view_login.showView();
	}
}
