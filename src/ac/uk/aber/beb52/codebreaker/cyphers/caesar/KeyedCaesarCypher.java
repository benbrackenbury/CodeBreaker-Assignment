package ac.uk.aber.beb52.codebreaker.cyphers.caesar;

import ac.uk.aber.beb52.codebreaker.managers.FileManager;
import ac.uk.aber.beb52.codebreaker.managers.KeyManager;
import ac.uk.aber.beb52.codebreaker.cyphers.Alphabet;
import ac.uk.aber.beb52.codebreaker.enums.Action;

/**
 * KeyedCaesarCypher class inherits from CaesarCypher
 * Represents a keyed Caesar cypher
 * @author Ben Brackenbury
 * @version 1.0
 */
public class KeyedCaesarCypher extends CaesarCypher {
    private final Alphabet shifted;
    private final Alphabet keyed;

    /**
     * Constructor for KeyedCaesarCypher class
     * @param keyManager the keyManager that contains the key information
     * @param inputFile the fileManager for the input file
     * @param outputFile the fileManager for the output file
     * @param action action to perform (encrypt or decrypt)
     */
    public KeyedCaesarCypher(KeyManager keyManager, FileManager inputFile, FileManager outputFile, Action action) {
        super(keyManager, inputFile, outputFile, action);
        this.shifted = new Alphabet(this.shift);
        this.keyed = new Alphabet(this.stringKey);
    }

    /**
     * Overrides the abstract method inherited from Cypher class
     * Calls the encrypt method from the CaesarCypher class, with shifted
     * and keyed alphabets as arguments
     * @return encrypted cyphertext
     */
    @Override
    protected String encrypt() {
        return super.encrypt(this.shifted, this.keyed);
    }

    /**
     * Overrides the abstract method inherited from Cypher class
     * Calls the decrypt method from the CaesarCypher class, with shifted
     * and keyed alphabets as arguments
     * @return decrypted plaintext
     */
    @Override
    protected String decrypt() {
        return super.decrypt(this.shifted, this.keyed);
    }
}
