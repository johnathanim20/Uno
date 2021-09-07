import java.util.ArrayList;
import java.util.Scanner;

/* 
 * The GameState Class is used to generate an Instance of the Card Game 'UNO' by utilizing the Player, Card, and Deck Classes.
 */
public class GameState {
	
	static boolean gameEnd;
	boolean drawStackedCards;
	int numPlayers = 0;
	int numStackedCards = 0;
	int currentPlayerIndex;
	Player[] players; 
	boolean clockwise;
	static Player currentPlayer;
	static String currentColor;
	public ArrayList<Card> initialDeck = new ArrayList<Card> ();
	public ArrayList<Card> discardPile = new ArrayList<Card> ();
	public ArrayList<Card> playingDeck = new ArrayList<Card> ();
	public int playDeckIndex = 107;
	
	public GameState() {
		currentPlayer = null;
		currentPlayerIndex = 0;
		clockwise = true;
		drawStackedCards = false;
		gameEnd = false;
	}
	
	/*
	 * Function used for initializing game environment - for testing
	 */
	public void setGameUp() {
		initializeDeck();
		shuffleDeck();
		inputNumPlayers();
		buildPlayerArr();
		currentPlayer = players[0];
	}
	
	public void clearDecks() {
		initialDeck.clear();
		discardPile.clear();
		playingDeck.clear();
	}
	
	public int getPlayDeckIndex() {
		return playDeckIndex;
	}
	
	public  ArrayList<Card> getInitialDeck() {
		return initialDeck;
	}
	
	public Card getInitialDeckCard(int i) {
		return initialDeck.get(i);
	}
	
	public ArrayList<Card> getPlayingDeck() {
		return playingDeck;
	}
	
	public Card getTopCardDiscardPile() {
		return discardPile.get(discardPile.size() - 1);
	}
	
	public ArrayList<Card> getDiscardPile() {
		return discardPile;
	}
	public void addToDiscardPile(Card card) {
		discardPile.add(card);
	}
	
	public Card getTopCardPlayingDeck() {
		return playingDeck.get(playDeckIndex);
	}
	
	public void removeFromPlayDeck(int index) {
		playingDeck.remove(index);
		playDeckIndex--;
	}
	/*
	 * Method that generates a standard deck of 108 UNO cards 
	 */
	public void initializeDeck() {
		for (int i = 0; i < Card.getColors().length - 1; i++) {
			String[] colors = Card.getColors(); 
			String[] values = Card.getValues(); 
			//add all 0 value'd cards first
			initialDeck.add(new IntegerCard(colors[i], values[0]));
			//cardCounter++;
			for (int j = 1; j < 13; j++) {
				//add all cards valued 1-9 and skip, draw2, reverse
				initialDeck.add(new IntegerCard(colors[i], values[j]));
				initialDeck.add(new IntegerCard(colors[i], values[j]));	
				//cardCounter+=2;
			}
			//add wild cards at the end.
			initialDeck.add(new Wild(colors[4], values[13]));
			initialDeck.add(new WildDrawFour(colors[4], values[14]));	
			//cardCounter+=2;
		} 
	}
	
	/*
	 * shuffle algorithm from stack overflow : https://stackoverflow.com/questions/39557701/shuffle-a-deck-of-cards-in-java 
	 */
	public void shuffleDeck() {
		for (int i = 0; i < initialDeck.size(); i++) {
			int index = (int) (Math.random() * initialDeck.size());
			initialDeck.add(initialDeck.remove(index));
		}	
		playingDeck = initialDeck;
	}
	
	/*
	 * Call this method if the playingDeck runs out of cards to draw to reset it using the discard pile
	 */
	public void playingDeckReset() {
		if (playingDeck.isEmpty()) {
			for (int i = 0; i < discardPile.size()-1; i++) {
				playingDeck.add(discardPile.get(i));
				discardPile.remove(discardPile.get(i));
			}
			shuffleDeck();
		}
	}

	public int getNumPlayers() {
		return numPlayers;
	}
	
	public boolean getDirection() {
		return clockwise;
	}
	
	public Player getPlayer() {
		return currentPlayer;
	}
	
	public int getPlayerIndex() {
		return currentPlayerIndex;
	}
	
	public boolean getGameEnd() {
		return gameEnd;
	}
	
	public String getCurrentColor() {
		return currentColor;
	}
	
	public void setCurrentColor(String color) {
		currentColor = color;
	}

	/*
	 * Method to read user input to determine number of players in the game.
	 */
	public void inputNumPlayers() {
		Scanner playerNumber = new Scanner(System.in);
		System.out.println("Enter Number of Desired Players : ");
		int numberPlayers = 0;
		if (playerNumber.hasNextInt()) {
			numberPlayers = playerNumber.nextInt();
			System.out.println("Number of Players : " + numberPlayers);
			numPlayers = numberPlayers;
		} else {
			playerNumber.close();
		}
	}

