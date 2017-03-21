/**
 * Exception handler for the use of annotation pattern
 *
 *@author Zhihao Cao
 */

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 5528415690278423524L;

	public AuthorizationException(String methodName) {
		super("Invalid Authorization - Access Denied to " 
				+ methodName + "() function!");
	}
}