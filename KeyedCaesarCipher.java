import java.util.*;

/**
 * KeyedCaesarCipher: subclass of Cipher
 * @author Abby Newman
 * @version 1.2 24/05/23
 */
public class KeyedCaesarCipher extends Cipher {

    public KeyedCaesarCipher(int cipherChoice) {
        super(cipherChoice);
    }

    /**
     * Encrypt method:
     * Search for letters of the key using a for loop
     * Delete the letter from the alphabet
     * Add it to the front of the alphabet
     * Have new alphabet
     * Call superclass to shift
     * @param key the key from this cipher
     * @param shift number for the plain text characters to be shifted by
     */
    @Override public void encrypt(String key, int shift) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the shift key: ");
        shift = sc.nextInt();
        alphabet = "abcdefghijklmnopqrstuvwxyz";
        keyChars = "";
        int i;
        for (i = 0; i < key.length(); i++) {
            alphabet = alphabet.replaceFirst(String.valueOf(key.charAt(i)), "");
            keyChars = keyChars + key.charAt(i);
        }
        String newAlphabet = keyChars + alphabet;

        super.setAlphabet(newAlphabet);
        super.encrypt(key, shift);
    }

    /**
     * Decrypt method
     * Exactly the same as encrypt here, the only thing that changes is in the super method call in the Cipher class
     * @param key the key used for this cipher
     * @param shift the number for the cipher to be shifted by
     */
    @Override public void decrypt(String key, int shift) {
        if (getCipherFile() == ""){
            System.out.println("Please choose a cipher text file first.");
        }else {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the shift key: ");
            shift = sc.nextInt();
            alphabet = "abcdefghijklmnopqrstuvwxyz";
            keyChars = "";
            int i;
            for (i = 0; i < key.length(); i++) {
                alphabet = alphabet.replaceFirst(String.valueOf(key.charAt(i)), "");
                keyChars = keyChars + key.charAt(i);
            }
            String newAlphabet = keyChars + alphabet;
            super.setAlphabet(newAlphabet);
            super.decrypt(key, shift);
        }
    }
}

