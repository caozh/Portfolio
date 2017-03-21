/**
 * Invocation handler for proxy
 * 	-It enables the use of annotation
 * 
 * invoke()
 * 
 * @author Zhihao Cao
 */

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AuthorizationInvocationHandler implements InvocationHandler, Serializable {
	private static final long serialVersionUID = 6925780928377938176L;
	private Object objectImpl;
 
	public AuthorizationInvocationHandler(Object impl) {
	   this.objectImpl = impl;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//check if the target method require role
		if (method.isAnnotationPresent(RequiresRole.class)) {
			RequiresRole accessControl = method.getAnnotation(RequiresRole.class);
			Session session = (Session) args[0];
			//check session role type with Access control
			System.out.println("annotation: " + accessControl.toString());
			System.out.println("session: " + session.getUser().getRoleType());
			if (session.getUser().getRoleType().equals(accessControl.value())) {
				 return method.invoke(objectImpl, args);
            } else {
            	throw new AuthorizationException(method.getName());
            }
		} else {
			System.out.println("AuthorizationInvocationHandler: no annotation");
			return method.invoke(objectImpl, args);
		}   
	}
}