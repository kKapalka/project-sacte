package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import org.example.App;
import org.example.pdf_creator.FontPresetPickerDialogBox;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.MainSection;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.Subsection;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;
import org.example.pdf_creator.factories.TextSectionListType;

import java.io.IOException;
import java.util.ArrayList;
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
 * TODO and hiding / showing text sections with tags.
 */
@Slf4j
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
    @FXML
    private Button returnToSectionButton;

    private TextSection highlightedTextSection;

    private TextSectionList textSectionList;

    /**
     * Method run upon transition into this .fxml element
     * Used to set up UI elements and populate them with values from inside the TextSectionList
     * @param currentTextSectionList opened text section list
     */
    public void init(TextSectionList currentTextSectionList) {
        this.textSectionList = currentTextSectionList;
        App.currentTextSectionList = currentTextSectionList;
        setUpListHandlerChoiceBox();
        setUpFontPresetListViewWithParameters(this.fontPresetListView, this.textSectionList);
        conditionallyCoverUIElementsBasedOnTextSectionListType(!(this.textSectionList instanceof PdfCreationConfiguration));
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
     * TODO apply fontPresets to new TextSection
     */
    public void onNewSectionButtonClicked() {
        TextSection textSection = textSectionList.getClass().equals(PdfCreationConfiguration.class) ? new MainSection(textSectionList.getChildrenFontPresets()) : new Subsection(textSectionList.getChildrenFontPresets());
        textSectionList.getTextSectionList().add(textSection);
        openSection(textSection);
    }

    /**
     * Transition method into new / child Text Section
     * @param textSection textSection
     */
    private void openSection(TextSection textSection) {
        App.currentNestOfTextSections.add(textSection);
        log.debug("Transition to new section: "+textSection);
        try {
            App.setRoot("config-element");
        } catch (IOException e) {
            log.error("Error while opening 'config-element' view file: "+e);
        }
    }

    /**
     * Transition to TextSection view.
     * Since TextSectionList object which can transition BACK into TextSection is a parameter of aforementioned
     * TextSection, this method simply flips the view to 'config-element.fxml' and initializes the view for it
     */
    public void returnToTextSectionView() {
        log.debug("Transition to - this - text section view");
        try {
            App.setRoot("config-element");
        } catch (IOException e) {
            log.error("Error while opening 'config-element' view file: "+e);
        }
    }

    /**
     * Method run on clicking 'Move to left' button
     */
    public void onMoveToLeftSelectedTextSection() {
        setHighlightedTextSectionAsSelected(false);
    }

    /**
     * Method run on clicking 'Move to right' button
     */
    public void onMoveToRightSelectedTextSection() {
        setHighlightedTextSectionAsSelected(true);
    }

    /**
     * Method for setting highlighted text sections as selected / unselected
     * Also updates list views, and clears highlight
     * @param selected flag for highlighted text section selection
     */
    private void setHighlightedTextSectionAsSelected(boolean selected) {
        if(highlightedTextSection != null) {
            highlightedTextSection.setSelected(selected);
            updateTextSectionListViews();
            highlightedTextSection = null;
        }
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
        List<String> textSectionListViewData = new ArrayList<>();
        for(int i = 0;i < textSections.size(); i++) {
            //very ugly, but I needed access to index of an element
            textSectionListViewData.add((i+1) + ": " + textSections.get(i).getTitle());
        }
        ObservableList<String> data =
              FXCollections.observableArrayList(textSectionListViewData);
        System.out.println(textSectionListViewData);
        listView.setItems(data);
        listView.setOnMouseClicked(click -> {
            if(click.getClickCount() == 1) {
                highlightedTextSection =
                      textSectionList.getTextSectionList().stream()
                                     .filter(el -> ((textSectionList.getTextSectionList().indexOf(el)+1)+": "+el.getTitle()).equals(listView.getSelectionModel().getSelectedItem()))
                                     .findFirst().orElse(null);
            } else if (click.getClickCount() == 2) {
                openSection(highlightedTextSection);
            }
        });
    }

    /**
     * Method for conditional covering of select UI elements.
     * Covers: ListType selection and return to parent TextSection button
     */
    private void conditionallyCoverUIElementsBasedOnTextSectionListType(boolean conditionallyVisible) {
        listHandlerChoicePane.setVisible(conditionallyVisible);
        returnToSectionButton.setVisible(conditionallyVisible);
    }

}
