package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.example.controllers.ConfigElementController;
import org.example.controllers.ConfigListController;
import org.example.controllers.PDFConfigurationLoaderController;
import org.example.pdf_creator.FontPresetPickerDialogBox;
import org.example.pdf_creator.PdfCreationEngine;
import org.example.pdf_creator.content.*;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    private PdfCreationEngine engine;
    private FontStyleSingleton fontCreator;
    public static List<TextSection> currentNestOfTextSections = new ArrayList<>();
    private static PdfCreationConfiguration pdfCreationConfiguration;
    private MenuHandler menuHandler;
    static FXMLLoader loader;
    public static BorderPane borderPane = new BorderPane();

    private PDFConfigurationLoaderController PDFConfigurationLoaderController = new PDFConfigurationLoaderController();

    public App() {
        AppSingletonAccessPoint.app = this;
    }


    @Override
    public void start(Stage stage) throws IOException {
        this.engine = new PdfCreationEngine();
        menuHandler = new MenuHandler();
        menuHandler.addMenuItemToMenu(0, "Zapisz", this.PDFConfigurationLoaderController::saveConfiguration,false, KeyCode.S);
        menuHandler.addMenuItemToMenu(0, "Wczytaj", this.PDFConfigurationLoaderController::loadConfigurationFromFile,true, KeyCode.L);
        menuHandler.addMenuItemToMenu(0, "Eksportuj do PDF", this::saveFile,false, KeyCode.P);
        borderPane.setTop(menuHandler.getMenuBar());
        borderPane.setCenter(loadFXML("hello"));
        scene = new Scene(borderPane, 900, 600);
        stage.setScene(scene);
        stage.show();
        App.stage = stage;
        fontCreator = FontStyleSingleton.getInstance();
    }

    public static void setRoot(String fxml) throws IOException {
        borderPane.setCenter(loadFXML(fxml));
        switch(fxml) {
            case "config-element":
                initForTextSection(currentNestOfTextSections.get(currentNestOfTextSections.size()-1));
                break;
            case "config-list":
                initForTextSectionList(currentNestOfTextSections.size() == 0 ? pdfCreationConfiguration : currentNestOfTextSections.get(currentNestOfTextSections.size()-1).getTextSectionList());
                break;
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    private Parent localLoadFXML(String fxml) {
        loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        try {
            return loader.load();
        } catch (IOException e) {
            System.out.println("Problems occured while loading file: " + fxml + ".fxml");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void saveFile() {
        if(App.pdfCreationConfiguration == null) {
            System.out.println("No file configuration detected. Save aborted");
        } else {
            try {
                fontCreator.setStandardFont(PdfFontFactory.createFont(StandardFonts.HELVETICA, PdfEncodings.CP1250));
                fontCreator.setBoldFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD, PdfEncodings.CP1250));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(engine.displaySavePDFDialog(stage, App.pdfCreationConfiguration));
        }
    }

    public void setConfig(PdfCreationConfiguration config) {
        App.pdfCreationConfiguration = config;
    }

    static void initForTextSection(TextSection section) {
        ((ConfigElementController) loader.getController()).init(section);
    }
    static void initForTextSectionList(TextSectionList textSectionList) {
        ((ConfigListController) loader.getController()).init(textSectionList);
    }
}