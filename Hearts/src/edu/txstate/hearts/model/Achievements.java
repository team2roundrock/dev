/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
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
	//private final Achievement achievement;
	Hashtable<Achievement, Boolean> listOfAchievements;
	
	public Achievements()
	{
		counterOvershootingTheMoon = 0; //each increment is progress toward achievement
		//TODO counter may need to be read in from file each time
		//this.achievement = achievement;
		listOfAchievements = new Hashtable<Achievement, Boolean>();
		
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
		BrokenHeart(score, notifyAchievementEarned);
		ShootingTheMoon(score);
		OvershootingTheMoon(score);
	}
	
	private boolean BrokenHeart(int score, boolean notifyAchievementEarned) {
		// "end game" usage
		// Score: (Full_score)-(QoS)
		if (score == 13) //26 full - 13 queen
		{
			//for(Achievements.Achievement achievement : Achievements.Achievement.values())
			//{
				//if the value contains BrokenHeart key...
				if (listOfAchievements.containsKey(Achievement.BrokenHeart)) 
				{
					//get the value (true or false). null if key not found
					if (listOfAchievements.get(Achievement.BrokenHeart) != null)
					{
						//place value in a boolean container
						achievedOrNot = listOfAchievements.get(Achievement.BrokenHeart);
						if (!achievedOrNot) //if it's false
						{
							notifyAchievementEarned = true; //notify user of earned achievement
							listOfAchievements.remove(Achievement.BrokenHeart);
							listOfAchievements.put(Achievement.BrokenHeart, true);
							return true;
						}
					}
				}
			//}
		}
		return false;
	}
	
	private boolean ShootingTheMoon(int score) {
		// "end game" usage
		// Score: (Full_score)
		if (score == 26) //26 full score
		{
			//for(Achievements.Achievement achievement : Achievements.Achievement.values())
			//{
				//if the value contains BrokenHeart key...
				if (listOfAchievements.containsKey(Achievement.ShootingTheMoon)) 
				{
					//get the value (true or false). null if key not found
					if (listOfAchievements.get(Achievement.ShootingTheMoon) != null)
					{
						//place value in a boolean container
						achievedOrNot = listOfAchievements.get(Achievement.ShootingTheMoon);
						if (!achievedOrNot) //if it's false
						{
							notifyAchievementEarned = true; //notify user of earned achievement
							listOfAchievements.put(Achievement.ShootingTheMoon, true);
							return true;
						}
					}
				}
			//}
			
		}
		return false;
	}
	
	private boolean OvershootingTheMoon(int score) {
		// "end game" usage
		// Score: (Full_score)
		// Score must be obtained three times (meaning three different rounds)
		return notifyAchievementEarned;
	}
	
	public boolean PassingTheBuck() {
		// tracked in "real time" - only when passing cards 
		// Passed: QoS
		return notifyAchievementEarned;
	}
	
	public boolean StartTheParty() {
		// tracked in "real time" - first card played every round
		// Played: Two of Clubs
		return notifyAchievementEarned;
	}
	
	public boolean HatTrick() {
		// tracked in "real time" - after 4 cards have been played
		// Collect all 4 cards on table (suit doesn't matter)
		return notifyAchievementEarned;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Achievements [counterOvershootingTheMoon="
				+ counterOvershootingTheMoon + ", listOfAchievements="
				+ listOfAchievements + "]";
	}

	
	
//	while (game is playing)
//	{
//		
//	}
}
