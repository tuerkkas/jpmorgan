package messageprocessing.messagetype;



public class MessageType3 extends MessageType {

	
	private static final long serialVersionUID = 3024017294107896054L;
	
	private Operation operation;
	
	public MessageType3(String name, double value, Operation operation) {
		super(name, value);
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "with a value of: " + this.getValue() + " and a operation: " + operation;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
}
