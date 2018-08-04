import java.sql.SQLException;

import dataModels.User;
import outputInput.ConsoleInput;
import outputInput.ConsoleOutput;
import outputInput.Input;
import outputInput.Output;
import securityAndDatabase.DataBaseHelper;
import securityAndDatabase.PasswordGenerator;
import stringData.Strings;
import stringData.StringsPL;
import validation.EmailValidator;
import validation.PasswordValidator;
import validation.PhoneValidator;
import validation.UserNameValidator;
import validation.Validator;

public class Main {

	public static void main(String[] args) {

		Output output = new ConsoleOutput();
		Input input = new ConsoleInput();
		Strings strings = new StringsPL();

		DataBaseHelper dataBaseHelper = new DataBaseHelper(output);
		User user = null;

		String menuState = "firstMenu";

		// flag for exiting entrie application
		boolean active = true;

		while (active) {

			switch (menuState) {

			case "firstMenu":
				menuState = firstMenu(output, input, strings);
				break;
			case "loginMenu":
				menuState = loginMenu(output, input, dataBaseHelper, strings, user);
				break;
			case "registeringMenu":
				menuState = registeringMenu(output, input, dataBaseHelper, strings, user);
				break;
			case "end":
				active = false;
				break;

			}

		}
	}

	private static String firstMenu(Output output, Input input, Strings strings) {
		while (true) {

			output.print(strings.welcome);
			String response = "";

			response = input.scan();

			switch (response) {
			case "1":
				return "loginMenu";
			case "2":
				return "registeringMenu";
			case "3":
				return "end";
			default:
				output.print(strings.wrong);

			}
		}

	}

	private static String registeringMenu(Output output, Input input, DataBaseHelper dataBaseHelper, Strings strings,
			User user) {
		while (true) {

			Validator nameValidator = new UserNameValidator();
			Validator passwordValidator = new PasswordValidator();
			Validator emailValidator = new EmailValidator();
			Validator phoneValidator = new PhoneValidator();

			// we need to cast nameValidator, so we can use setDataBaseHelper
			UserNameValidator userNameValidator = (UserNameValidator) nameValidator;
			userNameValidator.setDataBaseHelper(dataBaseHelper);

			String userName = "";

			userName = setData(output, input, strings, nameValidator, "UserName");

			output.print(strings.generatePasswordQuestion);
			String password = "";

			String response = "";

			PasswordGenerator generator = new PasswordGenerator(passwordValidator);
			boolean passwordGeneratingMenu = false;
			int howLong = 0;

			response = input.scan();
			if (isInteger(response)) {
				howLong = Integer.parseInt(response);
				if (howLong >= 8) {
					passwordGeneratingMenu = true;
					password = generator.generatePassword(howLong);
				}
			} else {
				password = setData(output, input, strings, passwordValidator, "Password");
			}

			while (passwordGeneratingMenu) {
				output.print(password);
				output.print(strings.generatedPasswordOk);
				response = "";

				response = input.scan();

				switch (response) {
				case "1":
					passwordGeneratingMenu = false;
					break;
				case "2":
					password = generator.generatePassword(howLong);
					break;
				case "3":
					password = setData(output, input, strings, passwordValidator, "Password");
					passwordGeneratingMenu = false;
					break;
				default:
					output.print(strings.wrong);

				}
			}

			String email = setData(output, input, strings, emailValidator, "Email");

			String phoneNumber = setData(output, input, strings, phoneValidator, "Phone");

			String[] hashAndSalt = dataBaseHelper.sHAController.generateHashAndSalt(password);
			dataBaseHelper.insertUser(userName, hashAndSalt[0], hashAndSalt[1], email, phoneNumber);

			output.print(strings.registeredSuccesfully);

			return "firstMenu";
		}

	}

	private static String loginMenu(Output output, Input input, DataBaseHelper dataBaseHelper, Strings strings,
			User user) {
		boolean firstTime = true;

		// login menu
		while (true) {
			if (!firstTime)
				output.print(strings.wrongLoginPassword);

			output.print(strings.login);
			String login = "";

			login = input.scan();

			output.print(strings.password);
			String password = "";

			password = input.scan();

			try {
				user = dataBaseHelper.login(login, password);
				// Invalid user means that user exist but password is invalid
				if (user.getId() != -1) {
					userMenu(output, input, dataBaseHelper, strings, user);
					return "end";
				}
				firstTime = false;
			} catch (SQLException e) {
				// Exceptiom means that user does not exist
				firstTime = false;
			}

		}
	}

	private static int userMenu(Output output, Input input, DataBaseHelper dataBaseHelper, Strings strings, User user) {
		Validator emailValidator = new EmailValidator();
		Validator phoneValidator = new PhoneValidator();
		boolean userMenuFlag = true;

		while (userMenuFlag) {
			output.print(user.getName() + strings.welcomeUser);
			String response = "";

			response = input.scan();

			switch (response) {

			case "1":
				output.print(strings.name + user.getName());
				output.print(strings.email + user.getEmail());
				output.print(strings.phoneNumber + user.getPhoneNumber());
				break;

			case "2":
				String newEmail = setData(output, input, strings, emailValidator, "Email");
				dataBaseHelper.updateEmail(user.getId(), newEmail);
				user.setEmail(newEmail);
				break;

			case "3":
				String newPhone = setData(output, input, strings, phoneValidator, "Phone");
				dataBaseHelper.updatePhoneNumber(user.getId(), newPhone);
				user.setPhoneNumber(newPhone);
				break;

			case "4":
				return 0;

			default:
				output.print(strings.wrong);

			}
		}
		return 0;
	}

	private static String setData(Output output, Input input, Strings strings, Validator validator, String setWhat) {
		// flag, so user doesnt see "wrong input" at first time
		boolean firstTime = true;

		// While until user gives validate password
		while (true) {
			if (!firstTime) {
				switch (setWhat) {

				case "Password":
					output.print(strings.wrongPassword);
					break;

				case "Email":
					output.print(strings.wrongEmail);
					break;

				case "Phone":
					output.print(strings.wrongPhone);
					break;

				case "UserName":
					output.print(strings.wrongUserName);
					break;

				}
			}

			switch (setWhat) {

			case "Password":
				output.print(strings.registrationPassword);
				break;

			case "Email":
				output.print(strings.registrationEmail);
				break;

			case "Phone":
				output.print(strings.registrationPhone);
				break;

			case "UserName":
				output.print(strings.registrationWelcome);
				break;

			}

			String validable = input.scan();

			if (validator.validate(validable)) {

				return validable;

			} else {

				firstTime = false;

			}
		}
	}

	private static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

}
