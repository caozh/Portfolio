/**
 * Implementation of front controller
 * 
 * Methods:
 * 1. loginRequest()
 * 2. isAuthenticUser()
 * 3. dispatcherRequest()
 * 
 * @author Zhihao Cao
 */

public class FrontController {
	//Declare objects
	private Dispatcher dispatcher;
	private Session session = null;
	public Controller_client controller_client;
	
	//Front Controller Constructor	
	public FrontController() {
		dispatcher = new Dispatcher();
		controller_client = Controller_client.getInstance();
	}
	
	public boolean loginRequest(){
		//input login combination
		Credential myCredential = new Credential();
		myCredential.setUname(System.console().readLine());
		myCredential.setPwd(System.console().readPassword("Enter your password: "));
		session = controller_client.loginProcess(myCredential);
		if (session == null){
			//show login fail view
			View_login_fail view_login_fail = new View_login_fail();
			view_login_fail.showView();	
			return false;
		} else {
			String role = session.getUser().getRoleType();
			System.out.println("FrontController: session created, getUser = "
				+ role);
			dispatchRequest("menu");
			return true;
		}	
	}
	
	//check if the client is authenticated 
	private boolean isAuthenticUser() {
		//validate by checking session
		return session.isAuthenticated();
	}
	
	//Responsible for dispatching the request to the Dispatcher.
	public void dispatchRequest(String request) {
		System.out.println("FrontController: Page Requested: " + request);   
		
		// If the user has been authenticated - dispatch request...
		if(isAuthenticUser()) {
			dispatcher.dispatch(request, session);
	    } else {
	    	System.out.println("FrontController: Invalid user");
	    }
	}
}
