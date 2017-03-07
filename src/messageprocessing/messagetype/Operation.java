package messageprocessing.messagetype;

import java.io.Serializable;
import java.math.BigDecimal;

public enum Operation implements Serializable{
	ADD, SUBTRACT, MULTIPLY;
	
	public BigDecimal calculate(BigDecimal x, BigDecimal y) {
        switch (this) {
            case ADD:
                return x.add(y);
            case SUBTRACT:
                return x.subtract(y);
            case MULTIPLY:
                return x.multiply(y);
            default:
                throw new AssertionError("Unknown operations " + this);
        }
    }
}
