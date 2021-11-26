package lapr.project.utils;
import java.awt.geom.Point2D;

public class NodeDTree<E extends Comparable<E>> {
    protected Point2D.Double coords;

    protected NodeDTree<E> left;

    protected NodeDTree<E> right;

    protected E info;

    public NodeDTree() {

    }

    public NodeDTree(E object, double x, double y) {
        this.coords = new Point2D.Double(x,y);
        this.info = object;
    }

    public E getObject() {
        return info;
    }

    public void setObject(E object) {
        this.info = object;
    }


    public Point2D.Double getCoords() {
        return coords;
    }

    public Double getX() {
        return coords.x;
    }

    public Double getY() {
        return coords.y;
    }


    @Override
    public String toString() {
        return "Node{" +
                "coords=" + coords +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
