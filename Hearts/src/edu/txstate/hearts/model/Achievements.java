/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.txstate.hearts.model.Achievements.Achievement;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;

/**
 * @author Jonathan Shelton
 *
 */


public class Achievements 
{

	boolean notifyAchievementEarned = false; //should this be moved into the while loop?
	public static enum Achievement { BrokenHeart, ShootingTheMoon,
		PassingTheBuck, StartTheParty, HatTrick, OvershootingTheMoon1, 
		OvershootingTheMoon2, OvershootingTheMoon3 };
		// TODO There are three Overshooting The Moon entries. Each indicates the 
		// progress toward the achievement (i.e. 1 out of 3, 2 out of 3, 3 out of 3)
	private int counterOvershootingTheMoon;
	boolean achievedOrNot;
	private UserData user = new UserData();
	private ReadFiles files = new ReadFiles();
	private Player player;
	Map<Achievement, Boolean> listOfAchievements;
	
	
	public Achievements()
	{
		counterOvershootingTheMoon = 0; //each increment is progress toward achievement
		//TODO counter may need to be read in from file each time
		//this.achievement = achievement;
		listOfAchievements = new HashMap<Achievement, Boolean>();
		
		// Create an array, populate each index with achievement name and
		// a default boolean of false, meaning it hasn't been earned. Will save 
		// array values to file only when they have changed. Array does NOT
		// necessarily reflect earned achievements; only the user's file
		// will hold saved achievement data throughout each game
		for(Achievements.Achievement achievement : Achievements.Achievement.values()) //steps through the enum
		{
			listOfAchievements.put(achievement, false);
		}
		
	}
	
	/**
	 * @return counter for Overshooting The Moon achievement
	 */
	public int getCounterOvershootingTheMoon() {
		return counterOvershootingTheMoon;
	}

	/**
	 * @param counterOvershootingTheMoon counter for Overshooting The Moon achievement
	 */
	public void setCounterOvershootingTheMoon(int counterOvershootingTheMoon) {
		this.counterOvershootingTheMoon = counterOvershootingTheMoon;
	}

	//TODO Read array from file using separate UserData class. Check if file exists first.
	
	public void endGameAchievement(int score, boolean notifyAchievementEarned){
		BrokenHeart(score, notifyAchievementEarned, counterOvershootingTheMoon);
		ShootingTheMoon(score, notifyAchievementEarned, counterOvershootingTheMoon);
		OvershootingTheMoon(score, notifyAchievementEarned, counterOvershootingTheMoon);
		notifyAchievementEarned = true;
	}
	
	//writeAchievement(counter,table)
	
