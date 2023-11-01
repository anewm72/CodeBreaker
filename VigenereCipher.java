import java.io.*;
import java.util.*;

/**
 * VigenereCipher: subclass of Cipher
 * @author Abby Newman
 * @version 1.2 24/05/23
 */

public class VigenereCipher extends Cipher{
    public VigenereCipher(int cipherChoice) {
        super(cipherChoice);
    }

    /**
     * Encrypt method:
     * Get a keyword (which repeats constantly) e.g. lemon then capitalise key
     * Cycle through a for loop for length of text, keyword char depends on what i is at the time
     * Compare the plain text and the key together to get the cipher text
     * @param key the key used for this cipher
     */
    public void encrypt(String key){
        File file = new File("prep.txt");
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String keyAlgorithm = "";
        String alphabet = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        plainText = "";
        while(scan.hasNextLine() == true){
            plainText = plainText + scan.nextLine();
        }
        int remainder = plainText.length() % key.length();
        int amount = plainText.length() - remainder;
        for(int i= 0; i< amount/ key.length(); i++){
            for(int j = 0; j< key.length(); j++){
                keyAlgorithm = keyAlgorithm + key.charAt(j);
            }
        }
        for(int k=0; k< remainder; k++){
            keyAlgorithm = keyAlgorithm + key.charAt(k);
        }

        String upperKeyAlgorithm = keyAlgorithm.toUpperCase();

        cipherText = "";

        for(int l=0; l<plainText.length(); l++) {
            int cipher = alphabet.indexOf(upperKeyAlgorithm.charAt(l)) + alphabet.indexOf(plainText.charAt(l));
            if (cipher > 25){
                cipher = cipher - 26;
            }else if (cipher < 0){
                cipher = cipher + 26;
            }
            cipherText = cipherText + alphabet.charAt(cipher);
        }
        System.out.println(cipherText);
        super.setCipherText(cipherText);
    }


    /**
     * Decrypt method:
     * Much like the encrypt method
     * Gets the cipher file instead of using prep.txt
     * @param key the key used for this cipher
     */
    public void decrypt(String key) {
        if (getCipherFile() == "") {
            System.out.println("Please choose a cipher text file first.");
        } else {
            File file = new File(cipherFile);
            try {
                scan = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            cipherText = "";
            String keyAlgorithm = "";
            String alphabet = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            while (scan.hasNextLine() == true) {
                cipherText = cipherText + scan.nextLine();
            }
            int remainder = cipherText.length() % key.length();
            int amount = cipherText.length() - remainder;
            for (int i = 0; i < amount / key.length(); i++) {
                for (int j = 0; j < key.length(); j++) {
                    keyAlgorithm = keyAlgorithm + key.charAt(j);
                }
            }
            if (!(remainder == 0)) {
                for (int k = 0; k < remainder; k++) {
                    keyAlgorithm = keyAlgorithm + key.charAt(k);
                }
            }

            String upperKeyAlgorithm = keyAlgorithm.toUpperCase();

            plainText = "";

            for (int l = 0; l < cipherText.length(); l++) {
                int plain = alphabet.indexOf(cipherText.charAt(l)) - alphabet.indexOf(upperKeyAlgorithm.charAt(l));
                if (plain > 25) {
                    plain = plain - 26;
                } else if (plain < 0) {
                    plain = plain + 26;
                }
                plainText = plainText + alphabet.charAt(plain);
            }
            System.out.println(plainText);
            super.setPlainText(plainText);
        }
    }
}
