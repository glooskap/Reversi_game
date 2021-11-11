
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("\nLet's play Reversi\n\n");
		Scanner scan = new Scanner(System.in);

		System.out.print("How deep would you like the algorithm to search?(1-4): ");
		int level = 1, playingColor = 1; //default values

		try {
			String tmp = scan.next().trim();
			if (tmp.equals("1") || tmp.equals("2") || tmp.equals("3") || tmp.equals("4"))
				level = Integer.parseInt(tmp);
			System.out.println("-Search depth level has been set to " + level);

			System.out.print("Would you like to play first?(y/n): ");

			if (scan.next().trim().equalsIgnoreCase("n")) playingColor = -1;

		} catch (Exception e) {
			System.err.println("Error processing user input!");
			e.printStackTrace();
			return;
		}
		
		Board board = new Board();
		Player pc = new Player(-playingColor);
		pc.setDepth(level);
		System.out.println();
		board.print();
		System.out.println("\n");
		int last = -1; // so that black plays next
		
		while (!board.isTerminal()) {
		
			if (last == -playingColor) {
				if (board.hasMoves(playingColor)) {

					int x = -4, y = -2; // random illegal move to enter while loop

					try {
						System.out.print("\nEnter your move ( Input format: x,y ): ");
						String input = scan.next().trim();

						while (board.LegalMove(playingColor, x, y).equals("illegal")) {
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
					
					board.MakeMove(playingColor, x, y);
					System.out.println();
					board.print();
					System.out.println("\n");
					last = board.getLastLetterPlayer();
				} else
					last = -last;
			}
	

			if (last==playingColor) {
				if (board.hasMoves(-playingColor)) {
					Move move = pc.MiniMax(board);
					
					board.MakeMove(-playingColor,move);
					System.out.println("I played "+move+"\n");
					board.print();
					last = board.getLastLetterPlayer();
				} else
					last = -last;
			}
		}
		scan.close();
		
		System.out.println("\nGame ended! " + board.getWinner());
		
	}

}
