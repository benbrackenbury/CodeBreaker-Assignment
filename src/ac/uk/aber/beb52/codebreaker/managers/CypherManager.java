package ac.uk.aber.beb52.codebreaker.managers;

import ac.uk.aber.beb52.codebreaker.cyphers.Cypher;
import ac.uk.aber.beb52.codebreaker.cyphers.VigenereCypher;
import ac.uk.aber.beb52.codebreaker.cyphers.caesar.CaesarCypher;
import ac.uk.aber.beb52.codebreaker.enums.CypherType;
import ac.uk.aber.beb52.codebreaker.menu.Menu;
import ac.uk.aber.beb52.codebreaker.cyphers.caesar.KeyedCaesarCypher;
import ac.uk.aber.beb52.codebreaker.enums.Action;

/**
 * CypherManager class
 * Handles the creation and execution of cyphers
 * @author Ben Brackenbury
 * @version 1.0
 */
public class CypherManager {
    private CypherType type;
    private final KeyManager keyManager;
    private final FileManager inputFile;
    private final FileManager outputFile;

    /**
     * Constructor for CypherManager class
     * @param type type of cypher (caesar|keyed_caesar|vigenère)
     * @param keyManager the keyManager with the key information
     * @param inputFile the fileManager for the input file
     * @param outputFile the fileManager for the output file
     */
    public CypherManager(CypherType type, KeyManager keyManager, FileManager inputFile, FileManager outputFile) {
        this.type = type;
        this.keyManager = keyManager;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * Constructor for the CypherManager called if a type is not specified
     * Default cypher type is caesar
     * @param keyManager the keyManager with the key information
     * @param inputFile the fileManager for the input file
     * @param outputFile the fileManager for the output file
     */
    public CypherManager(KeyManager keyManager, FileManager inputFile, FileManager outputFile) {
        this(CypherType.caesar, keyManager, inputFile, outputFile);
    }

    /**
     * Displays a Menu asking the user for a cypher type and sets the type property
     */
    public void changeCypher() {
        Menu changeCypherMenu = new Menu("Choose a cypher", new String[]{
                "Caesar",
                "Keyed Caesar",
                "Vigenère"
        });
        switch (changeCypherMenu.run()) {
            case 1 -> this.type = CypherType.caesar;
            case 2 -> this.type = CypherType.keyed_caesar;
            case 3 -> this.type = CypherType.vigenère;
            default -> {
                System.err.println("Try again");
                changeCypher();
            }
        }
    }

    /**
     * Creates the specified cypher and runs either the encrypt or decrypt methods
     * @param action either encrypt or decrypt
     */
    public void runCypher(Action action) {
        Cypher cypher;
        switch (this.type) {
            case caesar -> cypher = new CaesarCypher(keyManager, inputFile, outputFile, action);
            case keyed_caesar -> cypher = new KeyedCaesarCypher(keyManager, inputFile, outputFile, action);
            case vigenère -> cypher = new VigenereCypher(keyManager, inputFile, outputFile, action);
            default -> cypher = null;
        }
        cypher.run();
    }

    /**
     * @return name for the specified cypher type
     */
    public String getName() {
        return switch (this.type) {
            case caesar -> "Caesar";
            case keyed_caesar -> "Keyed Caesar";
            case vigenère -> "Vigenère";
        };
    }
}
