package ac.uk.aber.beb52.codebreaker.menu;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Menu class
 * Handles the creation, displaying and input of menus
 * @author Ben Brackenbury
 * @version 1.0
 */
public class Menu {
    private final String prompt;
    private final String[] options;
    private final ArrayList<MenuItem> menuItems;

    /**
     * Constructor for Menu class
     * @param prompt the prompt to display to the user
     * @param options array of strings to display the menu's options
     */
    public Menu(String prompt, String[] options) {
        this.prompt = prompt;
        this.options = options;
        this.menuItems = new ArrayList<>();
    }

    /**
     * Constructor for Menu class
     * Called if no prompt is given - prompt instance variable is set to null
     * @param options array of strings to display the menu's options
     */
    public Menu(String[] options) {
        this(null, options);
    }

    /**
     * Creates menu items for each option and calls the displayItems() method
     * @return number of the chosen option as an integer
     */
    public int run() {
        int optionsLength = 0;
        for (String option : options) optionsLength++;
        for (int i=0; i<optionsLength; i++) {
            MenuItem newItem = new MenuItem(i+1, options[i]);
            this.menuItems.add(newItem);
        }
        int response = this.displayItems();
        return response;
    }

    private int displayItems() {
        if (this.prompt != null)  {
            System.out.println(this.prompt);
        }
        menuItems.forEach(System.out::println);
        System.out.print("> ");
        Scanner in = new Scanner(System.in);
        int response = 0;
        try{
            response = in.nextInt();
            in.close();
            return response;
        } catch (Exception e) {
            in.close();
            return -1;
        }
    }

}