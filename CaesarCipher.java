/**
 * CaesarCipher: subclass of Cipher
 * @author Abby Newman
 * @version 1.2 24/05/23
 */
public class CaesarCipher extends Cipher {

    public CaesarCipher(int cipherChoice) {
        super(cipherChoice);
    }

    /**
     * Encrypt method
     * Gets the integer value of the key for the shift
     * Calls the superclass method
     * @param key the key used for this cipher
     * @param shift number for the plain text characters to be shifted by
     */
    @Override public void encrypt(String key, int shift) {
        shift = Integer.valueOf(key);


        super.setAlphabet(alphabet);
        super.encrypt(key, shift);
    }

    /**
     * Decrypt method
     * Gets the integer value of the key for the shift
     * Calls the superclass method
     * @param key the key used for this cipher
     * @param shift the number for the cipher to be shifted by
     */
    @Override public void decrypt(String key, int shift) {
        if (getCipherFile() == "") {
            System.out.println("Please choose a cipher text file first.");
        } else {
            shift = Integer.valueOf(key);
            super.setAlphabet(alphabet);
            super.decrypt(key, shift);
        }
    }
}
