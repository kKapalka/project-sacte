package org.example.pdf_creator.content.titlehandlers;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pdf_creator.content.abstractsclasses.ITitleHandler;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwoColumnTitleHandler implements ITitleHandler {

    private float columnsRatio;

    @Override
    public Div handleTitleAndSubtitle(Div div, Paragraph title, Paragraph subtitle) {
        Table table = new Table(2, false).setMarginLeft(5f).setMarginRight(5f).useAllAvailableWidth();
        UnitValue firstCell = new UnitValue(table.getWidth().getUnitType(), table.getWidth().getValue());
        UnitValue secondCell = new UnitValue(table.getWidth().getUnitType(), table.getWidth().getValue());
        firstCell.setValue(firstCell.getValue() * columnsRatio);
        secondCell.setValue(secondCell.getValue() * (1 - columnsRatio));
        table.addCell(new Cell().setWidth(firstCell).add(title).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().setWidth(secondCell).add(subtitle).setBorder(Border.NO_BORDER));
        div.add(table);
        return div;
    }
}
