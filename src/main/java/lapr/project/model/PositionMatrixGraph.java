package lapr.project.model;

import lapr.project.utils.Graph;
import lapr.project.utils.MatrixGraph;
import lapr.project.utils.ShipSummary;

import java.util.*;

public class PositionMatrixGraph {

    private Graph<Position, Double> completeMap;
    private ShipSummary ss;

    public void MatrizGraphPortCapital() {
        this.completeMap = new MatrixGraph<>(false);
        ss = new ShipSummary();
    }

    public void fillMatrixGraph(int n, List<Position> country, List<Position> ports, List<SeaDist> seadists, Map<Position, List<Position>> mapBorders) {
        createVertices(country);
        createVertices(ports);
        addBordersConection(mapBorders);
        addPortsOfCountryConection(seadists);
        addCapitalCPortConection(country, ports);
        addClosetNPortsConection(n, seadists);
    }


    public void createVertices(List<Position> list) {
        for (Position p : list)
            completeMap.addVertex(p);
    }

    public void addBordersConection(Map<Position, List<Position>> mapBorders) {
        for (Position p1 : mapBorders.keySet()) {
            List<Position> bordersOfOnCapital = mapBorders.get(p1);
            for (Position p : bordersOfOnCapital) {
                completeMap.addEdge(p1,p,ss.distanciaDelta(p1.getLatitude(), p.getLatitude(), p1.getLongitude(), p.getLongitude()) );
            }
        }
    }

    public void addPortsOfCountryConection(List<SeaDist> list) {
        for (SeaDist s : list) {
            if (s.getFromCountry().equals(s.getToCountry()))
                completeMap.addEdge(s.getFromPlace(), s.getToPlace(), s.getSeaDistance());
        }
    }

    public void addCapitalCPortConection(List<Position> country, List<Position> ports) {

        for (Position c : country) {
            Position p = null;
            double aux = 0;
            double disMin = 0;
            for (Position port : ports) {
                if (c.getCountryName().equals(port.getCountryName())) {
                    aux = ss.distanciaDelta(c.getLatitude(), port.getLatitude(), c.getLongitude(), port.getLongitude());
                    if (disMin == 0 || aux < disMin) {
                        disMin = aux;
                        p = port;
                    }
                }
            }
            if (p != null) {
                completeMap.addEdge(c, p, disMin);
            }
        }
    }

    public Map<Place, List<SeaDist>> closetNPortsMap(int n, List<SeaDist> seadists) {
        Map<Place, List<SeaDist>> mapSeadists = new HashMap<>();
        Collections.sort(seadists);
        for (SeaDist s : seadists) {
            if (!mapSeadists.containsKey(s.getFromPlace())) {
                mapSeadists.put(s.getFromPlace(), new ArrayList<>());
            }
            if (!mapSeadists.containsKey(s.getToPlace())) {
                mapSeadists.put(s.getToPlace(), new ArrayList<>());
            }
            if (mapSeadists.get(s.getToPlace()).size() < n)
                mapSeadists.get(s.getToPlace()).add(s);
            if (mapSeadists.get(s.getFromPlace()).size() < n)
                mapSeadists.get(s.getFromPlace()).add(s);
        }
        return mapSeadists;
    }

    public void addClosetNPortsConection(int n, List<SeaDist> seadists) {
        Map<Place, List<SeaDist>> mapSeadists = closetNPortsMap(n, seadists);
        for (Place p1 : mapSeadists.keySet()) {
            List<SeaDist> l2 = mapSeadists.get(p1);
            for (SeaDist l : l2) {
                completeMap.addEdge(l.getFromPlace(), l.getToPlace(), l.getSeaDistance());
            }
        }
    }

    public Graph<Position, Double> getCompleteMap() {
        return completeMap;
    }

}
