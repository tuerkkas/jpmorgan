package internationamarket;

import internationamarket.instructions.Constants;
import internationamarket.instructions.ExecuteInstructions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {

	/**
	 * Change the value of this constants for make test. If you need another day
	 * you have to change it by yourself.
	 */
	private static final String INSTRUCTION_DATE = "03 Mar 2017";
	private static final String SETTLEMENT_DATE = "07 Mar 2017";

	private static List<HashMap<String, String>> instructionsReceived;

	public static void main(String[] args) throws Exception {

		instructionsReceived = new ArrayList<HashMap<String, String>>();
		generateInstructionsReceived();
		ExecuteInstructions.processInstructions(instructionsReceived);

	}

	private static void generateInstructionsReceived() {
		HashMap<String, String> instruction = new HashMap<String, String>();

		instruction = new HashMap<String, String>();
		instruction.put(Constants.ENTITY, "foo");
		instruction.put(Constants.BUY_SELL, Constants.SELL);
		instruction.put(Constants.AGREED_FX, "0.50");
		instruction.put(Constants.CURRENCY, "EUR");
		instruction.put(Constants.INSTRUCTION_DATE, INSTRUCTION_DATE);
		instruction.put(Constants.SETTLEMENT_DATE, SETTLEMENT_DATE);
		instruction.put(Constants.UNITS, "200");
		instruction.put(Constants.PRICE_PER_UNIT, "100.25");

		instructionsReceived.add(instruction);

		instruction = new HashMap<String, String>();
		instruction.put(Constants.ENTITY, "Faa");
		instruction.put(Constants.BUY_SELL, Constants.SELL);
		instruction.put(Constants.AGREED_FX, "0.80");
		instruction.put(Constants.CURRENCY, "EUR");
		instruction.put(Constants.INSTRUCTION_DATE, INSTRUCTION_DATE);
		instruction.put(Constants.SETTLEMENT_DATE, SETTLEMENT_DATE);
		instruction.put(Constants.UNITS, "200");
		instruction.put(Constants.PRICE_PER_UNIT, "100.25");

		instructionsReceived.add(instruction);

		instruction = new HashMap<String, String>();
		instruction.put(Constants.ENTITY, "Faa");
		instruction.put(Constants.BUY_SELL, Constants.SELL);
		instruction.put(Constants.AGREED_FX, "0.85");
		instruction.put(Constants.CURRENCY, "EUR");
		instruction.put(Constants.INSTRUCTION_DATE, INSTRUCTION_DATE);
		instruction.put(Constants.SETTLEMENT_DATE, SETTLEMENT_DATE);
		instruction.put(Constants.UNITS, "200");
		instruction.put(Constants.PRICE_PER_UNIT, "100.25");

		instructionsReceived.add(instruction);

		instruction = new HashMap<String, String>();
		instruction.put(Constants.ENTITY, "Fai");
		instruction.put(Constants.BUY_SELL, Constants.SELL);
		instruction.put(Constants.AGREED_FX, "0.85");
		instruction.put(Constants.CURRENCY, "EUR");
		instruction.put(Constants.INSTRUCTION_DATE, INSTRUCTION_DATE);
		instruction.put(Constants.SETTLEMENT_DATE, SETTLEMENT_DATE);
		instruction.put(Constants.UNITS, "200");
		instruction.put(Constants.PRICE_PER_UNIT, "100.25");

		instructionsReceived.add(instruction);

		instruction = new HashMap<String, String>();
		instruction.put(Constants.ENTITY, "bor");
		instruction.put(Constants.BUY_SELL, Constants.BUY);
		instruction.put(Constants.AGREED_FX, "0.22");
		instruction.put(Constants.CURRENCY, "AED");
		instruction.put(Constants.INSTRUCTION_DATE, INSTRUCTION_DATE);
		instruction.put(Constants.SETTLEMENT_DATE, SETTLEMENT_DATE);
		instruction.put(Constants.UNITS, "450");
		instruction.put(Constants.PRICE_PER_UNIT, "150.2");

		instructionsReceived.add(instruction);
		instructionsReceived.add(instruction);

		instruction = new HashMap<String, String>();
		instruction.put(Constants.ENTITY, "bar");
		instruction.put(Constants.BUY_SELL, Constants.BUY);
		instruction.put(Constants.AGREED_FX, "0.22");
		instruction.put(Constants.CURRENCY, "AED");
		instruction.put(Constants.INSTRUCTION_DATE, INSTRUCTION_DATE);
		instruction.put(Constants.SETTLEMENT_DATE, SETTLEMENT_DATE);
		instruction.put(Constants.UNITS, "450");
		instruction.put(Constants.PRICE_PER_UNIT, "200");

		instructionsReceived.add(instruction);

	}

	/**
	 * generate instructions to be in memory for the process. I assume that this
	 * instructions were received in our system thougth an message processing
	 * program.
	 */

}
