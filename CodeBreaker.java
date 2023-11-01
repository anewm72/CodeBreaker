import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * The main class CodeBreaker
 * @author Abby Newman
 * @version 1.2 24/05/23
 */
public class CodeBreaker {
    private Scanner scan;
    public int choiceOfCipher;
    private String textFile;
    private ArrayList<String> key;
    private ArrayList<String> preparedPlainText;
    public Cipher cipher;
    public CaesarCipher caesarCipher;
    public KeyedCaesarCipher keyedCaesarCipher;
    public VigenereCipher vigenereCipher;
    public AtbashCipher atbashCipher;

    public CodeBreaker(int cipherChoosing, String fileOfText) {
        key = new ArrayList<String>(3);
        preparedPlainText = new ArrayList<String>(50);
        choiceOfCipher = cipherChoosing;
        textFile = fileOfText;
        cipher = new Cipher(getChoiceOfCipher());
        caesarCipher = new CaesarCipher(getChoiceOfCipher());;
        keyedCaesarCipher = new KeyedCaesarCipher(getChoiceOfCipher());
        vigenereCipher = new VigenereCipher(getChoiceOfCipher());
        atbashCipher = new AtbashCipher(getChoiceOfCipher());
    }

    public int getChoiceOfCipher() {
        return choiceOfCipher;
    }

    public void setChoiceOfCipher(int choiceOfCipher) {
        this.choiceOfCipher = choiceOfCipher;
    }


