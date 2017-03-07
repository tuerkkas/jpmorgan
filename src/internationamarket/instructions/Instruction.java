package internationamarket.instructions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Instruction implements Serializable {

	private static final long serialVersionUID = -6616660424737651866L;

	private final String entity;

	private final String buySell;

	private final BigDecimal agreedFx;

	private final String currency;

	private final LocalDate instructionDate;

	private LocalDate settlementDate;

	private final BigDecimal units;

	private final BigDecimal pricePerUnit;

	public Instruction(String entity, String buySell, BigDecimal agreedFx,
			String currency, LocalDate instructionDate,
			LocalDate settlementDate, BigDecimal units, BigDecimal pricePerUnit) {
		super();
		this.entity = entity;
		this.buySell = buySell;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
	}

	public String getEntity() {
		return entity;
	}

	public String getBuySell() {
		return buySell;
	}

	public BigDecimal getAgreedFx() {
		return agreedFx;
	}

	public String getCurrency() {
		return currency;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

}
