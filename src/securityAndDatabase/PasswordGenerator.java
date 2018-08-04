package securityAndDatabase;
import java.security.SecureRandom;

import validation.Validator;

public class PasswordGenerator {
	private static final String bigLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String smallLetters = "abcdefghijklmnopqrstuvwxyz";
	private static final String numbers = "0123456789";
	private static final String specialSigns = "!@#$%&*()_+-=[]|,./?><";

	private static SecureRandom random = new SecureRandom();

	private Validator validator;

	public PasswordGenerator(Validator passwordValidator) {
		this.validator = passwordValidator;
	}

	public String generatePassword(int len) {

		String dictionary = bigLetters + smallLetters + numbers + specialSigns;
		String result;

		// Repeat until get valid password
		do {
			result = "";
			for (int i = 0; i < len; i++) {
				int index = random.nextInt(dictionary.length());
				result += dictionary.charAt(index);
			}
		} while (!(validator.validate(result)));

		return result;
	}
}
