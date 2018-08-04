package tests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import securityAndDatabase.PasswordGenerator;
import validation.PasswordValidator;

class TestPasswordGenerator {

	@Test
	void testGeneratePassword() {
		PasswordValidator validator= new PasswordValidator();
		PasswordGenerator generator= new PasswordGenerator(validator);
		
		Assert.assertEquals(true, validator.validate(generator.generatePassword(10)));
	
		Assert.assertEquals(true, validator.validate(generator.generatePassword(11)));
	
		Assert.assertEquals(true, validator.validate(generator.generatePassword(9)));
	
		Assert.assertEquals(true, validator.validate(generator.generatePassword(8)));
	
		Assert.assertEquals(false, validator.validate(generator.generatePassword(1)));
	
		Assert.assertEquals(false, validator.validate(generator.generatePassword(7)));
	
	}

}
