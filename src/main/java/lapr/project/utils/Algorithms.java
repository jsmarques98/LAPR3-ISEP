package lapr.project.utils;

import lapr.project.data.Edge;
import lapr.project.data.MatrixGraph;
import lapr.project.model.Position;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BinaryOperator;

public class Algorithms {
    /** Performs breadth-first search of a Graph starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) {
            return null;
        }

        LinkedList<V> resultQueue = new LinkedList<>();

        boolean[] knownVertices = new boolean[g.numVertices()];
        Arrays.fill(knownVertices, Boolean.FALSE);

        LinkedList<V> auxQueue = new LinkedList<>();

        resultQueue.add(vert);
        auxQueue.add(vert);

        knownVertices[g.key(vert)] = true;

        while (!auxQueue.isEmpty()) {
            V currentVert = auxQueue.remove();

            Iterable<Edge<V, E>> edges = g.outgoingEdges(currentVert);

            for (Edge<V, E> edge : edges) {

                V destVert = edge.getVDest();

                if (!knownVertices[g.key(destVert)]) {
                    knownVertices[g.key(destVert)] = true;
                    resultQueue.add(destVert);
                    auxQueue.add(destVert);
                }
            }
        }

        return resultQueue;
    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vOrig vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {

        int vKey = g.key(vOrig);

        if (visited[vKey]){
            return;
        }
        qdfs.push(vOrig);
        visited[vKey] = true;

        for (V vAdj : g.adjVertices(vOrig)){
            DepthFirstSearch(g, vAdj, visited, qdfs);
        }

    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex of graph g that will be the source of the search

     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert))
            return null;
        LinkedList<V> qdfs = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        DepthFirstSearch(g,vert,visited,qdfs);
        return qdfs;
    }


    /** Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    /*private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
        int vKey = g.key(vOrig);
        if(visited[vKey]) return;

        if(vOrig.equals(vDest)){ //save clone of the reverse path
            LinkedList<V> pathcopy = new LinkedList<>(path);
            pathcopy.addFirst(vDest);
            Collections.reverse(pathcopy);
            paths.add(new LinkedList<>(pathcopy));
            return;
        }

        path.push(vOrig);
        visited[vKey] = true;

        for (V vAdj : g.adjVertices(vOrig)){
            allPaths(g, vAdj, vDest, visited, path, paths);
        }

        path.pop();
        visited[vKey] = false;

    }*/

   /* *//** Returns all paths from vOrig to vDest
     *
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     *//*
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        LinkedList<V> path = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        allPaths(g,vOrig,vDest,visited,path,paths);
        return paths;
    }*/

    public static <V, E> List<LinkedList<V>> allCycles(Graph<V, E> g) {

        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        LinkedList<V> path = new LinkedList<>();
        for (V vOrig : g.vertices()) {
            boolean[] visited = new boolean[g.numVertices()];
            allPaths(g, vOrig, vOrig, visited, path, paths);

        }
        return paths;
    }

    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

