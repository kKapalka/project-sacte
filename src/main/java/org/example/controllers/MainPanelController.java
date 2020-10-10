package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.example.App;
import org.example.pdf_creator.FontPresetPickerDialogBox;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.FontPresetListContainer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for controllers for config-list.fxml and config-element.fxml
 * Contains set up methods for UI elements used inside both classes:
 * Font Preset List View
 */
public class MainPanelController {

    /**
     * Method for setting up Font Preset List View.
     * First it grabs all font presets for this text section and puts their string representation into list
     * then applies a listener on a double click, which opens a FontPresetPickerDialogBox in which
     * user can edit already existing font preset
     */
    public void setUpFontPresetListViewWithParameters(ListView<String> fontPresetListView, FontPresetListContainer fontPresetListContainer) {
        ObservableList<String> data = FXCollections.observableArrayList(fontPresetListContainer.getFontPresetList().stream().map(FontPreset::toListViewString).collect(Collectors.toList()));
        fontPresetListView.setItems(data);
        fontPresetListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                List<FontPreset> fontPresetList = fontPresetListContainer.getFontPresetList();
                FontPreset preset = fontPresetList.stream().filter(el -> el.equalsString(fontPresetListView.getSelectionModel()
                        .getSelectedItem())).findFirst().get();
                FontPresetPickerDialogBox dialogBox = new FontPresetPickerDialogBox(preset.clone(), App.stage, (value) -> {
                    int index =  fontPresetList.indexOf(fontPresetList.stream().filter(el -> el.getUuid().equals(((FontPreset)value).getUuid())).findFirst().get());
                    fontPresetList.remove(index);
                    fontPresetList.add(index, (FontPreset)value);
                    fontPresetListContainer.setFontPresetList(fontPresetList);
                    ObservableList<String> data1 = FXCollections.observableArrayList(fontPresetList.stream().map(FontPreset::toListViewString).collect(Collectors.toList()));
                    fontPresetListView.setItems(data1);
                });
                dialogBox.open();
            }
        });
    }
}
