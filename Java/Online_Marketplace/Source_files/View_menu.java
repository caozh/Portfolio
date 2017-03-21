// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Admin menu view
 * 
 * @author Zhihao Cao
 */

public class View_menu {
	public void showView(String role) {
		
    	System.out.println("Welcome to Online Market place, "  );// + username);
		System.out.println("Identity: " + role );
		System.out.println("Options:" );
		
		//show menu based on role type
		if (role.equalsIgnoreCase("admin")){
		System.out.println("1. Browse Items(Update/Remove) (browseAdmin)" );
		System.out.println("2. Add Item (empty)" );
		System.out.println("3. Remove Item (empty)" );
		System.out.println("4. Update Item (empty)" );
		System.out.println("5. Add administrator (addAdmin)" );
		System.out.println("6. Update Personal Information (empty)" );
		} 
		else if (role.equalsIgnoreCase("customer")){
		System.out.println("1. Browse Items (browseCustomer)" );
		System.out.println("2. Shopping Cart (empty)" );
		System.out.println("3. View Order (empty)" );
		System.out.println("4. Update Personal Information (empty)" );
		}
		else {
			System.out.println("menu error: unknown user role" );
		}
	}
}
