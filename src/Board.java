import java.util.ArrayList;
import java.util.StringTokenizer;

public class Board {
	
	public static final int BLACK = 1;
	public static final int WHITE = -1;
	public static final int EMPTY = 0;
	private static final String ILLEGAL = "illegal";
	private static final String NORTH = "NORTH";
	private static final String WEST = "WEST";
	private static final String EAST = "EAST";
	private static final String SOUTH = "SOUTH";
	    
	private Move lastMove;
	private int lastPlayer;
	private int MoveCounter;
	
	private int[][] cells;
	
	public Board() {
		lastMove = new Move();
		cells = new int[8][8];
		for( int i=0; i<=7; i++)
			for (int j=0; j<=7; j++)
				cells[i][j] = EMPTY;

		cells[3][3] = BLACK;
		cells[3][4] = WHITE;
		cells[4][3] = WHITE;
		cells[4][4] = BLACK;
		
		MoveCounter = 0;
		lastPlayer = WHITE;
	}

	//copy constructor used by minimax to produce search children states
	public Board(Board board) {
		lastMove = board.lastMove;
		lastPlayer = board.lastPlayer;
		MoveCounter = board.getMoveCounter();
		cells = new int[8][8];
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				cells[i][j] = board.getCell(i,j);
	}
	
	public int getCell(int x, int y) {
		return cells[x][y];
	}
	
	/**
	 * 
	 * @param color: Player's color
	 * @param x,y: move' coordinates
	 * @return the way the move affects the board or illegal if it cannot be made
	 */
	public String legalMove(int color, int x, int y) {
		
		if (x>7 || y>7) return ILLEGAL;
		if (x<0 || y<0) return ILLEGAL;
		if (cells[x][y] != EMPTY) return ILLEGAL;

		StringBuilder directions = new StringBuilder();
		int opponent = -color;
		boolean in = false;
		int line = x;
		int col = y;

		// check horizon
		while (line+1<7 && cells[line+1][col] == opponent) { // look south...
			line++;
			in = true;
		}
		if (++line<7)
			if(cells[line][col] == color && in)	// ...to play north
				directions.append(NORTH);

		// new direction
		line = x;
		in = false;
		while (line-1>0 && cells[line-1][col] == opponent) { // look north
			line--;
			in = true;
		}
		if (--line >=0)
			if (cells[line][col] == color && in) {
				directions.append(" ");
				directions.append(SOUTH);
			}

		// new direction
		in = false;
		line = x;
		while (col+1<7 && cells[line][col+1] == opponent) { // look west
			col++;
			in = true;
		}
		if (++col<8)
			if (cells[line][col] == color && in) {
				directions.append(" ");
				directions.append(EAST);
			}

		// new direction
		in = false;
		col = y;
		while (col-1>0 && cells[line][col-1] == opponent) { // look east
			col--;
			in = true;
		}
		if (--col>=0)
			if (cells[line][col] == color && in) {
				directions.append(" ");
				directions.append(WEST);
			}

		// new direction
		in = false;
		col = y;
		while (line+1<7 && col+1<7 && cells[line+1][col+1] == opponent) { // look southwest
			line++;
			col++;
			in = true;
		}
		if (++col < 8 && ++line < 8)
			if (cells[line][col] == color && in) {
				directions.append(" ");
				directions.append(NORTH);
				directions.append(EAST);
			}

		// new direction
		in = false;
		line = x;
		col = y;
		while (line+1<7 && col-1>0 && cells[line+1][col-1] == opponent) { // look southeast
			line++;
			col--;
			in = true;
		}
		if (--col >= 0 && ++line < 8)
			if (cells[line][col] == color && in) {
				directions.append(" ");
				directions.append(NORTH);
				directions.append(WEST);
			}

		// new direction
		in = false;
		line = x;
		col = y;
		while (line-1>0 && col+1<7 && cells[line-1][col+1] == opponent) { // look northwest
			line--;
			col++;
			in = true;
		}
		if (++col < 8 && --line >= 0)
			if (cells[line][col] == color && in) {
				directions.append(" ");
				directions.append(SOUTH);
				directions.append(EAST);
			}

		// new direction
		in = false;
		line = x;
		col = y;
		while (line-1>0 && col-1>0 && cells[line-1][col-1] == opponent) { // look northeast
			line--;
			col--;
			in = true;
		}
		if (--col >= 0 && --line >= 0)
			if (cells[line][col] == color && in) {
			directions.append(" ");
			directions.append(SOUTH);
			directions.append(WEST);
		}

		//this move does not affect anything
		if (directions.length()==0) return ILLEGAL;
		
		return String.valueOf(directions);
	}
	
