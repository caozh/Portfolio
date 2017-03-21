// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command customer browse
 * 
 * @author Zhihao Cao
 */

public class Command_browse_customer implements Interface_command{
	private MVC_model_interface myModel;

	//Constructor
	public Command_browse_customer(MVC_model_interface myModel){
		this.myModel = myModel;
	}

	//Execute Command Method
	public void execute(Session session) {
		try {
			myModel.browse(session);
		} catch (Exception e) {
			System.out.println("Command_browse_customer: Model calls Exception: " + e.getMessage());
		}
	}
}