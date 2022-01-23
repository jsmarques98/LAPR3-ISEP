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

}
