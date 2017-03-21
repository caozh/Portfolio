/**
 * Market view object
 *  -useless class
 *  -only use for testing during development
 *  
 * @author Zhihao Cao
 */

public class MVC_view {
	//constructor
	public MVC_view() {
		//start screen
		System.out.println("Welcome to Online Market Place");
		System.out.println("Option:");
		System.out.println("1. Register a new account(empty)");
		System.out.println("2. Login");
	}
	
	//login view
	public void view_login() {
		System.out.println("Please login");
		System.out.print("Enter your user name: ");

	    //System.out.println("Your account is: " + username);
	    //System.out.println("Your password is: " + String.valueOf(pwd));
	}
	//login fail view
	public void view_login_fail() {
		System.out.println("Unauthorized Login Attempt");
	}
	
	//administrator menu
	public void view_menu_admin(String username) {
    	System.out.println("Welcome to Online Market place, " + username);
		System.out.println("Identity: Administrator" );
		System.out.println("Options:" );
		System.out.println("1. Browse Items(Update/Remove)(empty)" );
		System.out.println("2. Add Item(empty)" );
		System.out.println("3. Add administrator(empty)" );
		System.out.println("4. Update Personal Information(empty)" );
	}
	
	//customer menu
	public void view_menu_customer(String username) {
		System.out.println("Welcome to Online Market place, " + username);
		System.out.println("Identity: Customer" );
		System.out.println("Options:" );
		System.out.println("1. Browse Items" );
		System.out.println("2. Cart(empty)" );
		System.out.println("3. View Order(empty)" );
		System.out.println("4. Update Personal Information(empty)" );
	}
	
	//customer browse items 
	public void view_browse_customer(String[] items) {
		System.out.println("You selected Browse Items");
		int index = 0;
		for (int i=0; i< items.length; i++){
			index = i+1;
			System.out.println(index + ": " + items[i]);
		}
	}
	
	public void view_cart_customer(String[] cart) {
		
	}
	
}
















