// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command admin update information
 * 
 * @author Zhihao Cao
 */
// FIX: next line
/**
 * Concrete command admin update info
 * 
 * @author Zhihao Cao
 */

public class Command_updateInfo_admin implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_updateInfo_admin(MVC_model_interface myModel){
		this.myModel = myModel;
	}


	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.updateInfoAdmin(session);
		} catch (Exception e) {
			System.out.println("Command_updateInfo_admin: Model calls Exception: " + e.getMessage());
		}
	}
}