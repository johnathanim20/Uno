
public class mainGameLoop {
	public static void main(String[] args) {
		GUI g = new GUI();
		while (!g.gamestate.gameEnd) {
			g.gamestate.strategicPlayCard();
		}
	}
}
