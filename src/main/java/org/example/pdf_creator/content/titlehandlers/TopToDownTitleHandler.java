package org.example.pdf_creator.content.titlehandlers;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pdf_creator.content.abstractsclasses.ITitleHandler;

@Data
@NoArgsConstructor
public class TopToDownTitleHandler implements ITitleHandler {
    @Override
    public Div handleTitleAndSubtitle(Div div, Paragraph title, Paragraph subtitle) {
        div.add(title);
        if(subtitle != null) {
            div.add(subtitle);
        }
        return div;
    }
}
