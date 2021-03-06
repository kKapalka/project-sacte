package org.example.pdf_creator.content.sectionorganizers;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.abstractsclasses.TextSection;
import org.example.pdf_creator.content.abstractsclasses.TextSectionList;
import org.example.pdf_creator.factories.TextSectionListType;

import java.util.List;

/**
 * Class used for representing TextSectionList in a grid
 * with a variable number of columns.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class GridTextSections extends TextSectionList {

    private int columnCount;

    public GridTextSections(List<TextSection> textSectionList, List<FontPreset> presets, int columnCount) {
        super(textSectionList, presets);
        this.columnCount = columnCount;
    }
    public GridTextSections(List<TextSection> textSectionList, int columnCount) {
        super(textSectionList, null);
        this.columnCount = columnCount;
    }

    @Override
    public BlockElement createContent() {
        Table table = new Table(columnCount, false);
        this.getSelectedTextSections().forEach(el -> table.addCell(new Cell().add(el.prepareDiv()).setBorder(Border.NO_BORDER)));
        return table;
    }

    /**
     * Fail-safe mechanism - if required, this method adjusts the number of columns
     * in order to be properly represented in a PDF file.
     */
    @Override
    public void adjustForNullPointer() {
        this.columnCount = Math.max(1, this.columnCount - 1);
    }

    @Override
    public void modifyTextSectionListParameter(Object object) {
        if(object.getClass().equals(Integer.class)) {
            this.columnCount = (Integer)object;
        } else {
            throw new UnsupportedOperationException("GridTextSections takes in Integer");
        }
    }

    @Override
    public TextSectionListType getTextSectionListType() {
        return TextSectionListType.GRID;
    }

}
