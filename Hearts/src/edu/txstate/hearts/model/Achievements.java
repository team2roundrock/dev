/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
		PassingTheBuck, OvershootingTheMoon, StartTheParty, HatTrick };
		//TODO Should there be three OvershootingTheMoon entries, two hidden and one final?
		//     This method may not be needed if counter can be implemented correctly
	private int counterOvershootingTheMoon;
	//private final Achievement achievement;
	Hashtable<Achievement, Boolean> listOfAchievements;
	
	public Achievements()
	{
		counterOvershootingTheMoon = 0; //once counter hits 3, achievement can be set
		//this.achievement = achievement;
		listOfAchievements = new Hashtable<Achievement, Boolean>();
		
		// Create an array, populate each index with achievement name and
		// a default boolean of false, meaning it hasn't been earned. Will save 
		// array values to file only when they have changed. Array does NOT
		// necessarily reflect earned achievements; only the user's file
		// will hold saved achievement data throughout each game
		for(Achievements.Achievement achievement : Achievements.Achievement.values())
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
	
	private boolean BrokenHeart() {
		// "end game" usage
		// Score: (Full_score)-(QoS)
		return notifyAchievementEarned;
	}
	
	private boolean ShootingTheMoon() {
		// "end game" usage
		// Score: (Full_score)
		return notifyAchievementEarned;
	}
	
	private boolean OvershootingTheMoon() {
		// "end game" usage
		// Score: (Full_score)
		// Score must be obtained three times (meaning three different rounds)
		return notifyAchievementEarned;
	}
	
	private boolean PassingTheBuck() {
		// tracked in "real time" - only when passing cards 
		// Passed: QoS
		return notifyAchievementEarned;
	}
	
	private boolean StartTheParty() {
		// tracked in "real time" - first card played every round
		// Played: Two of Clubs
		return notifyAchievementEarned;
	}
	
	private boolean HatTrick() {
		// tracked in "real time" - after 4 cards have been played
		// Collect all 4 cards on table (suit doesn't matter)
		return notifyAchievementEarned;
	}

	
	
//	while (game is playing)
//	{
//		
//	}
}
