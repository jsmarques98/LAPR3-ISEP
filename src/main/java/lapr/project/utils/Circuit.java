package lapr.project.utils;

import lapr.project.model.Position;
import java.util.*;


public class Circuit {

    public static String mostEfficientCircuit(Graph<Position,Double> g) {


        List<LinkedList<Position>> paths = Algorithms.allCycles(g);
        int max=0;
        double dist=Double.MAX_VALUE;
        LinkedList<Position> tempPos= null;
        for(LinkedList<Position> p: paths) {
            if (p.size() > max) {
                max = p.size();
                tempPos = p;
                dist = pathDistance(p, g);
            } else if (p.size() == max && dist < pathDistance(p, g)) {
                tempPos = p;
                dist = pathDistance(p, g);
            }
        }
        return print(tempPos);
    }
    public static double pathDistance (LinkedList<Position> p,Graph<Position,Double> g){
        double temp=0;
        Position tempP= p.pop();

        for (Position pos: p) {
            temp=temp+g.edge(tempP,pos).getWeight();
            tempP=pos;
        }

        return temp;
    }

    public static String print(LinkedList<Position> p) {
        String output = "";
        for (Position port : p) {
            System.out.println(port.getCountryName());
            output += port.getCountryName() + "\n";
        }
        return output;
    }

}
