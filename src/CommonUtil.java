import java.util.Arrays;

// reference https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
// reference https://www.mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/

public class CommonUtil {

    /**
     * Use TEA to decrpt a bytes array
     * Take first length bytes.
     *
     * @param tea
     * @param bytes
     * @param length
     * @return String after TEA Decryption
     */
    public String getStringAfterDecrypt(TEA tea, byte[] bytes, int length) {
        byte[] byteArray = Arrays.copyOf(bytes, length);
        byte[] resultArray = tea.decrypt(byteArray);
        String string = new String(resultArray);
        return string;
    }

    /**
     * Convert a byte array to string
     * Take first length bytes.
     *
     * @param bytes
     * @param length
     * @return
     */
    public String getString(byte[] bytes, int length) {
        byte[] keyByte = Arrays.copyOf(bytes, length);
        String string = new String(keyByte);
        return string;
    }

    /**
     * Used to verify the symmetric key
     *
     * @param string
     * @return
     */
    public boolean keyValidation(String string) {
        char[] chars = string.toCharArray();
        for (int c : chars) {
            if (c >= 128) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        TEA tea = new TEA("sadgfasdgdfgdsfgdsasf".getBytes());
        CommonUtil commonUtil = new CommonUtil();
        byte[] string = "hello".getBytes();
        byte[] encrypt = tea.encrypt(string);

        TEA tea1 = new TEA("sadgfasdgdfgdsfgdsasf".getBytes());

        byte[] decrypt = tea1.decrypt(encrypt);
        System.out.println(commonUtil.keyValidation(decrypt.toString()));
    }
}