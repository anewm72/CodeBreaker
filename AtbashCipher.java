import java.util.*;

/**
 * AtbashCipher: subclass of Cipher
 * @author Abby Newman
 * @version 1.2 24/05/23
 */

public class AtbashCipher extends Cipher {
    public AtbashCipher(int choiceOfCipher) {
        super(choiceOfCipher);
    }

    /**
     * Encrypt method: reverse alphabet
     * A matches to Z, B to Y, C to X, and so on
     */
    public void encrypt() {
        plainText = "";
        cipherText = "";
        File file = new File("prep.txt");
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (scan.hasNextLine()) {
            plainText = plainText + scan.nextLine();
        }
        plainText = plainText.toUpperCase();

        for(int i = 0; i < plainText.length(); i++){
            int character = plainText.charAt(i);
            if ((character >= 65) && (character <= 90)){
                int pos = character - 65;
                pos = 25 - pos;
                character = pos + 65;
                char cipherChar = (char) character;
                cipherText = cipherText + cipherChar;
            }
        }
        System.out.println(cipherText);
        super.setCipherText(cipherText);
    }

    /**
     * Decrypt method: the same as the encrypt method
     */
    public void decrypt() {
        if (getCipherFile() == "") {
            System.out.println("Please choose a cipher text file first.");
        } else {
            File file = new File(cipherFile);
            plainText = "";
            cipherText = "";
            try {
                scan = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            while (scan.hasNextLine()) {
                cipherText = cipherText + scan.nextLine();
            }
            cipherText = cipherText.toUpperCase();

            for (int i = 0; i < cipherText.length(); i++) {
                int character = cipherText.charAt(i);
                if ((character >= 65) && (character <= 90)) {
                    int pos = character - 65;
                    pos = 25 - pos;
                    character = pos + 65;
                    char plainChar = (char) character;
                    plainText = plainText + plainChar;
                }
            }
            System.out.println(plainText);
            super.setCipherText(plainText);
        }
    }
}
