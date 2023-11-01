import java.io.*;
import java.util.*;

//Test the functions I have already; including load and save functions and menu
public class CodeBreaker {
    private Scanner scan;
    private int choiceOfCipher;
    private String textFile;
    private ArrayList<String> key;
    private ArrayList<String> preparedPlainText;
    private Cipher cipher;

    public CodeBreaker() {
        key = new ArrayList<String>(3);
        preparedPlainText = new ArrayList<String>(50);
        choiceOfCipher = 0;
        textFile = null;
        cipher = new Cipher(choiceOfCipher);
    }

    public int getChoiceOfCipher() {
        return choiceOfCipher;
    }

    public void setChoiceOfCipher(int choiceOfCipher) {
        this.choiceOfCipher = choiceOfCipher;
    }

    //Can now use load and save functions with key text files as well as cipher text files by determining whether the file is for a key or not
    //by using .contains method
    //So don't copy code over twice; saving memory
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
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialise() {
        load("caesar-key.txt");
        load("keyed-caesar-key.txt");
        load("vigenere-key.txt");
    }

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
                    //This is choosing the cipher text file if the user hasn't already encrypted it earlier in the program and saved it
                    System.out.println("Input cipher text file");
                    break;
                case "8":
                    System.out.println("Decrypt cipher text");
                    break;
                case "9":
                    System.out.println("Display cipher text");
                    break;
                case "10":
                    //Basically save as
                    System.out.println("Save cipher text");
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Please pick a valid number.");
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
        System.out.println("5 -  Display the plain text file ");
        System.out.println("6 -  Encrypt the prepared plain text using the chosen cipher ");
        System.out.println("7 -  Input a cipher text file ");
        System.out.println("8 - Decrypt the cipher text ");
        System.out.println("9 - Display the cipher text ");
        System.out.println("10 - Save the cipher text ");
        System.out.println("q - Quit the program");
    }

    public void cipherChoice() {
        System.out.println("");
        System.out.println("1 - Caesar Cipher ");
        System.out.println("2 - Keyed Caesar Cipher Shift");
        System.out.println("3 - Vigenere Cipher");
        System.out.print("Select an option: ");
        scan = new Scanner(System.in);
        int choice = scan.nextInt();
        setChoiceOfCipher(choice);
        System.out.println("Thank you for your choice!");
    }

    public void editKey() {
        scan = new Scanner(System.in);
        if (getChoiceOfCipher() == 1){
            System.out.print("Please pick a number to use for the Caesar cipher key: ");
            int number = scan.nextInt();
            String numberToString = String.valueOf(number);
            key.remove(0);
            key.add(0, numberToString);
        }else if (getChoiceOfCipher() == 2){
            System.out.print("Please pick a key value to use: ");
            String keyValue = scan.nextLine();
            key.remove(1);
            key.add(1,keyValue);
        }else if (getChoiceOfCipher()== 3){
            System.out.print("Please pick a key value to use: ");
            String keyValue = scan.nextLine();
            key.remove(2);
            key.add(2, keyValue);
        }else {
            System.out.println("Please pick the key you will be using first.");
        }
    }

    public void displayKey(){
        if (getChoiceOfCipher() == 1){
            System.out.println(key.get(0));
        }else if (getChoiceOfCipher() == 2){
            System.out.println(key.get(1));
        }else if (getChoiceOfCipher() == 3){
            System.out.println(key.get(2));
        }
    }

    public void chooseTextFile(){
        scan = new Scanner(System.in);
        System.out.print("Please enter the filename where the text file is stored: ");
        textFile = scan.nextLine();
    }

    public void displayTextFile(){
        if(textFile == null){
            System.out.println("Please choose a text file first.");
        }else{
            File file = new File(textFile);
            try {
                scan = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
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

    public void encryptText(){
        cipher.encrypt(key.get(0));
    }

    public void quitting() {
       save("caesar-key.txt", 0);
       save("keyed-caesar-key.txt", 1);
       save("vigenere-key.txt", 2);
    }

    public void save(String filename, int i) {
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {
            if (filename.contains("key")) {
                outfile.println(key.get(i));
            }else if (filename.contains("prep")) {
                for (int j = 0; i< preparedPlainText.size(); j++){
                    outfile.println(preparedPlainText.get(j));
                }
            }
        } catch (IndexOutOfBoundsException f) {
            System.out.println("Text saved.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String args[]) {
        System.out.println("**********WELCOME TO CODE BREAKER***********");
        CodeBreaker cb = new CodeBreaker();
        cb.initialise();
        cb.runMenu();
        cb.quitting();
        System.out.println("***********WE HOPE THIS PROGRAM WAS SATISFACTORY**********");
    }
}