package tests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import securityAndDatabase.SHAController;

class TestSHAController {
	//Only one method tested, because other methods are integrated within test
	
	
	@Test
	void testComparePasswords() {
		SHAController controller= new SHAController();
		
		String testPassword="ABC123$%^";
		String[] testHashAndSalt=controller.generateHashAndSalt(testPassword);
		
		String[] falseTestHashAndSalt=controller.generateHashAndSalt("foobarquux");
		
		Assert.assertEquals(true, controller.comparePasswords(testPassword, testHashAndSalt[0], testHashAndSalt[1]));
		
		Assert.assertEquals(false, controller.comparePasswords("foobarquux", testHashAndSalt[0], testHashAndSalt[1]));
		
		Assert.assertEquals(false, controller.comparePasswords(testPassword, falseTestHashAndSalt[0], falseTestHashAndSalt[1]));
		
		Assert.assertEquals(false, controller.comparePasswords(testPassword, falseTestHashAndSalt[0], testHashAndSalt[1]));
		
		Assert.assertEquals(false, controller.comparePasswords(testPassword, testHashAndSalt[0], falseTestHashAndSalt[1]));
	}

}
