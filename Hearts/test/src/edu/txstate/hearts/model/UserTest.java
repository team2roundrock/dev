package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for user class
 * @author Jonathan
 *
 */
public class UserTest {
	
	private User u;
	//private Achievements a;
	//private List<Card> listOfCards;
	List<Card> cardsPlayed;
	private Deck deck;
	File file, file2;

	@Before
	public void setUp() throws Exception {
		u = new User("TestPlayer", 0);
		//a = new Achievements(u.getName());
		//listOfCards = new ArrayList<Card>();
		cardsPlayed = new ArrayList<Card>();
		
		//Two files that get created in different tests
		//Set up for file operations and cleanup
		file = new File("Constructor Test.txt");
		file2 = new File("TestPlayer.txt");
	}

	@After
	public void tearDown() throws Exception {
		file.delete();
		file2.delete();
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
		/*Ensure correct constructor for achievements is
		called if user is a new user and 
		file is not found (after this test has run once,
		the file should always exist so the test will no
		longer be accurate for the intended usage, unless
		the filename is changed or the file is deleted )*/
		u = new User("Constructor Test", 0);
		
		assertEquals("File not created, ", true, file.exists());
		//Ensure new user's list of achievements are false
		assertEquals("File not created", "{BrokenHeart=false, " +
				"OvershootingTheMoon=false, StartTheParty=false, " +
				"PassingTheBuck=false, OvershootingTheMoon2=false, " +
				"OvershootingTheMoon1=false, HatTrick=false, " +
				"ShootingTheMoon=false}", u.getAchievements().
				listOfAchievements.toString());
		
		//Gives an achievement to newly created user
		u.getAchievements().HatTrick();
		
		/*Now, assuming the user quits, let's instantiate a new
		  user with the same name, as would happen if someone quit
		  and later started a new game with the same name */
		User newUser = new User("Constructor Test",0);
		//Ensure user's list of achievements is the same
		assertEquals("Proper achievement was never set, ","{BrokenHeart=false, " +
				"OvershootingTheMoon=false, StartTheParty=false, " +
				"PassingTheBuck=false, OvershootingTheMoon2=false, " +
				"OvershootingTheMoon1=false, HatTrick=true, " +
				"ShootingTheMoon=false}",newUser.getAchievements().listOfAchievements.toString());
		
		
	}

//	@Test
//	public void testGetAchievements() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testSetAchievements() {
//		fail("Not yet implemented");
//	} 

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
		
		//User tries to play Four of Clubs, a valid card
		Card card = new Card(Card.Face.Nine, Card.Suit.Spades);
		
		//Initialize method with new values
		try {
			u.TryPlayCard(card, cardsPlayed, heartsBroken , veryFirstTurn );
		} catch (Exception e) {
			fail("There should be no exception. This is a valid move");
		}
		
		//Player hand should still contain card that was invalid to play
		assertEquals("Playing an invalid card,", false, u.getHand().contains
				    (new Card(Card.Face.Nine, Card.Suit.Spades)) );
		
		u.clearCards();
		
	}
	
	@Test (expected = Exception.class) //if an exception is thrown, test will pass
	public void testTryPlayCardEx() throws Exception 
	{		
		// Not the first turn

		boolean veryFirstTurn = false;
		// Hearts has been broken
		boolean heartsBroken = true;

		// Adding 5 specific cards to User's hand, NO CLUBS
		u.addCard(new Card(Card.Face.Nine, Card.Suit.Spades));
		u.addCard(new Card(Card.Face.Four, Card.Suit.Hearts));
		u.addCard(new Card(Card.Face.Five, Card.Suit.Hearts));
		u.addCard(new Card(Card.Face.Deuce, Card.Suit.Diamonds));
		u.addCard(new Card(Card.Face.Ace, Card.Suit.Diamonds));

		// Adding 3 cards already played on table
		cardsPlayed.add(new Card(Card.Face.Jack, Card.Suit.Spades));
		cardsPlayed.add(new Card(Card.Face.Ten, Card.Suit.Clubs));
		cardsPlayed.add(new Card(Card.Face.Three, Card.Suit.Clubs));

		// User tries to play Deuce of Diamonds, an invalid card
		Card card = new Card(Card.Face.Deuce, Card.Suit.Diamonds);

		// Initialize method with new values
		u.TryPlayCard(card, cardsPlayed, heartsBroken, veryFirstTurn);

		// Deuce of Diamonds should still be in user's hand
		// Should never return a value, as exception is thrown)
		assertEquals("The correct card wasn't passed", true, u.getHand()
				.contains(new Card(Card.Face.Deuce, Card.Suit.Diamonds)));

		// Clear old cards
		cardsPlayed.clear();
		// Place three new cards on table
		cardsPlayed.add(new Card(Card.Face.Three, Card.Suit.Diamonds));
		cardsPlayed.add(new Card(Card.Face.Three, Card.Suit.Clubs));
		cardsPlayed.add(new Card(Card.Face.Ace, Card.Suit.Clubs));

		// Try to play a heart
		card = new Card(Card.Face.Four, Card.Suit.Hearts);
		// Initialize method with with hearts not broken
		u.TryPlayCard(card, cardsPlayed, heartsBroken = false, veryFirstTurn);

		assertEquals("Hearts has been played!", true,
				u.getHand()
						.contains(new Card(Card.Face.Four, Card.Suit.Hearts)));

		cardsPlayed.clear();
		// First turn
		veryFirstTurn = true;
		// Try to play card that is not Deuce of Clubs on first turn
		card = new Card(Card.Face.Ace, Card.Suit.Diamonds);
		assertEquals("Deuce of Clubs hasn't been played!", true,
				u.getHand()
						.contains(new Card(Card.Face.Ace, Card.Suit.Diamonds)));
	}
	
//	private void dealRandomHand(int size) {
//		deck.reset();
//		deck.shuffleCards();
//		u.getHand().clear();
//		for (int i = 0; i < size; i++) {
//			u.addCard(deck.dealCard());
//		}
//	}

}
