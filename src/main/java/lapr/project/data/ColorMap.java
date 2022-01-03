package lapr.project.data;

import lapr.project.model.Country;
import lapr.project.model.Position;
import lapr.project.utils.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorMap {
    private List<Position> vertices;
    private List<Position> capitals;
    private Map<String, String> graphColor;
    private Map<Position, List<Position>> bordersMap;
    private List<String> colors;

    public ColorMap() {
        graphColor = new HashMap<>();
        colors = new ArrayList<>();
        capitals = new ArrayList<>();
    }


    public String getColoredMap(Graph<Position, Double> G) {
        vertices = G.vertices();

        getCapitalVerticesList(vertices);   // Returns a list of the capitals of the map


        bordersMap = getBordersFromMatrixMap(capitals, G); // Gets the map of every border from the matrix of capitals


        for (Position v : capitals) {
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

    public List<Position> getCapitalVerticesList(List<Position> vertices) {
        for (Position p : vertices) {
            if (p.getClass().equals(Country.class)) {
                capitals.add(p);
            }
        }
        return capitals;
    }

    public Map<Position, List<Position>> getBordersFromMatrixMap(List<Position> capitals, Graph<Position, Double> G) {

        Map<Position, List<Position>> temp = new HashMap<Position, List<Position>>();

        for (int i = 0; i < capitals.size(); i++) {
            for (int j = i + 1; j < capitals.size(); j++) {
                if (G.edge(capitals.get(i), capitals.get(j)) != null) {
                    if (!temp.containsKey(capitals.get(i))) {
                        temp.put(capitals.get(i), new ArrayList<>());
                    }
                    if (!temp.containsKey(capitals.get(j))) {
                        temp.put(capitals.get(j), new ArrayList<>());
                    }
                    temp.get(capitals.get(i)).add(capitals.get(j));
                    temp.get(capitals.get(j)).add(capitals.get(i));
                }
            }
        }
        return temp;
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
