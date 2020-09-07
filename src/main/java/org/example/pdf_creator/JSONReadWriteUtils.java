package org.example.pdf_creator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.example.pdf_creator.content.enums.MessageCode;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONReadWriteUtils {

    public static MessageCode writePDFConfigurationToFile(PdfCreationConfiguration configuration, String filePath) {
        try (FileWriter file = new FileWriter(filePath)) {
            System.out.println(new ObjectMapper().writeValueAsString(configuration));
            file.write(new ObjectMapper().writeValueAsString(configuration));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return MessageCode.OPERATION_FAILED;
        }
        return MessageCode.OPERATION_SUCCESFUL;
    }

    public static PdfCreationConfiguration readPdfConfigurationFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath))
        {
            PdfCreationConfiguration configuration = new ObjectMapper().readValue(reader,
                  PdfCreationConfiguration.class);
            System.out.println(configuration);
            return configuration;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
