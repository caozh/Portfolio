// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Admin factory
 *  -return a concrete admin object
 * @author Zhihao Cao
 */

public class Factory_admin extends Factory_abstract{

	@Override
	Interface_user getAdmin() {
		return new Factory_concrete_admin();
	}

	@Override
	Interface_user getConsumer() {
		return null;
	}

}