	private boolean BrokenHeart(int score, boolean notifyAchievementEarned, int counterOvershootingTheMoon) {
		// "end game" usage
		// Score: (Full_score)-(QoS)
		if (score == 13) //26 full - 13 queen
		{
			// if the value contains BrokenHeart key...
			if (listOfAchievements.containsKey(Achievement.BrokenHeart)) {
				// get the value (true or false). null if key not found
				if (listOfAchievements.get(Achievement.BrokenHeart) != null) {
					// place value in a boolean container
					achievedOrNot = listOfAchievements.get(Achievement.BrokenHeart);
					if (!achievedOrNot) // if it's false
					{
						notifyAchievementEarned = true; // notify user of earned achievement
						listOfAchievements.remove(Achievement.BrokenHeart);
						listOfAchievements.put(Achievement.BrokenHeart, true);
						//user.writeAchievement(counterOvershootingTheMoon, listOfAchievements);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean ShootingTheMoon(int score, boolean notifyAchievementEarned, int counterOvershootingTheMoon) {
		// "end game" usage
		// Score: (Full_score)
		if (score == 26) //26 full score
		{
			// if the value contains BrokenHeart key...
			if (listOfAchievements.containsKey(Achievement.ShootingTheMoon)) {
				// get the value (true or false). null if key not found
				if (listOfAchievements.get(Achievement.ShootingTheMoon) != null) {
					// place value in a boolean container
					achievedOrNot = listOfAchievements.get(Achievement.ShootingTheMoon);
					if (!achievedOrNot) // if it's false
					{
						notifyAchievementEarned = true; // notify user of earned achievement
						listOfAchievements.remove(Achievement.ShootingTheMoon);
						listOfAchievements.put(Achievement.ShootingTheMoon,true);
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	private boolean OvershootingTheMoon(int score, boolean notifyAchievementEarned, int counterOvershootingTheMoon) {
		// "end game" usage
		// Score: (Full_score)
		// Score must be obtained three times (meaning three different rounds)
		int counter = getCounterOvershootingTheMoon();
		
		if (score == 26) 
		{
			if (counter == 0) {

				if (listOfAchievements.containsKey(Achievement.OvershootingTheMoon1)) {
					// get the value (true or false). null if key not found
					if (listOfAchievements.get(Achievement.BrokenHeart) != null) {
						// place value in a boolean container
						achievedOrNot = listOfAchievements.get(Achievement.OvershootingTheMoon1);
						if (!achievedOrNot) // if it's false
						{
							notifyAchievementEarned = true; // notify user of earned achievement
							listOfAchievements.remove(Achievement.OvershootingTheMoon1);
							listOfAchievements.put(Achievement.OvershootingTheMoon1,true);
							setCounterOvershootingTheMoon(1);
							return true;
						}
					}
				}
			}
			
			if (counter == 1) {
				
				if (listOfAchievements.containsKey(Achievement.OvershootingTheMoon2)) {
					// get the value (true or false). null if key not found
					if (listOfAchievements.get(Achievement.BrokenHeart) != null) {
						// place value in a boolean container
						achievedOrNot = listOfAchievements.get(Achievement.OvershootingTheMoon2);
						if (!achievedOrNot) // if it's false
						{
							notifyAchievementEarned = true; // notify user of earned achievement
							listOfAchievements.remove(Achievement.OvershootingTheMoon2);
							listOfAchievements.put(Achievement.OvershootingTheMoon2,true);
							setCounterOvershootingTheMoon(2);
							return true;
						}
					}
				}
			}
			
			if (counter == 2) {
				
				if (listOfAchievements.containsKey(Achievement.OvershootingTheMoon3)) {
					// get the value (true or false). null if key not found
					if (listOfAchievements.get(Achievement.BrokenHeart) != null) {
						// place value in a boolean container
						achievedOrNot = listOfAchievements.get(Achievement.OvershootingTheMoon3);
						if (!achievedOrNot) // if it's false
						{
							notifyAchievementEarned = true; // notify user of earned achievement
							listOfAchievements.remove(Achievement.OvershootingTheMoon3);
							listOfAchievements.put(Achievement.OvershootingTheMoon3,true);
							setCounterOvershootingTheMoon(3);
							return true;
						}
					}
				}
			}
		
		}
		return false;
}
	
	public boolean PassingTheBuck(int score, boolean notifyAchievementEarned, int counterOvershootingTheMoon) {
		// tracked in "real time" - only when passing cards 
		// Passed: QoS
		return notifyAchievementEarned;
	}
	
	public boolean StartTheParty(int score, boolean notifyAchievementEarned, int counterOvershootingTheMoon) {
		// tracked in "real time" - first card played every round
		// Played: Two of Clubs
		return notifyAchievementEarned;
	}
	
	public boolean HatTrick(int score, boolean notifyAchievementEarned, int counterOvershootingTheMoon) {
		// tracked in "real time" - after 4 cards have been played
		// Collect all 4 cards on table (suit doesn't matter)
		return notifyAchievementEarned;
	}
	
	public Map<Achievement, Boolean> returnList(Map<Achievement, Boolean> listOfAchievements)
	{
		String name = this.player.getName();
		user.CreateUserData(name);
		return listOfAchievements;
	}

	
	
//	while (game is playing)
//	{
//		
//	}
}
