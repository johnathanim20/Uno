import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	int order;
	boolean turn = false;
	static boolean clockwise = true;
	static Player[] players; 
	static Player currentPlayer;
	static int currentPlayerIndex = 0;
	static ArrayList<ArrayList<Card>> playerHands = new ArrayList<ArrayList<Card>> ();
	//private static ArrayList<Card> individualHand = new ArrayList<Card> ();

	static boolean drawStackedCards = false;
	static String currentColor;
	
	
	public Player() {
		
	}
	//Sets each players hands.
	public static void initializeHand(int numberPlayers) {
		int x = 0;
		players = new Player[numberPlayers];
		System.out.println(numberPlayers);
		for (int i = 0; i < numberPlayers; i++) {
			ArrayList<Card> hand = new ArrayList<Card>();
			for (int j = 0; j < 7; j++) {
				hand.add(Deck.getPlayingDeck().get(107 - x));
				Deck.removeFromPlayDeck((107 - x));
				x++;
			}
			playerHands.add(hand);
		}
		
	}
	
 	public static ArrayList<ArrayList<Card>> getPlayerHands() {
 		return playerHands;
 	}
	

	public static String getCurrentColor() {
		return currentColor;
	}
	
	public static void setCurrentColor(String color) {
		currentColor = color;
	}

	public boolean getTurn() {
		return this.turn;
	}
	
	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public int getOrder() {
		return this.order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public static boolean getDirection() {
		return clockwise;
	}
	
	public static Player getPlayer() {
		return currentPlayer;
	}
	
	public static int getPlayerIndex() {
		return currentPlayerIndex;
	}
	
	public static void buildPlayerArr() {
		//GameState.inputNumPlayers();
		players = new Player[GameState.getNumPlayers()];
		for (int i = 0; i < GameState.getNumPlayers(); i++) {
			players[i] = new Player();
		}
	}
	
	//Method to set the nextPlayer's turn and set currentPlayer to be the nextPlayer (does not account for special cards in it of itself)
	public static void setNextPlayer() {
		if (clockwise && players[players.length - 1] == currentPlayer) {
			currentPlayer = players[0];
			currentPlayerIndex = 0;
			currentPlayer.setTurn(true);
		} else if (!clockwise && currentPlayer == players[0]) {
			currentPlayer = players[players.length - 1];
			currentPlayerIndex = players.length - 1;
			currentPlayer.setTurn(true);
		} else if (clockwise) {
			currentPlayer = players[currentPlayerIndex + 1];
			currentPlayerIndex = currentPlayerIndex + 1;
			currentPlayer.setTurn(true);
		} else {
			currentPlayer = players[currentPlayerIndex - 1];
			currentPlayerIndex = currentPlayerIndex - 1;
			currentPlayer.setTurn(true);
		}	
	}
	
	
	
	public static void reverseCard() {
		clockwise = !clockwise;
		drawStackedCards = false;
	}
	
	public static void drawTwoCard() {
		ArrayList<Card> playDeck = Deck.getPlayingDeck();
		playerHands.get(currentPlayerIndex).add(playDeck.get(Deck.getPlayDeckIndex() - 1));
		playerHands.get(currentPlayerIndex).add(playDeck.get(Deck.getPlayDeckIndex() - 2));
		Deck.removeFromPlayDeck(playDeck.size() - 1);
		Deck.removeFromPlayDeck(playDeck.size() - 2);
		setNextPlayer();
		drawStackedCards = true;
		//numberCardsStacked +=2;
	}
	
	public static void wildCard() {
		System.out.println("Enter Desired Color");
		String color;
		Scanner myColor = new Scanner(System.in);
		color = myColor.next();
		System.out.print("Color is : " + color);
		myColor.close();
		setCurrentColor(color);
		drawStackedCards = false;
	}
	
	public static void wildDrawFourCard() {
		System.out.println("Enter Desired Color");
		String color;
		Scanner myColor = new Scanner(System.in);
		color = myColor.next();
		System.out.print("Color is : " + color);
		myColor.close();
		setCurrentColor(color);
		ArrayList<Card> playDeck = Deck.getPlayingDeck();
		playerHands.get(currentPlayerIndex).add(playDeck.get(Deck.getPlayDeckIndex() - 1));
		playerHands.get(currentPlayerIndex).add(playDeck.get(Deck.getPlayDeckIndex() - 2));
		playerHands.get(currentPlayerIndex).add(playDeck.get(Deck.getPlayDeckIndex() - 3));
		playerHands.get(currentPlayerIndex).add(playDeck.get(Deck.getPlayDeckIndex() - 4));
		Deck.removeFromPlayDeck(playDeck.size() - 1);
		Deck.removeFromPlayDeck(playDeck.size() - 2);
		Deck.removeFromPlayDeck(playDeck.size() - 3);
		Deck.removeFromPlayDeck(playDeck.size() - 4);
		setNextPlayer();
		drawStackedCards = true;
		//numberCardsStacked+=2;
	}
	
	public static void cardHandler(Card card) {
		String value = Card.getValue(card).toString();
		if (value == "Wild") {
			wildCard();
			drawStackedCards = false;
		} else if (value == "WildDrawFour" && drawStackedCards == false) {
			wildDrawFourCard();
			drawStackedCards = false;
		} else if (value == "Reverse") {
			reverseCard();
			drawStackedCards = false;
		} else if (value == "Skip") {
			setNextPlayer();
			drawStackedCards = false;
		} else if (value == "DrawTwo" && drawStackedCards == false) {
			drawTwoCard();
			drawStackedCards = false;
		} else if (value == "DrawTwo" && drawStackedCards == true) {
			drawTwoCard();
			drawTwoCard();
			drawStackedCards = false;
		} else if (value == "WildDrawFour" && drawStackedCards == true) {
			wildDrawFourCard();
			wildDrawFourCard();
			drawStackedCards = false;
		}
		else {
			drawStackedCards = false;
		}
	}
	
	public static void playCard() {
		//if player has a playable card, add to pile, subtract from hand
			for (int j = 0; j < playerHands.get(currentPlayerIndex).size(); j++) {
				if (Card.validMove(playerHands.get(currentPlayerIndex).get(j), Deck.getTopCardDiscardPile())) {
					playerHands.get(currentPlayerIndex).remove(playerHands.get(currentPlayerIndex).get(j));
					Deck.addToDiscardPile(playerHands.get(currentPlayerIndex).get(j));
					cardHandler(playerHands.get(currentPlayerIndex).get(j));
					currentPlayer.setTurn(false);
					setNextPlayer();
					setCurrentColor(Card.getColor(playerHands.get(currentPlayerIndex).get(j)).toString());
					break;
				} else {
					//otherwise draw from deck and play if possible... if not, no card will be played
					Card draw1 = Deck.getTopCardPlayingDeck();
					playerHands.get(currentPlayerIndex).add(draw1);
					if (Card.validMove(Deck.getTopCardDiscardPile(), draw1)) {
						playerHands.get(currentPlayerIndex).remove(draw1);
						Deck.addToDiscardPile(draw1);
						cardHandler(Deck.getTopCardDiscardPile());
						currentPlayer.setTurn(false);
						setNextPlayer();
						setCurrentColor(Card.getColor(playerHands.get(currentPlayerIndex).get(j)).toString());						
						break;
					}
				}
			currentPlayer.setTurn(false);
			setNextPlayer();
			}
		}
	
}
 