package tests;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

import validation.EmailValidator;

class TestEmailValidator {

	@Test
	void testValidate() {
		EmailValidator validator = new EmailValidator();
		
		Assert.assertEquals(true, validator.validate("foo@bar.quux"));
		
		Assert.assertEquals(false, validator.validate("foo.@bar.quux"));
		
		Assert.assertEquals(false, validator.validate("foo.bar.quux"));
		
		Assert.assertEquals(false, validator.validate("foo@barquux"));
		
		Assert.assertEquals(false, validator.validate("foo@.quux"));
		
		Assert.assertEquals(false, validator.validate("aaaaa"));
	}

}