        path.push(vOrig);
        visited[g.key(vOrig)] = true;
        for (V vAdj : g.adjVertices(vOrig)) {
            if (vAdj == vDest) {
                path.push(vDest);
                LinkedList<V> pathClone = (LinkedList<V>) path.clone();
                Collections.reverse(pathClone);
                paths.add(pathClone);

                path.pop();
            } else {
                if (!visited[g.key(vAdj)]) {
                    allPaths(g, vAdj, vDest, visited, path, paths);
                }
            }
        }
        path.pop();
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    private static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    boolean[] visited, V [] pathKeys, E [] dist) {
        int vKey = g.key(vOrig);
        dist[vKey] = zero;
        pathKeys[vKey] = vOrig;
        while (vOrig != null) {
            vKey = g.key(vOrig);
            visited[vKey] = true;
            for (V vAdj : g.adjVertices(vOrig)) {
                Edge<V, E> edge = g.edge(vOrig, vAdj);
                int vKeyAdj = g.key(vAdj);
                if (!visited[vKeyAdj]) {
                    E s = sum.apply(dist[vKey], edge.getWeight());
                    if (dist[vKeyAdj] == null || ce.compare(dist[vKeyAdj], s) > 0) {
                        dist[vKeyAdj] = s;
                        pathKeys[vKeyAdj] = vOrig;
                    }
                }
            }
            E minDist = null; //next vetice, that has minimun dist
            vOrig = null;
            for (int i = 0; i < g.numVertices(); i++) {
                if (!visited[i] && (dist[i] != null) && ((minDist == null) || ce.compare(dist[i], minDist) < 0)) {
                    minDist = dist[i];
                    vOrig = g.vertex(i);
                }
            }
        }
    }


    /** Shortest-path between two vertices
     *
     * @param g graph
     * @param vOrig origin vertex
     * @param vDest destination vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {

        shortPath.clear();
        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return null;

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts];

        @SuppressWarnings("unchecked")
        E[] dist = (E[]) Array.newInstance(zero.getClass(), nverts);
        @SuppressWarnings("unchecked")
        V[] vertices = (V[]) Array.newInstance(vOrig.getClass(), nverts);
        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, vertices, dist);
        E lengthPath = dist[g.key(vDest)];

        if (lengthPath != null) {
            getPath(g, vOrig, vDest, vertices, shortPath);
            return lengthPath;
        }
        return null;
    }

    /** Shortest-path between a vertex and all other vertices
     *
     * @param g graph
     * @param vOrig start vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                               ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {

        if (!g.validVertex(vOrig)) return false;

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts];

        @SuppressWarnings("unchecked")
        E[] dist = (E[]) Array.newInstance(zero.getClass(), nverts);
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) Array.newInstance(vOrig.getClass(), nverts);
        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
        dists.clear();
        paths.clear();

        for (int i = 0; i < nverts; i++) {
            paths.add(null);
            dists.add(null);
        }
        ArrayList<V> vertices = g.vertices();
        for (int i = 0; i < nverts; i++) {
            LinkedList<V> shortPath = new LinkedList<>();
            if (dist[i] != null){
                getPath(g, vOrig, vertices.get(i), pathKeys, shortPath);
            }
            paths.set(i, shortPath);
            dists.set(i, dist[i]);
        }
        return true;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       V [] pathKeys, LinkedList<V> path) {
        if (vOrig.equals(vDest)) {
            path.push(vDest);
        } else {
            path.push(vDest);
            int vKey = g.key(vDest);
            vDest = pathKeys[vKey];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }

    /** Calculates the minimum distance graph using Floyd-Warshall
     *
     * @param g initial graph
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance graph
     */
    public static <V,E> MatrixGraph<V,E> minDistGraph(Graph <V,E> g, Comparator<E> ce, BinaryOperator<E> sum) {

        int numVerts = g.numVertices();
        if (numVerts == 0) return null;

        @SuppressWarnings("unchecked")
        E[][] s = (E[][]) new Object[numVerts][numVerts];

        for(int i = 0; i < numVerts; i++){
            for (int j = 0; j < numVerts; j++){
                Edge<V,E> e = g.edge(i,j);
                if(s != null) s[i][j] = e.getWeight();
            }
        }

        for(int k = 0; k < numVerts; k++){
            for (int i = 0; i < numVerts; i++){
                if (i != k && s[i][k] != null) {
                    for (int j = 0; j < numVerts; j++) {
                        if (j != i && j != k && s[k][j] != null){
                            E m = sum.apply(s[i][k],s[k][j]);
                            if((s[i][j] == null) || ce.compare(s[i][j],m) > 0) s[i][j] = m;
                        }
                    }
                }
            }
        }
        return new MatrixGraph<>(g.isDirected(), g.vertices(), s);
    }

    /*final int V = 5;
    int path[];

    *//* A utility function to check if the vertex v can be
       added at index 'pos'in the Hamiltonian Cycle
       constructed so far (stored in 'path[]') *//*
    boolean isSafe(int v, int graph[][], int path[], int pos)
    {
        *//* Check if this vertex is an adjacent vertex of
           the previously added vertex. *//*
        if (graph[path[pos - 1]][v] == 0)
            return false;

        *//* Check if the vertex has already been included.
           This step can be optimized by creating an array
           of size V *//*
        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;

        return true;
    }

    *//* A recursive utility function to solve hamiltonian
       cycle problem *//*
    boolean hamCycleUtil(Graph<Position, Double> g, int path[], int pos)
    {
        *//* base case: If all vertices are included in
           Hamiltonian Cycle *//*
        if (pos == V)
        {
            // And if there is an edge from the last included
            // vertex to the first vertex
            if (g[path[pos - 1]][path[0]] == 1)
                return true;
            else
                return false;
        }

        // Try different vertices as a next candidate in
        // Hamiltonian Cycle. We don't try for 0 as we
        // included 0 as starting point in hamCycle()
        for (int v = 1; v < V; v++)
        {
            *//* Check if this vertex can be added to Hamiltonian
               Cycle *//*
            if (isSafe(v, graph, path, pos))
            {
                path[pos] = v;

                *//* recur to construct rest of the path *//*
                if (hamCycleUtil(graph, path, pos + 1) == true)
                    return true;

                *//* If adding vertex v doesn't lead to a solution,
                   then remove it *//*
                path[pos] = -1;
            }
        }

        *//* If no vertex can be added to Hamiltonian Cycle
           constructed so far, then return false *//*
        return false;
    }

    *//* This function solves the Hamiltonian Cycle problem using
       Backtracking. It mainly uses hamCycleUtil() to solve the
       problem. It returns false if there is no Hamiltonian Cycle
       possible, otherwise return true and prints the path.
       Please note that there may be more than one solutions,
       this function prints one of the feasible solutions. *//*
    int hamCycle(Graph<Position, Double> g)
    {
        path = new int[V];
        for (int i = 0; i < V; i++)
            path[i] = -1;

        *//* Let us put vertex 0 as the first vertex in the path.
           If there is a Hamiltonian Cycle, then the path can be
           started from any point of the cycle as the graph is
           undirected *//*
        path[0] = 0;
        if (hamCycleUtil(g, path, 1) == false)
        {
            System.out.println("\nSolution does not exist");
            return 0;
        }

        printSolution(path);
        return 1;
    }

    *//* A utility function to print solution *//*
    void printSolution(int path[])
    {
        System.out.println("Solution Exists: Following" +
                " is one Hamiltonian Cycle");
        for (int i = 0; i < V; i++)
            System.out.print(" " + path[i] + " ");

        // Let us print the first vertex again to show the
        // complete cycle
        System.out.println(" " + path[0] + " ");
    }*/
}
