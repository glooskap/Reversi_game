/**
 * Heuristic evaluation of the state of the board.
 */
public class Evaluation {
	
	private double[] weight; // heuristic function weights
	Board board;
	
	public Evaluation(Board board) {
		if (board == null) return;
		this.board = board;
		
		weight = new double[7];
		weight[0] = 1;		// number of disks has very little impact before the end of the game
		weight[1] = 800;	// corners are the most important cells
		weight[2] = -300;	// so their neighbouring cells should be avoided
		weight[3] = 200;	// wall cells are pretty important
		weight[4] = -50;	// so their non-wall neighbouring cells should be avoided
		weight[5] = 20;		// it is important to have options
		weight[6] = -20;	// expanding the frontier usually offers good moves to the opponent

	}
	
	
	/**
	 * evaluate concerning corner occupancy, corner closeness, 
	 * wall occupancy, wall closeness, mobility,
	 * disk frontier and percentage
	 * 
	 * @return the evaluation of the state
	 */

	public double heuristic_evaluation()  {
		
		double disk_percentage = 0;
		double corner_occupancy = 0;
		double corner_closeness = 0;
		double wall_occupancy = 0;
		double wall_closeness = 0;
		double mobility = 0;
		double frontier = 0;

		int blackColor = Board.BLACK;
		int whiteColor = Board.WHITE;
		
		// Number of disks
		int black = board.countDisks(blackColor);
		int white = board.countDisks(whiteColor);
		if(black > white)
			disk_percentage = (black)/(double)(black + white);
		else if(black < white)
			disk_percentage = -(white)/(double)(black + white);

		// if the game is almost over
		if (black + white > 60) weight[0] = 400;

		// Corner occupancy
		black = white = 0;
		if(board.getCell(0,0) == blackColor) black++;
		else if(board.getCell(0,0) == whiteColor) white++;
		if(board.getCell(0,7) == blackColor) black++;
		else if(board.getCell(0,7) == whiteColor) white++;
		if(board.getCell(7,0) == blackColor) black++;
		else if(board.getCell(7,0) == whiteColor) white++;
		if(board.getCell(7,7) == blackColor) black++;
		else if(board.getCell(7,7) == whiteColor) white++;
		if(black > white)
			corner_occupancy = (black)/(double)(black + white);
		else if(black < white)
			corner_occupancy = -(white)/(double)(black + white);
		
		// Wall occupancy
		black = white = 0;
		for (int i=1; i<7; i++) {
			if(board.getCell(0,i) == blackColor) black++;
			else if(board.getCell(0,i) == whiteColor) white++;
			if(board.getCell(i,0) == blackColor) black++;
			else if(board.getCell(i,0) == whiteColor) white++;
			if(board.getCell(i,7) == blackColor) black++;
			else if(board.getCell(i,7) == whiteColor) white++;
			if(board.getCell(7,i) == blackColor) black++;
			else if(board.getCell(7,i) == whiteColor) white++;
		}
		if(black > white)
			wall_occupancy = (black)/(double)(black + white);
		else if(black < white)
			wall_occupancy = -(white)/(double)(black + white);
		
		// Corner and wall closeness
		black = white = 0;
		int black_corners = 0, white_corners = 0;
		for (int i=1; i<7; i++) {
			if(board.getCell(0,i) == blackColor) black++;
			else if(board.getCell(0,i) == whiteColor) white++;
			if(board.getCell(i,0) == blackColor) black++;
			else if(board.getCell(i,0) == whiteColor) white++;
			if(board.getCell(i,7) == blackColor) black++;
			else if(board.getCell(i,7) == whiteColor) white++;
			if(board.getCell(7,i) == blackColor) black++;
			else if(board.getCell(7,i) == whiteColor) white++;
		}
		
		if(board.getCell(0,0) == Board.EMPTY) {
			if(board.getCell(0,1) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(0,7) == whiteColor) {
				white--;
				white_corners++;
			}
			if(board.getCell(1,1) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(1,1) == whiteColor) {
				white--;
				white_corners++;
			}
			if(board.getCell(1,0) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(1,0) == whiteColor) {
				white--;
				white_corners++;
			}
		}
		if(board.getCell(0,7) == Board.EMPTY) {
			if(board.getCell(0,6) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(0,6) == whiteColor) {
				white--;
				white_corners++;
			}
			if(board.getCell(1,6) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(1,6) == whiteColor) {
				white--;
				white_corners++;
			}
			if(board.getCell(1,7) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(1,7) == whiteColor) {
				white--;
				white_corners++;
			}
		}
		if(board.getCell(7, 0) == Board.EMPTY) {
			if(board.getCell(7,1) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(7,1) == whiteColor) {
				white--;
				white_corners++;
			}
			if(board.getCell(6,1) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(6,1) == whiteColor) {
				white--;
				white_corners++;
			}
			if(board.getCell(6,0) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(6,0) == whiteColor) {
				white--;
				white_corners++;
			}
		}
		if(board.getCell(7,7) == Board.EMPTY)   {
			if(board.getCell(6,7) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(6,7) == whiteColor) {
				white--;
				white_corners++;
			}
			if(board.getCell(6,6) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(6,6) == whiteColor) {
				white--;
				white_corners++;
			}
			if(board.getCell(7,6) == blackColor) {
				black--;
				black_corners++;
			}
			else if(board.getCell(7,6) == whiteColor) {
				white--;
				white_corners++;
			}
		}

		if (black_corners > white_corners)
			corner_closeness = black/(double)black_corners + white_corners;
		if (white_corners > black_corners)
			corner_closeness = white_corners/(double)black_corners + white_corners;
		if (black > white)
			wall_closeness = black/(double)black + white;
		if (white > black)
			wall_closeness = white/(double)black + white;


		// Mobility
		black = board.feasibleMoves(blackColor).size();
		white = board.feasibleMoves(whiteColor).size();
		if(black > white)
			mobility = (black) / (double)(black + white);
		else if(black < white)
			mobility = -(white) / (double)(black + white);

		// Disk frontier
		black = board.getPlayerFrontier(blackColor);
		white = board.getPlayerFrontier(whiteColor);
		if(black > white)
			frontier = -(black)/(double)(black + white);
		else if(black < white)
			frontier = (white)/(double)(black + white);


		return (weight[0] * disk_percentage) + (weight[1] * corner_occupancy) + (weight[2] * corner_closeness) + (weight[3] * wall_occupancy) + (weight[4] * wall_closeness) + (weight[5] * mobility) + (weight[6] * frontier);
	}

}
