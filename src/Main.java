import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("\nLet's play Reversi\n\n");
		Scanner scan = new Scanner(System.in);
		
		System.out.print("How deep would you like the algorithm to search?(1-4):");
		int level = Integer.parseInt(scan.next().trim());
		
		System.out.print("Would you like to play first?(y/n):");
		int playcolor = 1;
		if (scan.next().trim().equals("n")) playcolor = -1;
		
		Board board = new Board();
		Player pc = new Player(-playcolor);
		pc.setDepth(level);
		System.out.println();
		board.print();
		System.out.println("\n");
		int last = -1; // so that black plays next
		
		while(!board.isTerminal()) {
		
			if (last==-playcolor) {
				if (board.hasMoves(playcolor)) {
					System.out.print("\nEnter your move ( Input format: x,y ): ");
					String input = scan.next().trim();
					int x = -4;
					int y = -2;
					while (board.LegalMove(playcolor, x, y).equals("illegal")) {
						if (x!=-4 && y!=-2) {
							System.out.println("You entered an invalid move!\nEnter another move: ");
							input = scan.next().trim();
						}
						
						while( input.length()!=3 && !input.contains(",")) {
							System.out.print(" Wrong input format!\nEnter your move again: ");
							input = scan.next().trim();
						}
						x = Integer.parseInt(input.substring(0,1));
						y = Integer.parseInt(input.substring(2));
					}
					
					board.MakeMove(playcolor, x, y);
					System.out.println();
					board.print();
					System.out.println("\n");
					last = board.getLastLetterPlayer();
				} else
					last = -last;
			}
	

			if (last==playcolor) {
				if (board.hasMoves(-playcolor)) {
					Move move = pc.MiniMax(board);
					
					board.MakeMove(-playcolor,move);
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
