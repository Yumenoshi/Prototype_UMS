package validation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Validator{

	// Supported email format:
	// foo@bar.quux

	private static final String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	private static Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);;
	private Matcher matcher;

	public boolean validate(String validetable) {
		matcher = pattern.matcher(validetable);
		return matcher.matches();
	}
}
