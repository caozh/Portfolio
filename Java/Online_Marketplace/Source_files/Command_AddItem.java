// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command add item
 * 
 * @author Zhihao Cao
 */

public class Command_AddItem implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_AddItem(MVC_model_interface myModel){
		this.myModel = myModel;
	}

	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.addItem(session);
		} catch (Exception e) {
			System.out.println("Command_AddItem: Model calls Exception: " + e.getMessage());
		}
	}
}