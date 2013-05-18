package serializator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public enum Formatters {
	THREE_PRECISION("#.###"), TWO_PRECISION_ZEROS("0.00"), ZERO_PRECISION("#");

	private final DecimalFormat format;

	private Formatters(String formatString) {
		format = new DecimalFormat(formatString);
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
	}
	
	public String format(double value){
		return format.format(value);
	}
}
