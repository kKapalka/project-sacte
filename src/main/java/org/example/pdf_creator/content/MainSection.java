package org.example.pdf_creator.content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.example.pdf_creator.content.abstractsclasses.ITitleHandler;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Paragraph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;

/**
 * Type of TextSection which can have its own icon, create a line divider between
 * this section and another one below it, and has its own TextSectionList
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class MainSection extends TextSection {
    private File icon;
    private boolean hasLineDivider;
    TextSectionList textSectionList;

    public MainSection(String title, String subtitle, List<String> tags, int priority, ITitleHandler titleHandler,
          File icon, TextSectionList textSectionList, boolean hasLineDivider, List<FontPreset> fontPresets) {
        super(title, subtitle, tags, priority, titleHandler, fontPresets);
        this.icon = icon;
        this.setTextSectionList(textSectionList);
        this.setUsingParentFontPresets(false);
        this.hasLineDivider = hasLineDivider;
    }

    public MainSection() {
        super();
        this.icon = null;
        this.setTextSectionList(null);
        this.setUsingParentFontPresets(false);
        this.hasLineDivider = true;
    }

    @Override
    public Paragraph createTitle() {
        if(icon == null) {
            return super.createTitle();
        }
        return null;
    }

    @Override
    public BlockElement createContent() {
        if(textSectionList != null) {
            return textSectionList.createContent();
        }
        return null;
    }
    public void setFontPresetList(List<FontPreset> presets) {
        this.fontPresetList = presets;
        if(textSectionList != null && presets != null) {
            textSectionList.conditionallySetFontPresets(getChildrenFontPresets());
        }
    }
    public void setTextSectionList(TextSectionList list) {
        this.textSectionList = list;
        if(this.fontPresetList != null && list != null) {
            textSectionList.conditionallySetFontPresets(getChildrenFontPresets());
        }
    }

    @Override
    public TextSectionList getTextSectionList() {
        return textSectionList;
    }
}
