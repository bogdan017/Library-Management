package services;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String AUDIT_FILE = "audit_log.csv";

    public static void logAction(String actionName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(AUDIT_FILE, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            writer.println(actionName + "," + timestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
