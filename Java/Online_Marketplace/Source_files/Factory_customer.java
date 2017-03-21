// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Customer factory
 *  - -return a concrete customer object
 * 
 * @author Zhihao Cao
 */

public class Factory_customer extends Factory_abstract {

	@Override
	Interface_user getAdmin() {
		return null;
	}

	@Override
	Interface_user getConsumer() {
		return new Factory_concrete_customer();
	}

}
