package lapr.project.utils;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DTree<E extends Comparable<E>> {

    private final Comparator<NodeDTree<E>> cmpX = new Comparator<NodeDTree<E>>() {

        @Override
        public int compare(NodeDTree<E> p1, NodeDTree<E> p2) {
            return Double.compare(p1.getX(), p2.getY());
        }
    };

    private final Comparator<NodeDTree<E>> cmpY = new Comparator<NodeDTree<E>>() {

        @Override
        public int compare(NodeDTree<E> p1, NodeDTree<E> p2) {
            return Double.compare(p1.getY(), p2.getY());
        }
    };

    private NodeDTree<E> root;

    public DTree() {

    }

    public DTree(List<NodeDTree<E>> nodes) {
        buildTree(nodes);
    }

    public void buildTree(List<NodeDTree<E>> nodes) {
        root = new Object() {
            NodeDTree<E> buildTree(boolean divX, List<NodeDTree<E>> nodes) {
                if (nodes == null || nodes.isEmpty())
                    return null;
                Collections.sort(nodes, divX ? cmpX : cmpY);
                int mid = nodes.size() >> 1;
                NodeDTree<E> node = new NodeDTree<>();
                node.coords = nodes.get(mid).coords;
                node.info = nodes.get(mid).info;
                node.left = buildTree(!divX, nodes.subList(0, mid));
                if (mid + 1 <= nodes.size() - 1)
                    node.right = buildTree(!divX, nodes.subList(mid+1, nodes.size()));
                return node;
            }
        }.buildTree(true, nodes);
    }

    public E findNearestNeighbour(double x, double y) {
        return findNearestNeighbour(root, x, y, true);
    }

    private E findNearestNeighbour(NodeDTree<E> fromNode, final double x, final double y, boolean divX) {
        return new Object() {

            double closestDist = Double.POSITIVE_INFINITY;

            E closestNode = null;

            E findNearestNeighbour(NodeDTree<E> node, boolean divX) {
                if (node == null)
                    return null;
                double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
                if (closestDist > d) {
                    closestDist = d;
                    closestNode = node.info;
                }
                double delta = divX ? x - node.coords.x : y - node.coords.y;
                double delta2 = delta * delta;
                NodeDTree<E> node1 = delta < 0 ? node.left : node.right;
                NodeDTree<E> node2 = delta < 0 ? node.right : node.left;
                findNearestNeighbour(node1, !divX);
                if (delta2 < closestDist) {
                    findNearestNeighbour(node2, !divX);
                }
                return closestNode;
            }
        }.findNearestNeighbour(fromNode, divX);
    }


    public List<E> rangeSearch(final double x, final double y, final double dist) {
        return new Object() {
            List<E> result = new LinkedList<>();
            double radius = dist * dist;
            List<E> rangeSearch(NodeDTree<E> node, boolean divX) {
                if (node == null)
                    return result;
                double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);

                if (radius >= d)
                    result.add(node.info);

                double delta = divX ? x - node.coords.x : y - node.coords.y;
                double delta2 = delta * delta;
                NodeDTree<E> node1 = delta < 0 ? node.left : node.right;
                NodeDTree<E> node2 = delta < 0 ? node.right : node.left;
                rangeSearch(node1, !divX);
                if (delta2 < radius) {
                    rangeSearch(node2, !divX);
                }
                return result;
            }
        }.rangeSearch(root, true);
    }



    public List<E> findNearestNeighbours(final double x, final double y, final int limit) {
        final List<E> result = new LinkedList<>();
        final List<NodeDTree<E>> closestNodes  = new ArrayList<>();
        final Comparator<NodeDTree<E>> cmp = new Comparator<NodeDTree<E>>() {
            @Override
            public int compare(NodeDTree<E> node1, NodeDTree<E> node2) {
                Double p1 = Point2D.distanceSq(node1.coords.x, node1.coords.y, x, y);
                Double p2 = Point2D.distanceSq(node2.coords.x, node2.coords.y, x, y);
                return Double.compare(p1, p2);
            }
        };

        new Object() {
            double closestDist = Double.POSITIVE_INFINITY;
            int size = 0;

            void findNearestNeighbours(NodeDTree<E> node, boolean divX) {
                if (node == null)
                    return ;
                double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
                if (closestDist > d) {
                    closestDist = d;
                }
                closestNodes.add(node);
                size++;
                double delta = divX ? x - node.coords.x : y - node.coords.y;
                double delta2 = delta * delta;
                NodeDTree<E> node1 = delta < 0 ? node.left : node.right;
                NodeDTree<E> node2 = delta < 0 ? node.right : node.left;
                findNearestNeighbours(node1, !divX);
                if (delta2 < closestDist || size < limit) {
                    findNearestNeighbours(node2, !divX);
                }
            }

        }.findNearestNeighbours(root, true);

        Collections.sort(closestNodes, cmp);

        int cnt = 0;
        for(NodeDTree<E> node : closestNodes) {
            if (cnt >= limit)
                break;
            result.add(node.info);
            cnt++;
        }

        return result;
    }


    public void insert(E object, double x, double y) {
        NodeDTree<E> node = new NodeDTree<>(object, x, y);
        if (root == null)
            root = node;
        else
            insert(node, root, true);
    }

    private void insert(NodeDTree<E> node, NodeDTree<E> currentNode, boolean divX) {
        if (node == null)
            return;
        int cmpResult = (divX ? cmpX : cmpY).compare(node, currentNode);
        if (cmpResult == -1)
            if(currentNode.left == null)
                currentNode.left = node;
            else
                insert(node, currentNode.left, !divX);
        else
        if(currentNode.right == null)
            currentNode.right = node;
        else
            insert(node, currentNode.right, !divX);
    }

    public E findMin() {
        NodeDTree<E> node = findMin(root, true);
        if(node != null)
            return node.info;
        return null;
    }


    private NodeDTree<E> findMin(NodeDTree<E> node, boolean divX) {
        if (node == null)
            return null;
        if (divX) {
            if (node.left == null)
                return node;
            else
                return findMin(node.left, false);
        }
        else {
            List<NodeDTree<E>> list = new LinkedList<>();
            list.add(findMin(node, true));

            if(node.left != null)
                list.add(findMin(node.left, true));

            if(node.right != null)
                list.add(findMin(node.right, true));

            Collections.sort(list, cmpY);

            return list.get(0);

        }
    }

    public NodeDTree<E> fastDelete(final E target, double x, double y) {
        root = new Object() {
            NodeDTree<E> delete(E targetObject, double x, double y, NodeDTree<E> node, boolean divX) {
                if(node == null)
                    return null;
                if (targetObject.equals(node.info)) {
                    if(node.right != null) {
                        NodeDTree<E> minNode = findMin(node.right, !divX);
                        node.info = minNode.info;
                        node.coords = minNode.coords;
                        node.right = delete(node.info, node.getX(), node.getY(), node.right, !divX);
                    }
                    else if (node.left != null) {
                        NodeDTree<E> minNode = findMin(node.left, !divX);
                        node.info = minNode.info;
                        node.coords = minNode.coords;
                        node.left = delete(node.info, node.getX(), node.getY(), node.left, !divX);
                    }
                    else
                        node = null;
                }
                else {
                    double delta = divX ? x - node.coords.x : y - node.coords.y;
                    if(delta < 0)
                        node.left = delete(targetObject, x, y, node.left, !divX);
                    else
                        node.right = delete(targetObject, x, y, node.right, !divX);
                }
                return node;
            }
        }.delete(target, x, y, root, true);

        return root;
    }

    public NodeDTree<E> delete(final E target) {
        root = new Object() {
            NodeDTree<E> delete(E targetObject, NodeDTree<E> node, boolean divX) {
                if(node == null)
                    return null;
                if (targetObject.equals(node.info)) {
                    if(node.right != null) {
                        NodeDTree<E> minNode = findMin(node.right, !divX);
                        node.info = minNode.info;
                        node.coords = minNode.coords;
                        node.right = delete(node.info, node.right, !divX);
                    }
                    else if (node.left != null) {
                        NodeDTree<E> minNode = findMin(node.left, !divX);
                        node.info = minNode.info;
                        node.coords = minNode.coords;
                        node.left = delete(node.info, node.left, !divX);
                    }
                    else
                        node = null;
                }
                else {
                    node.left = delete(targetObject, node.left, !divX);
                    node.right = delete(targetObject, node.right, !divX);
                }
                return node;
            }
        }.delete(target, root, true);

        return root;
    }


    public void insertOrUpdate(E target, double x, double y) {
        delete(target);
        insert(target, x, y);
    }

    public int getSize() {
        return new Object() {
            int cnt = 0;
            int getSize(NodeDTree<E> node) {
                if (node == null)
                    return cnt - 1;
                if (node.left != null) {
                    cnt++;
                    getSize(node.left);
                }
                if (node.right != null) {
                    cnt++;
                    getSize(node.right);
                }
                return cnt;
            }
        }.getSize(root) + 1;
    }

    public List<E> getAll() {
        final List<E> result = new LinkedList<>();
        new Object() {
            void fillList(NodeDTree<E> node) {
                if(node == null)
                    return;
                result.add(node.getObject());
                if(node.left != null)
                    fillList(node.left);
                if(node.right != null)
                    fillList(node.right);
            }
        }.fillList(root);
        return result;
    }
}
