package internationamarket.instructions;

import java.io.Serializable;
import java.math.BigDecimal;

public class Amount implements Serializable{

	private static final long serialVersionUID = -2142872186649593981L;

	private final String entity;

	private final BigDecimal amount;

	
	
	public Amount(String entity, BigDecimal amount) {
		super();
		this.entity = entity;
		this.amount = amount;
	}

	public String getEntity() {
		return entity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

}
