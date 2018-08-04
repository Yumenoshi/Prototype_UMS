package validation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements Validator {

	// Supported phone number formats:
	// +48123456789
	// 12345678
	// +48 123 456 789
	// 123 456 789

	private static final String phoneRegex = "(\\+\\d{2})?(\\d{3}){3}|(\\+\\d{2} )?(\\d{3} ){2}(\\d{3})";
	private static Pattern pattern = Pattern.compile(phoneRegex, Pattern.CASE_INSENSITIVE);;
	private Matcher matcher;

	public boolean validate(String validetable) {
		matcher = pattern.matcher(validetable);
		return matcher.matches();
	}
}
