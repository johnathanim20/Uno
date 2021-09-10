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
	
	
	/*
	 * Test the initial player's hand size. Each player should have 7 cards.
	 */
	@Test
	void testInitializeHand() {
		gamestate.setGameUp();
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
	
	/*
	 * Test when a reverse card is played and if the direction of the gamestate is updated.
	 */
	@Test
	void testReverseCard() {
		gamestate.setGameUp();
		Reverse reverse = new Reverse("Blue", "Reverse");
		gamestate.cardHandler(reverse, gamestate.getPlayer().getHand());
		assertEquals(false, gamestate.getDirection());
		//System.out.println("\n" + "this is the current player index after reversing the order " + gamestate.getPlayerIndex());
		gamestate.cardHandler(reverse, gamestate.getPlayer().getHand());
		assertEquals(true, gamestate.getDirection());
		//System.out.println("\n" + "this is the current player index after reversing the order again " + gamestate.getPlayerIndex());
	}
	
	/*
	 * Test when a draw2 card is played and if playerindex and stackedcards are updated.
	 */
	@Test
	void testDrawTwoCard() {
		gamestate.setGameUp();
		DrawTwo d2 = new DrawTwo("Blue", "DrawTwo");
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		assertEquals(true, gamestate.getDirection());
		//System.out.println("\n" + "this is the current player index after the turn " + gamestate.getPlayerIndex());
		assertEquals(2, gamestate.getPlayerIndex());
		assertEquals(2, gamestate.numStackedCards);
	}
	
	/*
	 * Test when a wild card is played and if current color is set to user input
	 */
	@Test 
	void testWildCard() {
		gamestate.setGameUp();
		Wild w = new Wild("Wild", "Wild");
		gamestate.cardHandler(w, gamestate.getPlayer().getHand());
		assertEquals(true, gamestate.getDirection());
		assertEquals(1, gamestate.getPlayerIndex());
		assertEquals(0, gamestate.numStackedCards);
		System.out.println(gamestate.getCurrentColor());
		//assertEquals(gamestate.getCurrentColor(), "Blue");
		
	}
	/*
	 * Test when a wild4 card is played and if current color is set to user input, and if playerindex and stacked cards are updated properly.
	 */
	@Test 
	void testWildDrawFourCard() {
		gamestate.setGameUp();
		WildDrawFour w1 = new WildDrawFour("Wild", "WildDrawFour");
		gamestate.cardHandler(w1, gamestate.getPlayer().getHand());
		assertEquals(true, gamestate.getDirection());
		assertEquals(2, gamestate.getPlayerIndex());
		assertEquals(4, gamestate.numStackedCards);
		System.out.println(gamestate.getCurrentColor());
		//assertEquals(gamestate.getCurrentColor(), "Blue");
	}
	
	@Test
	/*
	 * Test to see if when 2 consecutive draw 2 cards are played, the next next player pays the penalty if he does not have anything to stack.
	 */
	void testStackedCards() {
		gamestate.setGameUp();
		DrawTwo d2 = new DrawTwo("Blue", "DrawTwo");
		gamestate.addToDiscardPile(d2);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(d2);
		gamestate.players[1].hand.clear();
		gamestate.players[1].hand.add(d2);
		gamestate.players[2].hand.clear();
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		gamestate.setNextPlayer();
		gamestate.setNextPlayer();
		gamestate.handlePenalty();
		assertEquals(gamestate.players[2].hand.size(), 4);
	}
	
	/*
	 * Test to see if when 2 consecutive draw4 cards are played, the next next player pays the penalty if he does not have anything to stack.
	 */
	@Test
	void testStackedCards2() {
		gamestate.setGameUp();
		WildDrawFour d2 = new WildDrawFour("Wild", "WildDrawFour");
		gamestate.addToDiscardPile(d2);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(d2);
		gamestate.players[1].hand.clear();
		gamestate.players[1].hand.add(d2);
		gamestate.players[2].hand.clear();
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		gamestate.setNextPlayer();
		gamestate.setNextPlayer();
		gamestate.handlePenalty();
		assertEquals(gamestate.players[2].hand.size(), 8);
	}
	
	@Test
	void testAddRule() {
		gamestate.setGameUp();
		IntegerCard c1 = new IntegerCard("Blue", "5");
		IntegerCard c2 = new IntegerCard("Yellow", "3");
		IntegerCard c3 = new IntegerCard("Yellow", "2");
		
		gamestate.addToDiscardPile(c1);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		
		System.out.println(gamestate.players[0].hand);
		System.out.println(gamestate.getDiscardPile());
		gamestate.cardHandler(c1, gamestate.players[0].hand);
		
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
	
	}
	
	@Test
	void testAddRule2() {
		gamestate.setGameUp();
		IntegerCard c1 = new IntegerCard("Blue", "5");
		IntegerCard c2 = new IntegerCard("Yellow", "1");
		IntegerCard c3 = new IntegerCard("Yellow", "2");
		IntegerCard c4 = new IntegerCard("Yellow", "3");
		gamestate.addToDiscardPile(c1);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		gamestate.players[0].hand.add(c4);
		System.out.println(gamestate.players[0].hand.size());
		gamestate.cardHandler(c4, gamestate.players[0].hand);
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
		System.out.println(gamestate.getPlayerIndex());
	}
	
	@Test
	void testAddRule3() {
		gamestate.setGameUp();
		IntegerCard c1 = new IntegerCard("Blue", "5");
		IntegerCard c2 = new IntegerCard("Yellow", "1");
		IntegerCard c3 = new IntegerCard("Yellow", "2");
		IntegerCard c4 = new IntegerCard("Yellow", "8");
		gamestate.addToDiscardPile(c1);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		gamestate.players[0].hand.add(c4);
		System.out.println(gamestate.players[0].hand.size());
		gamestate.cardHandler(c4, gamestate.players[0].hand);
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
		System.out.println(gamestate.getPlayerIndex());
	}
	@Test
	void testSubRule() {
		gamestate.setGameUp();
		IntegerCard c1 = new IntegerCard("Blue", "5");
		IntegerCard c2 = new IntegerCard("Yellow", "9");
		IntegerCard c3 = new IntegerCard("Yellow", "3");
		IntegerCard c4 = new IntegerCard("Yellow", "4");
		gamestate.addToDiscardPile(c1);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		gamestate.players[0].hand.add(c4);
		gamestate.cardHandler(c4, gamestate.players[0].hand);
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
	}
}
