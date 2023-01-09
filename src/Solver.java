import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {
    private int moves;
    private Node end;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null){
            throw new IllegalArgumentException("board is null");
        }
        MinPQ<Node> gameTree = new MinPQ<Node>();
        MinPQ<Node> twinTree = new MinPQ<Node>();
        gameTree.insert(new Node(null, initial));
        twinTree.insert(new Node(null, initial.twin()));
        Node min = gameTree.delMin();
        Node twin = twinTree.delMin();
        while (!min.board.isGoal() && !twin.board.isGoal()) {
            for (Board b : min.board.neighbors()) {
                if (min.parent == null || !b.equals(min.parent.board)) {
                    gameTree.insert(new Node(min, b));
                }
            }
            for (Board b : twin.board.neighbors()) {
                if (twin.parent == null || !b.equals(twin.parent.board)) {
                    twinTree.insert(new Node(twin, b));
                }
            }
            twin = twinTree.delMin();
            min = gameTree.delMin();
        }
        if(twin.board.isGoal()){
            end = null;
            moves = -1;
        } else {
            end = min;
            moves = end.mooves;
        }

        //end = solve(new Node(null, initial));
        //call solution so that moves is set
    }
    /*
    public Node solve(Node initial){

        if(initial.board.isGoal()){
            return initial;
        }
        for(Board b: initial.board.neighbors()){
            if(initial.parent == null || !b.equals(initial.parent.board)) {
                if(b.isGoal()){
                    return new Node(initial, b);
                }
                gameTree.insert(new Node(initial, b));
            }
        }
        System.out.println(gameTree.size());
        System.out.println(gameTree.min().priority- gameTree.min().mooves + " + " + gameTree.min().mooves);
        return solve(gameTree.delMin());
    }

     */

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        //filler code change later
        return moves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if(moves == -1){
            return null;
        }
        ArrayList<Board> solution = new ArrayList<Board>();
        Node last = end;
        solution.add(end.board);
        while(last.parent != null){
            last = last.parent;
            solution.add(0, last.board);
        }
        return solution;
    }
    private class Node implements Comparable<Node>{
        private final Node parent;
        private final Board board;
        private final int priority;
        private final int mooves;
        //private Board[] children;
        public Node(Node parent, Board board){
            this.parent = parent;
            this.board = board;
            if(parent != null){
                this.mooves = parent.mooves + 1;
            } else{
                this.mooves = 0;
            }
            priority = this.mooves + this.board.manhattan();
        }
        public int compareTo(Node other){
            return this.priority - other.priority;
        }

    }
    // test client (see below)
    public static void main(String[] args){
    }

}
