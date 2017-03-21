// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command remove item
 * 
 * @author Zhihao Cao
 */

public class Command_RemoveItem implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_RemoveItem(MVC_model_interface myModel){
		this.myModel = myModel;
	}

	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.removeItem(session);
		} catch (Exception e) {
			System.out.println("Command_RemoveItem: Model calls Exception: " + e.getMessage());
		}
	}
}