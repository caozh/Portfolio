// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command for admin browse
 * 
 * @author Zhihao Cao
 */
// FIX: next line
/**
 * Concrete command admin browse
 * @author Zhihao Cao
 */

public class Command_browse_admin implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_browse_admin(MVC_model_interface myModel){
		this.myModel = myModel;
	}

	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.browseAdmin(session);
		} catch (Exception e) {
			System.out.println("Command_browse_admin: Model calls Exception: " + e.getMessage());
		}
	}
}