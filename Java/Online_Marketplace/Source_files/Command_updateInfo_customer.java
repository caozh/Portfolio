// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command customer update information
 * 
 * @author Zhihao Cao
 */

public class Command_updateInfo_customer implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_updateInfo_customer(MVC_model_interface myModel){
		this.myModel = myModel;
	}

	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.updateInfo(session);
		} catch (Exception e) {
			System.out.println("Command_updateInfo_customer: Model calls Exception: " + e.getMessage());
		}
	}
}