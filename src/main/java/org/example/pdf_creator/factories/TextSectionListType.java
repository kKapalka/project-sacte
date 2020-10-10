package org.example.pdf_creator.factories;

import com.itextpdf.layout.property.ListNumberingType;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;
import org.example.pdf_creator.content.sectionorganizers.GridTextSections;
import org.example.pdf_creator.content.sectionorganizers.ListedTextSections;

/**
 * ListType enum used for converting between TextSectionList classes.
 */
public enum TextSectionListType {
    GRID{
        @Override
        public TextSectionList createList(TextSectionList baseList) {
            return new GridTextSections(baseList.textSectionList, baseList.fontPresetList, 3);
        }
    },
    LIST{
        @Override
        public TextSectionList createList(TextSectionList baseList) {
            return new ListedTextSections(baseList.textSectionList, baseList.fontPresetList, ListNumberingType.DECIMAL);
        }
    },
    PDFCONFIG{
        @Override
        public TextSectionList createList(TextSectionList baseList) {
            return new PdfCreationConfiguration(baseList.textSectionList, baseList.fontPresetList);
        }
    };

    public abstract TextSectionList createList(TextSectionList baseList);
}
