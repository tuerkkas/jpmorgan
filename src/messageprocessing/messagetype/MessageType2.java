package messageprocessing.messagetype;


public class MessageType2 extends MessageType {

	private static final long serialVersionUID = 4456742348010507211L;
	private int ocurrencies;
	
	public MessageType2(String name, double value, int ocurrencies) {
		super(name, value);
		this.ocurrencies = ocurrencies;
	}

	@Override
	public String toString() {
		return "MessageType2 ["+ super.toString() + ", ocurrencies=" + ocurrencies + "]";
	}

	public int getOcurrencies() {
		return ocurrencies;
	}

	public void setOcurrencies(int ocurrencies) {
		this.ocurrencies = ocurrencies;
	}

	
}
