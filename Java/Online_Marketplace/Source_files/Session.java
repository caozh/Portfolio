/**
 * Session object 
 *  -contain a user with a specific role
 *  
 *  @author Zhihao Cao
 */

import java.io.Serializable;

public class Session implements Serializable {
	private static final long serialVersionUID = -6745473220581903527L;
	
	private Obj_User user;
	private boolean isAuth;
	
	//constructor
	public Session(String userType) {
		user = new Obj_User(userType);
		isAuth = true;
	}
	
	//return user object
	public Obj_User getUser() {
		return user;
	}
	
	public void setAuth(boolean input) {
		isAuth = input;
	}
	
	public boolean isAuthenticated() {
		return isAuth;
	}
}