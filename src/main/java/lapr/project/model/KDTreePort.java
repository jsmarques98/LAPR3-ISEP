package lapr.project.model;

import lapr.project.model.Port;
import lapr.project.utils.KDTree;
import lapr.project.utils.CsvReader;
import lapr.project.utils.NodeKDTree;
//import org.graalvm.compiler.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class KDTreePort {

    KDTree portTree = new KDTree();

    ArrayList<Port> portArray = new ArrayList<>();
    {
        try {
            String path = "src/main/java/lapr/project/data/sports.csv";
            portArray = CsvReader.readPorts(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void insertPorts() {
        List<NodeKDTree<Port>> nodes = new ArrayList<>();
        for (Port port : portArray) {
            NodeKDTree<Port> node = new NodeKDTree<Port>(port, port.getLat(), port.getLon());
            nodes.add(node);
        }
        portTree.buildTree(nodes);
    }

}