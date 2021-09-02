import java.util.ArrayList;
import java.util.Scanner;

public class GameState {
	static ArrayList<ArrayList<Card>> playerHand = new ArrayList<ArrayList<Card>> ();
	static ArrayList<Card> playingDeck = new ArrayList<Card>(108);	
	static ArrayList<Card> pile = new ArrayList<Card>(108);
	static boolean gameEnd = false;
	static boolean clockwise = true;
	static Player currentPlayer;
	static String currentColor;
	static int numberCardsStacked = 0;
	
	//generates a deck of 108 uno cards.
	public static void fillDeck(ArrayList<Card> Deck) {
		Card.allColors[] colors = Card.allColors.values();
		Card.allValues[] values = Card.allValues.values();
		for (int i = 0; i < colors.length - 1; i++) {
			Card.allColors color = colors[i]; 
			Deck.add(new Card(color, Card.allValues.getValue(0)));
			for (int j = 1; j < 13; j++) {
				Deck.add(new Card(color, Card.allValues.getValue(j)));
				Deck.add(new Card(color, Card.allValues.getValue(j)));	
			}
			Deck.add(new Card(colors[4], values[13]));
			Deck.add(new Card(colors[4], values[14]));	
		} 
	}
		
	//shuffle algorithm from stack overflow 
	public static void shuffleDeck(ArrayList<Card> Deck) {
		while (Deck.size() > 0) {
			int index = (int) (Math.random() * Deck.size());
			Deck.add(Deck.remove(index));
		}	
	}
		
	//if the deck runs out of cards to draw, reset it with this method
	public static void playingDeckReset() {
		if (playingDeck.isEmpty()) {
			for (int i = 0; i < pile.size()-1; i++) {
				playingDeck.add(pile.get(i));
				pile.remove(pile.get(i));
			}
			shuffleDeck(playingDeck);
		}
	}
		
	public static void initializeHand(ArrayList<Card> Deck, int numberPlayers) {
		playerHand = new ArrayList<ArrayList<Card>> (numberPlayers);
		for (int i = 0; i < numberPlayers; i++) {
			for (int j = 0; j < 7; j++) {
				playerHand.get(i).add(playingDeck.get(j));
				playingDeck.remove(playingDeck.get(j));
			}
		}
	}
		
	
	public static boolean hasWon(ArrayList<Card> playerHand) {
		if (playerHand.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static void setNextPlayer(Player[] players, int index) {
		if (clockwise && players[players.length - 1] == currentPlayer) {
			currentPlayer = players[0];
			currentPlayer.setTurn(true);
		} else if (!clockwise && currentPlayer == players[0]) {
			currentPlayer = players[players.length - 1];
			currentPlayer.setTurn(true);
		} else if (clockwise) {
			currentPlayer = players[index + 1];
			currentPlayer.setTurn(true);
		} else {
			currentPlayer = players[index - 1];
			currentPlayer.setTurn(true);
		}	
	}
	
	public static void reverseCard() {
		clockwise = !clockwise;
	}
	
	public static void drawTwoCard(int index) {
		playerHand.get(index).add(playingDeck.get(playingDeck.size() - 1));
		playerHand.get(index).add(playingDeck.get(playingDeck.size() - 2));
	}
	
	public static void wildCard() {
		System.out.println("Enter Desired Color");
		String color;
		Scanner myColor = new Scanner(System.in);
		color = myColor.next();
		System.out.print("Color is:" + color);
		myColor.close();
		currentColor = color;
	}
	public static void wildDrawFourCard(int index) {
		System.out.println("Enter Desired Color");
		String color;
		Scanner myColor = new Scanner(System.in);
		color = myColor.next();
		System.out.print("Color is:" + color);
		myColor.close();
		currentColor = color;
		playerHand.get(index).add(playingDeck.get(playingDeck.size() - 1));
		playerHand.get(index).add(playingDeck.get(playingDeck.size() - 2));
		playerHand.get(index).add(playingDeck.get(playingDeck.size() - 3));
		playerHand.get(index).add(playingDeck.get(playingDeck.size() - 4));
	}
	
	public static void cardHandler(int i, int j, Player[] players, int index) {
		String value = Card.getValue(playerHand.get(i).get(j)).toString();
		if (value == "Wild") {
			wildCard();
		} else if (value == "WildDrawFour") {
			wildDrawFourCard(i);
		} else if (value == "Reverse") {
			reverseCard();
		} else if (value == "Skip") {
			setNextPlayer(players, index);
		} else if (value == "DrawTwo" ) {
			drawTwoCard(index);
		}
	}

	/**public static void skipCard(Card topCard, Player[] players, int index) {
		if (clockwise && players[players.length - 1] == currentPlayer) {
			currentPlayer = players[1];
			currentPlayer.setTurn(true);
		} else if (!clockwise && currentPlayer == players[0]) {
			currentPlayer = players[players.length - 2];
			currentPlayer.setTurn(true);
		} else if (clockwise && players[players.length - 2] == currentPlayer) {
			currentPlayer = players[0];
			currentPlayer.setTurn(true);
		} else if (!clockwise && currentPlayer == players[1]) {
			currentPlayer = players[players.length - 1];
			currentPlayer.setTurn(true);
		} else if (clockwise) {
			currentPlayer = players[index + 2];
			currentPlayer.setTurn(true);
		} else {
			currentPlayer = players[index - 2];
			currentPlayer.setTurn(true);
		}	 
	}
	*/

	public static void playCard(Card topCard, Player[] players, int index) {
	//if player has a playable card, add to pile, subtract from hand
		for (int i = 0; i < playerHand.size(); i++) {
			for (int j = 0; j < playerHand.get(i).size(); j++) {
				if (Card.validMove(playerHand.get(i).get(j), topCard)) {
					playerHand.get(i).remove(playerHand.get(i).get(j));
					pile.add(playerHand.get(i).get(j));
					cardHandler(i, j, players, index);
					currentPlayer.setTurn(false);
					setNextPlayer(players, index);
					currentColor = Card.getColor(playerHand.get(i).get(j)).toString();
					break;
				} else {
					//otherwise draw from deck and play if possible... if not, no card will be played
					Card topDeckCard = playingDeck.get(playingDeck.size() - 1);
					playerHand.get(i).add(playingDeck.get(playingDeck.size() - 1));
					if (Card.validMove(topDeckCard, topCard)) {
						playerHand.get(i).remove(playingDeck.get(playingDeck.size() - 1));
						pile.add(topDeckCard);
						cardHandler(i, j, players, index);
						currentPlayer.setTurn(false);
						setNextPlayer(players, index);
						currentColor = Card.getColor(playerHand.get(i).get(j)).toString();
						break;
					}
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		//Lets user input amount of players

		System.out.println("Enter Number of Desired Players");
		int numberPlayers;
		Scanner playerNumber = new Scanner(System.in);
		numberPlayers = playerNumber.nextInt();
		System.out.print("Number of Players:" + numberPlayers);
		playerNumber.close();
		
		Player[] players = new Player[numberPlayers];
		for (int i = 0; i < numberPlayers; i++) {
			players[i] = new Player(i, false);
			players[i].setOrder(i);
			//0,1,2,3
		}
		players[0].setTurn(true);
		currentPlayer = players[0];
		fillDeck(playingDeck);
		shuffleDeck(playingDeck);
		initializeHand(playingDeck, numberPlayers);
	}
}
