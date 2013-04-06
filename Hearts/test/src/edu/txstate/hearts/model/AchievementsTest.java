/**
 * 
 */
package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import edu.txstate.hearts.model.Achievements.Achievement;

/**
 * @author Jonathan Shelton
 *
 */
public class AchievementsTest {

	private User p;
	//private String playerName;
	private Deck deck;
	private Achievements a;
	private List<Card> listOfCards = new ArrayList<Card>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p = new User("TestPlayer", 0);
		a = new Achievements(p.getName());
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
	 * Test method for {@link edu.txstate.hearts.model.Achievements#Achievements()}.
	 */
	@Test
	public void testAchievements() {
		
		//Ensure all achievements have been placed in list
		assertEquals("Map doesn't have BrokenHeart achievement",true,a.listOfAchievements.containsKey
				("BrokenHeart"));
		assertEquals("Map doesn't have HatTrick achievement",true,a.listOfAchievements.containsKey
				("HatTrick"));
		assertEquals("Map doesn't have OvershootingTheMoon1 achievement",true,a.listOfAchievements.containsKey
				("OvershootingTheMoon1"));
		assertEquals("Map doesn't have OvershootingTheMoon2 achievement",true,a.listOfAchievements.containsKey
				("OvershootingTheMoon2"));
		assertEquals("Map doesn't have OvershootingTheMoon3 achievement",true,a.listOfAchievements.containsKey
				("OvershootingTheMoon3"));
		assertEquals("Map doesn't have PassingTheBuck achievement",true,a.listOfAchievements.containsKey
				("PassingTheBuck"));
		assertEquals("Map doesn't have ShootingTheMoon achievement",true,a.listOfAchievements.containsKey
				("ShootingTheMoon"));
		assertEquals("Map doesn't have StartTheParty achievement",true,a.listOfAchievements.containsKey
				("StartTheParty"));
		
		//Ensure that player name is being passed correctly
		String checkName = "TestPlayer";
		p.getAchievements();
		assertEquals("Player Name isn't correct,", checkName, Achievements.getUserFileName());
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#getCounterOvershootingTheMoon()}.
	 */
	@Test
	public void testGetCounterOvershootingTheMoon() {
		assertEquals("Counter is not 0",0,a.getCounterOvershootingTheMoon());
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#setCounterOvershootingTheMoon(int)}.
	 */
	@Test
	public void testSetCounterOvershootingTheMoon() {
		a.setCounterOvershootingTheMoon(2);
		assertEquals("Counter not set correctly,",2,a.getCounterOvershootingTheMoon());
		
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#endGameAchievements(int, boolean)}.
	 */
	@Test
	public void testEndGameAchievements() {
		
		//Pass in a score of 25. Player is one heart short of an achievement.
		a.endGameAchievements(25, false);
		assertEquals("An achievement has been earned,",false,a.listOfAchievements.containsValue(true));
		assertEquals("Counter shouldn't be modified,",0,a.getCounterOvershootingTheMoon());
		
		
		//Pass in a score of 13. Test "Broken Hearts" achievement.
		a.endGameAchievements(13, false); 
		assertEquals("Counter shouldn't be modified,",0,a.getCounterOvershootingTheMoon());
		assertEquals("No achievement has been set,",true,a.listOfAchievements.containsValue(true));
		assertEquals("Broken Heart hasn't been set,",true,a.listOfAchievements.get
				("BrokenHeart"));
		//assertEquals("Achievement not set,",true,a.achievedOrNot);
		//assertEquals("Achievement notification not working,",true,a.notifyAchievementEarned);
		
		//Pass in first score of 26. Test "Overshooting The Moon1" & Shooting The M0on
		a.endGameAchievements(26, false);
		assertEquals("ShootingTheMoon hasn't been set,",true,a.listOfAchievements.get
				("ShootingTheMoon"));
		assertEquals("OvershootingTheMoon1 hasn't been set,",true,a.listOfAchievements.get
				("OvershootingTheMoon1"));
		//assertEquals("Counter is not 0,",0,a.getCounterOvershootingTheMoon());
		assertEquals("Counter is not 1,",1,a.getCounterOvershootingTheMoon());
		
		//Pass in a second score of 26. Test "Overshooting The Moon2" achievement
		a.endGameAchievements(26, false);
		assertEquals("OvershootingTheMoon2 hasn't been set,",true,a.listOfAchievements.get
				("OvershootingTheMoon2"));
		assertEquals("OvershootingTheMoon3 has been set,",false,a.listOfAchievements.get
				("OvershootingTheMoon3"));
		assertEquals("Counter is not 2,",2,a.getCounterOvershootingTheMoon());
		
		//Pass in a third score of 26. Test "Overshooting The Moon3" achievement
		a.endGameAchievements(26, false);
		assertEquals("OvershootingTheMoon3 hasn't been set,",true,a.listOfAchievements.get
				("OvershootingTheMoon3"));
		
		
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#PassingTheBuck(int, boolean, List<Card>)}.
	 */
	@Test
	public void testPassingTheBuck() {
		
		//Pass in a hand without desired Queen of Spades
		listOfCards.add(new Card(Card.Face.Ace, Card.Suit.Hearts));
		listOfCards.add(new Card(Card.Face.Queen, Card.Suit.Hearts));
		listOfCards.add(new Card(Card.Face.Three, Card.Suit.Spades));
		assertEquals("PassingTheBuck is set, but shouldn't be,",false,a.listOfAchievements.
				get("PassingTheBuck"));
		
		//Ensure entire method returns false when requirements not met
		assertEquals("Method should return false",false,a.PassingTheBuck(true, listOfCards));	
		
		//Pass in a hand with desired Queen of Spades
		//Ensure entire method returns true when requirements met
		listOfCards.add(new Card(Card.Face.Ace, Card.Suit.Hearts));
		listOfCards.add(new Card(Card.Face.Queen, Card.Suit.Spades));
		listOfCards.add(new Card(Card.Face.Three, Card.Suit.Spades));
		assertEquals("Method should return true",true,a.PassingTheBuck(false, listOfCards));
		
		//Ensure achievement is set when requirements met
		assertEquals("PassingTheBuck hasn't been set,",true,a.listOfAchievements.
				get("PassingTheBuck"));
		
		listOfCards.add(new Card(Card.Face.Ace, Card.Suit.Hearts));
		listOfCards.add(new Card(Card.Face.Queen, Card.Suit.Spades));
		listOfCards.add(new Card(Card.Face.Three, Card.Suit.Spades));
		assertEquals("Method should return false",false,a.PassingTheBuck(false, listOfCards));
		
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#StartTheParty(List<Card>, Player)}.
	 */
	@Test
	public void testStartTheParty() {
		
		//User does not play the two of clubs
		listOfCards.add(new Card(Card.Face.Ace, Card.Suit.Hearts));
		assertEquals("StartTheParty is set, but shouldn't be,",false,a.listOfAchievements.
				get("StartTheParty"));
		
		//Ensure entire method returns false when requirements not met
		assertEquals("Method should return false",false,a.StartTheParty(listOfCards, p));
		
		//User plays the two of clubs
		//Ensure entire method returns true when requirements met
		listOfCards.add(new Card(Card.Face.Deuce, Card.Suit.Clubs));
		assertEquals("Method should return true",true,a.StartTheParty(listOfCards, p));
		
		//Ensure achievement is set when requirements met
		assertEquals("StartTheParty hasn't been set,",true,a.listOfAchievements.
				get("StartTheParty"));
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#HatTrick(int, boolean, int)}.
	 */
	@Test
	public void testHatTrick() {
		//Special method - achievement is given to user that called it, if not already earned
		
		//Ensure achievement is set
		assertEquals("StartTheParty hasn't been set,",true,a.HatTrick());
		
		//Call method again
		a.HatTrick();
		
		//Ensure method returns false (since achievement already earned)
		assertEquals("Method should return false,",false,a.HatTrick());
	}

//	/**
//	 * Test method for {@link edu.txstate.hearts.model.Achievements#getListOfAchievements()}.
//	 */
//	@Test
//	public void TestGetListOfAchievements() {
//		//Activate the Hat Trick achievement
//		a.HatTrick();
//		assertEquals("The lists are not equal",a.listOfAchievements.get("HatTrick"),
//				Achievements.getListOfAchievements().contains
//				("HatTrick"));
//	}

}
