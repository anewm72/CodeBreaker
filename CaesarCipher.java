import java.util.*;
import java.io.*;

public class CaesarCipher extends Cipher{
    public CaesarCipher(int cipherChoice) {
        super(cipherChoice);
        String encryptedText = "";
    }

    public void encrypt(int key) {
        File file = new File("prep.txt");
        Scanner scan;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        while (scan.hasNextLine() == false) {
            String input = scan.nextLine();
            for (i=0; i<input.length(); i++){
                char[] inputChars = input.toCharArray();
                int encodingValue = inputChars[i];

                char shiftedChar = 0;
                inputChars[i] = shiftedChar;
                input = String.valueOf(inputChars);
            }
        }
    }
}
