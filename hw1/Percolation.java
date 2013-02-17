public class Percolation {
    private WeightedQuickUnionUF uf1, uf2;
    private byte[] bfield;
    private int N, top, bottom;

    public Percolation(int N) {
        uf1 = new WeightedQuickUnionUF(N*N+2);
        uf2 = new WeightedQuickUnionUF(N*N+1);
        bfield = new byte[N*N/8+1];

        for (int i = 0; i < N*N/8+1; i++)
            bfield[i] = 0x0;

        this.N = N;
        top = 0;
        bottom = N * N + 1;
    }

    public void open(int i, int j) {
        validateCoords(i, j);

        bfield[index(i, j)/8] |= (1 << (index(i, j) % 8));

        if (isValid(i - 1, j) && isOpen(i - 1, j)) {
            uf1.union(index(i -1, j), index(i, j));
            uf2.union(index(i -1, j), index(i, j));
        }

        if (isValid(i + 1, j) && isOpen(i + 1, j)) {
            uf1.union(index(i + 1, j), index(i, j));
            uf2.union(index(i + 1, j), index(i, j));
        }

        if (isValid(i, j - 1) && isOpen(i, j - 1)) {
            uf1.union(index(i, j - 1), index(i, j));
            uf2.union(index(i, j - 1), index(i, j));
        }

        if (isValid(i, j + 1) && isOpen(i, j + 1)) {
            uf1.union(index(i, j + 1), index(i, j));
            uf2.union(index(i, j + 1), index(i, j));
        }

        if (i == 1) {
            uf1.union(index(i, j), top);
            uf2.union(index(i, j), top);
        }

        if (i == N) {
            uf1.union(index(i, j), bottom);
        }
    }

    public boolean isOpen(int i, int j) {
        validateCoords(i, j);
        return (bfield[index(i, j) / 8] & (1 << (index(i, j) % 8))) != 0;
    }

    public boolean isFull(int i, int j) {
        validateCoords(i, j);
        return uf2.connected(top, index(i, j));
    }

    public boolean percolates() {
        return uf1.connected(top, bottom);
    }

    private void validateCoords(int i, int j) {
        if (!isValid(i, j))
            throw new java.lang.IndexOutOfBoundsException();
    }

    private boolean isValid(int i, int j) {
        return i >= 1 && i <= N && j >= 1 && j <= N;
    }

    private int index(int i, int j) {
        return (i-1) * N + j;
    }
}
