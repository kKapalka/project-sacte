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

public class ConfigListController {

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
        listHandlerChoiceBox.getItems().addAll(TextSectionListType.GRID, TextSectionListType.LIST);
        listHandlerChoiceBox.setValue(textSectionList.getTextSectionListType());
        listHandlerChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            this.textSectionList = listHandlerChoiceBox.getItems().get(new_value.intValue()).createList(currentTextSectionList);
        });
        ObservableList<String> data = FXCollections.observableArrayList(currentTextSectionList.fontPresets.stream().map(FontPreset::toListViewString).collect(Collectors.toList()));
        fontPresetListView.setItems(data);
        fontPresetListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                List<FontPreset> fontPresetList = currentTextSectionList.fontPresets;
                FontPreset preset = fontPresetList.stream().filter(el -> el.equalsString(fontPresetListView.getSelectionModel()
                        .getSelectedItem())).findFirst().get();
                FontPresetPickerDialogBox dialogBox = new FontPresetPickerDialogBox(preset.clone(), App.stage, (value) -> {
                    int index =  fontPresetList.indexOf(fontPresetList.stream().filter(el -> el.getUuid().equals(((FontPreset)value).getUuid())).findFirst().get());
                    fontPresetList.remove(index);
                    fontPresetList.add(index, (FontPreset)value);
                    currentTextSectionList.setFontPresets(fontPresetList);
                    ObservableList<String> data1 = FXCollections.observableArrayList(fontPresetList.stream().map(FontPreset::toListViewString).collect(Collectors.toList()));
                    fontPresetListView.setItems(data1);
                });
                dialogBox.open();
            }
        });
        newPresetButton.setOnMouseClicked((event) -> {
            new FontPresetPickerDialogBox(new FontPreset(), App.stage, (value -> {
                List<FontPreset> fontPresets = this.textSectionList.getFontPresets();
                fontPresets.add((FontPreset) value);
                this.textSectionList.setFontPresets(fontPresets);
                ObservableList<String> data1 = FXCollections.observableArrayList(fontPresets.stream().map(FontPreset::toListViewString).collect(Collectors.toList()));
                fontPresetListView.setItems(data1);

            })).open();
        });
        listHandlerChoicePane.setVisible(!(currentTextSectionList instanceof PdfCreationConfiguration));

    }


}
