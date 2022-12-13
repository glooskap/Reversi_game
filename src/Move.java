/** A class describing a move on the board. <br>
 * Every produced child corresponds to a move,
 * and we need to keep the moves as well as the states.
 */
public class Move {
	private int row;
	private int col;
	private double value;

	public Move() {
		row = -1;
		col = -1;
		value = 0;
	}

	public Move(int row, int col) {
		this.row = row;
		this.col = col;
		this.value = 0;
	}

	public Move(double value) {
		this.row = -1;
		this.col = -1;
		this.value = value;
	}

	public Move(int row, int col, double value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public double getValue() {
		return value;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public String toString() {
		return row+","+col;
	}
}