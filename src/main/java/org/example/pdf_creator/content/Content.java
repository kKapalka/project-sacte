package org.example.pdf_creator.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itextpdf.layout.element.BlockElement;
import lombok.Data;
import lombok.ToString;
import org.example.pdf_creator.content.abstractsclasses.ITitleHandler;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;

import java.util.List;

/**
 * Implementation of TextSection in its base form
 */
@Data
@ToString(callSuper = true)
public class Content extends TextSection {
    public Content(String title, String subtitle, List<String> tags, int priority, ITitleHandler titleHandler, List<FontPreset> fontPresets) {
        super(title, subtitle, tags, priority, titleHandler, fontPresets);
    }

    @Override
    @JsonIgnore
    public TextSectionList getTextSectionList() {
        return null;
    }

    public Content(String title, String subtitle, List<String> tags, int priority, ITitleHandler titleHandler) {
        super(title, subtitle, tags, priority, titleHandler, null);
    }

    public Content() {
        super();
    }

    @Override
    public BlockElement createContent() {
        return null;
    }
}
