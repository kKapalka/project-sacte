package org.example.pdf_creator.content.abstractsclasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.itextpdf.layout.element.BlockElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.sectionorganizers.GridTextSections;
import org.example.pdf_creator.content.sectionorganizers.ListedTextSections;
import org.example.pdf_creator.factories.TextSectionListType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GridTextSections.class, name = "GridTextSections"),
        @JsonSubTypes.Type(value = ListedTextSections.class, name = "ListedTextSections"),
        @JsonSubTypes.Type(value = PdfCreationConfiguration.class, name = "PdfCreationConfiguration")
})

public abstract class TextSectionList {

    public UUID uuid;
    public List<TextSection> textSectionList;
    public List<FontPreset> fontPresets;

    public TextSectionList(List<TextSection> textSectionList, List<FontPreset> fontPresets) {
        this.uuid = UUID.randomUUID();
        this.textSectionList = textSectionList;
        this.setFontPresets(fontPresets);
    }
    public TextSectionList(List<TextSection> textSectionList) {
        this(textSectionList, new ArrayList<>());
    }

    public abstract BlockElement createContent();

    public void setFontPresets(List<FontPreset> fontPresets) {
        this.textSectionList.forEach(el -> {
            el.setFontPresets(fontPresets);
        });
        this.fontPresets = fontPresets;
    }

    public abstract void adjustForNullPointer();

    public void conditionallySetFontPresets(List<FontPreset> presets) {
        if(this.fontPresets == null || this.fontPresets != presets) {
            setFontPresets(presets);
        }
    }

    public abstract void modifyTextSectionListParameter(Object object);

    @JsonIgnore
    public abstract TextSectionListType getTextSectionListType();
}
