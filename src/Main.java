
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("\n\tLET'S PLAY REVERSI\n\n");
		Scanner scan = new Scanner(System.in);

		System.out.println("Select difficulty level [EASY][NORMAL][HARD][EXPERT]");
		System.out.print("--- type your choice: ");
		int userColor = 1; //default value, is asked later
		Player pc = new Player();

		try {
			pc.setDepth(scan.next().trim());

			System.out.println("-- difficulty has been set to " + pc.getDepth());

			System.out.print("Would you like to play first?(y/n): ");

			if (scan.next().trim().equalsIgnoreCase("n")) userColor = -1;

		} catch (Exception e) {
			System.err.println("Error processing user input!");
			e.printStackTrace();
			return;
		}
		
		Board board = new Board();
		pc.setColor(-userColor);
		System.out.println();
		board.print();
		System.out.println();
		int last = -1; // so that white plays first
		
		while (!board.isTerminal()) {
		
			if (last == -userColor) {
				if (board.hasMoves(userColor)) {

					int x = -4, y = -2; // random illegal move to enter while loop

					try {
						System.out.print("\nEnter your move ( Input format: x,y ): ");
						String input = scan.next().trim();

						while (board.LegalMove(userColor, x, y).equals("illegal")) {
							if (x != -4 && y != -2) {
								System.out.println("You entered an invalid move!\nEnter another move: ");
								input = scan.next().trim();
							}

							while (input.length() != 3 && !input.contains(",")) {
								System.out.print(" Wrong input format!\nEnter your move again: ");
								input = scan.next().trim();
							}
							x = Integer.parseInt(input.substring(0, 1));
							y = Integer.parseInt(input.substring(2));
						}
					} catch (Exception e) {
						System.err.println("Error processing user input!");
						e.printStackTrace();
						return;
					}
					
					board.MakeMove(userColor, x, y);
					System.out.println();
					board.print();
					System.out.println();
					last = board.getLastPlayer();
				} else
					last = -last;
			}
	

			if (last == userColor) {
				if (board.hasMoves(-userColor)) {
					Move move = pc.MiniMax(board);
					
					board.MakeMove(-userColor,move);
					System.out.println("I played "+move+"\n");
					board.print();
					last = board.getLastPlayer();
				} else
					last = -last;
			}
		}
		scan.close();
		
		System.out.println("\n\t- GAME ENDED -");
		board.getWinner();
		
	}

}
