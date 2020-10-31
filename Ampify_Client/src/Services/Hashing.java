package Services;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//      generates a unique hash by using mac address of pc and current timestamp//
public class Hashing {
    public static String hashPassword(String pass){
        String generatedPassword=null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            //Add password bytes to digest
            messageDigest.update(pass.getBytes());
            //Get the hash's bytes
            byte[] bytes = messageDigest.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}

