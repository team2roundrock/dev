/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan Shelton
 *
 */

public class Achievements 
{

	boolean notifyAchievementEarned = false; //TODO re-think notification system
	List<String> achievementNames = Arrays.asList("BrokenHeart","ShootingTheMoon","PassingTheBuck",
			"StartTheParty","HatTrick","OvershootingTheMoon1","OvershootingTheMoon2", 
			"OvershootingTheMoon3");
			// There are three Overshooting The Moon entries. The first two indicate 
			// progress toward the actual achievement, which is OvershootingTheMoon3
	private int counterOvershootingTheMoon;
	boolean achievedOrNot;
	private UserData user = new UserData();
	private ReadFiles files = new ReadFiles();
	private Player player;
	private String userFileName;
	Map<String, Boolean> listOfAchievements;
	
	public Achievements(String userName, List<String> passedAchievements)
	{
		super();
		userFileName = userName;
		for(String achievement: passedAchievements)
		{
			listOfAchievements.put(achievement, true);
		}
		if(passedAchievements.contains("OvershootingTheMoon1"))
			counterOvershootingTheMoon = 1;
		if(passedAchievements.contains("OvershootingTheMoon2"))
			counterOvershootingTheMoon = 2;
		if(passedAchievements.contains("OvershootingTheMoon3"))
			counterOvershootingTheMoon = 3;
	}
	
	
	public Achievements(String userName)
	{	
		userFileName = userName;
		counterOvershootingTheMoon = 0; //each increment is progress toward achievement
		listOfAchievements = new HashMap<String, Boolean>();		
		for(String achievement : achievementNames) //steps through the enum
		{
			listOfAchievements.put(achievement, false);
		}
		user.addUserNameToFile();
		user.CreateUserData(userName);
		
		
	}
	
	/**
	 * @return the userFileName
	 */
	public String getUserFileName() {
		return userFileName;
	}

	/**
	 * @param userFileName the userFileName to set
	 */
	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
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
	
	/**
	 * Check for and give the user any earned achievements at the end of each round.
	 * The achievements to be checked are those which rely upon user score.
	 * @param score
	 * @param notifyAchievementEarned
	 */
	public void endGameAchievements(int score, boolean notifyAchievementEarned){
		BrokenHeart(score, notifyAchievementEarned);
		ShootingTheMoon(score, notifyAchievementEarned);
		OvershootingTheMoon(score, notifyAchievementEarned);
		notifyAchievementEarned = true;
	}
	
	//writeAchievement(counter,table)
	
	private boolean BrokenHeart(int score, boolean notifyAchievementEarned) {
		// "end game" usage
		// Score: (Full_score)-(QoS)
		String nameOfAchievement = "BrokenHeart";
		if (score == 13) //26 full - 13 queen
		{
			return giveAchievement(nameOfAchievement);
		}
		return false;
	}
	
	private boolean ShootingTheMoon(int score, boolean notifyAchievementEarned) {
		// "end game" usage
		// Score: (Full_score)
		String nameOfAchievement = "ShootingTheMoon";
		if (score == 26) //26 full score
		{
			return giveAchievement(nameOfAchievement);
		}
		return false;
	}
	
	private boolean OvershootingTheMoon(int score, boolean notifyAchievementEarned) {
		// "end game" usage
		// Score: (Full_score)
		// Score must be obtained three times (meaning three different rounds)
		String nameOfAchievement1 = "OvershootingTheMoon1";
		String nameOfAchievement2 = "OvershootingTheMoon2";
		String nameOfAchievement3 = "OvershootingTheMoon3";
		int counter = getCounterOvershootingTheMoon();
		
		if (score == 26) 
		{
			if (counter == 0) {

				if (giveAchievement(nameOfAchievement1))
				{
					setCounterOvershootingTheMoon(1);
					//no need to notify achievement earned
					return true;
				}
			}
			
			if (counter == 1) {
				
				if (giveAchievement(nameOfAchievement2))
				{
					setCounterOvershootingTheMoon(2);
					//no need to notify achievement earned
					return true;
				}
			}
			
			if (counter == 2) {
				
				if (giveAchievement(nameOfAchievement3))
				{
					setCounterOvershootingTheMoon(3);
					//notify achievement earned
					return true;
				}
			}
		
		}
		return false;
}
	/**
	 * Takes in the user's list of cards to pass from the passing mechanism and checks for
	 * the requirement to earn this particular achievement (Queen of Spades)
	 * @param score
	 * @param notifyAchievementEarned
	 * @param cardsToPass
	 * @return
	 */
	public boolean PassingTheBuck(int score, boolean notifyAchievementEarned, List<Card> cardsToPass) {
		// tracked in "real time" - only when passing cards 
		// Passed: QoS
		// Implementation: Take list of cards to pass as an argument. Iterate through to look for QoS
		//				   Place call to function at the end of passingCards() in Hearts.java
		String nameOfAchievement = "PassingTheBuck";
		for (Card card : cardsToPass)
		{
			if ((card.getSuit() == Card.Suit.Spades)
					&& (card.getFace() == Card.Face.Queen))
			{
				if (giveAchievement(nameOfAchievement))
				{
					//notify achievement earned
					return true;
				}
			}
		}	
		return false;
	}
	
	public boolean StartTheParty(int score, boolean notifyAchievementEarned) {
		// tracked in "real time" - first card played every round
		// Played: Two of Clubs
		// Implementation: If cardsPlayed for user is 2 of clubs, unlock achievement
		return notifyAchievementEarned;
	}
	
	public boolean HatTrick(int score, boolean notifyAchievementEarned) {
		// tracked in "real time" - after 4 cards have been played
		// Collect all 4 cards on table (suit doesn't matter)
		return notifyAchievementEarned;
	}
	
	/**
	 * @param nameOfAchievement
	 * @return
	 */
	private boolean giveAchievement(String nameOfAchievement)
	{
		//boolean notifyAchievementEarned; //not needed?
		
		// if the value contains the named achievement key...
		if (listOfAchievements.containsKey(nameOfAchievement)) {
			// get the value (true or false). null if key not found
			if (listOfAchievements.get(nameOfAchievement) != null) {
				// place value in a boolean container
				achievedOrNot = listOfAchievements.get(nameOfAchievement);
				if (!achievedOrNot) // if it's false (not achieved yet)
				{
					notifyAchievementEarned = true; // notify user of earned achievement. Not needed?
					listOfAchievements.remove(nameOfAchievement);
					listOfAchievements.put(nameOfAchievement, true);
					//user.writeAchievement(currentAchievement);
					return true;
				}
			}
		}
		return false;
	}
	
	public Map<String, Boolean> returnList(Map<String, Boolean> listOfAchievements)
	{
		
		user.CreateUserData(userFileName);
		
//		for(String achievement : achievementNames) //steps through the enum
//		{
//			listOfAchievements.put(achievement, false);
//		}
		
		return listOfAchievements;
	}
	
	
//	while (game is playing)
//	{
//		
//	}
}
