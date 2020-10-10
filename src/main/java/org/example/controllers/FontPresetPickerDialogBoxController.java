package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;
import org.example.ModalDialogOnCloseListener;
import org.example.pdf_creator.content.FontPreset;

import java.util.regex.Pattern;

/**
 * Controller for dialog box for font preset - file: font-preset-setter.fxml
 * Contains 2 sets of TextField and ColorPicker inputs, one for title, other for subtitle
 * TextField has input validation which blocks all non-number values.
 * Has 'cancel' and 'confirm changes'
 */
public class FontPresetPickerDialogBoxController {

    private static final Pattern validNumberText = Pattern.compile("(\\d*)");

    @FXML
    private TextField titleSizeField;
    @FXML
    private TextField subtitleSizeField;
    @FXML
    private ColorPicker titleColorInput;
    @FXML
    private ColorPicker subtitleColorInput;

    private FontPreset parent;
    private ModalDialogOnCloseListener listener;

    /**
     * Initialize dialog box using Font Preset supplied,
     * and perform supplied action upon closing using 'confirm changes' button
     * @param preset Font Preset
     * @param listener Listener, which listens on dialog box closing
     */
    public void initValues(FontPreset preset, ModalDialogOnCloseListener listener) {
        this.parent = preset;
        this.listener = listener;
        setUpFontSizeInputField(titleSizeField, parent.getTitleSize());
        setUpFontSizeInputField(subtitleSizeField, parent.getSubtitleSize());
        setUpColorInput(titleColorInput, parent.getTitleColorRgba());
        setUpColorInput(subtitleColorInput, parent.getSubtitleColorRgba());
    }

    /**
     * Method for setting up individual font size input field.
     * Applies string formatter prohibiting from putting in other values than numbers
     * and initializes it with supplied font size
     * @param textField textField to set up
     * @param fontSize initial value for this text field
     */
    private void setUpFontSizeInputField(TextField textField, int fontSize) {
        textField.setTextFormatter(new TextFormatter<>(new NumberStringConverter(), 0, change -> {
            String newText = change.getControlNewText();
            if (validNumberText.matcher(newText).matches()) {
                return change ;
            } else return null ;
        }));
        textField.setText("" + fontSize);
    }

    /**
     * Method for setting up individual color input
     * using supplied color, in an int[3] representation
     * @param colorInput input to set up
     * @param color initial color for this input
     */
    private void setUpColorInput(ColorPicker colorInput, int[] color) {
        Color colorTitle = new Color((float)color[0]/255f, (float)color[1]/255f, (float)color[2]/255f, 1.0f);
        colorInput.getCustomColors().add(colorTitle);
        colorInput.setValue(colorTitle);
    }

    /**
     * Method which runs after confirming all the changes.
     * Font Preset object is changed using values from input fields
     * and resulting object is passed to the listener and its execute(object) method is called
     */
    public void onConfirmChanges() {
        parent.setTitleSize(Integer.parseInt(titleSizeField.getText()));
        parent.setSubtitleSize(Integer.parseInt(subtitleSizeField.getText()));
        Color c = subtitleColorInput.getValue();
        parent.setSubtitleColorRgba(new int[]{(int)(c.getRed()*255f), (int)(c.getGreen()*255f), (int)(c.getBlue()*255f)});
        c = titleColorInput.getValue();
        parent.setTitleColorRgba(new int[]{(int)(c.getRed()*255f), (int)(c.getGreen()*255f), (int)(c.getBlue()*255f)});
        listener.execute(parent);
    }
}
