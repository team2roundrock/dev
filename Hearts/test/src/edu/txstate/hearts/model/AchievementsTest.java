/**
 * 
 */
package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.txstate.hearts.model.Achievements.Achievement;

/**
 * @author Jonathan
 *
 */
public class AchievementsTest {

	private Player p;
	private final static String playerName = "TestPlayer";
	private Deck deck;
	private Achievements a = new Achievements();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p = new User(playerName, 0);
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
		assertEquals("Map doesn't have BrokenHeart achievement",true,a.listOfAchievements.containsKey
				(Achievements.Achievement.BrokenHeart));
		assertEquals("Map doesn't have HatTrick achievement",true,a.listOfAchievements.containsKey
				(Achievements.Achievement.HatTrick));
		assertEquals("Map doesn't have OvershootingTheMoon1 achievement",true,a.listOfAchievements.containsKey
				(Achievements.Achievement.OvershootingTheMoon1));
		assertEquals("Map doesn't have OvershootingTheMoon2 achievement",true,a.listOfAchievements.containsKey
				(Achievements.Achievement.OvershootingTheMoon2));
		assertEquals("Map doesn't have OvershootingTheMoon3 achievement",true,a.listOfAchievements.containsKey
				(Achievements.Achievement.OvershootingTheMoon3));
		assertEquals("Map doesn't have PassingTheBuck achievement",true,a.listOfAchievements.containsKey
				(Achievements.Achievement.PassingTheBuck));
		assertEquals("Map doesn't have ShootingTheMoon achievement",true,a.listOfAchievements.containsKey
				(Achievements.Achievement.ShootingTheMoon));
		assertEquals("Map doesn't have StartTheParty achievement",true,a.listOfAchievements.containsKey
				(Achievements.Achievement.StartTheParty));
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#getCounterOvershootingTheMoon()}.
	 */
	@Test
	public void testGetCounterOvershootingTheMoon() {
		assertEquals("Counter is not 0",a.getCounterOvershootingTheMoon(),0);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#setCounterOvershootingTheMoon(int)}.
	 */
	@Test
	public void testSetCounterOvershootingTheMoon() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#endGameAchievement(int, boolean)}.
	 */
	@Test
	public void testEndGameAchievement() {
		
		//Pass in a score of 25. Player is one heart short of an achievement.
		a.endGameAchievement(25, false);
		assertEquals("An achievement has been earned,",false,a.listOfAchievements.containsValue(true));
		assertEquals("Counter shouldn't be modified,",0,a.getCounterOvershootingTheMoon());
		
		
		//Pass in a score of 13. Test "Broken Hearts" achievement.
		a.endGameAchievement(13, false); 
		assertEquals("Counter shouldn't be modified,",0,a.getCounterOvershootingTheMoon());
		assertEquals("No achievement has been set,",true,a.listOfAchievements.containsValue(true));
		assertEquals("Broken Heart hasn't been set,",true,a.listOfAchievements.get
				(Achievements.Achievement.BrokenHeart));
		//assertEquals("Achievement not set,",true,a.achievedOrNot);
		//assertEquals("Achievement notification not working,",true,a.notifyAchievementEarned);
		
		//Pass in a score of 26. Test "Overshooting The Moon1" achievement
		a.endGameAchievement(26, false);
		assertEquals("OvershootingTheMoon1 hasn't been set,",true,a.listOfAchievements.get
				(Achievements.Achievement.OvershootingTheMoon1));
		
		//Pass in a second score of 26. Test "Overshooting The Moon2" achievement
		a.endGameAchievement(26, false);
		assertEquals("OvershootingTheMoon2 hasn't been set,",true,a.listOfAchievements.get
				(Achievements.Achievement.OvershootingTheMoon2));
		assertEquals("OvershootingTheMoon3 has been set,",false,a.listOfAchievements.get
				(Achievements.Achievement.OvershootingTheMoon3));
		
		//Pass in a third score of 26. Test "Overshooting The Moon3" achievement
		a.endGameAchievement(26, false);
		assertEquals("OvershootingTheMoon2 hasn't been set,",true,a.listOfAchievements.get
				(Achievements.Achievement.OvershootingTheMoon3));
		
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#PassingTheBuck(int, boolean, int)}.
	 */
	@Test
	public void testPassingTheBuck() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#StartTheParty(int, boolean, int)}.
	 */
	@Test
	public void testStartTheParty() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#HatTrick(int, boolean, int)}.
	 */
	@Test
	public void testHatTrick() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.txstate.hearts.model.Achievements#returnList(java.util.Map)}.
	 */
	@Test
	public void testReturnList() {
		fail("Not yet implemented");
	}

}
