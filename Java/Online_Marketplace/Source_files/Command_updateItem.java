// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command update item
 * 
 * @author Zhihao Cao
 */

public class Command_updateItem implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_updateItem(MVC_model_interface myModel){
		this.myModel = myModel;
	}

	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.updateItem(session);
		} catch (Exception e) {
			System.out.println("Command_updateItem: Model calls Exception: " + e.getMessage());
		}
	}
}