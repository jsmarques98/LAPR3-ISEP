package lapr.project.utils;

import lapr.project.model.Ship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CsvReader {

    public static Map<Integer, Ship> readCSV() throws Exception {

        String path = "src\\main\\java\\lapr\\project\\data\\sships.csv";

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Map<Integer, Ship> shipMap = new HashMap();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){

            //BufferedReader br = new BufferedReader(new FileReader(path));

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                int imo = cutImo(values[8]);
                int cargo = fixCargo(values[14]);

                Ship ship = new Ship(
                        Integer.parseInt(values[0]),
                        LocalDateTime.parse(values[1], formatDate),
                        Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]),
                        Double.parseDouble(values[4]),
                        Double.parseDouble(values[5]),
                        Double.parseDouble(values[6]),
                        values[7], // name
                        imo, // imo
                        values[9], // callsign
                        Integer.parseInt(values[10]), // vessel
                        Double.parseDouble(values[11]), // length
                        Double.parseDouble(values[12]), // width
                        Double.parseDouble(values[13]), // draft)
                        cargo, // cargo
                        values[15].charAt(0));
                shipMap.put(Integer.parseInt(values[0]), ship);
            }
        }
        return shipMap;
    }
    private static int cutImo(String imo) {
        String temp = imo.substring(3, imo.length());
        return Integer.parseInt(temp);
    }
    private static int fixCargo(String value) {
        if(value.equals("NA")){
            return 0;
        }
        return Integer.parseInt(value);
    }
}

