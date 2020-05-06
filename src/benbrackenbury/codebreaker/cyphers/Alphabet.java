package benbrackenbury.codebreaker.cyphers;

/**
 * Alphabet class
 * Represents an alphabet of characters
 * @author Ben Brackenbury
 * @version 1.0
 */
public class Alphabet {
    private char[] chars;

    /**
     * Constructor for Alphabet class
     * @param chars the array of characters to create the alphabet
     */
    public Alphabet(char[] chars) {
        this.chars = chars;
    }

    /**
     * Constructor for Alphabet class
     * Creates a shifted alphabets
     * @param shift the value to shift the alphabet by
     */
    public Alphabet(int shift) {
        this();
        this.shift(shift);
    }

    /**
     * Constructor for Alphabet class
     * Creates a a standard alphabet with a key prepended to it
     * @param key the key to prepend to the alphabet
     */
    public Alphabet(String key) {
        this();
        this.prepend(key);
    }

    /**
     * Constructor for Alphabet class
     * Creates a standard alphabet of A-Z
     */
    public Alphabet() {
        this.chars = standardAlphabet();
    }

    private void shift(int shift) {
        char[] output = new char[26];
        for (int i=0; i<26;i++) {
            char newChar;
            int charIndex = chars[i] - 'A';
            int shiftedIndex = (charIndex + shift) % 26;
            newChar = (char) (shiftedIndex + 'A');
            output[i] = newChar;
        }
        this.chars = output;
    }

    private void prepend(String key) {
        String strippedKey = stripKeyOfDuplicates(key);
        StringBuilder output = new StringBuilder();
        output.append(strippedKey);
        char[] strKeyChars = key.toCharArray();
        for (char currentChar : chars) {
            boolean isDuplicate = output.toString().contains(Character.toString(currentChar));
            if (!isDuplicate) {
                output.append(currentChar);
            }
        }
        this.chars = output.toString().toCharArray();
    }

    private String stripKeyOfDuplicates(String key) {
        StringBuilder strippedStringKey = new StringBuilder();
        char[] strKeyChars = key.toCharArray();
        for (char keyChar : strKeyChars) {
            boolean isDuplicate = strippedStringKey.toString().contains(Character.toString(keyChar));
            if (!isDuplicate) {
                strippedStringKey.append(keyChar);
            }
        }
        return strippedStringKey.toString();
    }

    private char[] standardAlphabet() {
        char[] output = new char[26];
        for (int i=1; i<27; i++) {
            output[i-1] = (char) (64+i);
        }
        return output;
    }

    /**
     * @return the alphabet as a string
     */
    @Override
    public String toString() {
        return new String(chars);
    }

    /**
     * @return the alphabet as an array of characters
     */
    public char[] toCharArray() {
        return chars;
    }

    /**
     * @return the number of characters in the alphabet
     */
    public int getLength() {
        return this.chars.length;
    }
}
