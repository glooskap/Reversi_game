public class Game {

	public static void main(String[] args) {
		
		System.out.println("\n\t- LET'S PLAY REVERSI -\n\n");

		Reversi game = new Reversi();

		game.init();

		game.play();

		game.over();

		System.out.println("\n\t- GAME ENDED -");

	}
}
