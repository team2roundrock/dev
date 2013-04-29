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

public class AgentDeterminedTest 
{	
	Player agentDetermined;

	@Before
	public void setUp() throws Exception 
	{
		agentDetermined = new AgentDetermined("Determined", 0);
	}

	@After
	public void tearDown() throws Exception 
	{
		agentDetermined = null;
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.AgentDetermined#playCard(java.util.List, boolean, boolean)}.
	 */
	@Test
	public void testPlayCard() 
	{
		agentDetermined.addCard(new Card(Face.Deuce, Suit.Clubs));
		agentDetermined.addCard(new Card(Face.Three, Suit.Clubs));
		agentDetermined.addCard(new Card(Face.Three, Suit.Spades));
		agentDetermined.addCard(new Card(Face.Nine, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Ten, Suit.Clubs));
		agentDetermined.addCard(new Card(Face.Jack, Suit.Clubs));
		agentDetermined.addCard(new Card(Face.Jack, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Ten, Suit.Spades));
		agentDetermined.addCard(new Card(Face.King, Suit.Clubs));
		agentDetermined.addCard(new Card(Face.King, Suit.Diamonds));
		agentDetermined.addCard(new Card(Face.King, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.King, Suit.Spades));
		agentDetermined.addCard(new Card(Face.Ace, Suit.Clubs));
		
		//very first turn, play deuce of clubs
		Card card = agentDetermined.playCard(new ArrayList<Card>(), false, true);
		assertEquals(card, new Card(Face.Deuce, Suit.Clubs));
		
		//play lowest available card
		card = agentDetermined.playCard(new ArrayList<Card>(), false, false);
		assertEquals(card, new Card(Face.Three, Suit.Clubs));
		
		//play highest available card
		agentDetermined.addInPlayCards(new Card(Face.Four, Suit.Clubs));
		card = agentDetermined.playCard(new ArrayList<Card>(), false, false);
		assertEquals(card, new Card(Face.Ace, Suit.Clubs));
		
		//hearts broken, find highest heart not exceeding hearts on table
		List<Card> playedCards = new ArrayList<Card>(1);
		playedCards.add(new Card(Face.Ten, Suit.Hearts));
		agentDetermined.addInPlayCards(new Card(Face.Ten, Suit.Hearts));
		card = agentDetermined.playCard(playedCards, true, false);
		assertEquals(card, new Card(Face.Nine, Suit.Hearts));
		
		//hearts broken, get lowest playable card
		playedCards = new ArrayList<Card>(1);
		playedCards.add(new Card(Face.Eight, Suit.Hearts));
		agentDetermined.addInPlayCards(new Card(Face.Eight, Suit.Hearts));
		card = agentDetermined.playCard(playedCards, true, false);
		assertEquals(card, new Card(Face.Jack, Suit.Hearts));
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.AgentDetermined#getCardsToPass()}.
	 */
	@Test
	public void testGetCardsToPass() 
	{
		agentDetermined.addCard(new Card(Face.King, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.King, Suit.Clubs));
		agentDetermined.addCard(new Card(Face.Queen, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Queen, Suit.Spades));
		agentDetermined.addCard(new Card(Face.Ten, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Nine, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Eight, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Seven, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Six, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Five, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Four, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Three, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Deuce, Suit.Hearts));
		
		List<Card> cardsToPass = agentDetermined.getCardsToPass(Passing.Left);
		
		assertEquals(cardsToPass.size(), 3);
		assertEquals(cardsToPass.get(0), new Card(Face.King, Suit.Hearts));
		assertEquals(cardsToPass.get(1), new Card(Face.Queen, Suit.Spades));
		assertEquals(cardsToPass.get(2), new Card(Face.Queen, Suit.Hearts));
	}
	
	/**
	 * Test method for {@link edu.txstate.hearts.model.AgentDetermined#getCardsToPass()}.
	 */
	@Test
	public void testGetCardsToPass2() 
	{
		agentDetermined.addCard(new Card(Face.King, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.King, Suit.Clubs));
		agentDetermined.addCard(new Card(Face.Queen, Suit.Diamonds));
		agentDetermined.addCard(new Card(Face.Queen, Suit.Clubs));
		agentDetermined.addCard(new Card(Face.Ten, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Nine, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Eight, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Seven, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Six, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Five, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Four, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Three, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Deuce, Suit.Hearts));
		
		List<Card> cardsToPass = agentDetermined.getCardsToPass(Passing.Left);
		
		assertEquals(cardsToPass.size(), 3);
		assertEquals(cardsToPass.get(0), new Card(Face.King, Suit.Hearts));
		assertEquals(cardsToPass.get(1), new Card(Face.Ten, Suit.Hearts));
		assertEquals(cardsToPass.get(2), new Card(Face.King, Suit.Clubs));
	}
	
	/**
	 * Test method for {@link edu.txstate.hearts.model.AgentDetermined#getCardsToPass()}.
	 */
	@Test
	public void testGetCardsToPass3() 
	{
		agentDetermined.addCard(new Card(Face.King, Suit.Clubs));
		agentDetermined.addCard(new Card(Face.Queen, Suit.Diamonds));
		agentDetermined.addCard(new Card(Face.Nine, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Eight, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Seven, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Six, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Five, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Four, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Three, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Deuce, Suit.Hearts));
		agentDetermined.addCard(new Card(Face.Deuce, Suit.Diamonds));
		agentDetermined.addCard(new Card(Face.Three, Suit.Diamonds));
		agentDetermined.addCard(new Card(Face.Four, Suit.Diamonds));
		
		List<Card> cardsToPass = agentDetermined.getCardsToPass(Passing.Left);
		
		assertEquals(cardsToPass.size(), 3);
		assertEquals(cardsToPass.get(0), new Card(Face.King, Suit.Clubs));
		assertEquals(cardsToPass.get(1), new Card(Face.Queen, Suit.Diamonds));
		assertEquals(cardsToPass.get(2), new Card(Face.Nine, Suit.Hearts));
	}
}