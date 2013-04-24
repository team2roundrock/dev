package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AgentDeterminedTest {
	
	List<Card> cardsPlayed = new ArrayList();
	Player player = new AgentDetermined("name", 0);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlayCard() {
		boolean heartsBroken = false;
		boolean veryFirstTurn = false;
		Card cardToPlay = null;
		
		
		cardsPlayed.add(new Card(Card.Face.Ace, Card.Suit.Diamonds));
		cardsPlayed.add(new Card(Card.Face.Eight, Card.Suit.Diamonds));
		cardsPlayed.add(new Card(Card.Face.Four, Card.Suit.Diamonds));
		
		player.addCard(new Card(Card.Face.Five, Card.Suit.Diamonds));
		player.addCard(new Card(Card.Face.Seven, Card.Suit.Diamonds));
		player.addCard(new Card(Card.Face.Three, Card.Suit.Diamonds));
		
		
		
		cardToPlay = new Card(Card.Face.Five, Card.Suit.Diamonds);
		
		assertEquals("It did not pass the highest card", cardToPlay, player.playCard(cardsPlayed, heartsBroken, veryFirstTurn));
	}

	@Test
	public void testGetCardsToPass() {
		fail("Not yet implemented");
	}

}
