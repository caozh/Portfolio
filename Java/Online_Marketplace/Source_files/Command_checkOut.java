// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Concrete command check out
 * 
 * @author Zhihao Cao
 */

public class Command_checkOut implements Interface_command{
	private MVC_model_interface myModel;

	/**
	 * Constructor
	 * 
	 * @param consumer1
	 */
	public Command_checkOut(MVC_model_interface myModel){
		this.myModel = myModel;
	}

	/**
	 * Execute Command Method
	 */
	public void execute(Session	session) {
		try {
			myModel.checkOut(session);
		} catch (Exception e) {
			System.out.println("Command_checkOut: Model calls Exception: " + e.getMessage());
		}
	}
}