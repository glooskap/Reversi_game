import java.util.Scanner;
import java.util.regex.Pattern;

public class Reversi {

    private final Scanner scan;
    private int userColor;
    private final Player playerAI;
    private final Board board;
    private boolean state;

    public Reversi() {
        state = true;
        scan = new Scanner(System.in);
        userColor = 1;
        playerAI = new Player();
        board = new Board();
    }

    /**
     * sets up the game <br>
     * if difficulty level is not properly typed, it is set to easy <br>
     * if turn selection is not properly typed, user plays first
     */
    public void init() {

        System.out.println("Select difficulty level [EASY][NORMAL][HARD][EXPERT]");
        System.out.print("--- type your choice: ");

        try {
            playerAI.setDepth(scan.nextLine().trim());

            System.out.println("- Difficulty has been set to " + playerAI.getDepth());

            System.out.print("Would you like to play first? (y/n): ");

            if (scan.nextLine().trim().equalsIgnoreCase("n")) userColor = -1;

        } catch (Exception e) {
            System.err.println("Error processing user input!");
            e.printStackTrace();
        }

        System.out.print("- You are playing ");
        System.out.println(userColor == 1 ? "blacks (X)" : "whites (O)");

        playerAI.setColor(-userColor);

        board.print();

    }

    /**
     * play until the board is full or a player has no moves on their turn
     */
    public void play() {
        int last = -1; // blacks always play first

        while (!board.isTerminal()) {
            if (last == -userColor) {
                if (!board.hasMoves(userColor)) return;

                int x = -1, y = -1;
                String move = null;
                String input;

                try {
                    while (move==null || move.equals("illegal")) {
                        if (move!=null)
                            System.out.println(" Invalid move!");

                        System.out.print("\nEnter your move ( Input format: x,y ): ");
                        input = scan.nextLine().trim();

                        if (Pattern.matches("^\\d,\\d$", input)) {
                            x = Integer.parseInt(input.substring(0, 1));
                            y = Integer.parseInt(input.substring(2));
                            move = board.legalMove(userColor, x, y);
                        } else
                            System.out.println(" Wrong input format!");
                    }
                } catch (Exception e) {
                    System.err.println("Error processing user input!");
                    e.printStackTrace();
                    state =  false;
                    return;
                }

                board.makeMove(userColor, x, y, move);
            }

            if (last == userColor) {
                if (!board.hasMoves(-userColor)) return;

                Move myMove = playerAI.MiniMax(board);
                board.makeMove(-userColor, myMove);
                System.out.println("I played " + myMove);
            }

            board.print();
            last = board.getLastPlayer();
        }
    }

    /**
     * GAME OVER... <br><br>
     * finalize process and display the result if there were no errors
     */
    public void over() {
        scan.close();
        if (state) board.getWinner();
    }
}
