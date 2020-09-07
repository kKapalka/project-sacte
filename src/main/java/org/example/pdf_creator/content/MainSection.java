package org.example.pdf_creator.content;

import java.io.File;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    public void setFontPresets(List<FontPreset> presets) {
        this.fontPresets = presets;
        if(textSectionList != null) {
            textSectionList.conditionallySetFontPresets(getChildrenFontPresets());
        }
    }
    public void setTextSectionList(TextSectionList list) {
        this.textSectionList = list;
        if(this.fontPresets != null) {
            textSectionList.conditionallySetFontPresets(getChildrenFontPresets());
        }
    }

    @Override
    public TextSectionList getTextSectionList() {
        return textSectionList;
    }
}
