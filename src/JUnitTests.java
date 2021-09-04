import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitTests {
	GameState gamestate;
	
	@Test
	void testNumberFillDeck() {
		Deck deck = new Deck();
		Deck.initializeDeck();
		assertEquals(108, Deck.getInitialDeck().size());
	}
	
	@Test
	void testContentFillDeck() {
		//Test if the ordering of the deck is consistent with the fillDeck() algorithm.
		//final ArrayList<Card> playDeck = new ArrayList<Card>();
		Deck deck = new Deck();
		for (int i = 0; i < 108; i++) {
			Card card = Deck.getInitialDeckCard(i);
			assertEquals(card.toString(), Card.getColor(card).toString() + " - " + Card.getValue(card).toString());
			System.out.println(card.toString());
		}
	}
	
	@Test 
	void testNumberShuffle() {
		Deck deck = new Deck();
		Deck.initializeDeck();
		Deck.shuffleDeck(Deck.getInitialDeck());
		assertEquals(108, Deck.getPlayingDeck().size());
	}
	
	
	@Test
	void testInitializeHand() {
		Deck deck = new Deck();
		Deck.initializeDeck();
		Deck.shuffleDeck(Deck.getInitialDeck());
		GameState.inputNumPlayers();
		Player player = new Player();
		Player.initializeHand(GameState.getNumPlayers());
		System.out.println(Player.getPlayerHands());
		for (int i = 0; i < Player.getPlayerHands().size(); i++) {
			assertEquals(7, Player.getPlayerHands().get(i).size());
		}
		//ArrayList<Card> hand = Player.getPlayerHand(0);
		//System.out.println(Deck.getPlayingDeck());
	}
	
	@Test
	void testSetNextPlayer() {
		Deck deck = new Deck();
		Deck.initializeDeck();
		Deck.shuffleDeck(Deck.getInitialDeck());
		GameState.inputNumPlayers();
		Player player = new Player();
		Player.buildPlayerArr();
		Player.setNextPlayer();
		assertEquals(1, Player.getPlayerIndex());
	}
	
	@Test
	void testReverseCard() {
		Player.getDirection();
		Player.reverseCard();
		assertEquals(false, Player.getDirection());
	}
	
	@Test
	void testDrawTwoCard() {
		Deck deck = new Deck();
		Deck.initializeDeck();
		Deck.shuffleDeck(Deck.getInitialDeck());
		GameState.inputNumPlayers();
		Player player = new Player();
		Player.initializeHand(GameState.getNumPlayers());
		//System.out.println(Deck.getPlayingDeck().size());
		Player.buildPlayerArr();
		Player.drawTwoCard();
		assertEquals(Deck.getPlayingDeck().size(), 108 - (GameState.getNumPlayers() * 7) - 2);
	}
	
	@Test 
	void testWildCard() {
		Player.wildCard();
		System.out.println("\n" + "Color is still " + Player.getCurrentColor());
	}
	
	@Test 
	void testWildDrawFourCard() {
		Deck deck = new Deck();
		Deck.initializeDeck();
		Deck.shuffleDeck(Deck.getInitialDeck());
		GameState.inputNumPlayers();
		Player player = new Player();
		Player.initializeHand(GameState.getNumPlayers());
		System.out.println(Deck.getPlayingDeck().size());
		Player.buildPlayerArr();
		Player.wildDrawFourCard();
		assertEquals(Deck.getPlayingDeck().size(), 108 - (GameState.getNumPlayers() * 7) - 4);
	}
	@Test
	void testCardHandler() {
		Deck deck = new Deck();
		Deck.initializeDeck();
		Deck.shuffleDeck(Deck.getInitialDeck());
		GameState.inputNumPlayers();
		Player player = new Player();
		Player.initializeHand(GameState.getNumPlayers());
		System.out.println(Deck.getPlayingDeck().size());
		Player.buildPlayerArr();
		Player.cardHandler(Deck.getInitialDeckCard(79));
	}
	
	@Test 
	void testPlayCard() {
		Deck deck = new Deck();
		Deck.initializeDeck();
		Deck.shuffleDeck(Deck.getInitialDeck());
		GameState.inputNumPlayers();
		Player player = new Player();
		Player.initializeHand(GameState.getNumPlayers());
		//System.out.println(Deck.getPlayingDeck().size());
		Player.buildPlayerArr();
		//System.out.println("this is the intial discard pile card : " + Deck.getTopCardPlayingDeck());
		System.out.println("this is player 0's hand : " + Player.getPlayerHands().get(0));
		Deck.addToDiscardPile(Deck.getTopCardPlayingDeck());
		Deck.removeFromPlayDeck(Deck.getPlayDeckIndex());
		System.out.println("this is the discard pile's top card : " + Deck.getTopCardDiscardPile());
		System.out.println("this is the card to be drawn if no card is playable : " + Deck.getTopCardPlayingDeck());
		System.out.println("this is the player 0's index : " + Player.getPlayerIndex());
		Player.playCard();
		System.out.println("\n" + "this is the next player's index : " + Player.getPlayerIndex());
		System.out.println("this is the card that was played : " + Deck.getTopCardDiscardPile());

	}
	
}
