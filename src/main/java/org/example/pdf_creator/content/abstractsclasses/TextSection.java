package org.example.pdf_creator.content.abstractsclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.scene.text.Font;
import org.example.pdf_creator.content.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type")
        @JsonSubTypes({
            @JsonSubTypes.Type(value = MainSection.class, name = "MainSection"),
            @JsonSubTypes.Type(value = Subsection.class, name = "Subsection"),
            @JsonSubTypes.Type(value = Content.class, name = "TopToDownContent")
})
public abstract class TextSection implements Comparable<TextSection>{
    private UUID uuid;
    private String title;
    private String subtitle;
    private List<String> tags;
    private int priority;
    private ITitleHandler titleHandler;
    public List<FontPreset> fontPresets;
    private boolean usingParentFontPresets;

    public TextSection(String title, String subtitle, List<String> tags, int priority, ITitleHandler titleHandler, List<FontPreset> fontPresets) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.subtitle = subtitle;
        this.tags = tags;
        this.priority = priority;
        this.titleHandler = titleHandler;
        this.setUsingParentFontPresets(true);
        this.setFontPresets(fontPresets);
    }

    @Override
    public int compareTo(TextSection o) {
        return priority - o.priority;
    }

    public IBlockElement prepareDiv() {
        Div div = new Div();
        div = titleHandler.handleTitleAndSubtitle(div, createTitle(), createSubtitle());
        div.add(new Paragraph(""));
        BlockElement content = createContent();
        if(content != null) {
            div.add(content);
        }
        return div;
    }

    public Paragraph createTitle() {
        FontStyleSingleton instance = FontStyleSingleton.getInstance();
        FontPreset fontPreset = getSectionFontPreset();
        Color textColor = new DeviceRgb(fontPreset.getTitleColorRgba()[0],fontPreset.getTitleColorRgba()[1],
              fontPreset.getTitleColorRgba()[2]);
        Paragraph para = new Paragraph(title)
              .setFont(instance.getBoldFont())
              .setFontSize(fontPreset.getTitleSize())
              .setFontColor(textColor)
              .setMultipliedLeading(0.5f);
        return para;
    }

    public Paragraph createSubtitle() {
        Paragraph para = null;
        if(subtitle != null) {
            FontPreset fontPreset = getSectionFontPreset();
            FontStyleSingleton instance = FontStyleSingleton.getInstance();
            Color textColor = new DeviceRgb(fontPreset.getSubtitleColorRgba()[0],fontPreset.getSubtitleColorRgba()[1],
                  fontPreset.getSubtitleColorRgba()[2]);
            para = new Paragraph(subtitle)
                  .setFont(instance.getStandardFont())
                  .setFontSize(fontPreset.getSubtitleSize())
                  .setFontColor(textColor);
        }
        return para;
    }

    public BlockElement createContent() {
        return null;
    }

    @JsonIgnore
    public FontPreset getSectionFontPreset() {
        return fontPresets.get(0);
    }

    @JsonIgnore
    public List<FontPreset> getChildrenFontPresets() {

        List<FontPreset> childrenFontPresets = new ArrayList<>();
        fontPresets.forEach(el -> childrenFontPresets.add(el.clone()));
        if(fontPresets.size() > 1) {
            childrenFontPresets.remove(0);
        }
        return childrenFontPresets;
    }

    public TextSectionList getTextSectionList() {
        return new TextSectionList() {
            @Override
            public BlockElement createContent() {
                return null;
            }

            @Override
            public void adjustForNullPointer() {

            }
        };
    }
}
