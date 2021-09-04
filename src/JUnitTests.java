import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitTests {
	GameState gamestate;
	
	@Test
	void testNumberFillDeck() {
		Deck deck = new Deck();
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
		Deck.shuffleDeck(Deck.getInitialDeck());
		assertEquals(108, Deck.getPlayingDeck().size());
	}
	
	
	@Test
	void testInitializeHand() {
		Deck deck = new Deck();
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
		Deck.shuffleDeck(Deck.getInitialDeck());
		GameState.inputNumPlayers();
		Player player = new Player();
		Player.initializeHand(GameState.getNumPlayers());
		System.out.println(Deck.getPlayingDeck().size());
		Player.buildPlayerArr();
		//String input1 = "WildDrawFour";
		//String input2 = "DrawTwo";
		//System.out.println(Deck.getInitialDeck());
		Player.cardHandler(Deck.getInitialDeckCard(79));
		//if (Card.getValue(Deck.getInitialDeckCard(79)).toString() == "DrawTwo") {
		//	
		//}
	}
	
	@Test 
	void testPlayCard() {
		
	}
	
}
