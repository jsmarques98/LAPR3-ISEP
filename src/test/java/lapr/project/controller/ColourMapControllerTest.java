package lapr.project.controller;

import lapr.project.data.MapStore;
import lapr.project.model.Country;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ColourMapControllerTest {

    public ColourMapControllerTest() {
    }

    /**
     * Test of PaisReader method
     */
    @Test
    void paisReader() {
        System.out.println("\nTest countryReader\n");
        String fileName = "borderReaderTest";
        MapStore m = new MapStore();
        m.reader.paisReader(fileName);

        int numEsperado = 0;                         //número de países no ficheiro .csv
        int paisesNoFicheiro = m.grafo.numVertices(); //depois da leitura, quantidade de países no grafo

        for (Country p : m.grafo.vertices()) {
            System.out.printf("Country: %14s\tCapital: %s\n", p.getCountry(), p.getCapital());
        }
        assertEquals(numEsperado, paisesNoFicheiro);
    }

    /**
     * Test of FronteirasReader method, of class ReaderManager.
     */
    @Test
    void fronteirasReader() {
        System.out.println("\nTest bordersReader\n--------------------------------------------");
        String fileName = "borderReaderTest";
        MapStore m = new MapStore();
        m.reader.paisReader("countryReaderTest");
        m.reader.fronteirasReader(fileName);

        int esperado = 0;             //número de ramos no ficheiro .csv
        int n = m.grafo.numEdges();     //depois da leitura, a quantidade de ramos que está em numEdges
        ArrayList<Country>[] arrayAdjacentes = new ArrayList[m.grafo.numVertices()];

        for (int u = 0; u < m.grafo.numVertices(); u++) {
            arrayAdjacentes[u] = new ArrayList();
            for (Country p : m.grafo.adjVertices(m.getPaisByKey(u))) {
                arrayAdjacentes[u].add(p);
            }
        }
        int index = 0;
        for (ArrayList<Country> lista : arrayAdjacentes) {
            System.out.println(m.getPaisByKey(index).getCountry() + " - " + lista);
            index++;
        }
        assertEquals(esperado, n);
    }
}