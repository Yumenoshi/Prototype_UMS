package validation;
import securityAndDatabase.DataBaseHelper;

public class UserNameValidator implements Validator{
	// Supported user name: min 3 signs, unique name
	private DataBaseHelper helper=null;
	public boolean validate(String userName) {
		if (userName.length() < 3)
			return false;

		return helper.checkIfUserNameFree(userName);

	}
	public void setDataBaseHelper(DataBaseHelper helper) {
		this.helper=helper;
	}
}