    /**
     * Load method: loads in files
     * Can use this with key text files as well as others
     * @param filename the filename to be loaded from
     */
    public void load(String filename) {
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr);
             Scanner input = new Scanner(br)) {

            input.useDelimiter("\r?\n|\r");
            if(filename.contains("key")){
                key.add(input.nextLine());
            }else{
                String line;
                while ((line = br.readLine()) != null){
                    System.out.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("We tried to load" + filename + " but the file wasn't found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialise() {
        load("caesar-key.txt");
        load("keyed-caesar-key.txt");
        load("vigenere-key.txt");
    }

    /**
     * Menu
     * Uses a do…while loop and has a switch statement which takes the choice of the user.
     */
    public void runMenu() {
        String response;
        System.out.println("Hello and welcome to the code breaker. ");
        do {
            menu();
            System.out.print("Select an option: ");
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    cipherChoice();
                    break;
                case "2":
                    editKey();
                    break;
                case "3":
                    displayKey();
                    break;
                case "4":
                    chooseTextFile();
                    break;
                case "5":
                    displayTextFile();
                    break;
                case "6":
                    encryptText();
                    break;
                case "7":
                    //Open the cipher text file the user selects with the cipher text in it
                    inputTextFile();
                    break;
                case "8":
                    decryptText();
                    break;
                case "9":
                    displayCipherText();
                    break;
                case "10":
                    //Save to a user specified file
                    saveCipherText();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Please pick a valid menu item.");
            }
        }while (!(response.equals("Q")));

    }

    public static void menu() {
        System.out.println("");
        System.out.println("MENU");
        System.out.println("1 -  Choose a cipher to use ");
        System.out.println("2 - Edit the key ");
        System.out.println("3 -  Display the key ");
        System.out.println("4 -  Choose the plain text file ");
        System.out.println("5 -  Display the plain text file (in prepared plain text) ");
        System.out.println("6 -  Encrypt the prepared plain text using the chosen cipher ");
        System.out.println("7 -  Input a cipher text file (to be displayed or decrypted) ");
        System.out.println("8 - Decrypt the cipher text ");
        System.out.println("9 - Display the cipher text ");
        System.out.println("10 - Save the cipher text ");
        System.out.println("q - Quit the program");
    }

    /**
     * Cipher choice method
     * User selects a number between 1 and 4
     * Any other number/character causes an error message
     */
    public void cipherChoice() {
        System.out.println("");
        System.out.println("1 - Caesar Cipher ");
        System.out.println("2 - Keyed Caesar Cipher Shift");
        System.out.println("3 - Vigenere Cipher");
        System.out.println("4 - Atbash Cipher");
        System.out.print("Select an option: ");
        try {
            scan = new Scanner(System.in);
            int choice = scan.nextInt();
            if ((choice > 4)|| (choice < 1)){
                System.out.println("Sorry, that choice is invalid. Please try again.");
            }else{
                setChoiceOfCipher(choice);
                System.out.println("Thank you for your choice.");
            }
        }catch (InputMismatchException e){
            System.out.println("Please enter a valid number.");
        }
    }

    /**
     * User is able to edit their key: based on the chosen cipher
     * Can't edit key if they haven't chosen a cipher
     */
    public void editKey() {
        scan = new Scanner(System.in);
        if (getChoiceOfCipher() == 1){
            System.out.print("Please pick a number to use for the Caesar cipher key: ");
            int number = 0;
            try {
                number = scan.nextInt();
                String numberToString = String.valueOf(number);
                key.remove(0);
                key.add(0, numberToString);
                save("caesar-key.txt", 0);
                System.out.println("Your new key is: " + numberToString);
            }catch (InputMismatchException e){
                System.out.println("Your number is far too large.");
            }
        }else if (getChoiceOfCipher() == 2){
            System.out.print("Please pick a key value to use: ");
            String keyValue = scan.nextLine();
            key.remove(1);
            key.add(1,keyValue);
            save("keyed-caesar-key.txt", 1);
            System.out.println("Your new key is: " + keyValue);
        }else if (getChoiceOfCipher()== 3) {
            System.out.print("Please pick a key value to use: ");
            String keyValue = scan.nextLine();
            key.remove(2);
            key.add(2, keyValue);
            save("vigenere-key.txt", 2);
            System.out.println("Your new key is: " + keyValue);
        }else if (getChoiceOfCipher() == 4){
            System.out.println("The Atbash cipher does not use a key.");
        }else {
            System.out.println("Please pick the key you will be using first.");
        }
    }

    /**
     * Key is able to be displayed using this method
     * Can not select the Atbash cipher, as it doesn't use a key
     */
    public void displayKey(){
        if (getChoiceOfCipher() == 0){
            System.out.println("Please choose a cipher first.");
        }else if (getChoiceOfCipher() == 1){
            System.out.println(key.get(0));
        }else if (getChoiceOfCipher() == 2){
            System.out.println(key.get(1));
        }else if (getChoiceOfCipher() == 3){
            System.out.println(key.get(2));
        }else if (getChoiceOfCipher() == 4){
            System.out.println("The Atbash cipher does not use a key.");
        }
    }

    /**
     * User chooses a plain text file
     * Using regex here to make the name of the text file a valid one
     */
    public void chooseTextFile(){
        scan = new Scanner(System.in);
        System.out.print("Please enter the filename where the text file is stored: ");
        textFile = scan.nextLine();
        if (textFile == ""){
            System.out.println("You have not entered a text file, please try again.");
        }else if (textFile.contains(" ")){
            System.out.println("You must not have whitespace in your text file name, please try again.");
        }else if (!textFile.contains(".txt")){
            System.out.println("This is not a valid text file. Please enter '.txt' after your text file name.");
        } else{
            Pattern p = Pattern.compile("[^-.a-z0-9]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(textFile);
            boolean b = m.find();
            if (b) {
                System.out.println("This is not a valid text file name. Please do not include special characters.");
            }else {
                System.out.println("Thank you for entering your text file.");
            }
        }
    }

    /**
     * The plain text file is displayed into prepared plain text
     * Using regex to do this
     * Also saves the file to prep.txt afterward
     */
    public void displayTextFile(){
        if(textFile == ""){
            System.out.println("Please choose a text file first.");
        }else{
            File file = new File(textFile);
            try {
                scan = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.out.println("Your chosen text file was not found. You may want to check if the file is in your folder.");
            }
            int i = 0;

            if(!preparedPlainText.isEmpty()){
                preparedPlainText.clear();
            }

            while (scan.hasNextLine()!= false) {
                String input = scan.nextLine();
                String strippedInput = input.replaceAll("\\W", "");
                String noSpaces = strippedInput.replaceAll(" ", "");
                String preparedText = noSpaces.toUpperCase();
                preparedPlainText.add(preparedText);
                System.out.println(preparedText);
                i += 1;
            }
            save("prep.txt", 0);
        }
    }

    /**
     * Encrypts the prepared plain text based on the chosen cipher
     * Links to class Cipher
     */
    public void encryptText() {
        if (getChoiceOfCipher() == 0){
            System.out.println("Please choose a cipher first.");
        }else if (getChoiceOfCipher() == 1) {
            caesarCipher.encrypt(key.get(0), 0);
        } else if (getChoiceOfCipher() == 2) {
            keyedCaesarCipher.encrypt(key.get(1), 0);
        } else if (getChoiceOfCipher() == 3) {
            vigenereCipher.encrypt(key.get(2));
        } else if (getChoiceOfCipher() == 4) {
            atbashCipher.encrypt();
        }
    }

    /**
     * Decrypts the cipher text based on the choice of cipher
     * Links to class Cipher
     */
    public void decryptText(){
        if (getChoiceOfCipher() == 0){
            System.out.println("Please choose a cipher first.");
        }else if (getChoiceOfCipher() == 1){
            caesarCipher.decrypt(key.get(0), 0);
        }else if (getChoiceOfCipher() == 2) {
            keyedCaesarCipher.decrypt(key.get(1), 0);
        }else if (getChoiceOfCipher() == 3){
            vigenereCipher.decrypt(key.get(2));
        }else if (getChoiceOfCipher() == 4){
            atbashCipher.decrypt();
        }
    }

    /**
     * Cipher text is able to be displayed
     * Links to class Cipher
     */
    public void displayCipherText(){
        if (getChoiceOfCipher() == 0){
            System.out.println("Please choose a cipher first.");
        }else if (getChoiceOfCipher() == 1){
            caesarCipher.display();
        }else if (getChoiceOfCipher() == 2) {
            keyedCaesarCipher.display();
        }else if (getChoiceOfCipher() == 3){
            vigenereCipher.display();
        }else if (getChoiceOfCipher() == 4){
            atbashCipher.display();
        }
    }

    /**
     * User can input a cipher text file
     */
    public void inputTextFile() {
        if (getChoiceOfCipher()== 0){
            System.out.println("Please choose a cipher first.");
        }else if (getChoiceOfCipher() == 1){
            caesarCipher.inputFile();
        }else if (getChoiceOfCipher() == 2) {
            keyedCaesarCipher.inputFile();
        }else if (getChoiceOfCipher() == 3){
            vigenereCipher.inputFile();
        }else if (getChoiceOfCipher() == 4){
            atbashCipher.inputFile();
        }
    }

    /**
     * User selects a filename to save the cipher text to
     * Only able to save cipher text if the user has encrypted some plain text first
     */
    public void saveCipherText() {
        scan = new Scanner(System.in);
        System.out.print("Pick a filename to save your cipher text to: ");
        textFile = scan.nextLine();
        if (textFile == "") {
            System.out.println("You have not entered a text file, please try again.");
        } else if (textFile.contains(" ")) {
            System.out.println("You must not have whitespace in your text file name, please try again.");
        } else if (!textFile.contains(".txt")) {
            System.out.println("This is not a valid text file. Please enter '.txt' after your text file name.");
        } else {
            Pattern p = Pattern.compile("[^-.a-z0-9]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(textFile);
            boolean b = m.find();
            if (b) {
                System.out.println("This is not a valid text file name. Please do not include special characters.");
            } else {
                if (getChoiceOfCipher() == 0){
                    System.out.println("Please choose a cipher before saving cipher text.");
                }else if (getChoiceOfCipher() == 1) {
                    caesarCipher.save(textFile);
                } else if (getChoiceOfCipher() == 2) {
                    keyedCaesarCipher.save(textFile);
                } else if (getChoiceOfCipher() == 3) {
                    vigenereCipher.save(textFile);
                } else if (getChoiceOfCipher() == 4) {
                    atbashCipher.save(textFile);
                }
            }
        }
    }

    public void quitting() {
       save("caesar-key.txt", 0);
       save("keyed-caesar-key.txt", 1);
       save("vigenere-key.txt", 2);
    }

    /**
     * Save method: able to save key files and prep.txt
     * Saves key files at the end of the program and prep.txt when the prepared plain text is displayed
     * @param filename key file (or prep.txt) to be updated
     * @param i the index of where the key information is in key ArrayList
     */
    public void save(String filename, int i) {
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {
            if (filename.contains("key")) {
                outfile.println(key.get(i));
            }else if (filename.contains("prep")) {
                for (int j = 0; j< preparedPlainText.size(); j++){
                    outfile.println(preparedPlainText.get(j));
                }
            }
        } catch (IndexOutOfBoundsException f) {
            System.out.println("Text saved.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Main method: where the code starts and ends
     * @param args
     */
    public static void main(String args[]) {
        System.out.println("**********WELCOME TO CODE BREAKER***********");
        CodeBreaker cb = new CodeBreaker(0, "");
        cb.initialise();
        cb.runMenu();
        cb.quitting();
        System.out.println("***********WE HOPE THIS PROGRAM WAS SATISFACTORY**********");
    }
}
