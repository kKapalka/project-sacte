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

/**
 * Class used to save supplied PdfCreationConfiguration as a PDF file itself.
 * Handles: display of PDF saving dialog box,
 * and creation of PDF document itself
 */
public class PdfCreationEngine {

    /**
     * Method for displaying PDF export dialog window, and initializing PDF document creation
     * @param stage app stage
     * @param configuration configuration for PDF file export
     * @return OPERATION_ABORTED if aborted, otherwise refer to createPdfDocument method
     */
    public MessageCode displaySavePDFDialog(Stage stage, PdfCreationConfiguration configuration) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
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
                while(!noNullPointer) {
                    try{
                        document.add(element.prepareDiv());
                        noNullPointer = true;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        element.getTextSectionList().adjustForNullPointer();
                    }
                }
                if(((MainSection)element).isHasLineDivider()) {
                    SolidLine line = new SolidLine();
                    line.setColor(ColorConstants.BLACK);
                    line.setLineWidth(1f);
                    LineSeparator ls = new LineSeparator(line);
                    ls.setMargins(5f, 30f, 5f, 30f);
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
