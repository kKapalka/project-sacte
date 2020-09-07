package org.example.pdf_creator.content.abstractsclasses;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import org.example.pdf_creator.content.titlehandlers.SideToSideTitleHandler;
import org.example.pdf_creator.content.titlehandlers.TopToDownTitleHandler;
import org.example.pdf_creator.content.titlehandlers.TwoColumnTitleHandler;

@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TopToDownTitleHandler.class, name = "TopToDownTitleHandler"),
        @JsonSubTypes.Type(value = SideToSideTitleHandler.class, name = "SideToSideTitleHandler"),
        @JsonSubTypes.Type(value = TwoColumnTitleHandler.class, name = "TwoColumnTitleHandler")
})
public interface ITitleHandler {

    Div handleTitleAndSubtitle(Div div, Paragraph title, Paragraph subtitle);

}
