// Ryan: What is the purpose of this class?
// FIX: next line
/**
 * User object 
 * 	-to realize the RBAC
 * 
 * @author Zhihao Cao
 */

import java.io.Serializable;

public class Obj_User implements Serializable {	
	private static final long serialVersionUID = 8084523177681775893L;
	private String roleType;
	
	//constructor
	public Obj_User(String roleType) {
		this.roleType = roleType;
	}
	
	//return role type of user
	public String getRoleType() {
		return roleType;
	}
	
}

