package lapr.project.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateReader {
    public static LocalDateTime readDate(BufferedReader sc, String msg) throws IOException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.print("Format: DD/MM/YYYY HH:MM\n");
        System.out.print(msg);
        LocalDateTime dateTime;

        String str = sc.readLine();

        try {
            dateTime = LocalDateTime.parse(str, format);
        } catch (Exception e) {
            System.out.print("Invalid date.\n");
            return null;
        }
        return dateTime;
    }
}
