package tests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import validation.PhoneValidator;

class TestPhoneValidator {

	@Test
	void testValidate() {
		PhoneValidator validator = new PhoneValidator();
		
		Assert.assertEquals(true, validator.validate("+48123456789"));

		Assert.assertEquals(true, validator.validate("+48 123 456 789"));

		Assert.assertEquals(true, validator.validate("123456789"));

		Assert.assertEquals(true, validator.validate("123 456 789"));

		Assert.assertEquals(false, validator.validate("123-456-789"));

		Assert.assertEquals(false, validator.validate("aaaaa"));

		Assert.assertEquals(false, validator.validate("+12-123-456-789"));

		Assert.assertEquals(false, validator.validate("abcdefghi"));

		Assert.assertEquals(false, validator.validate("abc def ghi"));
	}

}
