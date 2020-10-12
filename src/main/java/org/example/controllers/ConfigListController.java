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
import org.example.pdf_creator.content.MainSection;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.Subsection;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
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
 *
 * TODO section removing with confirmation
 * TODO and transition to and from text section
 * TODO and hiding / showing text sections with tags.
 */
public class ConfigListController extends MainPanelController {

    @FXML
    private Pane listHandlerChoicePane;
    @FXML
    private ChoiceBox<TextSectionListType> listHandlerChoiceBox;
    @FXML
    private ListView<String> fontPresetListView;
    @FXML
    private ListView<String> allSectionsListView;
    @FXML
    private ListView<String> selectedSectionsListView;

    private TextSection highlightedTextSection;

    private TextSectionList textSectionList;

    /**
     * Method run upon transition into this .fxml element
     * Used to set up UI elements and populate them with values from inside the TextSectionList
     * @param currentTextSectionList opened text section list
     */
    public void init(TextSectionList currentTextSectionList) {
        this.textSectionList = currentTextSectionList;
        setUpListHandlerChoiceBox();
        setUpFontPresetListViewWithParameters(this.fontPresetListView, this.textSectionList);
        conditionallyCoverUIElementsBasedOnTextSectionListType();
        updateTextSectionListViews();
    }

    /**
     * Setup method for Text Section List Type Choice Box
     * Puts values inside the choice box
     * And sets current TextSectionList to a selected type
     */
    private void setUpListHandlerChoiceBox() {
        listHandlerChoiceBox.getItems().addAll(TextSectionListType.GRID, TextSectionListType.LIST);
        listHandlerChoiceBox.setValue(textSectionList.getTextSectionListType());
        listHandlerChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            this.textSectionList = listHandlerChoiceBox.getItems().get(new_value.intValue()).createList(textSectionList);
        });
    }

    /**
     * Method run on clicking 'Add new Font Preset' button.
     * Opens a new dialog box allowing user to add new Font Preset
     */
    public void onNewPresetButtonClicked() {
        new FontPresetPickerDialogBox(new FontPreset(), App.stage, (value -> {
            List<FontPreset> fontPresets = this.textSectionList.getFontPresetList();
            fontPresets.add((FontPreset) value);
            this.textSectionList.setFontPresetList(fontPresets);
            ObservableList<String> data1 = FXCollections.observableArrayList(fontPresets.stream().map(FontPreset::toListViewString).collect(Collectors.toList()));
            fontPresetListView.setItems(data1);
        })).open();
    }

    /**
     * Method run on clicking 'Add new section' button.
     * Creates a fresh TextSection and transitions into it.
     */
    public void onNewSectionButtonClicked() {
        TextSection textSection = textSectionList.getClass().equals(PdfCreationConfiguration.class) ? new MainSection() : new Subsection();
        openSection(textSection);
    }

    /**
     * Transition method into new / child Text Section
     * TODO this
     * @param textSection textSection
     */
    private void openSection(TextSection textSection) {
        System.out.println("Transition to new section: "+textSection);
    }

    /**
     * Method run on clicking 'Move to left' button
     * Unselects highlighted Text Section
     * and updates list views
     */
    public void onMoveToLeftSelectedTextSection() {
        highlightedTextSection.setSelected(false);
        updateTextSectionListViews();
    }

    /**
     * Method run on clicking 'Move to right' button
     * Unselects highlighted Text Section
     * and updates list views
     */
    public void onMoveToRightSelectedTextSection() {
        highlightedTextSection.setSelected(true);
        updateTextSectionListViews();
    }

    /**
     * Method for updating both Text Section List Views:
     * Left one contains not selected,rRight one contains selected
     * All text sections are visible, unless there are tags selected;
     * in that case only those text sections, which have selected tags, will be displayed
     * TODO handle tags
     */
    private void updateTextSectionListViews() {
        populateTextSectionListViewWithData(allSectionsListView, textSectionList.getNonSelectedTextSections());
        populateTextSectionListViewWithData(selectedSectionsListView, textSectionList.getSelectedTextSections());
    }

    /**
     * Method for population Text Section List Views with data
     * @param listView selected list view
     * @param textSections assigned text section data
     */
    private void populateTextSectionListViewWithData(ListView<String> listView, List<TextSection> textSections) {
        ObservableList<String> data = FXCollections.observableArrayList(textSections.stream().map(TextSection::getTitle).collect(Collectors.toList()));
        listView.setItems(data);
        listView.setOnMouseClicked(click -> {
            if(click.getClickCount() == 1) {
                highlightedTextSection = textSectionList.getTextSectionList().stream().filter(el -> el.getTitle().equals(listView.getSelectionModel().getSelectedItem())).findFirst().orElse(null);
            } else if (click.getClickCount() == 2) {
                openSection(highlightedTextSection);
            }
        });
    }

    /**
     * Method for conditional covering of select UI elements.
     * Covers: ListType selection and return to parent TextSection button
     * TODO return to parent TextSection button
     */
    private void conditionallyCoverUIElementsBasedOnTextSectionListType() {
        listHandlerChoicePane.setVisible(!(this.textSectionList instanceof PdfCreationConfiguration));
    }

}