	public void makeMove(int player, int x, int y, String directions) {

		StringTokenizer st = new StringTokenizer(directions);
		
		int multimove = st.countTokens()>1 ? 2 : 0;
		/*	if there are multiple moves make it 2, it decreases with each move
		 *  2 signifies multiple moves. 0 a single move
		 */

		/* a multiple move affects the board in more than one direction
		* eg.
		*  O X O -			O X O
		*  O X X - becomes	O O O
		*  O X - -			O O O
		*/

	    while (st.hasMoreTokens()) {
	    	String direction = st.nextToken();
	    	
			if (direction.equals("NORTH")) {
				int i = x;
				if (multimove == 1) // the first cell
					i++;
				if (multimove > 0) 
					multimove--;
				while( cells[i][y] != player) {
					cells[i++][y] = player;
				}
			}
			
			if (direction.equals("SOUTH")) {
				int i = x;
				if (multimove == 1)
					i--;
				if (multimove > 0) 
					multimove--;
				while( cells[i][y] != player) {
					cells[i--][y] = player;
				}
			}
			
			if (direction.equals("WEST")) {
				int i = y;
				if (multimove ==1)
					i--;
				if (multimove > 0) 
					multimove--;
				while( cells[x][i] != player) {
					cells[x][i--] = player;
				}
			}
			
			if (direction.equals("EAST")) {
				int i = y;
				if (multimove == 1)
					i++;
				if (multimove > 0) 
					multimove--;
				while( cells[x][i] != player) {
					cells[x][i++] = player;
				}
			}
			
			if (direction.equals("NORTHWEST")) {
				int i = x;
				int j = y;
				if (multimove == 1) {
					i++;
					j--;
				}
				if (multimove > 0) 
					multimove--;
				while( cells[i][j] != player) {
					cells[i++][j--] = player;
				}
			}
			
			if (direction.equals("NORTHEAST")) {
				int i = x;
				int j = y;
				if (multimove == 1) {
					i++;
					j++;
				}
				if (multimove > 0) 
					multimove--;
				while( cells[i][j] != player) {
					cells[i++][j++] = player;
				}
			}
			
			if (direction.equals("SOUTHWEST")) {
				int i = x;
				int j = y;
				if (multimove == 1) {
					i--;
					j--;
				}
				if (multimove > 0) 
					multimove--;
				while( cells[i][j] != player) {
					cells[i--][j--] = player;
				}
			}
			
			if (direction.equals("SOUTHEAST")) {
				int i = x;
				int j = y;
				if (multimove == 1) {
					i--;
					j++;
				}
				if (multimove > 0) 
					multimove--;
				while( cells[i][j] != player) {
					cells[i--][j++] = player;
				}
			}
	    }
	    
	    lastMove = new Move(x, y);
	    lastPlayer = player;
	    MoveCounter++;
	}

	public void makeMove(int player, int x, int y) {
		makeMove(player, x, y, legalMove(player,x,y));
	}

	public void makeMove(int player, Move move) {
		if (move!=null)
			makeMove(player, move.getRow(), move.getCol());
		else
			makeMove(player, -1, -1);
	}

