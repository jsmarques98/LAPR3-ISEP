package lapr.project.controller;

import lapr.project.model.Country;
import lapr.project.model.MapStore;
import lapr.project.utils.ShipSummary;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.exit;

public class ColourMapController {

    private MapStore mapa;

    public ColourMapController(MapStore mapa) {
        this.mapa = mapa;
    }

    /**
     * Método utilizado para ler os países de um ficheiro .csv e adicionar as mesmas ao grafo da classe central
     *
     * @param fileName nome do ficheiro .csv
     */
    public void PaisReader(String fileName) {
        try {
            Country p = null;
            String line;
            int lineCounter = 1;
            try (BufferedReader csvFile = new BufferedReader(new FileReader(fileName))) {
                line = csvFile.readLine();
                while ((line = csvFile.readLine()) != null) {
                    String[] leitura = line.split(",");
                    try {
                        String continent = leitura[0];
                        String alpha2Code = leitura[1];
                        String alpha3Code = leitura[2];
                        String country = leitura[3];
                        double population = Double.parseDouble(leitura[4]);
                        String capital = leitura[5];
                        double latitude = Double.parseDouble(leitura[6]);
                        double longitude = Double.parseDouble(leitura[7]);

                        p = new Country(continent, alpha2Code, alpha3Code, country, population, capital, latitude, longitude);
                        mapa.grafo.addVertex(p);
                    } catch (Exception e) {
                        System.out.println("Invalid data format on line " + lineCounter + ".");
                    }
                    lineCounter++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file 'Countries' don't exist. Please, confirm it!");
            e.printStackTrace();
            exit(0);
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    /**
     * Método utilizado para ler as fronteiras de um ficheiro .csv e adicionar as mesmas como ramos do grafo entre os países
     *
     * @param fileName nome do ficheiro .csv
     */
    public void FronteirasReader(String fileName) {
        try {
            ShipSummary ss = new ShipSummary();
            String line;
            int lineCounter = 1;
            try (BufferedReader csvFile = new BufferedReader(new FileReader(fileName))) {
                line = csvFile.readLine();
                while ((line = csvFile.readLine()) != null) {
                    String[] leitura = line.split(",");
                    try {
                        String country1 = leitura[0];
                        String country2 = leitura[1].trim();

                        Country c1 = mapa.getPaisByCountry(country1);
                        Country c2 = mapa.getPaisByCountry(country2);

                        double distancia = ss.distanciaDelta(c1.getLatitude(), c1.getLongitude(), c2.getLatitude(), c2.getLongitude());
                        mapa.addFronteira(c1, c2, distancia);

                    } catch (Exception e) {
                        System.out.println("Invalid port in country data on line " + lineCounter + ".");
                    }
                    lineCounter++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file 'Borders' don't exist. Please, confirm it!");
            e.printStackTrace();
            exit(0);
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}
