// Ryan: Should this really be a Command? Does this tell us "what" to do?
// FIX: I change the class name to load cart.

// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command load cart
 * 
 * @author Zhihao Cao
 */

public class Command_loadCart implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_loadCart(MVC_model_interface myModel){
		this.myModel = myModel;
	}

	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.loadCart(session);
		} catch (Exception e) {
			System.out.println("Command_loadCart: Model calls Exception: " + e.getMessage());
		}
	}
}