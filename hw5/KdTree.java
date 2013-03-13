import java.util.List;
import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private boolean vertical;
        private Point2D point;
        private Node leftNode;
        private Node rightNode;
        private RectHV rect;

        public Node(Point2D point, RectHV rect, boolean vertical) {
            this.point = point;
            this.rect = rect;
            this.vertical = vertical;
            this.leftNode = null;
            this.rightNode = null;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D point) {
        if (root == null) {
            size++;
            root = new Node(point, new RectHV(0, 0, 1, 1), true);
            return;
        }

        if (contains(point))
            return;

        Node parentNode = findParent(root, point);
        RectHV parentRect = parentNode.rect;
        RectHV newRect = null;

        if (less(point, parentNode.point, parentNode.vertical)) {
            if (parentNode.vertical) {
                newRect = new RectHV(parentRect.xmin(),
                                     parentRect.ymin(), 
                                     parentNode.point.x(),
                                     parentRect.ymax());
            } else {
                newRect = new RectHV(parentRect.xmin(),
                                     parentRect.ymin(),
                                     parentRect.xmax(),
                                     parentNode.point.y());
            }

            parentNode.leftNode = new Node(point, newRect, !parentNode.vertical);
        } else {
            if (parentNode.vertical) {
                newRect = new RectHV(parentNode.point.x(),
                                     parentRect.ymin(),
                                     parentRect.xmax(),
                                     parentRect.ymax());
            } else {
                newRect = new RectHV(parentRect.xmin(),
                                     parentNode.point.y(),
                                     parentRect.xmax(),
                                     parentRect.ymax());
            }

            parentNode.rightNode = new Node(point, newRect, !parentNode.vertical);
        }

        size++;
    }

    private Node findParent(Node node, Point2D point) {
        if (less(point, node.point, node.vertical)) {
            if (node.leftNode == null) {
                return node;
            }

            return findParent(node.leftNode, point);
        } else {
            if (node.rightNode == null) {
                return node;
            }

            return findParent(node.rightNode, point);
        }
    }

    private boolean less(Point2D pointA, Point2D pointB, boolean vertical) {
        if (vertical) {
            return pointA.x() < pointB.x();
        } else {
            return pointA.y() < pointB.y();
        }
    }

    public boolean contains(Point2D point) {
        return findPoint(root, point);
    }

    private boolean findPoint(Node node, Point2D point) {
        if (node == null)
            return false;
        
        if (point.equals(node.point))
            return true;

        if (less(point, node.point, node.vertical))
            return findPoint(node.leftNode, point);
        else
            return findPoint(node.rightNode, point);
    }

    public void draw() {
        if (isEmpty())
            return;

        drawNode(root);
    }

    private void drawNode(Node node) {
        if (node.leftNode != null)
            drawNode(node.leftNode);

        if (node.rightNode != null)
            drawNode(node.rightNode);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        node.point.draw();

        StdDraw.setPenRadius();

        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(),
                         node.rect.ymin(),
                         node.point.x(),
                         node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(),
                         node.point.y(),
                         node.rect.xmax(),
                         node.point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> result = new ArrayList<Point2D>();

        if (!isEmpty())
            findInRange(result, rect, root);

        return result;
    }

    private void findInRange(List<Point2D> result, RectHV rect, Node node) {
        if (node.leftNode != null && rect.intersects(node.leftNode.rect))
            findInRange(result, rect, node.leftNode);

        if (node.rightNode != null && rect.intersects(node.rightNode.rect))
            findInRange(result, rect, node.rightNode);

        if (rect.contains(node.point))
            result.add(node.point);
    }

    public Point2D nearest(Point2D point) {
        Point2D result = null;

        if (!isEmpty())
            result = findNearest(point, root.point, root);

        return result;
    }

    private Point2D findNearest(Point2D point, Point2D best, Node node) {
        Point2D result = best;

        /*
        if (!node.point.equals(point) 
            && point.distanceTo(node.point) < point.distanceTo(result))
            result = node.point;
            */
        if (point.distanceTo(node.point) < point.distanceTo(result))
            result = node.point;

        if (node.leftNode != null && node.rightNode != null) {
            if (node.vertical) {
                if (point.x() < node.point.x()) {
                    if (node.leftNode.rect.distanceSquaredTo(point) 
                        < point.distanceSquaredTo(result))
                        result = findNearest(point, result, node.leftNode);
                    if (node.rightNode.rect.distanceSquaredTo(point) 
                        < point.distanceSquaredTo(result))
                        result = findNearest(point, result, node.rightNode);
                } else {
                    if (node.rightNode.rect.distanceSquaredTo(point) 
                        < point.distanceSquaredTo(result))
                        result = findNearest(point, result, node.rightNode);
                    if (node.leftNode.rect.distanceSquaredTo(point) 
                        < point.distanceSquaredTo(result))
                        result = findNearest(point, result, node.leftNode);
                }
            } else {
                if (point.y() < node.point.y()) {
                    if (node.leftNode.rect.distanceSquaredTo(point) 
                        < point.distanceSquaredTo(result))
                        result = findNearest(point, result, node.leftNode);
                    if (node.rightNode.rect.distanceSquaredTo(point) 
                        < point.distanceSquaredTo(result))
                        result = findNearest(point, result, node.rightNode);
                } else {
                    if (node.rightNode.rect.distanceSquaredTo(point) 
                        < point.distanceSquaredTo(result))
                        result = findNearest(point, result, node.rightNode);
                    if (node.leftNode.rect.distanceSquaredTo(point) 
                        < point.distanceSquaredTo(result))
                        result = findNearest(point, result, node.leftNode);
                }
            }
        } else if (node.leftNode != null) {
            if (node.leftNode.rect.distanceSquaredTo(point) 
                < point.distanceSquaredTo(result))
                result = findNearest(point, result, node.leftNode);
        } else if (node.rightNode != null) {
            if (node.rightNode.rect.distanceSquaredTo(point) 
                < point.distanceSquaredTo(result))
                result = findNearest(point, result, node.rightNode);
        }

        return result;
    }
}
