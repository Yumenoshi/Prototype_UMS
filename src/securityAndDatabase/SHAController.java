package securityAndDatabase;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHAController {
	
	public boolean comparePasswords(String password, String passwordHash, String salt) {
		if(getSHA256SecurePassword(password, salt).equals(passwordHash))
			return true;
			
		return false;
	}
	
	public String[] generateHashAndSalt(String password) {
		//Returns Hashed password with salt as first element of array, and salt as second
		String[] result=new String[2];
		String salt="";
		String passwordHash="";
		
		salt=new String(this.getSalt());
		passwordHash=getSHA256SecurePassword(password, salt);
			
		
		result[0]=passwordHash;
		result[1]=salt;
		return result;
	}
	
	
	private static String getSHA256SecurePassword(String passwordToHash, String salt)
    {
		
		
        String generatedPassword = null;
        try {
        	MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
	
	public String getSalt() {
	    String saltStr = "";
	    try {
	        final SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	        final byte[] salt = new byte[16];
	        sr.nextBytes(salt);
	        saltStr = Base64.getEncoder().encodeToString(salt);
	    } catch (NoSuchAlgorithmException e) { 
	    	  e.printStackTrace();
	    }

	    return saltStr;
	}
}
