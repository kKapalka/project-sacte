package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.example.App;
import org.example.pdf_creator.FontPresetPickerDialogBox;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;
import org.example.pdf_creator.factories.TextSectionListType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for config-list.fxml
 * Handles: applying listeners on Font Preset List View,
 * listeners on Text Section List Type choice box,
 * button for creating a new FontPreset,
 * and transition to Parent / Child TextSection.
 * Transition to parent TextSection and choice of TextSectionListType
 * are hidden for TextSectionList of type PdfCreationConfiguration
 */
public class ConfigListController extends MainPanelController {

    @FXML
    private Pane listHandlerChoicePane;
    @FXML
    private ChoiceBox<TextSectionListType> listHandlerChoiceBox;
    @FXML
    private Button newSectionButton;
    @FXML
    private Button newPresetButton;
    @FXML
    private ListView<String> fontPresetListView;


    private TextSectionList textSectionList;


    public void init(TextSectionList currentTextSectionList) {
        this.textSectionList = currentTextSectionList;
        setUpListHandlerChoiceBox();
        setUpFontPresetListView();
        applyListenerOnNewPresetButton();
        conditionallyCoverUIElementsBasedOnTextSectionListType();

    }

    private void setUpListHandlerChoiceBox() {
        listHandlerChoiceBox.getItems().addAll(TextSectionListType.GRID, TextSectionListType.LIST);
        listHandlerChoiceBox.setValue(textSectionList.getTextSectionListType());
        listHandlerChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            this.textSectionList = listHandlerChoiceBox.getItems().get(new_value.intValue()).createList(textSectionList);
        });
    }


    /**
     * Method for setting up Font Preset List View.
     * First it grabs all font presets for this text section and puts their string representation into list
     * then applies a listener on a double click, which opens a FontPresetPickerDialogBox in which
     * user can edit already existing font preset
     */
    private void setUpFontPresetListView() {
        setUpFontPresetListViewWithParameters(this.fontPresetListView, this.textSectionList);
    }

    private void applyListenerOnNewPresetButton() {
        newPresetButton.setOnMouseClicked((event) -> {
            new FontPresetPickerDialogBox(new FontPreset(), App.stage, (value -> {
                List<FontPreset> fontPresets = this.textSectionList.getFontPresetList();
                fontPresets.add((FontPreset) value);
                this.textSectionList.setFontPresetList(fontPresets);
                ObservableList<String> data1 = FXCollections.observableArrayList(fontPresets.stream().map(FontPreset::toListViewString).collect(Collectors.toList()));
                fontPresetListView.setItems(data1);
            })).open();
        });
    }

    private void conditionallyCoverUIElementsBasedOnTextSectionListType() {
        listHandlerChoicePane.setVisible(!(this.textSectionList instanceof PdfCreationConfiguration));
    }

}
