package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.txstate.hearts.controller.Hearts.Passing;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;

public class AgentAggressiveTest {
	
	AgentAggressive aa;
	

	@Before
	public void setUp() throws Exception {
		aa = new AgentAggressive("test", 0);
		aa.addCard(new Card(Face.Deuce, Suit.Clubs));
		aa.addCard(new Card(Face.Three, Suit.Clubs));
		aa.addCard(new Card(Face.Ten, Suit.Clubs));
		aa.addCard(new Card(Face.Jack, Suit.Clubs));
		aa.addCard(new Card(Face.King, Suit.Clubs));
		aa.addCard(new Card(Face.Ace, Suit.Clubs));
		aa.addCard(new Card(Face.King, Suit.Diamonds));
		aa.addCard(new Card(Face.Nine, Suit.Hearts));
		aa.addCard(new Card(Face.Ten, Suit.Hearts));
		aa.addCard(new Card(Face.Jack, Suit.Hearts));
		aa.addCard(new Card(Face.King, Suit.Hearts));
		aa.addCard(new Card(Face.Three, Suit.Spades));
		aa.addCard(new Card(Face.King, Suit.Spades));
	}

	@Test
	public void testAddCard() {
		// my current hand doesn't have the Queen of Spades
		assertFalse("Agent shouldn't have the Queen of Spades", aa.hasQueenOfSpades());
		aa.addCard(new Card(Face.Queen, Suit.Spades));
		assertTrue("Agent should now have the Queen of Spades", aa.hasQueenOfSpades());		
	}

	@Test
	public void testPlayCard() {
		List<Card> playedCards = new ArrayList<Card>();
		// i have the two of clubs, so first test that i play that to start a game
		Card c = aa.playCard(playedCards, false, true);
		assertEquals("Should have been the two of clubs", c, new Card(Face.Deuce, Suit.Clubs));
		// after this, assume I am leading, I should play the king of diamonds
		// since that is my only diamond
		Card c2 = aa.playCard(playedCards, false, false);
		assertEquals("Should have been the king of diamonds", c2, new Card(Face.King, Suit.Diamonds));
		// next i should play the 3 of spades, since the king of spades would
		// mean i could take the queen of spades
		Card c3 = aa.playCard(playedCards, false, false);
		assertEquals("Should have been the three of spades", c3, new Card(Face.Three, Suit.Spades));
		// now let's change this up, and say that i am not leading, but someone
		// else is.  And they play a diamond... i should play king of hearts
		playedCards.add(new Card(Face.Ten, Suit.Diamonds));
		Card c4 = aa.playCard(playedCards, false, false);
		assertEquals("Should have been the king of hearts", c4, new Card(Face.King, Suit.Hearts));
		// for the last test, lets say someone led the nine of clubs, then
		// someone else played a heart, i have a club, so i have to play clubs
		// but i don't want to win, so it should be the 3 of clubs
		playedCards.clear();
		playedCards.add(new Card(Face.Nine, Suit.Clubs));
		Card c5 = aa.playCard(playedCards, true, false);
		assertEquals("Should have been the 3 of clubs", c5, new Card(Face.Three, Suit.Clubs));
		
		
	}

	@Test
	public void testGetCardsToPassPassing() {
		// with this hand, I should pass the 2 of clubs, the king of diamonds,
		// and the ace of clubs
		// the king of diamonds because that is my only diamond
		// the two of clubs because this agent always gets rid of the two of
		// clubs if it can
		// and the ace of clubs because my next smallest suit is spades, but
		// I have the king of spades, so I want to keep myself protected there,
		// so then I go to clubs, and i have a ton of clubs, so pass my highest
		List<Card> cards = aa.getCardsToPass(Passing.Front);
		assertTrue(cards.size() == 3);
		assertTrue(cards.contains(new Card(Face.Deuce, Suit.Clubs)));
		assertTrue(cards.contains(new Card(Face.King, Suit.Diamonds)));
		assertTrue(cards.contains(new Card(Face.Ace, Suit.Clubs)));
	}


}
