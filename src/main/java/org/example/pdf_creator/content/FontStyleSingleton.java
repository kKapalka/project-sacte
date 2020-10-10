package org.example.pdf_creator.content;

import com.itextpdf.kernel.font.PdfFont;

import lombok.Getter;
import lombok.Setter;

/**
 * Singleton class containing a PdfFont used for generating a PDF file
 */
public class FontStyleSingleton {

    private static FontStyleSingleton instance;
    @Setter
    @Getter
    private PdfFont standardFont;

    @Setter
    @Getter
    private PdfFont boldFont;

    public static FontStyleSingleton getInstance() {
        if(instance == null) {
            synchronized(FontStyleSingleton.class) {
                if(instance == null) {
                    instance = new FontStyleSingleton();
                }
            }
        }
        return instance;
    }
}
