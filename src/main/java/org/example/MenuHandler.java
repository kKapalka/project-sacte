package org.example;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

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
