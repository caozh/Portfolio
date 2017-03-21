/**
 * Online Market Server
 * 
 * 1. create a proxy for MVC model object
 * 2. bind the proxy object to RMI Lookup
 * 
 * @author Zhihao Cao
 */

import java.rmi.Naming;
import java.lang.reflect.Proxy;

public class MarketServer {

	public static void main(String[] args) {
		// Java RMI Security Manager
		System.setSecurityManager(new SecurityManager());
		try{
			System.out.println("Creating a Online Market Server!");
			String name = "//tesla.cs.iupui.edu:1888/MarketServer";
			
			// Create a proxy and put model object into it
			MVC_model_interface myProxy = (MVC_model_interface) Proxy.newProxyInstance(MVC_model_interface.class.getClassLoader(),
	                new Class<?>[] {MVC_model_interface.class},
	                new AuthorizationInvocationHandler(new MVC_model()));
								
			// Binding of the newly created Bank server instance to the RMI Lookup.
			System.out.println("Marketplace Server: binding it to name: " + name);	
			Naming.rebind(name, myProxy);
			System.out.println("Marketplace Server Ready!");
			
		} catch (Exception e){
			System.out.println("Server RMI Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
