package lapr.project.data;


import lapr.project.model.Port;
import lapr.project.utils.CsvReader;

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
    public boolean insertPorts(ArrayList<Port> portArray){
        this.portArray = portArray;
        return true;
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
