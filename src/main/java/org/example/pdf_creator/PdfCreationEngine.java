package org.example.pdf_creator;

import java.io.File;
import java.io.FileOutputStream;

import org.example.pdf_creator.content.MainSection;
import org.example.pdf_creator.content.enums.MessageCode;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Class used to save supplied PdfCreationConfiguration as a PDF file itself.
 * Handles: display of PDF saving dialog box,
 * and creation of PDF document itself
 */
@Slf4j
public class PdfCreationEngine {

    // Offsets for created line dividers, in pixels
    private static final float LINE_OFFSET_LEFT_RIGHT = 30f;
    private static final float LINE_OFFSET_TOP_DOWN = 5f;
    // Extension expressions for .pdf file chooser
    private static final String PDF_EXTENSION_EXPRESSION = "PDF files (*.pdf)";
    private static final String PDF_EXTENSION = "*.pdf";

    private static final int MAXIMUM_NPE_ADJUSTMENT_COUNT = 8;

    /**
     * Method for displaying PDF export dialog window, and initializing PDF document creation
     * @param stage app stage
     * @param configuration configuration for PDF file export
     * @return OPERATION_ABORTED if aborted, otherwise refer to createPdfDocument method
     */
    public MessageCode displaySavePDFDialog(Stage stage, PdfCreationConfiguration configuration) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(PDF_EXTENSION_EXPRESSION, PDF_EXTENSION);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            MessageCode code = this.createPDFDocument(file, configuration);
            return code;
        }
        return MessageCode.OPERATION_ABORTED;
    }

    /**
     * Method used for creating PDF document
     * A file is created, and then each element of a configuration's text section list
     * spits out its content into it, separated by a black line if required.
     * @param file file to save PDF file under
     * @param configuration configuration for PDF file export
     * @return OPERATION_FAILED if failed, OPERATION_SUCCESSFUL if successful
     */
    MessageCode createPDFDocument(File file, PdfCreationConfiguration configuration) {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            configuration.getSelectedTextSections().forEach(element -> {
                boolean noNullPointer = false;
                int i = 0;
                while(!noNullPointer) {
                    try{
                        document.add(element.prepareDiv());
                        noNullPointer = true;
                    } catch (NullPointerException e) {
                        log.error("Error as occured while preparing div from "+element+"." +
                                  " Performing adjustment method...");
                        if(i > MAXIMUM_NPE_ADJUSTMENT_COUNT) {
                            log.error("Maximum Null Pointer Adjustment method call count exceeded. Throwing runtime " +
                                      "exception...");
                            log.error("Faulty code: "+e);
                            throw new RuntimeException("Null Pointer Adjustments are faulty. Forcing app shutdown...");
                        } else {
                            element.getTextSectionList().adjustForNullPointer();
                            i++;
                        }
                    }
                }
                if(((MainSection)element).isHasLineDivider()) {
                    SolidLine line = new SolidLine();
                    line.setColor(ColorConstants.BLACK);
                    line.setLineWidth(1f);
                    LineSeparator ls = new LineSeparator(line);
                    ls.setMargins(LINE_OFFSET_TOP_DOWN, LINE_OFFSET_LEFT_RIGHT, LINE_OFFSET_TOP_DOWN, LINE_OFFSET_LEFT_RIGHT);
                    document.add(ls);
                }
            });
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            return MessageCode.OPERATION_FAILED;
        }
        return MessageCode.OPERATION_SUCCESFUL;
    }

}
