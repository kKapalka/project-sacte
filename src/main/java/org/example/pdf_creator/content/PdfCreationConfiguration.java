package org.example.pdf_creator.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.itextpdf.layout.element.BlockElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;

@Data
@NoArgsConstructor
public class PdfCreationConfiguration extends TextSectionList {
    public List<MainSection> sections;

    public PdfCreationConfiguration(List<MainSection> sections) {
        this.sections = sections;
        if(this.sections.size() > 1) {
            Collections.sort(this.sections);
        }
    }

    @Override
    public BlockElement createContent() {
        return null;
    }

    @Override
    public void adjustForNullPointer() {

    }

    public TextSectionList asTextSectionList() {
        return new TextSectionList(new ArrayList<>(this.sections), null) {
            @Override
            public BlockElement createContent() {
                return null;
            }

            @Override
            public void adjustForNullPointer() {

            }
        };
    }

    public PdfCreationConfiguration fromTextSectionList(TextSectionList textSectionList) {
        if(textSectionList.textSectionList.get(0).getClass() == MainSection.class) {
            this.sections = new ArrayList<>((Collection<? extends MainSection>) textSectionList.textSectionList);
        }
        return this;
    }
}
