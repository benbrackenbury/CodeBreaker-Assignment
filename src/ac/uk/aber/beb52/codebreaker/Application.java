package ac.uk.aber.beb52.codebreaker;

import ac.uk.aber.beb52.codebreaker.managers.FileManager;
import ac.uk.aber.beb52.codebreaker.enums.Action;
import ac.uk.aber.beb52.codebreaker.managers.CypherManager;
import ac.uk.aber.beb52.codebreaker.managers.KeyManager;
import ac.uk.aber.beb52.codebreaker.menu.Menu;

/**
 * Application class
 * Contains the main method
 * @author Ben Brackenbury
 * @version 1.0
 */
public class Application {
    private boolean loopMainMenu;
    private final CypherManager cypherManager;
    private final KeyManager keyManager;
    private final FileManager inputFile;
    private final FileManager outputFile;

    /**
     * Constructor for Application class which is called if no
     * arguments are given to main method. Passes default file paths
     * for the key, input and output files to the main constructor
     */
    public Application() {
        this("keys", "file", "output");
    }

    /**
     * Constructor for Application class, which initialises
     * the instance variables, such as file paths
     * @param keyFilePath path to the file containing the keys
     * @param inputFilePath path to the file containing the input data
     * @param outputFilePath path to the file to which data will be written
     */
    public Application(String keyFilePath, String inputFilePath, String outputFilePath) {
        this.loopMainMenu = true;
        this.keyManager = new KeyManager(keyFilePath);
        this.inputFile = new FileManager(inputFilePath);
        this.outputFile = new FileManager(outputFilePath);
        this.cypherManager = new CypherManager(this.keyManager, this.inputFile, this.outputFile);
    }

    /**
     * Displays and handles input for the main menu
     */
    private void mainMenu() {
        System.out.println("Cypher: " + this.cypherManager.getName());
        System.out.println("Input file: " + this.inputFile.getFileName());
        Menu mainMenu = new Menu("Choose an action", new String[]{
            "Change cypher",
            "Display key",
            "Change key",
            "Display input file",
            "Change input file",
            "Encrypt",
            "Decrypt",
            "Quit application"
        });
        switch (mainMenu.run()) {
            case 1 -> cypherManager.changeCypher();
            case 2 -> keyManager.displayKey();
            case 3 -> keyManager.changeKey();
            case 4 -> inputFile.displayFile();
            case 5 -> inputFile.changeFile();
            case 6 -> cypherManager.runCypher(Action.encrypt);
            case 7 -> cypherManager.runCypher(Action.decrypt);
            case 8 -> loopMainMenu = false;
            default -> System.err.println("Try again");
        }
        System.out.println();
    }

    private void run() {
        while (loopMainMenu) {
            mainMenu();
        }
        this.quit();
    }

    /**
     * Displays the message "Goodbye" and exits the application with
     * the status code 0
     */
    private void quit() {
        System.out.println("Goodbye");
        System.exit(0);
    }

    /**
     * Main method, from which the application starts
     * Calls different Application constructor based on the number of arguments
     * @param args array of 3 strings, which are paths to the key file, input file and output file respectively
     */
    public static void main(String[] args) {
        Application app;

        app = (args.length == 3)
            ? new Application(args[0], args[1], args[2])
            : new Application();

        app.run();
    }
}