	/**
	 * 
	 * @param player: the player's color
	 * @return a list of all feasible moves player can make
	 * 
	 */
	public ArrayList<Move> feasibleMoves(int player) {
		
		ArrayList<Move> moves = new ArrayList<>();
		
		for (int i=0; i<=7; i++) {
			for (int j=0; j<=7; j++) {
				if (!legalMove(player, i, j).equals(ILLEGAL)) {
					moves.add(new Move(i,j));
				}
			}
		}
		
		return moves;
	}
	
	/**
	 * 
	 * @param color: the player's color
	 * @return the number of player's disks that are next to empty cells
	 */
	public int getPlayerFrontier(int color) {

		int ans = 0;
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				if (cells[i][j] == color ) {
					if ((i == 7 && j == 7) || (i == 7 && j == 0) || (i == 0 && j == 0) || (i == 0 && j == 7)) ; //corners cannot be changed
					// <wall cases
					else if (i == 7) {
						if (cells[i-1][j] + cells[i][j-1] + cells[i][j+1] + cells[i-1][j-1] + cells[i-1][j+1] == EMPTY)
							ans++;
					}
					else if (i == 0) {
						if (cells[i+1][j] + cells[i][j-1] + cells[i][j+1] + cells[i+1][j+1] + cells[i+1][j-1] == EMPTY)
							ans++;
					}
					else if (j == 7) {
						if (cells[i-1][j] + cells[i+1][j]+ cells[i][j-1] + cells[i-1][j-1] + cells[i+1][j-1] == EMPTY)
							ans++;
					}
					else if (j == 0) {
						if (cells[i-1][j] + cells[i+1][j] + cells[i][j+1] + cells[i+1][j+1] + cells[i-1][j+1] == EMPTY)
							ans++;
					}
					// /wall cases>
					else {
						if (cells[i-1][j] + cells[i+1][j] + cells[i][j-1] + cells[i][j+1] + cells[i+1][j+1]
								+ cells[i+1][j-1] + cells[i-1][j-1] + cells[i-1][j+1] == EMPTY)
							ans++;
					}
				}
			}
		}
		
		return ans;
	}
	
	public double evaluate() {
		return new Evaluation(this).heuristic_evaluation();
	}

	/**
	 * Generates the children of the state
	 * Every feasible move results to a child
	 *
	 * @param color: the player's color
	 * @return every feasible move player can make
	 */
	public ArrayList<Board> getChildren(int color) {
		ArrayList<Board> children = new ArrayList<Board>();
		ArrayList<Move> moves = this.feasibleMoves(color);
		for (Move move : moves) {
			Board child = new Board(this);
			child.makeMove(color, move.getRow(), move.getCol());
			move.setValue(child.evaluate());
			children.add(child);
		}
		return children;
	}
	
	public int countDisks(int player) {
		int sum = 0;
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++)
				if (cells[i][j] == player)
					sum++;
		return sum;
	}	
	
	public void getWinner() {

		int white = countDisks(WHITE);
		int black = countDisks(BLACK);

		System.out.println("-<| " + black + " - " + white + " |>-");
		if (black > white) {
			System.out.println("Blacks player won");
		}
		else if (black < white) {
			System.out.println("Whites player won");
		}
		else {
			System.out.println("It's a draw");
		}
	}

	public boolean hasMoves(int player) {
		return feasibleMoves(player).size() != 0;
	}
	
	public boolean isTerminal() {
		return !(hasMoves(BLACK) && hasMoves(WHITE));
	}

	public Move getLastMove() {
		return lastMove;
	}

	public int getMoveCounter() {
		return MoveCounter;
	}
	
	public int getLastPlayer() {
		return lastPlayer;
	}
	
	public void print() {

		System.out.println("\n* 0 1 2 3 4 5 6 7 y");
		for (int i=0; i<8; i++) {
			System.out.print(i);
			for (int j=0; j<8; j++) {
				if (cells[i][j] > 0)
					System.out.print(" X");
				else if (cells[i][j] < 0)
					System.out.print(" O");
				else
					System.out.print(" -");
			}
			System.out.println(" *");
		}
		System.out.println("x * * * * * * * * *\n");
	}
	

}
