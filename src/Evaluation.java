
public class Evaluation {
	
	private double[] weight; // heuristic function weights
	Board board;
	
	public Evaluation(Board board) {
		if (board == null) return;
		this.board = board;
		
		weight = new double[7];
		weight[0] = 1;
		weight[1] = 500;
		weight[2] = 100;
		weight[3] = 80;
		weight[4] = 30;
		weight[5] = 20;
		weight[6] = 20;
		
		if (board.getMoveCounter() > 7) {
			weight[3] = 50;
			weight[4] = 30;
		}

	}
	
	
	/**
	 * evaluate concerning corner occupancy, corner closeness, 
	 * wall occupancy, wall closeness, mobility,
	 * disk frontier and percentage
	 * 
	 * @return the evaluation of the state
	 */

	public double heuristic_evaluation()  {
		
		double p = 0; //disk percentage
		double c = 0; //corner occupancy
		double cl = 0;//corner closeness
		double w = 0; //wall occupancy
		double wl = 0;//wall closeness
		double m = 0; //mobility
		double f = 0; //frontier
		
		// Number of disks
		int black = board.countDisks(1);
		int white = board.countDisks(-1);
		if(black > white)
			p = (black)/(black + white);
		else if(black < white)
			p = -(white)/(black + white);
		else p = 0;


		// Corner occupancy
		black = white = 0;
		if(board.getCell(0,0) == 1) black++;
		else if(board.getCell(0,0) == -1) white++;
		if(board.getCell(0,7) == 1) black++;
		else if(board.getCell(0,7) == -1) white++;
		if(board.getCell(7,0) == 1) black++;
		else if(board.getCell(7,0) == -1) white++;
		if(board.getCell(7,7) == 1) black++;
		else if(board.getCell(7,7) == -1) white++;
		c = black - white;
		
		// Wall occupancy
		black = white = 0;
		for (int i=1; i<7; i++) {
			if(board.getCell(0,i) == 1) black++;
			else if(board.getCell(0,i) == -1) white++;
			if(board.getCell(i,0) == 1) black++;
			else if(board.getCell(i,0) == -1) white++;
			if(board.getCell(i,7) == 1) black++;
			else if(board.getCell(i,7) == -1) white++;
			if(board.getCell(7,i) == 1) black++;
			else if(board.getCell(7,i) == -1) white++;
		}
		w = black - white;
		
		// Corner and wall closeness
		black = white = 0;
		int black_co, white_co;
		black_co = white_co = 0;
		for (int i=1; i<7; i++) {
			if(board.getCell(0,i) == 1) black++;
			else if(board.getCell(0,i) == -1) white++;
			if(board.getCell(i,0) == 1) black++;
			else if(board.getCell(i,0) == -1) white++;
			if(board.getCell(i,7) == 1) black++;
			else if(board.getCell(i,7) == -1) white++;
			if(board.getCell(7,i) == 1) black++;
			else if(board.getCell(7,i) == -1) white++;
		}
		
		if(board.getCell(0,0) == 0)   {
			if(board.getCell(0,1) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(0,7) == -1) {
				white--;
				white_co++;
			}
			if(board.getCell(1,1) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(1,1) == -1) {
				white--;
				white_co++;
			}
			if(board.getCell(1,0) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(1,0) == -1) {
				white--;
				white_co++;
			}
		}
		if(board.getCell(0,7) == 0)   {
			if(board.getCell(0,6) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(0,6) == -1) {
				white--;
				white_co++;
			}
			if(board.getCell(1,6) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(1,6) == -1) {
				white--;
				white_co++;
			}
			if(board.getCell(1,7) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(1,7) == -1) {
				white--;
				white_co++;
			}
		}
		if(board.getCell(7, 0) == 0)   {
			if(board.getCell(7,1) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(7,1) == -1) {
				white--;
				white_co++;
			}
			if(board.getCell(6,1) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(6,1) == -1) {
				white--;
				white_co++;
			}
			if(board.getCell(6,0) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(6,0) == -1) {
				white--;
				white_co++;
			}
		}
		if(board.getCell(7,7) == 0)   {
			if(board.getCell(6,7) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(6,7) == -1) {
				white--;
				white_co++;
			}
			if(board.getCell(6,6) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(6,6) == -1) {
				white--;
				white_co++;
			}
			if(board.getCell(7,6) == 1) {
				black--;
				black_co++;
			}
			else if(board.getCell(7,6) == -1) {
				white--;
				white_co++;
			}
		}
		cl = black_co - white_co;
		wl = black - white;
		


		// Mobility
		black = board.FeasibleMoves(1).size();
		white = board.FeasibleMoves(-1).size();
		if(black > white)
			m = (100.0 * black)/(black + white);
		else if(black < white)
			m = -(100.0 * white)/(black + white);
		else m = 0;

		// Disk frontier
		black = board.getPlayerFrontier(1);
		white = board.getPlayerFrontier(-1);
		if(black > white)
			f = -(black)/(black + white);
		else if(black < white)
			f = (white)/(black + white);
		else f = 0;


		return (weight[0] * p) + (weight[1] * c) + (weight[2] * cl) + (weight[3] * w) + (weight[4] * wl) + (weight[5] * m) + (weight[6] * f);
	}

}
