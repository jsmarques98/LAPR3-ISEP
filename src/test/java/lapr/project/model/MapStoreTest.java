package lapr.project.model;

import lapr.project.model.Country;
import lapr.project.ui.usMenu.ColourMapUI;
import lapr.project.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapStoreTest {

    public MapStoreTest() {
    }

    /**
     * Test of addPais method
     */
    @Test
    void testAddPais() {
        System.out.println("Test addPais");
        MapStore instance = new MapStore();

        Country portugal = new Country("Europe","PT","PRT","Portugal",10.31,"Lisbon",38.71666667,-9.133333);
        Country noruega = new Country("Europe","NO","NOR","Norway",5.26,"Oslo",59.91666667,10.75);
        instance.addPais(portugal);
        instance.addPais(noruega);
        int keyEsperada = 1;
        int key = instance.grafo.getKey(noruega);
        assertEquals(keyEsperada,key);
        System.out.println("Graph content after insertion of Portugal and Norway: ");
        for(Country p: instance.grafo.vertices()){
            System.out.println("Country: " + p.getCountry() + ", Continente: " + p.getContinent() + ", Capital: " + p.getCapital());
        }
    }

    /**
     * Test of addFronteira method
     */
    @Test
    void addFronteira() {
        System.out.println("Test addFronteira");
        System.out.println("Portugal and Spain are added and the elements checked with getEdge:");
        MapStore instance = new MapStore();

        Country p1 = new Country("Europe","PT","PRT","Portugal",10.31,"Lisbon",38.71666667,-9.133333);
        Country p2 = new Country("Europe","ES","ESP","Spain",46.53,"Madrid",40.4,-3.683333);
        double distancia = Utils.calcularDistancia(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
        instance.addFronteira(p1, p2, distancia);

        Country origem = instance.grafo.edge(p1, p2).getVOrig();
        Country destino = instance.grafo.edge(p1, p2).getVDest();

        System.out.println(instance.grafo.edge(p1, p2).getVOrig() + " <--> " + instance.grafo.edge(p1, p2).getVDest());
        assertEquals(origem, p1);
        assertEquals(destino, p2);
    }

    /**
     * Test of getPaisByNome method
     */
    @Test
    void getPaisByCountry() {
        System.out.println("Test getPaisByCountry");
        System.out.println("Returns the object of type Pais through the string of its name:");
        String country = "Denmark";
        MapStore instance = new MapStore();
        instance.reader.PaisReader("src/main/resources/csv/countries.csv");

        Country expResult = new Country("Europe","DK","DNK","Denmark",5.75,"Copenhagen",55.66666667,12.583333);
        Country result = instance.getPaisByCountry(country);

        System.out.println("String : " + country);
        System.out.println("País: " + result.getCountry() + ", " + result.getContinent() + ", " + result.getCapital() + ", " + result.getPopulation() + "M de habitantes.");
        assertEquals(expResult, result);
    }

    /**
     * Test of getPaisByKey method
     */
    @Test
    void getPaisByKey() {
        System.out.println("Test getPaisByKey");
        System.out.println("Returns the object of type Pais through its identifying key in the graph:");
        MapStore instance = new MapStore();
        instance.reader.PaisReader("src/main/resources/csv/countries.csv");

        Country chipre = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        Country lituania = new Country("Europe","LT","LTU","Lithuania",2.85,"Vilnius",54.68333333,25.316667);
        Country uruguai = new Country("America","UY","URY","Uruguay",3.35,"Montevideo",-34.85,-56.166667);

        int keyChipre = instance.grafo.getKey(instance.getPaisByCountry("Cyprus"));
        int keyLituania = instance.grafo.getKey(instance.getPaisByCountry("Lithuania"));
        int keyUruguai = instance.grafo.getKey(instance.getPaisByCountry("Uruguay"));
        //int keyChipre = instance.grafo.getKey(chipre);
        //int keyLituania = instance.grafo.getKey(lituania);
        //int keyUruguai = instance.grafo.getKey(uruguai);

        Country p1 = instance.getPaisByKey(keyChipre);
        Country p2 = instance.getPaisByKey(keyLituania);
        Country p3 = instance.getPaisByKey(keyUruguai);
        System.out.println("Cyprus key on the map:  " + keyChipre);
        System.out.println("Country returned via key [" + keyChipre + "]: " + instance.getPaisByKey(keyChipre).getCountry().toUpperCase() + ", Capital: " + instance.getPaisByKey(keyChipre).getCapital());
        System.out.println("Key of Lithuania on the map:  " + keyLituania);
        System.out.println("Country returned via key [" + keyLituania+"]: " + instance.getPaisByKey(keyLituania).getCountry().toUpperCase() + ", Capital: " + instance.getPaisByKey(keyLituania).getCapital());
        System.out.println("Uruguay Key on the map:  " + keyUruguai);
        System.out.println("Country returned via key [" + keyUruguai + "]: " + instance.getPaisByKey(keyUruguai).getCountry().toUpperCase() + ", Capital: " + instance.getPaisByKey(keyUruguai).getCapital());
        assertEquals(chipre, p1);
        assertEquals(lituania, p2);
        assertEquals(uruguai, p3);
    }

    /**
     * Test of colorirMapa method
     */
    @Test
    void colorirMapa() {
        System.out.println("Test colorirMapa");
        MapStore instance = new MapStore();
        instance.reader.PaisReader("src/main/resources/csv/countries.csv");
        instance.reader.FronteirasReader("src/main/resources/csv/borders.csv");
        instance.colorirMapa();
    }
}