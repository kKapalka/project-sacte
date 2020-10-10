package org.example.pdf_creator.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.UUID;

/**
 * Representation of a single TextSection's font formatting - fontSize and fontColor
 */
@Data
public class FontPreset {

    @JsonIgnore
    private UUID uuid;
    private int titleSize;
    private int[] titleColorRgba = new int[]{0,0,0};
    private int subtitleSize;
    private int[] subtitleColorRgba = new int[]{0,0,0};

    public FontPreset() {
        this.uuid = UUID.randomUUID();
    }

    public FontPreset(int titleSize, int[] titleColorRgba, int subtitleSize, int[] subtitleColorRgba) {
        super();
        this.titleSize = titleSize;
        this.setTitleColorRgba(titleColorRgba);
        this.subtitleSize = subtitleSize;
        this.setSubtitleColorRgba(subtitleColorRgba);
    }

    public void setTitleColorRgba(int[] titleColorRgba) {
        switch(titleColorRgba.length) {
            case 0:
                this.titleColorRgba = new int[]{0, 0, 0};
                break;
            case 1:
                this.titleColorRgba = new int[]{titleColorRgba[0], 0, 0};
                break;
            case 2:
                this.titleColorRgba = new int[]{titleColorRgba[0], titleColorRgba[1], 0};
                break;
            default:
                this.titleColorRgba = new int[]{titleColorRgba[0], titleColorRgba[1], titleColorRgba[2]};
                break;
        }
    }

    public void setSubtitleColorRgba(int[] titleColorRgba) {
        switch(titleColorRgba.length) {
            case 0:
                this.subtitleColorRgba = new int[]{0, 0, 0};
                break;
            case 1:
                this.subtitleColorRgba = new int[]{titleColorRgba[0], 0, 0};
                break;
            case 2:
                this.subtitleColorRgba = new int[]{titleColorRgba[0], titleColorRgba[1], 0};
                break;
            default:
                this.subtitleColorRgba = new int[]{titleColorRgba[0], titleColorRgba[1], titleColorRgba[2]};
                break;
        }
    }

    /**
     * Method for displaying this FontPreset in a concise text representation to display in application
     * @return
     */
    public String toListViewString() {
        return "[(Title: "+ Arrays.toString(titleColorRgba) +", "+titleSize+"); Subtitle: "+ Arrays.toString(subtitleColorRgba) +", "+subtitleSize+")]";
    }

    /**
     * Method for comparing its text representation with another string
     * @param otherString other string, ideally a string representation of another FontPreset
     * @return true if both string values are the same
     */
    public boolean equalsString(String otherString) {
        return this.toListViewString().equals(otherString);
    }

    /**
     * Method for cloning this FontPreset, in order to modify its values
     * without meddling in the original object
     * @return clone of FontPreset
     */
    @Override
    public FontPreset clone() {
        FontPreset clone = new FontPreset();
        clone.uuid = this.uuid;
        clone.subtitleSize = this.subtitleSize;
        clone.subtitleColorRgba = this.subtitleColorRgba;
        clone.titleSize = this.titleSize;
        clone.titleColorRgba = this.titleColorRgba;
        return clone;
    }
}
