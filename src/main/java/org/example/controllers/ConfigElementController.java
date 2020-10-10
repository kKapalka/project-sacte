package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.App;
import org.example.pdf_creator.FontPresetPickerDialogBox;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.abstractsclasses.TextSection;

import java.util.List;
import java.util.stream.Collectors;

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
        setUpFontPresetListView();
    }

    private void popFromNest() {

    }

    /**
     * Method for setting up Font Preset List View.
     * First it grabs all font presets for this text section and puts their string representation into list
     * then applies a listener on a double click, which opens a FontPresetPickerDialogBox in which
     * user can edit already existing font preset
     */
    private void setUpFontPresetListView() {
        setUpFontPresetListViewWithParameters(this.fontPresetListView, this.currentTextSection);
    }
}
