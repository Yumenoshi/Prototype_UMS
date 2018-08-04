package validation;
public class PasswordValidator implements Validator {

	private static final String specialSigns = "!@#$%&*()_+-=[]|,./?><";

	public boolean validate(String validetable) {
		if (validetable.length() < 8)
			return false;

		int bigLettersCount = 0;
		int numbersCount = 0;
		int digitsCount = 0;

		for (int i = 0; i < validetable.length(); i++) {
			char sign = validetable.charAt(i);

			// I use values from ASCII table to compare password signs

			if (sign >= 'A' && sign <= 'Z') {
				bigLettersCount++;
			}

			if (sign >= '0' && sign <= '9') {
				numbersCount++;
			}

			// I use searching in String specialSigns, because using ASCII table for special
			// signs would require a lot of comparisons

			if (specialSigns.indexOf(sign) != -1) {
				digitsCount++;
			}

		}
		if (bigLettersCount >= 1 && numbersCount >= 1 && digitsCount >= 1)
			return true;

		return false;
	}
}
