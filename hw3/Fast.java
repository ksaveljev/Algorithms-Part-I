import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Fast {
    private static void printLine(Point[] points, int start, int end) {
        Arrays.sort(points, start, end);

        for (int i = start; i < end; i++) {
            StdOut.print(points[i] + " -> ");
        }
        StdOut.println(points[0]);

        points[start].drawTo(points[0]);
    }

    public static void main(String[] args) {
        In input = new In(args[0]);
        int n = input.readInt();
        Point[] points = new Point[n];
        Point[] pointsBySlope = new Point[n];
        double slopePQ, slopePR, slopePS;

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 0; i < n; i++) {
            points[i] = new Point(input.readInt(), input.readInt());
            pointsBySlope[i] = points[i];
            points[i].draw();
        }

        Arrays.sort(points);
        List<Double> ignoreSlopes = new ArrayList<Double>();

        for (int i = 0; i < n; i++) {
            Point currentPoint = points[i];

            Arrays.sort(pointsBySlope, 0, n, currentPoint.SLOPE_ORDER);

            ignoreSlopes.clear();

            int position = 1;
            for (int j = 1; j < n; j++) {
                Point point = pointsBySlope[j];
                double currentSlope = currentPoint.slopeTo(point);
                double previousSlope = currentPoint.slopeTo(pointsBySlope[j-1]);

                if (previousSlope != currentSlope) {
                    if (j - position > 2)
                        printLine(pointsBySlope, position, j);

                    position = j;
                }

                boolean alreadyIgnoredSlope = 
                  ignoreSlopes.contains(currentPoint.slopeTo(point));

                if (currentPoint.compareTo(point) < 0) {
                    if (!alreadyIgnoredSlope)
                        ignoreSlopes.add(currentPoint.slopeTo(point));

                    position = j + 1;
                }

                if (alreadyIgnoredSlope) {
                    position = j + 1;
                }
            }

            if (n - position > 2) {
              System.out.println("HI");
                printLine(pointsBySlope, position, n);
            }
        }
    }
}
