package lapr.project.utils;

import lapr.project.model.Place;
import lapr.project.model.Port;
import lapr.project.model.Position;

import java.util.*;

public class Centrality {
    Map<Position, Integer> centralityPort;

    public Centrality() {
        this.centralityPort = new HashMap<>();
    }

    public String getNCentralPorts(Graph<Position, Double> g, int n) {
        ArrayList<LinkedList<Position>> paths = new ArrayList<>();
        ArrayList<Double> dists = new ArrayList<>();
        List<Position> allPositions = g.vertices();
        for (Position p : allPositions) {
            dists.clear();
            paths.clear();
            Algorithms.shortestPaths(g, p, Double::compare, Double::sum, 0.0, paths, dists);
            for (int i = 0; i < paths.size(); i++) {
                LinkedList<Position> positionPath = paths.get(i);
                for (int j = 1; j < positionPath.size() - 1; j++) {
                    Position position = positionPath.get(j);
                    if (position.getClass().equals(Place.class) || position.getClass().equals(Port.class)) {
                        if (centralityPort.containsKey(positionPath.get(j)))
                            centralityPort.put(position, centralityPort.get(position) + 1);
                        else {
                            centralityPort.put(position, 1);
                        }
                    }
                }
            }
        }
        return toString(n,centralityPort);
    }


    public String toString(int n, Map<Position, Integer> portsCentrality) {
        String output ="";
        Map<Position, Integer> d = sortByComparator(portsCentrality);
        int i = 0;
        for (Position p : d.keySet()) {
            output += p + " => with " + String.format("%d", d.get(p)) + " detections in shortest paths. \n\n";
            i++;
            if (i == n) {
                break;
            }
        }
        return output;
    }


    private Map<Position, Integer> sortByComparator(Map<Position, Integer> unsortMap) {
        List<Map.Entry<Position, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        // Sorting the list based on values
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        Map<Position, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Position, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }


}
