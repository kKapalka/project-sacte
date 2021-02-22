package org.example.pdf_creator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.example.pdf_creator.content.enums.MessageCode;
import org.example.pdf_creator.content.PdfCreationConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class used for reading and writing PdfCreationConfiguration objects
 * to and from JSON files
 */
@Slf4j
public class JSONReadWriteUtils {

    /**
     * Method for writing PdfCreationConfiguration.
     * Creates a file at the supplpied path,
     * and writes supplied PdfCreationConfiguration as string into it
     * @param configuration configuration to be saved
     * @param filePath path to file where configuration needs to be saved
     * @return OPERATION_FAILED if failed, OPERATION_SUCCESFUL if succesful
     */
    public static MessageCode writePDFConfigurationToFile(PdfCreationConfiguration configuration, String filePath) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(new ObjectMapper().writeValueAsString(configuration));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return MessageCode.OPERATION_FAILED;
        }
        return MessageCode.OPERATION_SUCCESFUL;
    }

    /**
     * Method for writing PdfCreationConfiguration.
     * Accesses a file at the supplpied path,
     * and reads its contents as PdfCreationConfiguration.
     * VERY strict format, so be careful
     * @param filePath path to configuration file
     * @return configuration found under the file
     */
    public static PdfCreationConfiguration readPdfConfigurationFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath))
        {
            PdfCreationConfiguration configuration = new ObjectMapper().readValue(reader,
                  PdfCreationConfiguration.class);
            return configuration;
        } catch (FileNotFoundException e) {
            log.debug("File not found: "+e);
        } catch (IOException e) {
            log.debug("IO Exception: "+e);
        }
        return null;
    }

}
