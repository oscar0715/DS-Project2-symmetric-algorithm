import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jiaming on 9/29/16.
 */
public class PasswordHash {

    public String getHashValue(String originalString) {

        MessageDigest md = null;
        byte[] result = null;
        try {
            // md is a MessageDigest object that implements the SHA-1 digest algorithm.
            md = MessageDigest.getInstance("SHA");
            md.reset();
            // Updates the digest using the byte array of originalString.
            md.update(originalString.getBytes("UTF-8"));
            // Performs a final update on the digest using the specified array of bytes, then completes the digest computation.
            result = md.digest();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return DatatypeConverter.printHexBinary(result);
    }

    public static void main(String[] args) {
        PasswordHash hash = new PasswordHash();

        String salt = "asdgaskjbg5234ewgrhmlf213klnonfeoiwa";

        String james = "james";

        String joe = "joe";

        String mike = "mike";

        String sean = "sean";

        String jamesPassword = hash.getHashValue(salt + james);
        String joePassword = hash.getHashValue(salt + joe);
        String mikePassword = hash.getHashValue(salt + mike);
        String seanPassword = hash.getHashValue(salt + sean);

        System.out.println("james' password after hash = " + jamesPassword);
        System.out.println("joe's password after hash = " + joePassword);
        System.out.println("mike's password after hash = " + mikePassword);
        System.out.println("sean's password after hash = " + seanPassword);

    }
}
