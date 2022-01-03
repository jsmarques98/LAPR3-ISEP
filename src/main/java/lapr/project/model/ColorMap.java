package lapr.project.model;

import lapr.project.utils.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorMap {
    private List<Position> vertices;
    private List<Position> capitais;
    private Map<String, String> graphColor;
    private Map<Position, List<Position>> bordersMap;
    private List<String> colors;

    public ColorMap() {
        graphColor = new HashMap<>();
        colors = new ArrayList<>();
        capitais = new ArrayList<>();
    }


    public String getColoredMap(Graph<Position, Double> G) {
        vertices = G.vertices();

        getListOfCapitalVertices(vertices);


        bordersMap = getMapOfBordersFromMatrix(capitais, G);


        for (Position v : capitais) {
            fillListColors();
            List<Position> borderCountries = bordersMap.get(v);

            if (borderCountries == null) {
                graphColor.put(v.getCountryName(), colors.get(1));
            } else {
                for (int i = 0; i < borderCountries.size(); i++) {
                    Position p = borderCountries.get(i);
                    if (graphColor.get(p.getCountryName()) != null) {
                        colors.remove(graphColor.get(p.getCountryName()));
                    }
                }
                graphColor.put(v.getCountryName(), colors.get(1));
            }
        }
        return toStringMapColors();
    }

    public List<Position> getListOfCapitalVertices(List<Position> vertices) {
        for (Position p : vertices) {
            if (p.getClass().equals(Country.class)) {
                capitais.add(p);
            }
        }
        return capitais;
    }

    public Map<Position, List<Position>> getMapOfBordersFromMatrix(List<Position> capitais, Graph<Position, Double> G) {

        Map<Position, List<Position>> aux = new HashMap<Position, List<Position>>();

        for (int i = 0; i < capitais.size(); i++) {
            for (int j = i + 1; j < capitais.size(); j++) {
                if (G.edge(capitais.get(i), capitais.get(j)) != null) {
                    if (!aux.containsKey(capitais.get(i))) {
                        aux.put(capitais.get(i), new ArrayList<>());
                    }
                    if (!aux.containsKey(capitais.get(j))) {
                        aux.put(capitais.get(j), new ArrayList<>());
                    }
                    aux.get(capitais.get(i)).add(capitais.get(j));
                    aux.get(capitais.get(j)).add(capitais.get(i));
                }
            }
        }
        return aux;
    }

    public void fillListColors() {
        colors = new ArrayList<>();
        colors.add("Branco");
        colors.add("Preto");
        colors.add("Cinzento");
        colors.add("Azul");
        colors.add("Amarelo");
        colors.add("Verde");
        colors.add("Rosa");
        colors.add("Laranja");
        colors.add("Vermelho");
        colors.add("Roxo");
        colors.add("Violeta");
        colors.add("Ciano");
        colors.add("Magenta");
        colors.add("Castanho");
        colors.add("Bege");
    }

    public String toStringMapColors() {
        String colorsMap = "";

        for (Position p : vertices)
            colorsMap += "O vértice " + p + " tem a cor " + graphColor.get(p.getCountryName()) + "\n\n";

        return colorsMap;
    }
}
