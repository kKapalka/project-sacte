package org.example.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.example.App;
import org.example.pdf_creator.FontPresetPickerDialogBox;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.abstractsclasses.TextSection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for config-element.fxml
 * Handles: applying listeners on Font Preset List View,
 * listeners on Title Handler Selector,
 * modifying Title/Subtitle text in a specific TextSection,
 * and transition to Parent / Child TextSectionList
 */
@Slf4j
public class ConfigElementController extends MainPanelController {

    @FXML
    private ListView<String> fontPresetListView;
    @FXML
    private Button returnToSectionListButton;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea subtitleTextField;

    private TextSection currentTextSection;

    /**
     * Method run upon transition into this .fxml element
     * Used to set up UI elements and populate them with values from inside the TextSection
     * @param currentTextSection opened text section
     */
    public void init(TextSection currentTextSection) {
        this.currentTextSection = currentTextSection;
        setUpFontPresetListViewWithParameters(this.fontPresetListView, this.currentTextSection);
        titleTextField.setText(currentTextSection.getTitle());
        subtitleTextField.setText(currentTextSection.getSubtitle());
    }

    /**
     * Method run on transitioning to parent TextSectionList.
     * It pops this TextSection from the currentNestOfTextSections
     */
    private void popFromNest() {
        App.currentNestOfTextSections.remove(this.currentTextSection);
    }

    /**
     * Method run on clicking 'Add new Font Preset' button.
     * Opens a new dialog box allowing user to add new Font Preset
     */
    public void onNewPresetButtonClicked() {
        new FontPresetPickerDialogBox(new FontPreset(), App.stage, (value -> {
            List<FontPreset> fontPresets = this.currentTextSection.getFontPresetList();
            fontPresets.add((FontPreset) value);
            this.currentTextSection.setFontPresetList(fontPresets);
            ObservableList<String> data1 = FXCollections.observableArrayList(fontPresets.stream().map(FontPreset::toListViewString).collect(
                  Collectors.toList()));
            fontPresetListView.setItems(data1);
        })).open();
    }

    /**
     * Transition to TextSectionList view.
     * Since TextSection object which can transition BACK into TextSectionList is contained by aforementioned
     * TextSectionList, this method simply flips the view to 'config-list.fxml' and initializes the view for it
     */
    public void returnToTextSectionListView() {
        onNavigationAttempt();
        log.debug("Transition to - this - text section view");
        popFromNest();
        if(this.currentTextSection.getTitle().isBlank() && this.currentTextSection.getSubtitle().isBlank()) {
            App.currentTextSectionList.textSectionList.remove(this.currentTextSection);
        }
        transitionToConfigList();
    }

    /**
     * Method called upon "Open Text Section List" button press.
     * Transitions to 'config-list' view, where listed TextSections are children
     * of this TextSection
     */
    public void onTextSectionListOpenButtonClicked() {
        onNavigationAttempt();
        log.debug("Opening new text section view");
        transitionToConfigList();
    }

    /**
     * Method handling transition to 'config-list' view
     */
    private void transitionToConfigList() {
        try {
            App.setRoot("config-list");
        } catch (IOException e) {
            log.error("Error while opening 'config-list' view file: "+e);
        }
    }

    public void onNavigationAttempt() {
        currentTextSection.setTitle(titleTextField.getText());
        currentTextSection.setSubtitle(subtitleTextField.getText());
    }
}
