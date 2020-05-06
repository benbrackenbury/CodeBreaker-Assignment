package ac.uk.aber.beb52.codebreaker.cyphers;

import ac.uk.aber.beb52.codebreaker.managers.FileManager;
import ac.uk.aber.beb52.codebreaker.managers.KeyManager;
import ac.uk.aber.beb52.codebreaker.enums.Action;

/**
 * VigenereCypher class
 * Represents a Vigenere cypher
 * @author Ben Brackenbury
 * @version 1.0
 */
public class VigenereCypher extends Cypher {
    private final char[][] grid;
    private Alphabet textAlphabet;
    private Alphabet keyAlphabet;
    private String textToConvert = "";

    /**
     * Constructor for VigenereCypher class
     * @param keyManager the keyManager that contains the key information
     * @param inputFile the fileManager for the input file
     * @param outputFile the fileManager for the output file
     * @param action action to perform (encrypt or decrypt)
     */
    public VigenereCypher(KeyManager keyManager, FileManager inputFile, FileManager outputFile, Action action) {
        super(keyManager, inputFile, outputFile, action);
        this.grid = new char[26][26];
        this.populateGrid();
    }

    private void populateGrid() {
        for (int i=0; i<26; i++) {
            this.grid[i] = new Alphabet(i).toCharArray();
        }
    }

    private int standardAlphabetIndex(char character) {
        return getIndex(character, new Alphabet().toCharArray());
    }

    private void initAlphabets() {
        switch (this.action) {
            case encrypt -> this.textToConvert = this.plaintext;
            case decrypt -> this.textToConvert = this.cyphertext;
        }
        char[] textChars = this.textToConvert.toCharArray();
        int alphabetLength = textChars.length;
        int keyLength = this.stringKey.length();
        char[] keyAlphabetChars = new char[alphabetLength];

        for (int i=0; i<alphabetLength; i++) {
            int stringIndex = i % keyLength;
            keyAlphabetChars[i] = this.stringKey.toCharArray()[stringIndex];
        }

        this.textAlphabet = new Alphabet(textChars);
        this.keyAlphabet = new Alphabet(keyAlphabetChars);
    }

    /**
     * Overrides the abstract method inherited from Cypher class
     * Creates a plaintext alphabet and a keyed alpphabet and calls the crossRefrence
     * method with the two alphabets as arguments
     * @return encrypted cyphertext
     */
    @Override
    protected String encrypt() {
        this.initAlphabets();
        return crossReference(textAlphabet, keyAlphabet);
    }

    /**
     * Overrides the abstract method inherited from Cypher class
     * Creates a cyphertext alphabet and a keyed alpphabet and calls the crossRefrence
     * method with the two alphabets as arguments
     * @return decrypted plaintext
     */
    @Override
    protected String decrypt() {
        this.initAlphabets();
        return decryptVignere(textAlphabet, keyAlphabet);
    }

    private String decryptVignere(Alphabet textAlphabet, Alphabet keyAlphabet) {
        StringBuilder output = new StringBuilder();
        char[] standardAlphabet = new Alphabet().toCharArray();
        for (int i=0; i<textAlphabet.getLength(); i++) {
           int keyIndex = standardAlphabetIndex(keyAlphabet.toCharArray()[i]);
           char[] rowToSearch = grid[keyIndex];
           int columnIndex = getIndex(textAlphabet.toCharArray()[i], rowToSearch);
           output.append(standardAlphabet[columnIndex]);
        }
        return output.toString();
    }

    /**
     * Overrides the crossReference method from the Cypher class
     * @param alphabet1 alphabet to translate from
     * @param alphabet2 alphabet to translate to
     * @return encrypted cyphertext
     */
    @Override
    protected String crossReference(Alphabet alphabet1, Alphabet alphabet2) {
        StringBuilder output = new StringBuilder();
        for (int i=0; i<alphabet1.getLength(); i++) {
            char textChar = alphabet1.toCharArray()[i];
            char keyAlphabetChar = alphabet2.toCharArray()[i];
            int row = standardAlphabetIndex(textChar);
            int column = standardAlphabetIndex(keyAlphabetChar);
            output.append(grid[row][column]);
        }
        return output.toString();
    }
}
