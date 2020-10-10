package org.example.pdf_creator.content;

import java.util.List;

import org.example.pdf_creator.content.abstractsclasses.ITitleHandler;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import com.itextpdf.layout.element.BlockElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;

/**
 * Type of TextSection which has its own TextSectionList
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Subsection extends TextSection {
    private TextSectionList textSectionList;

    public Subsection(String title, String subtitle, List<String> tags, int priority, ITitleHandler titleHandler, TextSectionList contentList,
                      List<FontPreset> fontPresets) {
        super(title, subtitle, tags, priority, titleHandler, fontPresets);
        this.setContentList(contentList);
    }

    public Subsection(String title, String subtitle, List<String> tags, int priority, ITitleHandler titleHandler, TextSectionList contentList) {
        super(title, subtitle, tags, priority, titleHandler, null);
        this.setContentList(contentList);
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
        if(textSectionList != null) {
            textSectionList.conditionallySetFontPresets(getChildrenFontPresets());
        }
    }
    public void setContentList(TextSectionList list) {
        this.textSectionList = list;
        if(this.fontPresetList != null) {
            textSectionList.conditionallySetFontPresets(getChildrenFontPresets());
        }
    }

    @Override
    public TextSectionList getTextSectionList() {
        return textSectionList;
    }
}
