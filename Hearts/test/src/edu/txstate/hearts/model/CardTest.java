/**
 * 
 */
package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;

/**
 * @author gede.sutapa
 *
 */
public class CardTest
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
	 * Test method for {@link edu.txstate.hearts.model.Card#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		Card card1 = new Card(Face.Ace, Suit.Clubs);
		Card card2 = new Card(Face.Ace, Suit.Clubs);
		Card card3 = new Card(Face.Ace, Suit.Diamonds);
		Card card4 = new Card(Face.Nine, Suit.Diamonds);
		
		assertEquals(card1.equals(card2), true);
		assertEquals(card1.equals(card3), false);
		assertEquals(card2.equals(card3), false);
		assertEquals(card3.equals(card4), false);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Card#Card(edu.txstate.hearts.model.Card.Face, edu.txstate.hearts.model.Card.Suit)}.
	 */
	@Test
	public final void testCard()
	{
		Card card = new Card(Face.Four, Suit.Hearts);
		assertEquals(card.getFace(), Face.Four);
		assertEquals(card.getSuit(), Suit.Hearts);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Card#getFace()}.
	 */
	@Test
	public final void testGetFace()
	{
		Card card = new Card(Face.Six, Suit.Spades);
		assertEquals(card.getFace(), Face.Six);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Card#getSuit()}.
	 */
	@Test
	public final void testGetSuit()
	{
		Card card = new Card(Face.Six, Suit.Spades);
		assertEquals(card.getSuit(), Suit.Spades);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Card#toString()}.
	 */
	@Test
	public final void testToString()
	{
		Card card = new Card(Face.Six, Suit.Spades);
		assertEquals(card.toString(), "Six of Spades");
	}
}