package tests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import validation.PasswordValidator;

class TestPasswordValidator {

	@Test
	void testValidate() {
		PasswordValidator validator = new PasswordValidator();
		
		Assert.assertEquals(true, validator.validate("ABC123$%^"));

		Assert.assertEquals(true, validator.validate("A1@abcde"));
		
		Assert.assertEquals(true, validator.validate("ABC123$%^123456789"));

		Assert.assertEquals(false, validator.validate("abcdefghi"));
		
		Assert.assertEquals(false, validator.validate("abc def ghi"));
		
		Assert.assertEquals(false, validator.validate("12345679"));
		
		Assert.assertEquals(false, validator.validate("superman"));
		
	}

}
