package lapr.project.utils;

import lapr.project.model.Position;

import java.io.*;

import java.util.*;


public class Circuit {

    public static void mostEfficientCircuit(Graph<Position,Double> g) throws IOException {


        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String string;
        String string1;
        System.out.println("First location:");
        string = read.readLine();
        System.out.println("Second location:");
        string1 = read.readLine();
        int keyp1 = -1;
        int keyp2 = -1;
        for (Position p : g.vertices()) {
            if (p.getName().equalsIgnoreCase(string)  || p.getCountryName().equalsIgnoreCase(string))
                keyp1 = g.key(p);
            if (p.getName().equalsIgnoreCase(string1)  || p.getCountryName().equalsIgnoreCase(string1))
                keyp2 = g.key(p);
        }

        if (keyp1 == -1)
            System.out.println(string + " doesn't exist in the graph");
        if (keyp2 == -1)
            System.out.println(string + " doesn't exist in the graph");

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
        print(tempPos);

    }
    public static double pathDistance (LinkedList<Position> p,Graph<Position,Double> g){
        Algorithms alg = new Algorithms();
        double temp=0;
        Position tempP= p.pop();

        for (Position pos: p) {
            temp=temp+g.edge(tempP,pos).getWeight();
            tempP=pos;
        }

        return temp;
    }

    public static void print(LinkedList<Position> p) {
        for (Position port : p) {
            System.out.println(port.getCountryName());
        }
    }

}
