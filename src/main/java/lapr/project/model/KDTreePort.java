package lapr.project.model;


import lapr.project.utils.KDTree;
import lapr.project.utils.CsvReader;
import lapr.project.utils.NodeKDTree;

import java.util.ArrayList;
import java.util.List;

public class KDTreePort{

    KDTree portTree = new KDTree();

    ArrayList<Port> portArray = new ArrayList<>();

    public KDTreePort(){
        try {
            String path = "src/main/java/lapr/project/data/sports.csv";
            portArray = CsvReader.readPorts(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertPorts(ArrayList<Port> portArray){
        this.portArray = portArray;
    }
    public void insertPorts() {
        List<NodeKDTree<Port>> nodes = new ArrayList<>();
        for (Port port : portArray) {
            NodeKDTree<Port> node = new NodeKDTree<>(port, port.getLat(), port.getLon());
            nodes.add(node);
        }
        portTree.buildTree(nodes);
    }

    public ArrayList<Port> getArray(){
        return portArray;
    }

    public Port nearestPort(double lat, double lon){
        return (Port) portTree.findNearestNeighbour(lat,lon);
    }

}
