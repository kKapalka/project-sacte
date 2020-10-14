package org.example.pdf_creator.content;

import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Div;
import lombok.Data;
import lombok.ToString;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;
import org.example.pdf_creator.factories.TextSectionListType;

import java.util.List;

/**
 * Basic representation of TextSectionList.
 * Mostly used for listing of top-most sections.
 */
@Data
@ToString(callSuper = true)
public class PdfCreationConfiguration extends TextSectionList {

    public PdfCreationConfiguration(List<TextSection> textSectionList, List<FontPreset> fontPresets) {
        super(textSectionList, fontPresets);
    }

    public PdfCreationConfiguration() {
        super();
    }

    @Override
    public BlockElement createContent() {
        Div div = new Div();
        textSectionList.forEach(element -> {
            div.add(element.createContent());
        });
        return div;
    }

    @Override
    public void adjustForNullPointer() {}

    @Override
    public void modifyTextSectionListParameter(Object object) {}

    @Override
    public TextSectionListType getTextSectionListType() {
        return TextSectionListType.PDFCONFIG;
    }

    public PdfCreationConfiguration fromTextSectionList(TextSectionList textSectionList) {
        this.textSectionList = textSectionList.textSectionList;
        return this;
    }
}
