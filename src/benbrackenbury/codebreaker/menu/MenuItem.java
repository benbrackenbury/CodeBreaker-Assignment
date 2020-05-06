package benbrackenbury.codebreaker.menu;

/**
 * MenuItem class
 * Represents an item in a menu
 * @author Ben Brackenbury
 * @version 1.0
 */
public class MenuItem {
    private final int key;
    private final String description;

    /**
     * Constructor for MenuItem class
     * @param key the unique number of the option
     * @param description a description of the option
     */
    public MenuItem(int key, String description) {
        this.key = key;
        this.description = description;
    }

    /**
     * ToString method for presenting to the user
     * @return the menu item's key and description as a single string
     */
    @Override
    public String toString() {
        return " | " + this.key + ".  " + this.description;
    }
}