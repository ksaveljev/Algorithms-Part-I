import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        In input = new In(args[0]);
        int n = input.readInt();
        Point[] points = new Point[n];
        double slopePQ, slopePR, slopePS;

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 0; i < n; i++) {
            points[i] = new Point(input.readInt(), input.readInt());
            points[i].draw();
        }

        Arrays.sort(points);

        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                slopePQ = points[p].slopeTo(points[q]);

                for (int r = q + 1; r < n; r++) {
                    slopePR = points[p].slopeTo(points[r]);

                    if (slopePQ != slopePR)
                        continue;

                    for (int s = r + 1; s < n; s++) {
                        slopePS = points[p].slopeTo(points[s]);

                        if (slopePQ != slopePS)
                            continue;

                        Point[] result = {points[p],
                                          points[q],
                                          points[r],
                                          points[s]};

                        Arrays.sort(result);

                        StdOut.println(result[0] + " -> "
                                     + result[1] + " -> "
                                     + result[2] + " -> "
                                     + result[3]);

                        result[0].drawTo(result[3]);
                    }
                }
            }
        }

        input.close();
    }
}
