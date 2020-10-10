package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.pdf_creator.content.abstractsclasses.TextSection;

/**
 * Controller for config-element.fxml
 * Handles: applying listeners on Font Preset List View,
 * listeners on Title Handler Selector,
 * modifying Title/Subtitle text in a specific TextSection,
 * and transition to Parent / Child TextSectionList
 */
public class ConfigElementController extends MainPanelController {

    @FXML
    private ListView<String> fontPresetListView;

    private TextSection currentTextSection;

    /**
     * Method run upon transition into this .fxml element
     * Used to set up UI elements and populate them with values from inside the TextSection
     * @param currentTextSection opened text section
     */
    public void init(TextSection currentTextSection) {
        this.currentTextSection = currentTextSection;
        setUpFontPresetListViewWithParameters(this.fontPresetListView, this.currentTextSection);
    }

    private void popFromNest() {

    }

}
