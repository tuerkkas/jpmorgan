package internationamarket.instructions;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public final class DateUtils {
	
	
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy",
			Locale.ENGLISH);
	
	/**
	 * Method which verify and return a valid Work Week Day in function of the currency.
	 * @param localDate
	 * @param currency
	 * @return
	 */
	public static LocalDate getWorkWeekDayValid(LocalDate localDate,
			String currency) {
		if (!isWorkWeekDay(localDate, currency)) {
			localDate = getNextWorkWeekDay(localDate, currency);
		}
		return localDate;
	}

	/**
	 * Method which verify if a Date is a work week or not for different
	 * Currencies: For currency AED or SAR the work week is in a range (Sunday
	 * to Thursday. For the rest of currencies the work wee is in a range
	 * (Monday to Friday).
	 * 
	 * @param d
	 *            Date to analyze
	 * @param currency
	 *            Current currency
	 * @return
	 */
	public static boolean isWorkWeekDay(LocalDate d, String currency) {
		boolean result = true;
		if (currency.equals(Constants.AED_CURRENCY) || currency.equals(Constants.SAR_CURRENCY)) {
			if ((DayOfWeek.FRIDAY == d.getDayOfWeek())
					|| (DayOfWeek.SATURDAY == d.getDayOfWeek())) {
				result = false;
			}
		} else {
			if ((DayOfWeek.SATURDAY == d.getDayOfWeek())
					|| (DayOfWeek.SUNDAY == d.getDayOfWeek())) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * Method which return the next Work Week Day valid in case of the LocalDate
	 * is not a valid Work Week Day. If the currency are (AED or SAR) the first
	 * work week day valid starts on SUNDAY. In other case the first work week
	 * day valid starts on Monday.
	 * 
	 * @param date
	 * @param currency
	 * @return
	 */
	public static LocalDate getNextWorkWeekDay(LocalDate date, String currency) {

		LocalDate nextWorkWeekDay = null;

		if (currency.equals(Constants.AED_CURRENCY) || currency.equals(Constants.SAR_CURRENCY)) {
			nextWorkWeekDay = date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		} else {
			nextWorkWeekDay = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		}

		return nextWorkWeekDay;
	}
	
	public static LocalDate generateDate(String date){
		return LocalDate.parse(date, formatter);
	}


   
	
}
