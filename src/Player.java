import java.util.ArrayList;
import java.util.Random;

/**
 * Intelligent player. Decides on minimax heuristic evaluation. <br>
 * Game difficulty level corresponds depth level of evaluation.
 */
public class Player {

    private int color;
    private int maxDepth;

    public Move MiniMax(Board board) {
        // blacks player wants to maximize the heuristics value
        if (color == Board.BLACK) {
            return max(new Board(board), 0);
        }
        // whites player wants to minimize the heuristics value
        else {
            return min(new Board(board), 0);
        }
    }

    // max and min functions are called alternately, one after the other until max depth is reached
    public Move max(Board board, int depth) {

        Random r = new Random();

        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if((board.isTerminal()) || (depth == maxDepth)) {
            return new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
        }
        //The children-moves of the state are calculated
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.BLACK));
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (Board child : children) {
            //And for each child min is called, on a lower depth
            Move move = min(child, depth + 1);
            //The child-move with the greatest value is selected and returned by max
            if(move.getValue() >= maxMove.getValue()) {
                if ((move.getValue() == maxMove.getValue())) {
                    //If they have the same value then we randomly choose one
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
            return new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
        }
        
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.WHITE));
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

    public void setColor(int color) {
        this.color = color;
    }

    public void setDepth(String depth) {
        if (depth.equalsIgnoreCase("normal"))
            maxDepth = 2;
        else if (depth.equalsIgnoreCase("hard"))
            maxDepth = 3;
        else if (depth.equalsIgnoreCase("expert"))
            maxDepth = 4;
        else
    	    maxDepth = 1;
    }

    public String getDepth() {
        if (maxDepth == 1) return "EASY";
        if (maxDepth == 2) return "NORMAL";
        if (maxDepth == 3) return "HARD";
        return "EXPERT";
    }

}
