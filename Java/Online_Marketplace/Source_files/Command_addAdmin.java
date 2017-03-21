// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command add admin
 * 
 * @author Zhihao Cao
 */

public class Command_addAdmin implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_addAdmin(MVC_model_interface myModel){
		this.myModel = myModel;
	}


	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.addAdmin(session);
		} catch (Exception e) {
			System.out.println("Command_addAdmin: Model calls Exception: " + e.getMessage());
		}
	}
}