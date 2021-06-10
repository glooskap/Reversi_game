import java.util.ArrayList;
import java.util.StringTokenizer;

public class Board {
	
	public static final int X = 1;  // black
	public static final int O = -1; // white
	public static final int EMPTY = 0;
	    
	private Move lastMove;

	private int lastLetterPlayed;
	
	private int MoveCounter;
	
	private int[][] cells;
	
	public Board() {
		lastMove = new Move();
		cells = new int[8][8];
		for( int i=0; i<=7; i++)
			for (int j=0; j<=7; j++)
				cells[i][j] = EMPTY;
		

		cells[3][3] = X;
		cells[3][4] = O;
		cells[4][3] = O;
		cells[4][4] = X;
		
		MoveCounter = 0;
		lastLetterPlayed = O;
	}

	public Board(Board board) {
		lastMove = board.lastMove;
		lastLetterPlayed = board.lastLetterPlayed;
		MoveCounter = board.getMoveCounter();
		cells = new int[8][8];
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				cells[i][j] = board.getCell(i,j);
			}
		}
	}
	
	public int getCell(int x, int y) {
		return cells[x][y];
	}
	
	/**
	 * 
	 * @param color: Player's color
	 * @param x,y: move' coordinates
	 * @return the directions that must be changed or illegal if the move cannot be made
	 */
	public String LegalMove(int color, int x, int y) {
		
		if (x>7 || y>7) return "illegal";
		if (x<0 || y<0) return "illegal";
		if (cells[x][y] != EMPTY) return "illegal";
		
		int ocolor = -color; //other color
		
		String directions = "";
		boolean in = false;
		
		int line = x;
		int col = y;
		// check horizon
		while (line+1<7 && cells[line+1][col] == ocolor) { // look south...
			line++;
			in = true;
		}
		line++;
		
		if (line == 8) directions += "";
		else if (cells[line][col] == color && in) {	// ...to play north
			directions += " NORTH";  
			
		}
		
		in = false;
		line = x;	//new direction
		col = y;
		while (line-1>0 && cells[line-1][col] == ocolor) { // look north
			line--;
			in = true;
		}
		line--;
		
		if (line == -1) directions += "";
		else if (cells[line][col] == color && in) {
			directions += " SOUTH";
		}
		
		in = false;
		line = x;	//new direction
		col = y;
		while (col+1<7 && cells[line][col+1] ==ocolor) { // look west
			col++;
			in = true;
		}
		col++;
		
		if (col == 8) directions += "";
		else if (cells[line][col] == color && in) {
			directions += " EAST";
		}
		
		in = false;
		line = x;	//new direction
		col = y;
		while (col-1>0 && cells[line][col-1] == ocolor) { // look east
			col--;
			in = true;
		}
		col--;
		
		if (col == -1) directions += "";
		else if (cells[line][col] == color && in) {
			directions += " WEST";  
		}
		
		in = false;
		line = x;	//new direction
		col = y;
		while (line+1<7 && col+1<7 && cells[line+1][col+1] == ocolor) { // look southwest
			line++;
			col++;
			in = true;
		}
		
		
		if (++col == 8 || ++line == 8) directions += "";
		else if (cells[line][col] == color && in) {
			directions += " NORTHEAST";
		}
		
		in = false;
		line = x;	//new direction
		col = y;
		while (line+1<7 && col-1>0 && cells[line+1][col-1] == ocolor) { // look southeast
			line++;
			col--;
			in = true;
		}
		
		if (--col == -1 || ++line == 8) directions += "";
		else if (cells[line][col] == color && in) {
			directions += " NORTHWEST";			
		}
		
		in = false;
		line = x;	//new direction
		col = y;
		while (line-1>0 && col+1<7 && cells[line-1][col+1] == ocolor) { // look northwest
			line--;
			col++;
			in = true;
		}
		
		if (++col == 8 || --line == -1) directions += "";
		else if (cells[line][col] == color && in) {
			directions += " SOUTHEAST";  	
		}
		
		in = false;
		line = x;	//new direction
		col = y;
		while (line-1>0 && col-1>0 && cells[line-1][col-1] == ocolor) { // look northeast
			line--;
			col--;
			in = true;
		}
		
		if (--col == -1 || --line == -1) directions += "";
		else if (cells[line][col] == color && in) {
			directions += " SOUTHWEST";		
		}
		
		if (directions.equals("")) directions = "illegal";
		
		return directions;
	}
	
	public void MakeMove(int player, int x, int y) {
		
		String directions = LegalMove(player,x,y);
		StringTokenizer st = new StringTokenizer(directions);
		
		int multimove = st.countTokens()>1 ? 2 : 0;
		/*	if there are multiple moves make it 2, it decreases with each move
		 *  2 signifies multiple moves. 0 a single move
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
	    lastLetterPlayed = player;
	    MoveCounter++;

	}
	
	public void MakeMove(int player, Move move) {
		MakeMove(player,move.getRow(),move.getCol());
	}
	
	/**
	 * 
	 * @param player: who's playin
	 * @return a list of all feasible moves player can make
	 * 
	 */
	public ArrayList<Move> FeasibleMoves(int player) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		Move tmp;
		
		for (int i=0; i<=7; i++) {
			for (int j=0; j<=7; j++) {
				if (!LegalMove(player, i, j).equals("illegal")) {
					tmp = new Move(i,j);
					moves.add(tmp);
				}
			}
		}
		
		return moves;
	}
	
	/**
	 * 
	 * @param color: the player's color
	 * @return the number of disks that are next to empty cells
	 */
	public int getPlayerFrontier(int color) {

		int ans = 0;
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				if (cells[i][j] == color) {
					if ((i == 7 && j == 7) || (i == 7 && j == 0) || (i == 0 && j == 0) || (i == 0 && j == 7))
						continue; //corners cannot be changed
					else if (i == 7) {
						if (cells[i-1][j] == EMPTY || cells[i][j-1] == EMPTY || cells[i][j+1] == EMPTY || cells[i-1][j-1] == EMPTY || cells[i-1][j+1] == EMPTY)
							ans++;
					}
					else if (i == 0) {
						if (cells[i+1][j] == EMPTY || cells[i][j-1] == EMPTY || cells[i][j+1] == EMPTY || cells[i+1][j+1] == EMPTY || cells[i+1][j-1] == EMPTY)
							ans++;
					}
					else if (j == 7) {
						if (cells[i-1][j] == EMPTY || cells[i+1][j] == EMPTY || cells[i][j-1] == EMPTY || cells[i-1][j-1] == EMPTY || cells[i+1][j-1] == EMPTY)
							ans++;
					}
					else if (j == 0) {
						if (cells[i-1][j] == EMPTY || cells[i+1][j] == EMPTY || cells[i][j+1] == EMPTY || cells[i+1][j+1] == EMPTY || cells[i-1][j+1] == EMPTY)
							ans++;
					}
					else {
						if (cells[i-1][j] == EMPTY || cells[i+1][j] == EMPTY || cells[i][j-1] == EMPTY || cells[i][j+1] == EMPTY || cells[i+1][j+1] == EMPTY || cells[i+1][j-1] == EMPTY || cells[i-1][j-1] == EMPTY || cells[i-1][j+1] == EMPTY)
							ans++;
					}
				}
			}
			
		}
		
		return ans;
	}
	
	public double evaluate() {
		Evaluation val = new Evaluation(this);
		return val.heuristic_evaluation();
	}

	/* Generates the children of the state
	 * Every feasible move results to a child
	 */
	public ArrayList<Board> getChildren(int color) {
		ArrayList<Board> children = new ArrayList<Board>();
		ArrayList<Move> moves = this.FeasibleMoves(color); 
		for (Move move : moves) {
			Board child = new Board(this);
			child.MakeMove(color, move.getRow(), move.getCol());
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
	
	public String getWinner() {
		
		int Sum = 0;
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++)
				Sum += cells[i][j];
		
		if (Sum > 0)
			return "Black player won";
		if (Sum < 0)
			return "White player won";
		return "It's a draw";
	}

	public boolean hasMoves(int player) {
		return FeasibleMoves(player).size() != 0;
	}
	
	public boolean isTerminal() {
		return (FeasibleMoves(X).size() + FeasibleMoves(O).size() == 0);
	}


	public Move getLastMove() {
		return lastMove;
	}

	public int getMoveCounter() {
		return MoveCounter;
	}
	
	public int getLastLetterPlayer() {
		return lastLetterPlayed;
	}
	
	public void print() {
		//System.out.println("* * * * * * * * * *");
		System.out.println("* 0 1 2 3 4 5 6 7 *");
		for (int i=0; i<8; i++) {
			//System.out.print("*");
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
		System.out.println("* * * * * * * * * *");
	}
	

}
