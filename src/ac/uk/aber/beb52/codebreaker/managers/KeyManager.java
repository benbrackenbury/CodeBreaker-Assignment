package ac.uk.aber.beb52.codebreaker.managers;

import java.io.*;
import java.util.Scanner;

/**
 * Keymanager class
 * Handles the loading, saving and parsing of the key file
 * @author Ben Brackenbury
 * @version 1.0
 */
public class KeyManager {
    private final String filePath;
    private int intKey;
    private String stringKey;

    /**
     * Constructor for KeyManager class
     * @param filePath path to the key file
     */
    public KeyManager(String filePath) {
        this.filePath = filePath;
        this.init();
    }

    private void init() {
        try {
            this.loadKeys();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load keys from file:");
            System.err.println(e.toString());
            System.exit(1);
        }
    }

    private void loadKeys() throws FileNotFoundException {
        File file = new File(this.filePath);
        Scanner scanner = new Scanner(file);
        // get file data as array of chars
        char[] keyData = scanner.nextLine().toCharArray();
        // convert first char to an int
        int parsedIntKey = Integer.parseInt(Character.toString(keyData[0]));
        // create string from rest of chars
        StringBuilder stringKeySB = new StringBuilder();
        for (int i=1; i<keyData.length; i++) {
            stringKeySB.append(keyData[i]);
        }
        String parsedStringKey = stringKeySB.toString();
        this.intKey = parsedIntKey;
        this.stringKey = parsedStringKey.toUpperCase();
    }

    /**
     * Prints the keys and file information to the console
     */
    public void displayKey() {
        System.out.println("Integer key: " + this.intKey);
        System.out.println("String key: " + this.stringKey);
        System.out.println("Stored in file as: " + this.intKey + this.stringKey.toLowerCase());
    }

    private boolean isStringValidInt(String string) {
        try {
            int i = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean validateKey(char[] key) {
        // check if first char is valid int
        String firstChar = Character.toString(key[0]);
        if (!isStringValidInt(firstChar)) return false;

        // check if rest are not ints
        for (int i=1; i<key.length; i++) {
            String stringToCheck = Character.toString(key[i]);
            if (isStringValidInt(stringToCheck)) return false;
        }

        return true;
    }

    private void writeKey(char[] keyData) throws IOException {
        Writer fileWriter = new FileWriter(this.filePath, false);
        fileWriter.write(new String(keyData));
        fileWriter.close();
        this.init();
        System.out.println("Successfully changed key");
    }

    /**
     * Asks user for a new key, then validates and writes it to the key file
     */
    public void changeKey() {
        System.out.println("Enter new key\n(format as <integer><string>, e.g. 7lemon)");
        Scanner in = new Scanner(System.in);
        char[] newKeyData = in.nextLine().toCharArray();
        if (validateKey(newKeyData)) {
            this.intKey = 0;
            this.stringKey = "";
            try {
                writeKey(newKeyData);
            } catch (IOException e) {
                System.err.println("Could not write key to file");
                System.err.println(e.toString());
            }
        } else {
            System.err.println("The provided key does not have a valid format, please try again");
            this.changeKey();
        }
    }

    /**
     * @return the integer key
     */
    public int getIntKey() {
        return intKey;
    }

    /**
     * @return the string key
     */
    public String getStringKey() {
        return stringKey;
    }
}
