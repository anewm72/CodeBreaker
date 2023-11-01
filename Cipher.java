import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Cipher class: used to process all functionalities that involve cipher text
 * @author Abby Newman
 * @version 1.2 24/05/23
 */

public class Cipher {
    public String cipherText;
    public String cipherFile;
    public Scanner scan;
    public int cipherChoice;
    public int shift;
    public String alphabet;
    public String keyChars;
    public String plainText;

    public Cipher(int choiceOfCipher) {
        cipherChoice = choiceOfCipher;
        cipherText = "";
        cipherFile = "";
        shift = 0;
        alphabet = ("abcdefghijklmnopqrstuvwxyz");
        keyChars = "";
        plainText = "";
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String letter) {
        this.alphabet = letter;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getCipherFile() {
        return cipherFile;
    }

    public void setCipherFile(String cipherFile) {
        this.cipherFile = cipherFile;
    }

    /**
     * Encrypt method that shifts the plain text
     * @param key the key from either the Caesar cipher or keyed Caesar cipher
     * @param shift number for the plain text characters to be shifted by
     */
    public void encrypt(String key, int shift) {
        File file = new File("prep.txt");
        ArrayList<String> shiftedAlphabet = new ArrayList<String>(26);
        try {
            scan = new Scanner(file);
            shiftedAlphabet.clear();
            plainText = "";

            while (scan.hasNextLine()) {
                plainText = plainText + scan.nextLine();
            }

            for (int k = 0; k < 26; k++) {
                shiftedAlphabet.add("");
            }

            for (int j = 0; j < 26; j++) {
                int newPos = j - shift;
                if (newPos < 0) {
                    newPos = newPos + 26;
                } else if (newPos > 25) {
                    newPos = newPos - 26;
                }
                shiftedAlphabet.set(newPos, String.valueOf(getAlphabet().charAt(j)));
            }

            String finalAlphabet = "";
            for (int l = 0; l < 26; l++) {
                finalAlphabet = finalAlphabet + shiftedAlphabet.get(l);
            }

            String capitalFinalAlphabet = finalAlphabet.toUpperCase();
            String capitalAlphabet = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            cipherText = "";

            for (int m = 0; m < plainText.length(); m++) {
                int index = capitalAlphabet.indexOf(plainText.charAt(m));
                cipherText = cipherText + capitalFinalAlphabet.charAt(index);
            }
            System.out.println(cipherText);
            setCipherText(cipherText);

        } catch (FileNotFoundException e) {
            System.out.println("Sorry, your plain text file was not found. Please enter it again from the menu.");
        }
    }

    /**
     * Decrypt method that shifts the cipher text back
     * @param key the key used for this cipher
     * @param shift the number for the cipher to be shifted by
     */
    public void decrypt(String key, int shift) {
        try {
            ArrayList<String> shiftedAlphabet = new ArrayList<String>(26);
            shiftedAlphabet.clear();

            File file = new File(getCipherFile());
            scan = new Scanner(file);
            cipherText = "";
            while (scan.hasNextLine()) {
                cipherText = cipherText + scan.nextLine();
            }

            for (int k = 0; k < 26; k++) {
                shiftedAlphabet.add("");
            }

            for (int j = 0; j < 26; j++) {
                int newPos;
                if (cipherChoice == 1) {
                    newPos = j + shift;
                } else {
                    newPos = j - shift;
                }
                if (newPos < 0) {
                    newPos = newPos + 26;
                } else if (newPos > 25) {
                    newPos = newPos - 26;
                }
                shiftedAlphabet.set(newPos, String.valueOf(getAlphabet().charAt(j)));
            }

            String finalAlphabet = "";
            for (int l = 0; l < 26; l++) {
                finalAlphabet = finalAlphabet + shiftedAlphabet.get(l);
            }

            String capitalFinalAlphabet = finalAlphabet.toUpperCase();
            String capitalAlphabet = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

            plainText = "";

            if (cipherChoice == 1) {
                for (int m = 0; m < cipherText.length(); m++) {
                    int index = capitalAlphabet.indexOf(cipherText.charAt(m));
                    plainText = plainText + capitalFinalAlphabet.charAt(index);
                }
            } else {
                for (int m = 0; m < cipherText.length(); m++) {
                    int index = capitalFinalAlphabet.indexOf(cipherText.charAt(m));
                    plainText = plainText + capitalAlphabet.charAt(index);
                }
            }
            setPlainText(plainText);
            System.out.println(plainText);
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, your cipher text file was not found. Please enter it again from the menu.");
        }
    }

    /**
     * Displaying the cipher file
     * If there is no cipher file, an error message will be displayed
     */
    public void display() {
        if (getCipherFile() == null) {
            System.out.println("Please choose a cipher text file first.");
        } else {
            File file = new File(getCipherFile());
            try {
                scan = new Scanner(file);
                while (scan.hasNextLine() != false) {
                    String input = scan.nextLine();
                    System.out.println(input);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Sorry, your cipher text file was not found. Please enter it again from the menu.");
            }
        }
    }

    /**
     * User inputs a cipher file
     * Uses regex to make sure that the file is valid
     */
    public void inputFile() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter the filename where the cipher text file is stored: ");
        cipherFile = scan.nextLine();
        if (cipherFile == "") {
            System.out.println("You have not entered a filename. Please try again.");
        } else if (cipherFile.contains(" ")) {
            System.out.println("You must not have whitespace in your cipher text file name, please try again.");
        } else if (!cipherFile.contains(".txt")) {
            System.out.println("This is not a valid cipher text file. Please enter '.txt' after your text file name.");
        } else {
            Pattern p = Pattern.compile("[^-.a-z0-9]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(cipherFile);
            boolean b = m.find();
            if (b) {
                System.out.println("This is not a valid cipher text file name. Please do not include special characters.");
            } else {
                System.out.println("Thank you for entering your cipher text file.");
                System.out.println(cipherFile);
                setCipherFile(cipherFile);
            }
        }
    }

    /**
     * Save method: just for ciphertext
     * @param filename filename to be saved to
     */
    public void save(String filename) {
        if ((getCipherText() == "")||(getCipherText() == null)) {
            System.out.println("You need cipher text before you can save it.");
        } else {
            try (FileWriter fw = new FileWriter(filename);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter outfile = new PrintWriter(bw)) {
                for (int i = 0; i < getCipherText().length(); i++) {
                    outfile.print(getCipherText().charAt(i));
                }
                System.out.println("Saved cipher text to " + filename + ".");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
