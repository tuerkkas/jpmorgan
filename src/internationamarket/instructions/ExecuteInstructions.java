package internationamarket.instructions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ExecuteInstructions {

	private static final boolean VALID_DATES = true;
	private static final boolean INVALID_DATES = false;

	/**
	 * List Instructions received for insert in our system.
	 */
	private static List<Instruction> instructionListToProcess = new ArrayList<Instruction>();
	private static List<Instruction> instructionListSettleToday = new ArrayList<Instruction>();
	private static List<Instruction> instructionListSettleFuture = new ArrayList<Instruction>();

	private static List<Amount> rankingAmountEntitiesIncoming = new ArrayList<Amount>();
	private static List<Amount> rankingAmountEntitiesOutcoming = new ArrayList<Amount>();

	public static void processInstructions(
			List<HashMap<String, String>> instructionsReceived) {
		generateInstructionsToProcess(instructionsReceived);

		// filterInstructionsToProcessToday();

		instructionListSettleToday = filterInstructions(VALID_DATES);
		instructionListSettleFuture = filterInstructions(INVALID_DATES);

		updateInstructions();

		calculateInUsdSettled(Constants.SELL, rankingAmountEntitiesIncoming);

		calculateInUsdSettled(Constants.BUY, rankingAmountEntitiesOutcoming);

		generateReports();

	}

	/**
	 * 
	 * @param instructionsReceived
	 */
	private static void generateInstructionsToProcess(
			List<HashMap<String, String>> instructionsReceived) {
		// transform to Object the new Instructions to process.

		for (HashMap<String, String> data : instructionsReceived) {
			Instruction instruction = new Instruction(
					data.get(Constants.ENTITY),
					data.get(Constants.BUY_SELL),
					new BigDecimal(data.get(Constants.AGREED_FX)),
					data.get(Constants.CURRENCY),
					DateUtils.generateDate(data.get(Constants.INSTRUCTION_DATE)),
					DateUtils.generateDate(data.get(Constants.SETTLEMENT_DATE)),
					new BigDecimal(data.get(Constants.UNITS)), new BigDecimal(
							data.get(Constants.PRICE_PER_UNIT)));

			instructionListToProcess.add(instruction);
		}

	}

	/**
	 * Get Instructions will be to settled today.
	 */

	/**
	 * Get Instructions in order to validDate:
	 * 
	 * if valid Date is true ->Get Instructions which it will be to settled
	 * today. If valid Date is false ->Get Instructions which it will be to
	 * settled in a future day.
	 * 
	 * @return List Instructions in order to validDate.
	 */
	private static List<Instruction> filterInstructions(boolean validDate) {
		LocalDate today = LocalDate.now();

		List<Instruction> filterInstructions = instructionListToProcess
				.stream()
				.filter((instruction -> today.compareTo(instruction
						.getSettlementDate()) == 0
						&& DateUtils.isWorkWeekDay(
								instruction.getSettlementDate(),
								instruction.getCurrency()) == validDate))
				.collect(Collectors.toList());

		return filterInstructions;
	}

	/**
	 * Update SettlementDate for any Instruction which a wrong SettlementDate to
	 * the next work week day.
	 */
	private static void updateInstructions() {

		instructionListSettleFuture.forEach(strInstruction -> {
			strInstruction.setSettlementDate(DateUtils.getNextWorkWeekDay(
					strInstruction.getSettlementDate(),
					strInstruction.getCurrency()));
		});
	}

	private static void calculateInUsdSettled(String typeSetted,
			List<Amount> amountList) {

		// BigDecimal amount = new BigDecimal(0);

		List<Instruction> filterInstructions = instructionListSettleToday
				.stream()
				.filter(instruction -> instruction.getBuySell().equals(
						typeSetted)).collect(Collectors.toList());

		createAmountList(filterInstructions, amountList);

	}

	private static void createAmountList(List<Instruction> filterInstructions,
			List<Amount> instructionList) {

		Map<String, List<Instruction>> instructionByEntity = filterInstructions
				.stream()
				.collect(Collectors.groupingBy(Instruction::getEntity));

		List<String> types = instructionByEntity.entrySet().stream()
				.sorted(Comparator.comparing(Map.Entry::getKey))
				.map(Map.Entry::getKey).collect(Collectors.toList());

		for (String type : types) {
			BigDecimal amountValue = new BigDecimal(0);

			List<Instruction> ins = instructionByEntity.get(type);

			for (Instruction in : ins) {
				BigDecimal value = (in.getPricePerUnit()
						.multiply(in.getUnits()).multiply(in.getAgreedFx()));
				amountValue = amountValue.add(value);
			}
			instructionList.add(new Amount(type, amountValue));
		}

	}

	private static void generateReports() {

		// Print Amount USD settled Incoming Everyday
		printAmountUsd();

		printRankingEntities();
	}

	private static void printAmountUsd() {

		System.out.println("Amount USD incoming0"
				+ rankingAmountEntitiesIncoming.stream()
						.map(f -> f.getAmount())
						.reduce(BigDecimal.ZERO, BigDecimal::add));
		System.out.println("Amount USD outcoming"
				+ rankingAmountEntitiesOutcoming.stream()
						.map(f -> f.getAmount())
						.reduce(BigDecimal.ZERO, BigDecimal::add));

	}

	private static void printRankingEntities() {

		
		System.out.println("Ranking of entities based on Outcoming:");
		rankingAmountEntitiesOutcoming
				.stream()
				.sorted(Comparator.comparing(Amount::getAmount).reversed())
				.collect(Collectors.toList())
				.forEach(
						e -> System.out.println("Entity:" + e.getEntity()
								+ ", Value: " + e.getAmount()));

		System.out.println("Ranking of entities based on Incoming:");
		rankingAmountEntitiesIncoming
				.stream()
				.sorted(Comparator.comparing(Amount::getAmount).reversed())
				.collect(Collectors.toList())
				.forEach(
						e -> System.out.println("Entity:" + e.getEntity()
								+ ", Value: " + e.getAmount()));
		;
	}
}
