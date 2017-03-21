// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command view order
 * 
 * @author Zhihao Cao
 */

public class Command_viewOrder implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_viewOrder(MVC_model_interface myModel){
		this.myModel = myModel;
	}

	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.viewOrder(session);
		} catch (Exception e) {
			System.out.println("Command_viewOrder: Model calls Exception: " + e.getMessage());
		}
	}
}