	/*
	 * Method to initialize a Player Array of size equal to the number of players, and initializes the hands of each player. (1 < number of players <= 10)
	 */
	public void buildPlayerArr() {
		players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player(i);
			players[i].initializeHand(this);
		}
	}
	
	/*
	 * Method to set the next Player's turn and set Current Player to be the next Player. Takes care of edge cases of being at the start or end of the Player Array.
	 */
	
	public void setNextPlayer() {
		if (clockwise) {
			currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
			currentPlayer = players[currentPlayerIndex];
		} else {
			currentPlayerIndex = (currentPlayerIndex - 1) % numPlayers + numPlayers;
			currentPlayer = players[currentPlayerIndex];
		}	
	} 	
	
	/*
	 * helper function to add number of stacked cards to a player's hand.
	 */
	public void checkStackedCards() {
		boolean flag = false;
		if (getTopCardDiscardPile().getValue().toString() == "WildDrawFour") {
			for (int i = 0; i < players[currentPlayerIndex].getHand().size(); i++) {
				if ((players[currentPlayerIndex].getCardInHand(i).getValue().toString() == "WildDrawFour")) {
					flag = true;
				}
			}
			if (!flag) {
				for (int j = 1; j <= numStackedCards; j++) {
					if (getPlayingDeck().isEmpty()) {
						playingDeckReset();
					}
					players[currentPlayerIndex].addToHand(getPlayingDeck().get(getPlayDeckIndex() - j));
					removeFromPlayDeck(getPlayDeckIndex() - j);
				}
			}
		} else if (getTopCardDiscardPile().getValue().toString() == "DrawTwo") {
			for (int i = 0; i < players[currentPlayerIndex].getHand().size(); i++) {
				if ((players[currentPlayerIndex].getCardInHand(i).getValue().toString() == "DrawTwo")) {
					flag = true;
				}
			}
			if (!flag) {
				for (int j = 1; j <= numStackedCards; j++) {
					if (getPlayingDeck().isEmpty()) {
						playingDeckReset();
					}
					players[currentPlayerIndex].addToHand(getPlayingDeck().get(getPlayDeckIndex() - j));
					removeFromPlayDeck(getPlayDeckIndex() - j);
				}
			}
		}
	}

	/*
	 * Method that handles if there was card stacking.
	 */
	public void handlePenalty() {
		if (numStackedCards > 0 && drawStackedCards == true) {
			 checkStackedCards();
		}
	}
	
	
	/*
	 * Method that handles whenever a card is played and handles their effect
	 */
	public void cardHandler(Card card) {
		if (card.getValue().toString() == "WildDrawFour") {
			WildDrawFour wildDrawFour = (WildDrawFour) card;
			wildDrawFour.playCard(this, card);
		} else if (card.getValue().toString() == "DrawTwo") {
			DrawTwo drawTwo = (DrawTwo) card;
			drawTwo.playCard(this, card);
		} else if (card.getValue().toString() == "Skip") {
			Skip skip = (Skip) card;
			skip.playCard(this, card);
		} else if (card.getValue().toString() == "Reverse") {
			Reverse reverse = (Reverse) card;
			reverse.playCard(this, card);
		} else if (card.getValue().toString() == "Wild") {
			Wild wild = (Wild) card;
			wild.playCard(this, card);
		} else {
			Wild wild = (Wild) card;
			wild.playCard(this, card);
		}
	}
	
	
	/** Method that handles playing a Card in a GameState instance. - still bugged
	
	public void playCard() {
		if (players[currentPlayerIndex].isHandEmpty()) {
			gameEnd = true;
			return;
		}
		if (getPlayingDeck().isEmpty()) {
			playingDeckReset();
		}
		
		for (int j = 0; j < players[currentPlayerIndex].getHand().size(); j++) {
			if (players[currentPlayerIndex].getCardInHand(j).validMove(getTopCardDiscardPile(), this)) {
				handlePenalty();
				cardHandler(players[currentPlayerIndex].getCardInHand(j)); //handle card effect
				return;
			}
		}
		//otherwise draw from deck and play if possible... if not, no card will be played
		Card draw1 = getTopCardPlayingDeck();
		players[currentPlayerIndex].addToHand(draw1);
		players[currentPlayerIndex].removeFromHand(draw1);
		if (getTopCardDiscardPile().validMove(draw1, this)) {
			handlePenalty();
			addToDiscardPile(draw1);
			setCurrentColor(draw1.getColor().toString());		
			cardHandler(draw1);
			return;
		} 
		setNextPlayer();
	}
	**/
}
