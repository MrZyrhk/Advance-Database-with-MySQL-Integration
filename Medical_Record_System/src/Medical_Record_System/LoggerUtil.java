/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Medical_Record_System;

/**
 *
 * @author ambet
 */
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {

    private static final Logger logger = Logger.getLogger("MedicalRecordSystem");

    static {
        try {
            FileHandler fileHandler = new FileHandler("errors.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            Logger logger = LoggerUtil.getLogger();
            logger.severe("Error: Unable to add patient to the database. " + e.getMessage());

            System.err.println("Error setting up logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
