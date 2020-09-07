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

import java.util.List;

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
        this.getTextSectionList().forEach(el -> table.addCell(new Cell().add(el.prepareDiv()).setBorder(Border.NO_BORDER)));
        return table;
    }

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

}
