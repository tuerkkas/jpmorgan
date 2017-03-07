package messageprocessing.messagetype;


public class MessageType1 extends MessageType {

	private static final long serialVersionUID = 8577263030920731859L;

	public MessageType1(String name, double value) {
		super(name, value);
	}

	@Override
	public String toString() {
		return "MessageType1 [" + super.toString() + "]";
	}

	
	
}
