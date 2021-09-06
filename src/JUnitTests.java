import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Test Class to check if helper methods and class generation are working properly.
 */

public class JUnitTests {
	GameState gamestate;
	
	@BeforeEach
	void setUp() {
		gamestate = new GameState();
	}
	
	@Test
	void testInitializeHand() {
		gamestate.setGameUp();
		//System.out.println("\n" + gamestate.getInitialDeck().size());
		assertEquals(gamestate.getInitialDeck().size(), 80);
		for (int i = 0; i < gamestate.numPlayers; i++) {
			assertEquals(gamestate.players[i].hand.size(), 7);
		}
	}
	
	@Test
	void testNumberFillDeck() {
		gamestate.setGameUp();
		assertEquals(108, gamestate.getInitialDeck().size() + 7 * gamestate.numPlayers);
	}
	
	
	/*
	Test if all 108 UNO cards are generated.
	 */
	@Test
	void testContentFillDeck() {
		gamestate.initializeDeck();
		for (int i = 0; i < 108; i++) {
			Card card = gamestate.getInitialDeckCard(i);
			assertEquals(card.toString(), card.getColor() + " - " + card.getValue());
			//System.out.println(card.toString());
		}
	}
	
	/*
	 * Test if setNextPlayer correctly updates the index of the currentPlayer.
	 */
	@Test
	void testSetNextPlayer() {
		gamestate.setGameUp();
		gamestate.setNextPlayer();
		assertEquals(1, gamestate.getPlayerIndex());
		gamestate.setNextPlayer();
		assertEquals(2, gamestate.getPlayerIndex());
		gamestate.setNextPlayer();
		assertEquals(3, gamestate.getPlayerIndex());
		gamestate.setNextPlayer();
		assertEquals(0, gamestate.getPlayerIndex());
	}
	
	@Test
	void testReverseCard() {
		gamestate.setGameUp();
		Reverse reverse = new Reverse("Blue", "Reverse");
		gamestate.cardHandler(reverse);
		assertEquals(false, gamestate.getDirection());
		//System.out.println("\n" + "this is the current player index after reversing the order " + gamestate.getPlayerIndex());
		gamestate.cardHandler(reverse);
		assertEquals(true, gamestate.getDirection());
		//System.out.println("\n" + "this is the current player index after reversing the order again " + gamestate.getPlayerIndex());
	}
	
	
	@Test
	void testDrawTwoCard() {
		gamestate.setGameUp();
		DrawTwo d2 = new DrawTwo("Blue", "DrawTwo");
		gamestate.cardHandler(d2);
		assertEquals(true, gamestate.getDirection());
		//System.out.println("\n" + "this is the current player index after the turn " + gamestate.getPlayerIndex());
		assertEquals(2, gamestate.getPlayerIndex());
		assertEquals(2, gamestate.numStackedCards);
	}
	
	@Test 
	void testWildCard() {
		gamestate.setGameUp();
		Wild w = new Wild("Wild", "Wild");
		gamestate.cardHandler(w);
		assertEquals(true, gamestate.getDirection());
		assertEquals(1, gamestate.getPlayerIndex());
		assertEquals(0, gamestate.numStackedCards);
		//System.out.println(gamestate.getCurrentColor());
		assertEquals(gamestate.getCurrentColor(), "Blue");
		
	}
	
	@Test 
	void testWildDrawFourCard() {
		gamestate.setGameUp();
		WildDrawFour w1 = new WildDrawFour("Wild", "WildDrawFour");
		gamestate.cardHandler(w1);
		assertEquals(true, gamestate.getDirection());
		assertEquals(2, gamestate.getPlayerIndex());
		assertEquals(4, gamestate.numStackedCards);
		//System.out.println(gamestate.getCurrentColor());
		assertEquals(gamestate.getCurrentColor(), "Blue");
	}
	
	
}
