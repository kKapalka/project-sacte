package org.example.pdf_creator.content;

import com.itextpdf.layout.element.BlockElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;
import org.example.pdf_creator.factories.TextSectionListType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PdfCreationConfiguration extends TextSectionList {

    public PdfCreationConfiguration(List<TextSection> sections) {
        this.fontPresets = new ArrayList<>();
        this.textSectionList = sections;
        if(this.textSectionList.size() > 1) {
            Collections.sort(this.textSectionList);
        }
    }
    public PdfCreationConfiguration() {
        this(new ArrayList<>());
    }
    @Override
    public BlockElement createContent() {
        return null;
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
