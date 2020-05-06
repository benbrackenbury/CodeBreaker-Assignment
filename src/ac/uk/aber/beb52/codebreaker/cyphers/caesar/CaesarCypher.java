package ac.uk.aber.beb52.codebreaker.cyphers.caesar;

import ac.uk.aber.beb52.codebreaker.cyphers.Alphabet;
import ac.uk.aber.beb52.codebreaker.cyphers.Cypher;
import ac.uk.aber.beb52.codebreaker.enums.Action;
import ac.uk.aber.beb52.codebreaker.managers.FileManager;
import ac.uk.aber.beb52.codebreaker.managers.KeyManager;

/**
 * CaesarCypher class
 * Represents a Caesar cypher
 * @author Ben Brackenbury
 * @version 1.0
 */
public class CaesarCypher extends Cypher {
    int shift;

    /**
     * Constructor for CaesarCypher class
     * @param keyManager the keyManager that contains the key information
     * @param inputFile the fileManager for the input file
     * @param outputFile the fileManager for the output file
     * @param action action to perform (encrypt or decrypt)
     */
    public CaesarCypher(KeyManager keyManager, FileManager inputFile, FileManager outputFile, Action action) {
        super(keyManager, inputFile, outputFile, action);
        this.shift = this.keyManager.getIntKey();
    }

    /**
     * Overrides the abstract method inherited from Cypher class
     * Creates a standard and a shifted alphabet and runs the crossRefrence method on them
     * @return encrypted cyphertext
     */
    @Override
    protected String encrypt() {
        Alphabet stdAlphabet = new Alphabet(); //standard A-Z
        Alphabet shiftedAlphabet = new Alphabet(this.shift); //shifted
        return this.crossReference(stdAlphabet, shiftedAlphabet);
    }

    String encrypt(Alphabet shiftedAlphabet, Alphabet keyedAlphabet) {
        return this.crossReference(shiftedAlphabet, keyedAlphabet);
    }

    /**
     * Overrides the abstract method inherited from Cypher class
     * Creates a standard and a shifted alphabet and runs the crossRefrence method on them
     * @return decrypted plaintext
     */
    @Override
    protected String decrypt() {
        Alphabet stdAlphabet = new Alphabet(); //standard A-Z
        Alphabet shiftedAlphabet = new Alphabet(this.shift); //shifted
        return this.crossReference(shiftedAlphabet, stdAlphabet);
    }

    String decrypt(Alphabet shiftedAlphabet, Alphabet keyedAlphabet) {
        return this.crossReference(keyedAlphabet, shiftedAlphabet);
    }
}
