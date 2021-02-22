package org.example.controllers;

import java.io.File;
import java.io.IOException;
import org.example.App;
import org.example.AppSingletonAccessPoint;
import org.example.pdf_creator.JSONReadWriteUtils;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.enums.MessageCode;

import javafx.stage.FileChooser;

/**
 * Controller for the loading panel for PdfCreationConfiguration - hello.fxml
 * Supplies methods for: creating new configuration file, loading a configuration from file,
 * and transition from 'hello.fxml' to 'config-list.fxml'
 */
public class PDFConfigurationLoaderController {

    private static final String JSON_EXTENSION_EXPRESSION = "JSON files (*.json)";
    private static final String JSON_EXTENSION = "*.json";

    public void saveConfiguration() {
        if(App.pdfCreationConfiguration == null) {
            System.out.println("No file configuration detected. Save aborted");
        } else {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(JSON_EXTENSION_EXPRESSION,
                  JSON_EXTENSION);
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(App.stage);
            if(file != null) {
                System.out.println(JSONReadWriteUtils.writePDFConfigurationToFile(App.pdfCreationConfiguration, file.getAbsolutePath()));
            } else {
                System.out.println(MessageCode.OPERATION_ABORTED);
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
            e.printStackTrace();
        }
    }

}
