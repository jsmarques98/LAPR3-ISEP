package lapr.project.data;

import lapr.project.controller.ColourMapController;
import lapr.project.model.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MapStore {

    public ColourMapController reader;
    public MapGraph<Country, Integer> grafo;

    /**
     * Construtor da classe central mapa
     */
    public MapStore() {
        this.reader = new ColourMapController(this);
        this.grafo = new MapGraph<>(false);
    }

    /**
     * Adiciona país, ou seja, um vértice ao grafo
     * @param p país a ser inserido
     */
    public void addPais(Country p) {
        grafo.addVertex(p);
    }

    /**
     * Adiciona um ramo entre dois países, indicando a existência de uma fronteira entre os 2
     *
     * @param origem país vertice de origem
     * @param destino país vertice de destino
     * @param distancia peso do ramo (distância)
     */
    public void addFronteira(Country origem, Country destino, double distancia) {
        grafo.addEdge(origem, destino, (int) distancia);
    }

    /**
     * Método usado para devolver um País através do seu pais
     *
     * @param country nome do País
     * @return País(objecto)
     */
    public Country getPaisByCountry(String country) {
        for (Country p : grafo.vertices()) {
            if (p.getCountry().equalsIgnoreCase(country)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Método usado para devolver um País através da sua Key no grafo
     *
     * @param key key pela qual o País é identificado
     * @return País(objecto)
     */
    public Country getPaisByKey(int key) {
        for (Country p : grafo.vertices()) {
            if (grafo.getKey(p) == key) {
                return p;
            }
        }
        return null;
    }

    /**
     * Método do algoritmo Welsh-Powell para colorir o mapa seguindo o teorema matemática das 4 cores
     */
    public void colorirMapa() {

        HashMap<Country, Integer> mapaCores = new HashMap<>();
        Country[] paises = grafo.allkeyVerts();
        ArrayList<Country> arrayPaises = new ArrayList<>(Arrays.asList(paises));

        for (int i = 0; i < arrayPaises.size() - 1; i++) {
            int numeroFronteiras_i = grafo.outDegree(arrayPaises.get(i));
            for (int j = i; j < arrayPaises.size(); j++) {
                int numeroFronteiras_j = grafo.outDegree(arrayPaises.get(j));
                if (numeroFronteiras_i < numeroFronteiras_j) {
                    Country pais_i = arrayPaises.get(i);
                    arrayPaises.set(i, arrayPaises.get(j));
                    arrayPaises.set(j, pais_i);
                }
            }
        }

        int cor = 0;
        while (arrayPaises.stream().anyMatch((pais) -> (!mapaCores.containsKey(pais)))) {
            for (Country pais : arrayPaises) {
                if (!mapaCores.containsKey(pais)) {
                    boolean corDisponivel = true;
                    for (Country fronteira : grafo.adjVertices(pais)) {
                        if (mapaCores.get(fronteira) != null) {
                            if (mapaCores.get(fronteira) == cor) {
                                corDisponivel = false;
                                break;
                            }
                        }
                    }
                    if (corDisponivel) {
                        mapaCores.put(pais, cor);
                    }
                }
            }
            cor++;
        }
        System.out.println("\n--------------------------------------\n\t    Colored Map\n--------------------------------------");
        System.out.println("Colours: \nColour: 0 - Blue\nColour: 1 - Green\nColour: 2 - Yellow\nColour: 3 - Red\n--------------------------------------");
        mapaCores.entrySet().forEach(entry -> {
            System.out.printf("Country: %14s\t\tColor: %d\n", entry.getKey(), entry.getValue());
        });
    }
}
