package org.example;

import org.example.pdf_creator.content.Content;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.MainSection;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.enums.LineDrawerEnum;
import org.example.pdf_creator.content.sectionorganizers.GridTextSections;
import org.example.pdf_creator.content.titlehandlers.SideToSideTitleHandler;
import org.example.pdf_creator.content.titlehandlers.TopToDownTitleHandler;
import org.example.pdf_creator.content.titlehandlers.TwoColumnTitleHandler;

import java.util.List;

public class MyMocks {
    public static List<FontPreset> presets = List.of(new FontPreset(20, new int[]{65, 105, 255}, 12, new int[]{0,0,0}), new FontPreset( 14, new int[]{65, 105, 255}, 9, new int[]{0,0,0}));

    public static PdfCreationConfiguration config = new PdfCreationConfiguration(
          List.of(new MainSection("To jest tytuł","To jest podtytuł", null, 1, new TopToDownTitleHandler(),  null, new GridTextSections(
                List.of(new Content("To jest tytuł","To jest podtytuł", null, 1, new SideToSideTitleHandler(LineDrawerEnum.DOTTED)),
                      new Content("To jest tytuł","To jest podtytuł", null, 2, new TopToDownTitleHandler()),
                      new Content("To jest tytuł","To jest podtytuł", null, 2, new TwoColumnTitleHandler(0.8f)),
                      new Content("To jest tytuł","To jest podtytuł", null, 2, new SideToSideTitleHandler(LineDrawerEnum.DOTTED)),
                      new Content("To jest tytuł","To jest podtytuł", null, 2, new SideToSideTitleHandler(LineDrawerEnum.DOTTED)),
                      new Content("To jest tytuł","To jest podtytuł testowy, dlatego sprawdzimy jak to wygląda tutaj", null, 2, new TwoColumnTitleHandler(0.4f))),
                 3
          ), true,presets
          )), presets);

}
