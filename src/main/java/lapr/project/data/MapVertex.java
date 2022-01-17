package lapr.project.data;

import java.util.*;

/**
 * @param <V>
 * @param <E>
 * @author DEI-ESINF
 */
public class MapVertex<V, E> {

    private int key;                     //Vertex key number
    final private V element;                            // Vertex information
    final private Map<V, Edge<V, E>> outVerts;    // Adjacent vertices

    public MapVertex() {
        key = -1;
        element = null;
        outVerts = new LinkedHashMap<>();
    }

    public MapVertex(int K, V vert) {
        if (vert == null) throw new RuntimeException("Vertice information cannot be null!");
        element = vert;
        outVerts = new LinkedHashMap<>();
        key = K;
    }

    public MapVertex(MapVertex<V, E> v) {
        key = v.getKey();
        element = v.getElement();
        outVerts = new LinkedHashMap<>();
        for (V vert : v.outVerts.keySet()) {
            Edge<V, E> edge = v.outVerts.get(vert);
            outVerts.put(vert, edge);
        }
    }

    public int getKey() {
        return key;
    }

    public void setKey(int k) {
        key = k;
    }

    public V getElement() {
        return element;
    }

    public void addAdjVert(V vAdj, Edge<V, E> edge) {
        outVerts.put(vAdj, edge);
    }

    public void remAdjVert(V vAdj) {
        outVerts.remove(vAdj);
    }

    public Edge<V, E> getEdge(V vAdj) {
        return outVerts.get(vAdj);
    }

    public int numAdjVerts() {
        return outVerts.size();
    }

    public Collection<V> getAllAdjVerts() {
        return new ArrayList<>(outVerts.keySet());
    }

    public Collection<Edge<V, E>> getAllOutEdges() {
        return new ArrayList<>(outVerts.values());
    }
    
    public Collection<Edge<V, E>> getAllInEdges(V vert) {
        
        ArrayList<Edge<V, E>> collect = new ArrayList<>();
        
        for (Edge<V, E> edge : outVerts.values()){
            if (edge.getVDest() == vert)
                collect.add(edge);
        }
        
        return collect;
    }

    @Override
    public String toString() {
        String st = element + ": \n";
        if (!outVerts.isEmpty())
            for (V vert : outVerts.keySet())
                st += outVerts.get(vert);

        return st;
    }
}
