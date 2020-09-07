package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;
import org.example.ModalDialogOnCloseListener;
import org.example.pdf_creator.content.FontPreset;

import java.util.regex.Pattern;

public class FontPresetPickerDialogBoxController {

    @FXML
    private TextField titleSizeField;

    @FXML
    private TextField subtitleSizeField;

    @FXML
    private ColorPicker titleColorInput;

    @FXML
    private ColorPicker subtitleColorInput;

    @FXML
    private Button confirmChanges;

    private FontPreset parent;

    Pattern validNumberText = Pattern.compile("(\\d*)");

    public void initValues(FontPreset preset, ModalDialogOnCloseListener listener) {
        this.parent = preset;
        titleSizeField.setTextFormatter(produceNewNumberFormatter());
        subtitleSizeField.setTextFormatter(produceNewNumberFormatter());
        titleSizeField.setText(""+parent.getTitleSize());
        subtitleSizeField.setText(""+parent.getSubtitleSize());
        int[] titleColor = parent.getTitleColorRgba();
        int[] subtitleColor = parent.getSubtitleColorRgba();
        Color colorTitle = new Color((float)titleColor[0]/255f, (float)titleColor[1]/255f, (float)titleColor[2]/255f, 1.0f);
        titleColorInput.getCustomColors().add(colorTitle);
        titleColorInput.setValue(colorTitle);
        Color colorSubtitle = new Color((float)subtitleColor[0]/255f, (float)subtitleColor[1]/255f, (float)subtitleColor[2]/255f, 1.0f);
        subtitleColorInput.getCustomColors().add(colorSubtitle);
        subtitleColorInput.setValue(colorSubtitle);

        confirmChanges.setOnAction((ev) -> {
            parent.setTitleSize(Integer.parseInt(titleSizeField.getText()));
            parent.setSubtitleSize(Integer.parseInt(subtitleSizeField.getText()));
            Color c = subtitleColorInput.getValue();
            parent.setSubtitleColorRgba(new int[]{(int)(c.getRed()*255f), (int)(c.getGreen()*255f), (int)(c.getBlue()*255f)});
            c = titleColorInput.getValue();
            parent.setTitleColorRgba(new int[]{(int)(c.getRed()*255f), (int)(c.getGreen()*255f), (int)(c.getBlue()*255f)});
            listener.execute(parent);
        });
    }

    private TextFormatter produceNewNumberFormatter() {
        return new TextFormatter<>(new NumberStringConverter(), 0, change -> {
            String newText = change.getControlNewText();
            if (validNumberText.matcher(newText).matches()) {
                return change ;
            } else return null ;
        });
    }
}
