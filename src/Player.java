import java.util.ArrayList;
import java.util.Random;

public class Player {

    private int color;
    private int maxDepth;

    public Player() {
    	this(-1);
    }
	
	public Player(int color) {
    	this.color = color;
    	maxDepth = 2;
    }

    public Move MiniMax(Board board) {
        // black player wants to maximize the heuristics value
        if (color == Board.X) {
            return max(new Board(board), 0);
        }
        // white player wants to minimize the heuristics value
        else {
            return min(new Board(board), 0);
        }
    }

    // The max and min functions are called interchangingly, one after another until a max depth is reached
    public Move max(Board board, int depth) {

        Random r = new Random();

        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if((board.isTerminal()) || (depth == maxDepth)) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
            return lastMove;
        }
        //The children-moves of the state are calculated
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.X));
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (Board child : children) {
            //And for each child min is called, on a lower depth
            Move move = min(child, depth + 1);
            //The child-move with the greatest value is selected and returned by max
            if(move.getValue() >= maxMove.getValue()) {
                if ((move.getValue() == maxMove.getValue())) {
                    //If the heuristic has the save value then we randomly choose one of the two moves
                    if (r.nextInt(2) == 0) {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setCol(child.getLastMove().getCol());
                        maxMove.setValue(move.getValue());
                    }
                }
                else {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }
            }
        }
        return maxMove;

    }

    public Move min(Board board, int depth) {

        Random r = new Random();

        if((board.isTerminal()) || (depth == maxDepth)) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
            return lastMove;
        }
        
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.O));
        Move minMove = new Move(Integer.MAX_VALUE);
        for (Board child : children) {
            Move move = max(child, depth + 1);
            if(move.getValue() <= minMove.getValue()) {
                if ((move.getValue() == minMove.getValue())) {
                    if (r.nextInt(2) == 0) {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                    }
                }
                else {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setCol(child.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                }
            }
        }
        return minMove;
    }
    
    public void setDepth(int depth) {
    	if (depth>4) maxDepth=4;
    	else if (depth<1) maxDepth=1;
    	else maxDepth = depth;
    }

}
