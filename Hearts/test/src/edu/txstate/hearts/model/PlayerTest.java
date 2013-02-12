/**
 * 
 */
package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Neil Stickels
 * 
 */
public class PlayerTest {

	private Player p;
	private final static String playerName = "TestPlayer";
	private Deck deck;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p = new Agent(playerName);
		deck = new Deck();
		deck.shuffleCards();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Player#getName()}.
	 */
	@Test
	public void testGetName() {
		assertSame ("Player's name not set correctly", p.getName(),playerName);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Player#addScore(int)}.
	 */
	@Test
	public void testAddScore() {
		// make sure the score is 0 to start with
		assertEquals ("Player's score isn't 0 to start", p.getScore(), 0);
		// get two random numbers
		Random rand = new Random();
		int num1 = rand.nextInt(20);
		int num2 = rand.nextInt(20);
		// add the first number
		p.addScore(num1);
		// check the score
		assertEquals ("Player's score isn't right", p.getScore(), num1);
		p.addScore(num2);
		// add the second number
		assertEquals ("Player's score isn't right", p.getScore(), num1+num2);
		// check the score
		p.addScore(0);
		// make sure that adding nothing doesn't change the score
		assertEquals ("Player's score isn't right", p.getScore(), num1+num2);
	}

	/**
	 * Test method for
	 * {@link edu.txstate.hearts.model.Player#addCard(edu.txstate.hearts.model.Card)}
	 * .
	 */
	@Test
	public void testAddCard() {
		// start with a fresh deck
		deck.reset();
		// make sure the player has no cards in his hand
		assertEquals ("Player's hand isn't empty to start", p.getHand().size(), 0);
		Card c = deck.dealCard();
		p.addCard(c);
		// get the top card from the deck, give that to the player, and make
		// sure
		// they have the card we just gave them
		assertEquals ("New card wasn't added", p.getHand().get(0), c);
		// reshuffle the deck and give the card back
		deck.reset();
		p.getHand().remove(0);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Player#hasTwoOfClubs()}.
	 */
	@Test
	public void testHasTwoOfClubs() {
		// make sure the player doesn't have any cards
		assertEquals ("Player's hand isn't empty to start", p.getHand().size(), 0);
		// since they have no cards, they shouldn't have the two of clubs
		assertFalse(p.hasTwoOfClubs());
		// give them some non two of club cards
		p.addCard(new Card(Card.Face.Ace, Card.Suit.Clubs));
		p.addCard(new Card(Card.Face.King, Card.Suit.Clubs));
		// they still shouldn't have the two
		assertFalse(p.hasTwoOfClubs());
		// give them more cards (still no two)
		p.addCard(new Card(Card.Face.Queen, Card.Suit.Clubs));
		p.addCard(new Card(Card.Face.Jack, Card.Suit.Clubs));
		p.addCard(new Card(Card.Face.Ten, Card.Suit.Clubs));
		p.addCard(new Card(Card.Face.Nine, Card.Suit.Clubs));
		p.addCard(new Card(Card.Face.Eight, Card.Suit.Clubs));
		p.addCard(new Card(Card.Face.Seven, Card.Suit.Clubs));
		// they still shouldn't ahve the two
		assertFalse(p.hasTwoOfClubs());
		// give them the two of clubs
		p.addCard(new Card(Card.Face.Deuce, Card.Suit.Clubs));
		// they should have the two now
		assertTrue (p.hasTwoOfClubs());
		// give them more cards
		p.addCard(new Card(Card.Face.Six, Card.Suit.Clubs));
		p.addCard(new Card(Card.Face.Five, Card.Suit.Clubs));
		p.addCard(new Card(Card.Face.Four, Card.Suit.Clubs));
		p.addCard(new Card(Card.Face.Three, Card.Suit.Clubs));
		// make sure it still says they have the two of clubs
		assertTrue (p.hasTwoOfClubs());
		p.getHand().clear();
	}

	/**
	 * Test method for
	 * {@link edu.txstate.hearts.model.Player#getLegalCards(java.util.List, boolean)}
	 * .
	 */
	@Test
	public void testGetLegalCards() {
		// test that an empty hand has no legal cards
		assertEquals ("Player's hand isn't empty to start", p.getHand().size(), 0);
		assertEquals ("There should be no cards to play with an empty hand", p.getLegalCards(Collections.EMPTY_LIST, false).size(), 0);
		// give the player some random cards, and they are leading with hearts
		// broken, so
		// anything should be fine
		dealRandomHand(10);
		// check to make sure they can play anything
		assertEquals ("Every card should be playable", p.getLegalCards(Collections.EMPTY_LIST, true).size(), p.getHand().size());
		int numHearts = 0;
		while (numHearts == 0) {
			dealRandomHand(10);
			for (int i = 0; i < p.getHand().size(); i++) {
				Card c = p.getHand().get(i);
				if (c.getSuit() == Card.Suit.Hearts)
					numHearts++;
			}
		}
		// check to make sure if hearts haven't been broken, they can't play any hearts
		int numLegalCards = p.getHand().size()-numHearts;
		List<Card> legalCards = p.getLegalCards(Collections.EMPTY_LIST, false);
		assertEquals("Not the right number of legal cards", legalCards.size(), numLegalCards);

	}

	private void dealRandomHand(int size) {
		deck.reset();
		deck.shuffleCards();
		p.getHand().clear();
		for (int i = 0; i < size; i++) {
			p.addCard(deck.dealCard());
		}
	}

}
