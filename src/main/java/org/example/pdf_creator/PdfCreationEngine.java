package org.example.pdf_creator;

import java.io.File;
import java.io.FileOutputStream;
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

public class PdfCreationEngine {

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

    MessageCode createPDFDocument(File file, PdfCreationConfiguration configuration) {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            configuration.getSections().forEach(element -> {
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
                if(element.isHasLineDivider()) {
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
