package org.example.pdf_creator.content.titlehandlers;

import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.property.TabAlignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pdf_creator.content.abstractsclasses.ITitleHandler;
import org.example.pdf_creator.content.enums.LineDrawerEnum;

/**
 * Title handler used for displaying title and subtitle like
 * | TITLE --------- subtitle |
 * Type of title-subtitle separation can be selected from:
 * Solid Line, Dotted Line
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SideToSideTitleHandler implements ITitleHandler {

    private LineDrawerEnum lineDrawerEnum;

    @Override
    public Div handleTitleAndSubtitle(Div div, Paragraph title, Paragraph subtitle) {
        Paragraph oneParagraph = title.setMarginLeft(5f);
        if(subtitle != null) {
            oneParagraph.addTabStops(new TabStop(1000, TabAlignment.RIGHT, lineDrawerEnum.getLineDrawer()));
            oneParagraph.add(new Tab()).add(subtitle);
        }
        oneParagraph.setMarginRight(5f);
        div.add(oneParagraph);
        return div;
    }
}
