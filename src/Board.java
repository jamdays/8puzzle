
import java.util.ArrayList;

public class Board{
    private int[][] tiles;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        this.tiles = new int[tiles.length][tiles.length];
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }


    // string representation of this board
    public String toString(){
        String board = "";
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                board += " " + this.tiles[i][j];
            }
            board += "\n";
        }
        return tiles.length + "\n" + board;
    }

    // board dimension n
    public int dimension(){
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming(){
        int hamming = 0;
        int c = 1;
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                /*
                the order of the statements is important because c needs
                to be incremented even if tiles[i][j] == 0
                 */
                if ( tiles[i][j] != c++ && tiles[i][j] != 0){
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int manhattan = 0;
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                if(tiles[i][j] != 0)
                    manhattan += Math.abs((i - (tiles[i][j]-1)/ tiles.length)) + Math.abs(j-(tiles[i][j]-1)%tiles.length);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(y == null || y.getClass() != this.getClass()){
            return false;
        }
        Board b = (Board) y;
        if(b.dimension() != this.dimension()){
            return false;
        }
        for(int i = 0; i < this.tiles.length; i++){
            for(int j = 0; j < this.tiles.length; j++){
                if(this.tiles[i][j] != b.tiles[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        ArrayList<Board> boards = new ArrayList<Board>();
        int[][] tilesCopy = new int[tiles.length][tiles.length];
        // make a copy of tiles and put it into tilesCopy, also find the zero element
        int x = -1, y = -1;
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                if(tiles[i][j] == 0){
                    x= i;
                    y = j;
                }
                tilesCopy[i][j] = tiles[i][j];

            }
        }
        int[] minusplus = {1, -1};
        for(int i: minusplus){
            if(x + i < tiles.length && x + i > -1){
                //switch tiles
                tilesCopy[x][y] = tilesCopy[x+i][y];
                tilesCopy[x+i][y] = 0;
                boards.add(new Board(tilesCopy));
                //switch tiles back, reuse tilesCopy
                tilesCopy[x+i][y] = tilesCopy[x][y];
                tilesCopy[x][y] = 0;

            }
            if(y + i < tiles.length && y + i > -1){
                tilesCopy[x][y] = tilesCopy[x][y + i];
                tilesCopy[x][y + i] = 0;
                boards.add(new Board(tilesCopy));
                //switch tiles back, reuse tilesCopy
                tilesCopy[x][y + i] = tilesCopy[x][y];
                tilesCopy[x][y] = 0;
            }
        }

        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        Board boardCopy = new Board(tiles);
        if(tiles[0][0] != 0) {
            if (tiles[1][0] != 0) {
                //switch tiles
                boardCopy.Swap(0, 0, 1, 0);
                return boardCopy;
            }
            boardCopy.Swap(0, 0, 0, 1);
            return boardCopy;
        }
        boardCopy.Swap(tiles.length -1, tiles.length -1, tiles.length-2, tiles.length-1);
        return boardCopy;

    }
    private void Swap(int i, int j, int x, int y){
        int temp = this.tiles[i][j];
        this.tiles[i][j] = tiles[x][y];
        tiles[x][y] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int[][] x = {{1,2,4},{6,5,3},{7,8,0}};
        Board b = new Board(x);
        System.out.println(b);
        for(Board bs: b.neighbors()){
            System.out.println(bs);
            System.out.println("hamming:" + bs.hamming());
            System.out.println("manhattan:" + bs.manhattan());
            for(Board bss: bs.neighbors()){
                System.out.print(bss);
                System.out.println(bss.equals(b));
                System.out.println("hamming:" + bss.hamming());
                System.out.println("manhattan:" + bss.manhattan());
            }
        }

    }

}
