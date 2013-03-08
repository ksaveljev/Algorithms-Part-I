public class Solver {
    private boolean solvable = false;
    private MinPQ<Node> originalPQ = new MinPQ<Node>();
    private MinPQ<Node> twinPQ = new MinPQ<Node>();
    private Stack<Board> solution = new Stack<Board>();

    private class Node implements Comparable<Node> {
        private Board board;
        private Node previousNode;
        private int moves;
        private int priority;

        public Node(Board board, Node previousNode) {
            this.board = board;
            this.previousNode = previousNode;

            if (previousNode == null) {
                moves = 0;
            } else {
                moves = previousNode.getMoves() + 1;
            }

            this.priority = board.manhattan() + moves;
        }

        public Node getPreviousNode() {
            return previousNode;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public int compareTo(Node that) {
            if (this.priority < that.priority)
                return -1;
            else if (this.priority > that.priority)
                return 1;

            return 0;
        }
    }

    public Solver(Board initial) {
        Node currentNode, previousNode;
        Board currentBoard, previousBoard;

        originalPQ.insert(new Node(initial, null));
        twinPQ.insert(new Node(initial.twin(), null));

        while (true) {
            currentNode = originalPQ.delMin();
            currentBoard = currentNode.getBoard();
            previousNode = currentNode.getPreviousNode();
            if (previousNode == null)
                previousBoard = null;
            else
                previousBoard = previousNode.getBoard();

            if (currentBoard.isGoal()) {
                saveSolution(currentNode);
                solvable = true;
                break;
            }

            for (Board nextBoard : currentBoard.neighbors()) {
                if (nextBoard.equals(previousBoard))
                    continue;

                originalPQ.insert(new Node(nextBoard, currentNode));
            }

            currentNode = twinPQ.delMin();
            currentBoard = currentNode.getBoard();
            previousNode = currentNode.getPreviousNode();
            if (previousNode == null)
                previousBoard = null;
            else
                previousBoard = previousNode.getBoard();

            if (currentBoard.isGoal()) {
                solvable = false;
                break;
            }

            for (Board nextBoard : currentBoard.neighbors()) {
                if (nextBoard.equals(previousBoard))
                    continue;

                twinPQ.insert(new Node(nextBoard, currentNode));
            }
        }
    }

    private void saveSolution(Node node) {
        Node currentNode = node;

        do {
            solution.push(currentNode.getBoard());
            currentNode = currentNode.getPreviousNode();
        } while (currentNode != null);
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (isSolvable())
            return solution.size() - 1;

        return -1;
    }

    public Iterable<Board> solution() {
        if (isSolvable())
            return solution;

        return null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
