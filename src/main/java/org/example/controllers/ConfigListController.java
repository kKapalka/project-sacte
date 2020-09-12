package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;
import org.example.pdf_creator.factories.TextSectionListType;

public class ConfigListController {

    @FXML
    private Pane listHandlerChoicePane;
    @FXML
    private ChoiceBox<TextSectionListType> listHandlerChoiceBox;

    private TextSectionList textSectionList;


    public void init(TextSectionList currentTextSectionList) {
        this.textSectionList = currentTextSectionList;
        listHandlerChoiceBox.getItems().addAll(TextSectionListType.GRID, TextSectionListType.LIST);
        listHandlerChoiceBox.setValue(textSectionList.getTextSectionListType());
        listHandlerChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            this.textSectionList = listHandlerChoiceBox.getItems().get(new_value.intValue()).createList(currentTextSectionList);
        });
        listHandlerChoicePane.setVisible(!(currentTextSectionList instanceof PdfCreationConfiguration));

    }


}
