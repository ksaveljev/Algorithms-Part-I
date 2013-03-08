import java.util.List;
import java.util.ArrayList;

public class Board {
    private int N;
    private int[] blocks;
    private int manhattan;
    private int hash;
    private int zeroX, zeroY;

    public Board(int[][] blocks) {
        this.N = blocks.length;

        this.blocks = new int[N*N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i * N + j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }

        this.manhattan = countManhattan();
        this.hash = jenkinsHash();
    }
    
    private Board(int[] blocks, int N, int zeroX, int zeroY) {
        this.N = N;
        this.zeroX = zeroX;
        this.zeroY = zeroY;

        this.blocks = new int[N*N];

        for (int i = 0, sz = blocks.length; i < sz; i++) {
            this.blocks[i] = blocks[i];
        }

        this.manhattan = countManhattan();
        this.hash = jenkinsHash();
    }

    private int jenkinsHash() {
        int result, i;

        for (result = 0, i = 0; i < blocks.length; i++) {
            result += blocks[i];
            result += (result << 10);
            result ^= (result >> 6);
        }

        result += (result << 3);
        result ^= (result >> 11);
        result += (result << 15);

        return result;
    }

    private int countManhattan() {
        int result = 0;
        int x, y;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i * N + j] == 0)
                    continue;

                x = (blocks[i * N + j] - 1) / N;
                y = (blocks[i * N + j] - 1) - x * N;

                result += Math.abs(x - i);
                result += Math.abs(y - j);
            }
        }

        return result;
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int result = 0;

        for (int i = 0, sz = N * N - 1; i < sz; i++) {
            if (blocks[i] != i + 1)
                result++;
        }

        return result;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        for (int i = 0; i < N*N - 1; i++) {
            if (blocks[i] != i + 1)
                return false;
        }

        return true;
    }

    public Board twin() {
        Board result = null;

        if (blocks[0] != 0 && blocks[1] != 0) {
            swap(0, 1);
            result = new Board(blocks, N, zeroX, zeroY);
            swap(0, 1);
        } else {
            swap(N, N + 1);
            result = new Board(blocks, N, zeroX, zeroY);
            swap(N, N + 1);
        }

        return result;
    }

    private void swap(int x, int y) {
        int tmp = blocks[x];
        blocks[x] = blocks[y];
        blocks[y] = tmp;
    }

    public boolean equals(Object y) {
        if (y == null)
            return false;

        if (y == this)
            return true;

        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;

        if (that.dimension() != this.dimension())
            return false;

        return this.hash == that.hash;
    }

    public Iterable<Board> neighbors() {
        List<Board> result = new ArrayList<Board>();

        if (zeroX > 0) {
            swap(zeroX * N + zeroY, (zeroX - 1) * N + zeroY);
            result.add(new Board(blocks, N, zeroX - 1, zeroY));
            swap(zeroX * N + zeroY, (zeroX - 1) * N + zeroY);
        }

        if (zeroX + 1 < N) {
            swap(zeroX * N + zeroY, (zeroX + 1) * N + zeroY);
            result.add(new Board(blocks, N, zeroX + 1, zeroY));
            swap(zeroX * N + zeroY, (zeroX + 1) * N + zeroY);
        }

        if (zeroY > 0) {
            swap(zeroX * N + zeroY, zeroX * N + zeroY - 1);
            result.add(new Board(blocks, N, zeroX, zeroY - 1));
            swap(zeroX * N + zeroY, zeroX * N + zeroY - 1);
        }

        if (zeroY + 1 < N) {
            swap(zeroX * N + zeroY, zeroX * N + zeroY + 1);
            result.add(new Board(blocks, N, zeroX, zeroY + 1));
            swap(zeroX * N + zeroY, zeroX * N + zeroY + 1);
        }

        return result;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i * N + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
