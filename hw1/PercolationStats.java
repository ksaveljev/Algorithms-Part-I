public class PercolationStats {
    private int T;
    private double[] fractions;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException();

        this.T = T;
        fractions = new double[T];
        simulate(N, T);
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    private void simulate(int n, int t) {
        for (int i = 0; i < t; i++) {
            int opened = 0;
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int r = StdRandom.uniform(1, n+1);
                int c = StdRandom.uniform(1, n+1);
                if (percolation.isOpen(r, c))
                    continue;

                opened += 1;
                percolation.open(r, c);
            }

            fractions[i] = opened * 1.0 / (n * n);
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = "
                            + ps.confidenceLo() 
                            + ", " 
                            + ps.confidenceHi());
    }
}
