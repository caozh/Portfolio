/**
 * Online Market Client
 * 
 * 1. prompt login
 * 2. receive request and send to front controller
 * 
 * @author Zhihao Cao
 */

import java.util.Scanner;

public class MarketClient {
	private static View_login view_login;
	
	public static void main(String[] args) {
		// Create new instance of a Front Controller...
		FrontController frontController = new FrontController();

		//start login routine
		boolean ifLogin = false;
		while(!ifLogin){
			view_login = new View_login();
			view_login.showView();	
			ifLogin = frontController.loginRequest();
		}
		
		//after login success, receive request from user
		Scanner scanner=new Scanner(System.in);
		String request = null;
		//get request
		while (scanner.hasNextLine()) {
			request = scanner.nextLine();
			//exit the program
			if (request.equalsIgnoreCase("exit")) 
				break;
			//send the request to front controller
			frontController.dispatchRequest(request);	
		}
		//close the scanner
		scanner.close();
		
		// Terminate the program.
		System.exit(0);
	}
}

