package org.example.pdf_creator.content.abstractsclasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.itextpdf.layout.element.BlockElement;
import lombok.Data;
import org.example.pdf_creator.content.FontPreset;
import org.example.pdf_creator.content.FontPresetListContainer;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import org.example.pdf_creator.content.sectionorganizers.GridTextSections;
import org.example.pdf_creator.content.sectionorganizers.ListedTextSections;
import org.example.pdf_creator.factories.TextSectionListType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Base class for representing a list of text sections.
 * Unique TextSectionLists are found by their UUID, and they contain both a list of TextSection
 * and a list of FontPreset, to pass down into TextSection classes
 */
@Data
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GridTextSections.class, name = "GridTextSections"),
        @JsonSubTypes.Type(value = ListedTextSections.class, name = "ListedTextSections"),
        @JsonSubTypes.Type(value = PdfCreationConfiguration.class, name = "PdfCreationConfiguration")
})

public abstract class TextSectionList implements FontPresetListContainer {

    public UUID uuid;
    public List<TextSection> textSectionList;
    public List<FontPreset> fontPresetList;

    /**
     * Main constructor. All constructors should use this one inside them, as this one sets UUID to randomUUID
     * and passes down fontPresets into textSections contained in a list
     * @param textSectionList list of TextSection classes
     * @param fontPresets list of presets
     */
    public TextSectionList(List<TextSection> textSectionList, List<FontPreset> fontPresets) {
        this.uuid = UUID.randomUUID();
        this.textSectionList = textSectionList;
        this.setFontPresetList(fontPresets);
    }
    public TextSectionList(List<TextSection> textSectionList) {
        this(textSectionList, new ArrayList<>());
    }
    public TextSectionList() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Method for representing content of this TextSectionList in a PDF format.
     * @return a block element to be appended into a PDF file
     */
    public abstract BlockElement createContent();

    /**
     * Custom setter for FontPresets, which sets fontPresets for every textSection in a list
     * which inherits parent fontPesets OR doesn't have its own FontPresets
     * @param fontPresetList fontPresets
     */
    public void setFontPresetList(List<FontPreset> fontPresetList) {
        this.textSectionList.forEach(el -> {
            el.setFontPresetList(fontPresetList);
        });
        this.fontPresetList = fontPresetList;
    }

    /**
     * Fail-safe method for modifying certain parameters for this TextSectionList
     * should createContent() method throw a NullPointerException.
     */
    public abstract void adjustForNullPointer();

    public void conditionallySetFontPresets(List<FontPreset> presets) {
        if(this.fontPresetList == null || this.fontPresetList != presets) {
            setFontPresetList(presets);
        }
    }

    public abstract void modifyTextSectionListParameter(Object object);

    /**
     * Method for getting this TextSectionList's type
     * @return type in an enum form.
     */
    @JsonIgnore
    public abstract TextSectionListType getTextSectionListType();

    /**
     * Method for returning selected text sections
     * @return all text sections which are selected by user for PDF exporting
     */
    @JsonIgnore
    public List<TextSection> getSelectedTextSections() {
        return this.textSectionList.stream().filter(TextSection::isSelected).collect(Collectors.toList());
    }

    /**
     * Method for returning not selected text sections
     * @return all text sections which are selected by user for PDF exporting
     */
    @JsonIgnore
    public List<TextSection> getNonSelectedTextSections() {
        return this.textSectionList.stream().filter(el -> !el.isSelected()).collect(Collectors.toList());
    }
}
