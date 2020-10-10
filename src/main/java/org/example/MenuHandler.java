package org.example;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Generator class for Topbar menu.
 * Populates menu with top-most menu sections
 * and provides a method for dynamically adding new element to each menu section,
 */
public class MenuHandler {
    MenuBar menuBar;

    public MenuHandler() {
        this.menuBar = new MenuBar();
        Menu menu = new Menu("Plik");
        this.menuBar.getMenus().add(menu);
    }
    MenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * Method for dynamically adding new element to a menu section
     * @param menuIndex index of menu section, starting from 0
     * @param menuItemString name of a menu element
     * @param listener listener to be executed on selecting this menu element
     * @param addDivider check, whether a line divider should be added after this element
     * @param keyCode hotkey for this menu element, will be combined with Ctrl key
     */
    void addMenuItemToMenu(int menuIndex, String menuItemString, OnMenuItemClickListener listener, boolean addDivider,
          KeyCode keyCode) {
        Menu menu = this.menuBar.getMenus().get(menuIndex);
        menu.getItems().add(createMenuItem(menuItemString, listener, keyCode));
        if(addDivider) {
            menu.getItems().add(new SeparatorMenuItem());
        }
    }


    private MenuItem createMenuItem(String menuItemString, OnMenuItemClickListener listener, KeyCode keyCode) {
        MenuItem menuItem = new MenuItem(menuItemString);
        menuItem.setOnAction(actionEvent -> listener.onClick());
        if(keyCode != null) {
            menuItem.setAccelerator(new KeyCodeCombination(keyCode, KeyCombination.CONTROL_DOWN));
        }
        return menuItem;
    }

    public interface OnMenuItemClickListener {
        void onClick();
    }
}
