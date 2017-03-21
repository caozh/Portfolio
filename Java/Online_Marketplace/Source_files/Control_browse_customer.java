// Ryan: Please include usefull comments in each file.
// FIX: next line
/**
 * Customer browse controller
 * 
 * execute()
 * 
 * create a customer object and 
 * create a command object having customer object as receiver
 * 
 * @author Zhihao Cao
 *
 */

public class Control_browse_customer {
	private View_browse_customer view_browse_consumer;
	private View_menu view_menu;
	
	public Control_browse_customer (){
		view_browse_consumer = new View_browse_customer();
		view_menu = new View_menu();
	}
	
	public void execute(Session session) {
		
		System.out.println("Control_browse_customer: executed.");
		
		//create a customer object
	    //Factory_abstract consumerFactory = Factory_producer.getFactory("CONSUMER");
		//Interface_user user = consumerFactory.getConsumer();
		System.out.println("Control_browse_customer: Factory produce a user object.");
		
	    
	    //dispatch the result to view
	    view_browse_consumer.showView("item object array");
	    System.out.println("====================");
	    //show menu
	    view_menu.showView(session.getUser().getRoleType());
	}
}
