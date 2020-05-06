package ac.uk.aber.beb52.codebreaker.managers;

import java.io.*;
import java.util.Scanner;

/**
 * FileManager class
 * Handles the loading and saving of input and output files
 * @author Ben Brackenbury
 * @version 1.0
 */
public class FileManager {
    private File file;
    private String fileName;
    private String fileData;

    /**
     * Constructor for FileManager class
     * @param fileName the path of the file
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
        this.init();
    }

    private void init() {
        try {
            this.loadFile();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load file \"" + fileName + "\"");
            System.err.println(e.toString());
            changeFile();
        }
    }

    private void loadFile() throws FileNotFoundException {
        this.file = new File(fileName);
        Scanner scanner = new Scanner(file);
        StringBuilder fileData = new StringBuilder("");
        while (scanner.hasNext()) {
            char[] block = scanner.next().toCharArray();
            for (char c : block) {
                if (Character.isLetter(c)) {
                    fileData.append(c);
                }
            }
        }
        this.fileData = fileData.toString().toUpperCase();
    }

    /**
     * Writes data to the file
     * @param data the data to write to the file
     * @param append whether to append to the file (false means overwrite)
     * @throws IOException if the file cannot be found
     */
    public void writeFile(String data, boolean append) throws IOException {
        Writer fileWriter = new FileWriter(this.fileName, append);
        fileWriter.write(data);
        fileWriter.write("\n");
        fileWriter.close();
        this.init();
        System.out.println("Successfully wrote to file " + this.fileName);
    }

    /**
     * Changes the file
     * Asks user for the path of the new file and recalls the private init() method with the new information
     */
    public void changeFile() {
        System.out.println("Enter path of file");
        Scanner in = new Scanner(System.in);
        String newFileName = in.nextLine();
        this.file = null;
        this.fileData = null;
        this.fileName = newFileName;
        this.init();
    }

    /**
     * Prints the name of the file and its data to the console
     */
    public void displayFile() {
        System.out.print(this.getFileName(true));
        System.out.println(":");
        System.out.println(this.fileData);
    }

    /**
     * @return the name of the file
     */
    public String getFileName() {
        return this.file.getName();
    }

    /**
     * Returns the name of the file either with or without also displaying its path
     * @param verbose pass in true to return the name and path of the file
     * @return
     */
    public String getFileName(Boolean verbose) {
        return verbose
                ? "\"" + getFileName()+  "\" at \'./" + fileName + "\'"
                : getFileName();
    }

    /**
     * @return the data contained in the file
     */
    public String getFileData() {
        return this.fileData;
    }
}
