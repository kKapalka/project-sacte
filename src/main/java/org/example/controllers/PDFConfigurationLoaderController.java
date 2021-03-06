package org.example.controllers;

import java.io.File;
import java.io.IOException;
import org.example.App;
import org.example.AppSingletonAccessPoint;
import org.example.pdf_creator.JSONReadWriteUtils;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.enums.MessageCode;

import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for the loading panel for PdfCreationConfiguration - hello.fxml
 * Supplies methods for: creating new configuration file, loading a configuration from file,
 * and transition from 'hello.fxml' to 'config-list.fxml'
 */
@Slf4j
public class PDFConfigurationLoaderController {

    private static final String JSON_EXTENSION_EXPRESSION = "JSON files (*.json)";
    private static final String JSON_EXTENSION = "*.json";

    public void saveConfiguration() {
        if(App.pdfCreationConfiguration == null) {
            log.error("No file configuration detected. Save aborted");
        } else {
            if(App.pdfCreationConfiguration.getSelectedTextSections().size() == 0) {
                log.error("User has selected no sections to save. Save aborted");
            } else {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(JSON_EXTENSION_EXPRESSION,
                      JSON_EXTENSION);
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(App.stage);
                if(file != null) {
                    log.debug(JSONReadWriteUtils
                          .writePDFConfigurationToFile(App.pdfCreationConfiguration, file.getAbsolutePath())
                          .toString());
                } else {
                    log.debug(MessageCode.OPERATION_ABORTED.toString());
                }
            }
        }
    }

    /**
     * Method for loading PdfCreationConfiguration from a JSON file.
     * Opens a FileChooser dialog opening .JSON files only,
     * reads it as object of this class, and transitions to 'config-list.fxml'
     */
    public void loadConfigurationFromFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(JSON_EXTENSION_EXPRESSION, JSON_EXTENSION);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(App.stage);
        if (file != null) {
            loadConfiguration(JSONReadWriteUtils.readPdfConfigurationFromFile(file.getAbsolutePath()));
        }
    }

    /**
     * Method for loading empty PdfCreationConfiguration
     */
    public void loadEmptyConfiguration() {
        loadConfiguration(new PdfCreationConfiguration());
    }

    private void loadConfiguration(PdfCreationConfiguration configuration) {
        AppSingletonAccessPoint.app.setConfig(configuration);
        try {
            App.setRoot("config-list");
        } catch (IOException e) {
            log.error("Error while opening 'config-list' view file: "+e);
        }
    }

}
