package benbrackenbury.codebreaker.cyphers;

import benbrackenbury.codebreaker.enums.Action;
import benbrackenbury.codebreaker.managers.FileManager;
import benbrackenbury.codebreaker.managers.KeyManager;
import benbrackenbury.codebreaker.menu.Menu;

import java.io.IOException;

/**
 * Cypher class
 * Abstract superclass from which all cyphers inherit
 * Contains methods and properties common to all cyphers
 * @author Ben Brackenbury
 * @version 1.0
 */
public abstract class Cypher {
    private final FileManager inputFile;
    private final FileManager outputFile;
    protected KeyManager keyManager;
    protected Action action;
    protected String plaintext;
    protected String cyphertext;
    protected String stringKey;

    /**
     * Constructor for Cypher class
     * @param keyManager the keyManager that contains the key information
     * @param inputFile the fileManager for the input file
     * @param outputFile the fileManager for the output file
     * @param action action to perform (encrypt or decrypt)
     */
    public Cypher(KeyManager keyManager, FileManager inputFile, FileManager outputFile, Action action) {
        this.keyManager = keyManager;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.action = action;
        this.stringKey = this.keyManager.getStringKey();
    }

    /**
     * Runs either the cypher's encrypt() or decrypt() method, prints the result and saves it to the output file
     * Asks if the user wants to overwrite or append to output file
     */
    public void run() {
        String result = "";
        boolean overwrite;

        if (this.action == Action.encrypt) {
            this.plaintext = inputFile.getFileData();
            result = this.cyphertext = this.encrypt();
        } else if (this.action == Action.decrypt) {
            this.cyphertext = inputFile.getFileData();
            result = this.plaintext = this.decrypt();
        }

        System.out.println("result: " + result);

        Menu overWriteMenu = new Menu("Overwrite output file?", new String[]{"Yes","No"});
        overwrite = switch (overWriteMenu.run()) {
            case 1 -> true;
            case 2 -> false;
            default -> throw new IllegalStateException("Unexpected value: " + overWriteMenu.run());
        };

        try {
            outputFile.writeFile(result, !overwrite);
        } catch (IOException e) {
            System.err.println("Could not write to file");
            System.err.println(e.toString());
        }
    }

    /**
     * Abstract encrypt method that all subclasses must implement
     * @return encrypted cyphertext
     */
    protected abstract String encrypt();

    /**
     * Abstract decrypt method that all subclasses must implement
     * @return decrypted plaintext
     */
    protected abstract String decrypt();

    /**
     * Gets index of character in an array of characters
     * @param character the character to find the index of
     * @param charList the array of characters to search
     * @return index of character in the array
     */
    protected int getIndex(char character, char[] charList) {
        for (int i=0; i<charList.length; i++) {
            if (charList[i] == character) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Takes two alphabets and cross-references their indexes to translate between one alphabegt
     * to another. e.g. standard alphabet to shifted alphabet
     * @param alphabet1 alphabet to translate from
     * @param alphabet2 alphabet to translate to
     * @return string of translated characters
     */
    protected String crossReference(Alphabet alphabet1, Alphabet alphabet2) {
        StringBuilder output = new StringBuilder();
        char[] textToConvert = {};
        switch (this.action) {
            case encrypt -> textToConvert = this.plaintext.toCharArray();
            case decrypt -> textToConvert = this.cyphertext.toCharArray();
        }
        for (int i=0; i< textToConvert.length; i++) {
            char currentChar = textToConvert[i];
            int index = getIndex(currentChar, alphabet1.toCharArray());
            output.append(alphabet2.toCharArray()[index]);
        }
        return output.toString();
    }
}