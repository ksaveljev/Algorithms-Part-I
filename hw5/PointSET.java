public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D point) {
        points.add(point);
    }

    public boolean contains(Point2D point) {
        return points.contains(point);
    }

    public void draw() {
        for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> result = new SET<Point2D>();

        for (Point2D point : points) {
            if (rect.contains(point))
                result.add(point);
        }

        return result;
    }

    public Point2D nearest(Point2D point) {
        Point2D result = null;

        for (Point2D currentPoint : points) {
            if (result == null
                || currentPoint.distanceTo(point) < result.distanceTo(point)) {
                result = currentPoint;
            }
        }

        return result;
    }
}
