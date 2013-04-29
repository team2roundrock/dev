package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.txstate.hearts.controller.Hearts.Passing;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;

/**
 * @author I Gede Sutapa
 *
 */
public class AgentGoofyTest
{

	private Player agentGoofy;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		agentGoofy = new AgentGoofy("Goofy", 0);
		
		agentGoofy.addCard(new Card(Face.Deuce, Suit.Clubs));
		agentGoofy.addCard(new Card(Face.Three, Suit.Clubs));
		agentGoofy.addCard(new Card(Face.Three, Suit.Spades));
		agentGoofy.addCard(new Card(Face.Deuce, Suit.Spades));
		agentGoofy.addCard(new Card(Face.Ten, Suit.Clubs));
		agentGoofy.addCard(new Card(Face.Jack, Suit.Clubs));
		agentGoofy.addCard(new Card(Face.Jack, Suit.Spades));
		agentGoofy.addCard(new Card(Face.Ten, Suit.Spades));
		agentGoofy.addCard(new Card(Face.King, Suit.Clubs));
		agentGoofy.addCard(new Card(Face.King, Suit.Diamonds));
		agentGoofy.addCard(new Card(Face.King, Suit.Hearts));
		agentGoofy.addCard(new Card(Face.King, Suit.Spades));
		agentGoofy.addCard(new Card(Face.Ace, Suit.Clubs));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		agentGoofy = null;
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.AgentGoofy#playCard(java.util.List, boolean, boolean)}.
	 */
	@Test
	public final void testPlayCard()
	{
		Card card = agentGoofy.playCard(new ArrayList<Card>(), true, true);
		assertEquals(card, new Card(Face.Deuce, Suit.Clubs));
		
		List<Card> cardsPlayed = new ArrayList<Card>(1);
		cardsPlayed.add(new Card(Face.Eight, Suit.Diamonds));
		card = agentGoofy.playCard(cardsPlayed, true, false);
		assertEquals(card, new Card(Face.King, Suit.Diamonds));
		
		cardsPlayed = new ArrayList<Card>(1);
		cardsPlayed.add(new Card(Face.Nine, Suit.Hearts));
		card = agentGoofy.playCard(cardsPlayed, true, false);
		assertEquals(card, new Card(Face.King, Suit.Hearts));
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.AgentGoofy#getCardsToPass()}.
	 */
	@Test
	public final void testGetCardsToPass()
	{
		List<Card> cardsToPass = agentGoofy.getCardsToPass(Passing.Left);
		
		assertEquals(cardsToPass.size(), 3);
		assertEquals(cardsToPass.get(0), new Card(Face.Ace, Suit.Clubs));
		assertEquals(cardsToPass.get(1), new Card(Face.King, Suit.Spades));
		assertEquals(cardsToPass.get(2), new Card(Face.King, Suit.Hearts));
	}
}