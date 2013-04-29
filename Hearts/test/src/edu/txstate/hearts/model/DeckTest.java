/**
 * 
 */
package edu.txstate.hearts.model;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;

/**
 * @author gede.sutapa
 *
 */
public class DeckTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Deck#Deck()}.
	 */
	@Test
	public final void testDeck()
	{
		Deck deck = new Deck();
		assertEquals(deck.getCards().size(), 52);
		assertEquals(deck.getCards().get(0).equals(new Card(Face.Deuce, Suit.Clubs)), true);
		assertEquals(deck.getCards().get(51).equals(new Card(Face.Ace, Suit.Spades)), true);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Deck#shuffleCards()}.
	 */
	@Test
	public final void testShuffleCards()
	{
		Deck deck = new Deck();
		deck.shuffleCards();
		assertEquals(deck.getCards().size(), 52);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Deck#dealCard()}.
	 */
	@Test
	public final void testDealCard()
	{
		Deck deck = new Deck();
		deck.shuffleCards();
		//first 2 cards
		deck.dealCard();
		deck.dealCard();
		
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		deck.dealCard();
		//last 2 cards
		Card card = deck.dealCard();
		Assert.assertFalse(card == null);
		card = deck.dealCard();
		Assert.assertFalse(card == null);
		
		//no more cards, return null
		card = deck.dealCard();
		Assert.assertEquals(card, null);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Deck#reset()}.
	 */
	@Test
	public final void testReset()
	{
		Deck deck = new Deck();
		deck.shuffleCards();
		
		deck.dealCard();
		deck.dealCard();
		
		deck.reset();
		assertEquals(deck.getCards().size(), 52);
		assertEquals(deck.getCards().get(0).equals(new Card(Face.Deuce, Suit.Clubs)), true);
		assertEquals(deck.getCards().get(51).equals(new Card(Face.Ace, Suit.Spades)), true);
	}
}