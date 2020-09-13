package org.example.pdf_creator.content.sectionorganizers;

import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.property.ListNumberingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;
import org.example.pdf_creator.factories.TextSectionListType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ListedTextSections extends TextSectionList {

    private ListNumberingType numberingType;
    public ListedTextSections(List<TextSection> textSectionList, List<FontPreset> presets, ListNumberingType numberingType) {
        super(textSectionList, presets);
        this.numberingType = numberingType;
    }

    public ListedTextSections(List<TextSection> textSectionList, ListNumberingType numberingType) {
        super(textSectionList, null);
        this.numberingType = numberingType;
    }

    @Override
    public BlockElement createContent() {
        com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List();
        if(numberingType != null) {
            list = new com.itextpdf.layout.element.List(numberingType);
        }
        final com.itextpdf.layout.element.List finalList = list;
        getSelectedTextSections().forEach(el -> {
            ListItem listItem = new ListItem();
            listItem.add(el.prepareDiv());
            finalList.add(listItem);
        });
        return finalList;
    }

    @Override
    public void adjustForNullPointer() {
        System.out.println("Still no potential NullPointerException encountered while producing sections from this list.");
    }

    @Override
    public void modifyTextSectionListParameter(Object object) {
        if(object.getClass().equals(ListNumberingType.class)) {
            this.numberingType = (ListNumberingType)object;
        } else {
            throw new UnsupportedOperationException("GridTextSections takes in ListNumberingType");
        }
    }

    @Override
    public TextSectionListType getTextSectionListType() {
        return TextSectionListType.LIST;
    }
}
