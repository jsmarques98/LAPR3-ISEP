package lapr.project.utils;

import lapr.project.data.Ship;
import lapr.project.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class CsvReader {
    private static ArrayList<Port> portArray = new ArrayList<>();
    private CsvReader(){}

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
                int cargo = fixCargo(values[14]);
                DynamicShip ds = null;

                    ds = new DynamicShip(
                            LocalDateTime.parse(values[1], formatDate), // base date time
                            Double.parseDouble(values[2]), // latitude
                            Double.parseDouble(values[3]), // longitude
                            Double.parseDouble(values[4]), // sog
                            Double.parseDouble(values[5]), //cog
                            Double.parseDouble(values[6]),//heading
                            cargo,//cargo
                            values[15].charAt(0)//Transcriever class
                    );

                int index = existShip(Integer.parseInt(values[0]), shipArray);
                if (index == -1) { // se o barco não existir
                    int imo = cutImo(values[8]);

                    Ship ship = new Ship(
                            Integer.parseInt(values[0]), // mmsi
                            null, values[7], // name
                            imo, // imo
                            values[9], // callsign
                            Integer.parseInt(values[10]), // vessel
                            Double.parseDouble(values[11]), // length
                            Double.parseDouble(values[12]), // width
                            Double.parseDouble(values[13]),// draft)
                            1,//nr_gen
                            10,//cap
                            1//gen_po
                            );
                    ship.initializeDynamicShip();
                        ship.addDynamicShip(ds);

                    shipArray.add(ship);
                } else { // se o barco existir
                    shipArray.get(index).addDynamicShip(ds);
                }
            }
        }catch (FileNotFoundException e){
            Logger.getLogger(CsvReader.class.getName())
                    .log(Level.SEVERE, null, e);
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


    public static ArrayList<Port> readPorts(String path) throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Port port = new Port(
                        values[0],          //continent
                        values[1],          //country
                        Integer.parseInt(values[2]),    //code
                        values[3],          //port
                        Double.parseDouble(values[4]),  //lat
                        Double.parseDouble(values[5])); //lon

                portArray.add(port);
            }
            return portArray;

        }catch (FileNotFoundException e){
            return new ArrayList<>();
        }

    }


    public static ArrayList<Country> readCountry(String path){
        ArrayList<Country> countryArray = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Country country = new Country(
                        values[0],  //continent
                        values[1],  //alpha2code
                        values[2],  //alpha3code
                        values[3],  //country
                        Double.parseDouble(values[4]),  //population
                        values[5],  //capital
                        Double.parseDouble(values[6]),  //latitude
                        Double.parseDouble(values[7])   //longitude
                );

                countryArray.add(country);
            }
            return countryArray;

        }catch (IOException e){
            return new ArrayList<>();
        }
    }

    private static boolean findOnPort(int id){
        for (Port p: portArray) {
            if (p.getCode()==id){
                return true;
            }
        }
        return false;
    }
    public static ArrayList<SeaDist> readSeaDist(String path){
        ArrayList<SeaDist> seaDistArray = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                SeaDist seaDist;
                String[] values = line.split(",");
                if(findOnPort(Integer.parseInt(values[1]))&&findOnPort(Integer.parseInt(values[4]))){
                     seaDist = new SeaDist(
                            values[0],  //fromCountry
                            Integer.parseInt(values[1]),  //fromPortId
                            values[2],  //fromPort
                            values[3],  //toCountry
                            Integer.parseInt(values[4]),  //toPortId
                            values[5],  //toPort
                            Double.parseDouble(values[6])  //seaDistance
                    );
                    seaDistArray.add(seaDist);
                }

            }
            return seaDistArray;

        }catch (IOException e){
            Logger.getLogger(CsvReader.class.getName())
                    .log(Level.SEVERE, null, e);
            return new ArrayList<>();
        }
    }


    public static ArrayList<Border> readBorder(String path){
        ArrayList<Border> borderArray = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                values[1] = values[1].replaceFirst(" ","");
                Border border = new Border(
                        values[0],  //Country1
                        values[1]  //Country2
                );

                borderArray.add(border);

            }
            return borderArray;

        }catch (IOException e){
            return new ArrayList<>();
        }
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

