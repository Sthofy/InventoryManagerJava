package inventorymanagerapp.others;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class PasswordManager {

    private static final Random RND = new SecureRandom();

    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RND.nextBytes(salt);
        return salt;
    }

    public static String hash(String password, String salt) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            String passWithSalt = password + "[B@2667f029";
            byte[] passBytes = passWithSalt.getBytes();
            byte[] passHash = sha256.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passHash.length; ++i) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            String generatedPassword = sb.toString();
            return salt + generatedPassword;
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(salt);      
        // Teszt pw [B@2667f02b7970d700bf918528d4e872c004feaf84ff34670c595fd028c6e97adfd3fc9bb
        return null;
    }

    public static boolean isValidPassword(String password, String salt, String expectedHash) {
        String generatedPassword = hash(password, salt);
        if (generatedPassword.equals(expectedHash)) {
            return true;
        } else {
            return false;
        }
    }
}
