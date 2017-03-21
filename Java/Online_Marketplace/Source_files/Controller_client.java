/**
 * Client controller
 *  -a singleton class acting as the gate between client and server
 *  
 * methods:
 * 1. loginProcess()
 * 2. 
 * 
 * @author Zhihao Cao
 */

import java.rmi.Naming;

public class Controller_client {
	private static Controller_client uniqueInstance;
	private MVC_model_interface myModel;
	private Session session = null;
	private Command_invoker invoker; 
	
	private Controller_client(){
		// Java RMI Security Manager
		System.setSecurityManager(new SecurityManager());
		
		// Try-Catch is necessary for remote exceptions.
		try {
			//look up the remote object
			String name = "//tesla.cs.iupui.edu:1888/MarketServer";
			myModel = (MVC_model_interface) Naming.lookup(name);						
					
		} catch(Exception e){
			System.out.println("Marketplace RMI Exception: " + e.getMessage());
			e.printStackTrace();
		}
		invoker = new Command_invoker();
	}
	
	public static Controller_client getInstance(){
		if (uniqueInstance == null){
			uniqueInstance = new Controller_client();
		}
		return uniqueInstance;
	}
	
	public Session loginProcess(Credential credential){
		//acquire session
		try{
			String result = myModel.processLogin(credential);
			System.out.println("Controller_client: processLogin() returns " + result);
			if (result != null){
				session = new Session(result);
				return session;
			} 
			
		} catch(Exception e){
			System.out.println("Controller_client: loginProcess Exception: " + e.getMessage());
		}
		return session;
	}
	
	public void addAdmin(Interface_user user, Session sessions){
		try {
			//session = new Session("customer");
			invoker.takeCommand(user.addAdmin(myModel));
			invoker.executeCommands(session);
			
		} catch (Exception e) {
			System.out.println("Controller_client: " + e.getMessage());
		}
		
	}
}
