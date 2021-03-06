package org.example.pdf_creator.content.titlehandlers;

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
import org.example.pdf_creator.factories.TitleHandlerType;

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
    private static final float TITLE_HANDLER_MARGIN_SIDES = 5f;
    private static final int TAB_STOP_TAB_POSITION = 1000;

    @Override
    public Div handleTitleAndSubtitle(Div div, Paragraph title, Paragraph subtitle) {
        Paragraph oneParagraph = title.setMarginLeft(TITLE_HANDLER_MARGIN_SIDES);
        if(subtitle != null) {
            oneParagraph.addTabStops(new TabStop(TAB_STOP_TAB_POSITION, TabAlignment.RIGHT, lineDrawerEnum.getLineDrawer()));
            oneParagraph.add(new Tab()).add(subtitle);
        }
        oneParagraph.setMarginRight(TITLE_HANDLER_MARGIN_SIDES);
        div.add(oneParagraph);
        return div;
    }

    @Override
    public TitleHandlerType getTitleHandlerType() {
        return TitleHandlerType.SIDE_TO_SIDE;
    }
}
