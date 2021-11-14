package lapr.project.utils;

import lapr.project.model.DynamicShip;
import lapr.project.model.Ship;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;


public class CsvReader {

    // verificar se um barco existe - através do mmsi/imo/callsign

    private static int existShip(int mmsi, ArrayList<Ship> shipArray) {

        for (int i = 0; i < shipArray.size(); i++) {
            if (shipArray.get(i).getMmsi() == mmsi) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<Ship> readCSV(String path) throws Exception {
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        ArrayList<Ship> shipArray = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                DynamicShip ds = new DynamicShip(
                        LocalDateTime.parse(values[1], formatDate), // base date time
                        Double.parseDouble(values[2]), // latitude
                        Double.parseDouble(values[3]), // longitude
                        Double.parseDouble(values[4]), // sog
                        Double.parseDouble(values[5]), //cog
                        Double.parseDouble(values[6]), //heading
                        values[15].charAt(0)); //Transcriever class
                int index = existShip(Integer.parseInt(values[0]), shipArray);
                if (index == -1) { // se o barco não existir
                    int imo = cutImo(values[8]);
                    int cargo = fixCargo(values[14]);
                    Ship ship = new Ship(
                            Integer.parseInt(values[0]), // mmsi
                            null, values[7], // name
                            imo, // imo
                            values[9], // callsign
                            Integer.parseInt(values[10]), // vessel
                            Double.parseDouble(values[11]), // length
                            Double.parseDouble(values[12]), // width
                            Double.parseDouble(values[13]),// draft)
                            cargo); //cargo
                    ship.initializeDynamicShip();
                    ship.addDynamicShip(ds);
                    shipArray.add(ship);
                } else { // se o barco existir
                    shipArray.get(index).addDynamicShip(ds);
                }
            }
        }catch (FileNotFoundException e){
                System.out.println("File not found!");
            }
            return sortByDate(shipArray);
        }


    public static ArrayList<Ship> sortByDate(ArrayList<Ship> shipArray) throws Exception {

        for (int i = 0; i < shipArray.size(); i++) {
            ArrayList<DynamicShip> sortedArray = (ArrayList<DynamicShip>) shipArray.get(i).getDynamicShip().stream()
                    .sorted(Comparator.comparing(DynamicShip::getBaseDateTime))
                    .collect(Collectors.toList());

            shipArray.get(i).setShipData(sortedArray);
        }

        return shipArray;
    }


    /**
     * Method to take the first 3 chars (IMO) of the integer so that we can cast the value
     */
    private static int cutImo (String imo){
        String temp = imo.substring(3, imo.length());
        return Integer.parseInt(temp);
    }

    /**
     * Method to verify if the cargo is a char or a value, if it's a char it returns 0
     * so that we can cast the value to an integer
     */
    private static int fixCargo (String value){
        if (value.equals("NA")) {
            return 0;
        }
        return Integer.parseInt(value);
    }
}

