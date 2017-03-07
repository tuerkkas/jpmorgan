package messageprocessing.messagetype;

import java.io.Serializable;


public abstract class MessageType implements Serializable {
	
	private static final long serialVersionUID = -8818794426841101109L;

	private String field;
	private double value;
	
	public MessageType(String name, double value){
		this.field = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return "name=" + field + ", value=" + value;
	}

	
	public String getName() {
		return field;
	}

	public double getValue() {
		return value;
	}
}
