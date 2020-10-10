package org.example.controllers;

import javafx.stage.FileChooser;
import org.example.App;
import org.example.AppSingletonAccessPoint;
import org.example.pdf_creator.JSONReadWriteUtils;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.enums.MessageCode;

import java.io.File;
import java.io.IOException;

public class PDFConfigurationLoaderController {

    public void saveConfiguration() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(App.stage);
        System.out.println(App.pdfCreationConfiguration);
        if (file != null) {
            System.out.println(JSONReadWriteUtils.writePDFConfigurationToFile(App.pdfCreationConfiguration,
                    file.getAbsolutePath()));
        } else {
            System.out.println(MessageCode.OPERATION_ABORTED);
        }
    }

    public void loadConfigurationFromFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(App.stage);
        if (file != null) {
            loadConfiguration(JSONReadWriteUtils.readPdfConfigurationFromFile(file.getAbsolutePath()));
        }
    }

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
