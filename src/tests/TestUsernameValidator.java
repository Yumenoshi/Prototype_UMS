package tests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import outputInput.ConsoleOutput;
import outputInput.Output;

import securityAndDatabase.DataBaseHelper;
import validation.UserNameValidator;

class TestUsernameValidator {
	//Test requires creation of database and example user
	//BE CAREFULL!, as it can potentialy make damage to real database
	
	@Test
	void testValidate() {
		UserNameValidator validator = new UserNameValidator();
		Output output = new ConsoleOutput();
		DataBaseHelper helper=new DataBaseHelper(output);
		validator.setDataBaseHelper(helper);
		String[] saltAndHash =helper.sHAController.generateHashAndSalt("ABC123$%^");
		
		helper.insertUser("foo", saltAndHash[0], saltAndHash[1], "ABC@ABC.ABC", "123456789");
		
		Assert.assertEquals(false, validator.validate("foo"));
		
		Assert.assertEquals(false, validator.validate("fo"));
		
		Assert.assertEquals(true, validator.validate("foobar"));
		
		Assert.assertEquals(true, validator.validate("foobarquux"));
		
	}



}
