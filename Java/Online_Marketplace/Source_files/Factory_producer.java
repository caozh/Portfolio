// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Factory producer
 *  -to produce a sub-factory
 *  
 * @author Zhihao Cao
 */

public class Factory_producer {
	public static Factory_abstract getFactory(String choice) {
		
		if(choice.equalsIgnoreCase("ADMIN")){
			return new Factory_admin();
		}
		else if(choice.equalsIgnoreCase("CONSUMER")){
	        return new Factory_customer();
		}
		return null;
	}
}
