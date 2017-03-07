package messageprocessing.sales;

import java.math.BigDecimal;

public class ProductType {

	private String field;
	private BigDecimal value;

	public ProductType(String field, BigDecimal value) {
		super();
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
