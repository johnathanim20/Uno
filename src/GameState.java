import java.util.ArrayList;
import java.util.Scanner;

public class GameState {
	static boolean gameEnd = false;
	static int numPlayers = 0;
	
	public static boolean hasWon(ArrayList<Card> playerHand) {
		if (playerHand.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	public static int getNumPlayers() {
		 return numPlayers;
	}
	public static void inputNumPlayers() {
		//Lets user input amount of players
		Scanner playerNumber = new Scanner(System.in);
		System.out.print("Enter Number of Desired Players : ");
		int numberPlayers = 0;
		if (playerNumber.hasNextInt()) {
			numberPlayers = playerNumber.nextInt();
			System.out.print("Number of Players : ");
			//playerNumber.nextLine();
			//playerNumber.close();
			numPlayers = numberPlayers;
		} else {
			playerNumber.close();
		}
	}
}
