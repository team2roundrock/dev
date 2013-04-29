package edu.txstate.hearts.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.txstate.hearts.utils.UserData;

/**
 * @author Jonathan Shelton
 *
 */

public class Achievements
{
	static List<String> achievementNames = Arrays.asList("BrokenHeart","ShootingTheMoon","PassingTheBuck",
			"StartTheParty","HatTrick","OvershootingTheMoon1","OvershootingTheMoon2", 
			"OvershootingTheMoon");
			// There are three Overshooting The Moon entries. The first two indicate 
			// progress toward the actual achievement, which is OvershootingTheMoon
	private int counterOvershootingTheMoon;
	boolean achievedOrNot;
	private final UserData user;
	private static String userFileName;
	private static ArrayList<String> arrayOfAchievements;
	Map<String, Boolean> listOfAchievements;
	
	public Achievements(String userName, List<String> passedAchievements)
	{
		user = new UserData(userName);
		userFileName = userName;
		initializeUser();
		for(String achievement: passedAchievements)
		{
			listOfAchievements.put(achievement, true);
		}
		if(passedAchievements.contains("OvershootingTheMoon1"))
			counterOvershootingTheMoon = 1;
		if(passedAchievements.contains("OvershootingTheMoon2"))
			counterOvershootingTheMoon = 2;
		if(passedAchievements.contains("OvershootingTheMoon"))
			counterOvershootingTheMoon = 3;
	}
	
	
	public Achievements(String userName)
	{	
		user = new UserData(userName);
		userFileName = userName;
		initializeUser();
		try {
			user.createUserDataFile(new ArrayList<String>());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void initializeUser()
	{
		counterOvershootingTheMoon = 0; //each increment is progress toward achievement
		listOfAchievements = new HashMap<String, Boolean>();		
		for(String achievement : achievementNames) //steps through list of names
		{
			listOfAchievements.put(achievement, false);
		}
		try {
			user.addUserNameToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if a particular achievement exists or not
	 * @param nameOfAchievement name of achievement to
	 * be checked
	 * @return true or false
	 */
	public boolean isAchievementEarned(String nameOfAchievement)
	{
		// if the value contains the named achievement key...
				if (listOfAchievements.containsKey(nameOfAchievement)) {
					// get the value (true or false). null if key not found
					if (listOfAchievements.get(nameOfAchievement) != null) {
						// place value in a boolean container
						achievedOrNot = listOfAchievements.get(nameOfAchievement);
						return achievedOrNot;
//						if (!achievedOrNot) // if it's false (not achieved yet)
//						{
//							return true;
//						}
					}
					//if key does not exist, achievement not earned
				}
				return false;
	}

	/**
	 * Sends message to the console that achievement has been earned.
	 * This method is unable to send a message to the tooltip in the
	 * GUI.
	 * 
	 * @param achievementName Name of the achievement earned
	 */
	public void notifyAchievementEarned(String achievementName)
	{
		System.out.println("Achievement Unlocked - " + achievementName);
		
	}

	/**
	 * @return the userFileName
	 */
	public static String getUserFileName() {
		return userFileName;
	}

	/**
	 * @param userFileName the userFileName to set
	 */
	public void setUserFileName(String userFileName) {
		Achievements.userFileName = userFileName;
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
	 * @return int 0, 1, 2, or 3. Each number corresponds to a certain achievement
	 * deemed to be true. 1 = Broken Heart, 2 = Shooting The Moon, 3 = Overshooting The 
	 * Moon. It is impossible to earn more than one of these achievements at a time.
	 */
	public int endGameAchievements(int score){
		Boolean isTrue1, isTrue2, isTrue3 = false;
		
		isTrue1 = BrokenHeart(score);
		isTrue2 = ShootingTheMoon(score);
		isTrue3 = OvershootingTheMoon(score);
		
		if (isTrue1 == true)
		{
			return 1;
		}
		else if (isTrue2 == true)
		{
			return 2;
		}
		else if (isTrue3 == true)
		{
			return 3;
		}
		return 0;
	}
	
	private boolean BrokenHeart(int score) {
		// "end game" usage
		// Score: (Full_score)-(QoS)
		String nameOfAchievement = "BrokenHeart";
		if (score == 13) //26 full - 13 queen
		{
			if (giveAchievement(nameOfAchievement))
			{
				//notify achievement earned
				notifyAchievementEarned("Broken Heart");
				return true;
			}
		}
		return false;
	}
	
	private boolean ShootingTheMoon(int score) {
		// "end game" usage
		// Score: (Full_score)
		String nameOfAchievement = "ShootingTheMoon";
		if (score == 26) //26 full score
		{
			if (giveAchievement(nameOfAchievement))
			{
				//notify achievement earned
				notifyAchievementEarned("Shooting The Moon");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * There are three "achievements" to be earned here, according to the
	 * achievementNames list, however, OvershootingTheMoon1 and
	 * OvershootingTheMoon2 are "progression" achievements and do not
	 * actually count as a full achievement. Therefore, this method will
	 * only return true when the requirements for OvershootingTheMoon 
	 * are earned (which includes having  both progression "achievements")
	 * @param score
	 * @return true or false, which indicates achievement has been earned.
	 */
	private boolean OvershootingTheMoon(int score) {
		// "end game" usage
		// Score: (Full_score)
		// Score must be obtained three times (meaning three different rounds)
		String nameOfAchievement1 = "OvershootingTheMoon1";
		String nameOfAchievement2 = "OvershootingTheMoon2";
		String nameOfAchievement3 = "OvershootingTheMoon";
		int counter = getCounterOvershootingTheMoon();
		
		if (score == 26) 
		{
			if (counter == 0) {

				if (giveAchievement(nameOfAchievement1))
				{
					setCounterOvershootingTheMoon(1);
					//progress toward achievement only
					//no need to notify achievement earned
					return false;
				}
			}
			
			if (counter == 1) {
				
				if (giveAchievement(nameOfAchievement2))
				{
					setCounterOvershootingTheMoon(2);
					//progress toward achievement only
					//no need to notify achievement earned
					return false;
				}
			}
			
			if (counter == 2) {
				
				if (giveAchievement(nameOfAchievement3))
				{
					setCounterOvershootingTheMoon(3);
					notifyAchievementEarned("Overshooting The Moon");
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
	public boolean PassingTheBuck(boolean notifyAchievementEarned, List<Card> cardsToPass) {
		// tracked in "real time" - only when passing cards 
		// Passed: QoS
		// Implementation: Take list of cards to pass as an argument. Iterate through 
		//    to look for Queen of Spades. Call to this method is made from within 
		//    passingCards() in Hearts.java
		String nameOfAchievement = "PassingTheBuck";
		for (Card card : cardsToPass)
		{
			if ((card.getSuit() == Card.Suit.Spades)
					&& (card.getFace() == Card.Face.Queen))
			{
				if (giveAchievement(nameOfAchievement))
				{
					notifyAchievementEarned("Passing The Buck");
					return true;
				}
			}
		}	
		return false;
	}
	
	public boolean StartTheParty(List<Card> cardsPlayed) {
		// tracked in "real time" - first card played every round
		// Played: Two of Clubs
		// Implementation: Take list of cardsPlayed (passed from game in progress).
		//	  If two of clubs found, unlock achievement. Will be activated the moment 
		//    the card is played
		String nameOfAchievement = "StartTheParty";
		//Hearts.accessUI.ShowBalloonTip("You are IN the achievement");
		for (Card card : cardsPlayed)
		{
			if ((card.getSuit() == Card.Suit.Clubs)
					&& (card.getFace() == Card.Face.Deuce))
			{
				if (giveAchievement(nameOfAchievement))
				{
					notifyAchievementEarned("Start The Party");
					return true;
				}
			}
		}
		//Hearts.accessUI.ShowBalloonTip("You are NOT getting the achievement");
		return false;
	}
	
	/**
	 * Method automatically hands the achievement to a user 
	 * (if not already earned), therefore it MUST be called 
	 * on the "playerWithHighestValue" after cards have been 
	 * passed (near the bottom of runTurn() in Hearts.java)
	 * @param achievementEarned
	 * @return
	 */
	public boolean HatTrick() {
		// tracked in "real time" - after 4 cards have been played
		// Collect all 4 cards on table (suit doesn't matter)
		String nameOfAchievement = "HatTrick";
		if (giveAchievement(nameOfAchievement))
		{
			notifyAchievementEarned("Hat Trick");
			return true;
		}
		return false;
	}
	
	/**
	 * @param nameOfAchievement
	 * @return
	 */
	private boolean giveAchievement(String nameOfAchievement)
	{		
		// if the value contains the named achievement key...
		if (listOfAchievements.containsKey(nameOfAchievement)) {
			// get the value (true or false). null if key not found
			if (listOfAchievements.get(nameOfAchievement) != null) {
				// place value in a boolean container
				achievedOrNot = listOfAchievements.get(nameOfAchievement);
				if (!achievedOrNot) // if it's false (not achieved yet)
				{
					listOfAchievements.remove(nameOfAchievement);
					listOfAchievements.put(nameOfAchievement, true);
					user.writeAchievements(listOfAchievements);
					return true;
				}
			}
			//what if key does not exist? (i.e. reading file of earned achievements)
			//set achievement to true
			else
			{
				listOfAchievements.put(nameOfAchievement, true);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns all of a user's earned achievements
	 * @param listOfAchievements
	 * @return Earned achievements
	 */
	public static List<String> getListOfAchievements()
	{
		if(arrayOfAchievements == null)
			arrayOfAchievements = new ArrayList<String>(); 
		for(String achievement : achievementNames) //steps through list of names
		{
			arrayOfAchievements.add(achievement);
		}
		return arrayOfAchievements;
	}
}
