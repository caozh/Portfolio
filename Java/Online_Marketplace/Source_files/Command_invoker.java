// Ryan: Please include usefull comments in each file.
/**
 * Invoker 
 * 
 * @author Zhihao Cao
 */

import java.util.ArrayList;
import java.util.List;

public class Command_invoker {
	private List<Interface_command> commandList = new ArrayList<Interface_command>(); 
	/**
	 * Given an command request take the command.
	 * 
	 * @param command
	 */
	public void takeCommand(Interface_command command) {
		commandList.add(command);		
    }
	
	/**
	 * Execute each command that has been taken.
	 */
	public void executeCommands(Session session) {
		for (Interface_command commnad : commandList) {
			commnad.execute(session);
		}
      
		commandList.clear();
	}
	
}
