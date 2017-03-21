/**
 * Credential
 *  -an object to hold username and password
 *  
 *  @author Zhihao Cao
 */

import java.io.Serializable;

public class Credential implements Serializable {
	private static final long serialVersionUID = -8324897573374213819L;
	
	private String uname;
	private char[] pwd;
	
	public Credential() {
		uname = null;
		pwd = null;
	}
	
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	public void setPwd(char[] pwd) {
		this.pwd =  pwd;
	}	
	
	public String getUname() {
		return uname;
	}
	
	public char[] getPwd() {
		return pwd;
	}	
}
