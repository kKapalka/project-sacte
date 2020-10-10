package org.example.pdf_creator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import org.example.App;
import org.example.ModalDialogOnCloseListener;
import org.example.controllers.FontPresetPickerDialogBoxController;
import org.example.pdf_creator.content.FontPreset;

import java.io.IOException;

/**
 * Class for opening a font preset picker dialog box.
 * Handles the display of dialog box as a modal window
 */
@Data
public class FontPresetPickerDialogBox {

    public FontPresetPickerDialogBox(FontPreset parent, Stage parentStage, ModalDialogOnCloseListener listener) {
        this.parent = parent;
        this.parentStage = parentStage;
        this.listener = listener;
    }

    private FontPreset parent;
    private Stage parentStage;
    ModalDialogOnCloseListener listener;

    public void open() {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("font-preset-setter.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.setTitle("My modal window");
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(App.stage);
        dialog.show();
        ((FontPresetPickerDialogBoxController)loader.getController()).initValues(parent, (value) -> {
            dialog.close();
            listener.execute(value);
        });
    }
}
