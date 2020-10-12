package org.example.controllers;

import javafx.stage.FileChooser;
import org.example.App;
import org.example.AppSingletonAccessPoint;
import org.example.MyMocks;
import org.example.pdf_creator.JSONReadWriteUtils;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.enums.MessageCode;

import java.io.File;
import java.io.IOException;

/**
 * Controller for the loading panel for PdfCreationConfiguration - hello.fxml
 * Supplies methods for: creating new configuration file, loading a configuration from file,
 * and transition from 'hello.fxml' to 'config-list.fxml'
 */
public class PDFConfigurationLoaderController {

    public void saveConfiguration() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(App.stage);
        if (file != null) {
            System.out.println(JSONReadWriteUtils.writePDFConfigurationToFile(App.pdfCreationConfiguration,
                    file.getAbsolutePath()));
        } else {
            System.out.println(MessageCode.OPERATION_ABORTED);
        }
    }

    /**
     * Method for loading PdfCreationConfiguration from a JSON file.
     * Opens a FileChooser dialog opening .JSON files only,
     * reads it as object of this class, and transitions to 'config-list.fxml'
     */
    public void loadConfigurationFromFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
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
        loadConfiguration(MyMocks.config);
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
