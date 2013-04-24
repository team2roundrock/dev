package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	
	private User u;
	private Achievements a;
	private List<Card> listOfCards = new ArrayList<Card>();
	List<Card> cardsPlayed = new ArrayList<Card>();
	private Deck deck;

	@Before
	public void setUp() throws Exception {
		u = new User("TestPlayer", 0);
		a = new Achievements(u.getName());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlayCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCardsToPass() {
		fail("Not yet implemented");
	}

	@Test
	public void testUser() {
		fail("Not yet implemented");
	}

//	@Test
//	public void testGetAchievements() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testSetAchievements() {
//		fail("Not yet implemented");
//	} 

	//TEST IS NOT FINISHED
	@Test
	public void testTryPlayCard() {
		//(expected = Exception.class) //if an exception is thrown, test will pass
			
		//Not the first turn
		boolean veryFirstTurn = false;
		//Hearts has been broken
		boolean heartsBroken = true;
		
		//Adding 5 specific cards to User's hand, NO CLUBS
		u.addCard(new Card(Card.Face.Nine, Card.Suit.Spades));
		u.addCard(new Card(Card.Face.Four, Card.Suit.Hearts));
		u.addCard(new Card(Card.Face.Five, Card.Suit.Hearts));
		u.addCard(new Card(Card.Face.Deuce, Card.Suit.Diamonds));
		u.addCard(new Card(Card.Face.Ace, Card.Suit.Diamonds));
		
		//Adding 3 cards already played on table
		cardsPlayed.add(new Card(Card.Face.Six, Card.Suit.Spades));
		cardsPlayed.add(new Card(Card.Face.Six, Card.Suit.Hearts));
		cardsPlayed.add(new Card(Card.Face.Jack, Card.Suit.Spades));
		
		//User tries to play Four of Clubs, an invalid card
		Card card = new Card(Card.Face.Nine, Card.Suit.Spades);
		
		//Initialize method with new values
		try {
			u.TryPlayCard(card, cardsPlayed, heartsBroken , veryFirstTurn );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Exception should be thrown
//		assertEquals("Player is playing an invalid card,", Exception.class, 
//				u.getHand().
//				contains(new Card(Card.Face.Four, Card.Suit.Clubs)) );
		
		//PLayer hand should still contain card that was invalid to play
		assertEquals("Player is playing an invalid card,", false, 
				u.getHand().
				contains(new Card(Card.Face.Nine, Card.Suit.Spades)) );
	}
	
	/*
	 * Test to ensure exceptions are working on TryPlayCards()
	 * TEST NOT FINISHED
	 */
	@Test (expected = Exception.class) //if an exception is thrown, test will pass
	public void testTryPlayCardEx() throws Exception 
	{		
		//Not the first turn
		
				boolean veryFirstTurn = false;
				//Hearts has been broken
				boolean heartsBroken = true;
				
				//Adding 5 specific cards to User's hand, NO CLUBS
				u.addCard(new Card(Card.Face.Nine, Card.Suit.Spades));
				u.addCard(new Card(Card.Face.Four, Card.Suit.Hearts));
				u.addCard(new Card(Card.Face.Five, Card.Suit.Hearts));
				u.addCard(new Card(Card.Face.Deuce, Card.Suit.Diamonds));
				u.addCard(new Card(Card.Face.Ace, Card.Suit.Diamonds));
				
				//Adding 3 cards already played on table
				cardsPlayed.add(new Card(Card.Face.Nine, Card.Suit.Spades));
				cardsPlayed.add(new Card(Card.Face.Four, Card.Suit.Hearts));
				cardsPlayed.add(new Card(Card.Face.Jack, Card.Suit.Spades));
				
				//User tries to play Four of Clubs, a valid card
				Card card = new Card(Card.Face.Four, Card.Suit.Spades);
				
				Card testCard = new Card(Card.Face.Four, Card.Suit.Spades);
				
				//Initialize method with new values
				u.TryPlayCard(card, cardsPlayed, heartsBroken , veryFirstTurn );
				assertEquals("The correct card wasn't passed", true, u.getHand().
						contains(new Card(Card.Face.Four, Card.Suit.Spades)));
		
	}
	
	private void dealRandomHand(int size) {
		deck.reset();
		deck.shuffleCards();
		u.getHand().clear();
		for (int i = 0; i < size; i++) {
			u.addCard(deck.dealCard());
		}
	}

}
