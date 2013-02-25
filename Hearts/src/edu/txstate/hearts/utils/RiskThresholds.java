package edu.txstate.hearts.utils;

//import java.util.List;
import java.util.Random;
import java.util.SortedMap;
//import java.util.TreeMap;

import edu.txstate.hearts.model.Card;

/**
 * Class is designed to house risk thresholds and make it easier 
 * to track and store them for use between sessions.
 * 
 * @author Jonathan Shelton
 */
public class RiskThresholds {

	Random rand = new Random();
	float threshold = rand.nextFloat();
	
//	/**
//	 *  Risk thresholds are generated in the this method. These
//	 *  thresholds indicate the level of "risk tolerance" that
//	 *  is acceptable.
//	 *  
//	 *  @return The next pseudorandom, uniformly distributed float 
//	 *  value between 0.0 and 1.0 from the random number generator.
//	 */
//	private float calculateRisk()
//	{		
//		if (this.Player == player1){ //Placeholder until I figure out how to get player numbers
//			threshold = rand.nextFloat();
//		} else if (this.Player == player2) {
//			threshold = rand.nextFloat();
//		} else if (this.Player == player3) {
//			threshold = rand.nextFloat();
//		} else if (this.Player == player4) {
//			threshold = rand.nextFloat();
//		} else
//			threshold = rand.nextFloat();
//		
//		return threshold;
//	}
	
	/**
	 * @return threshold
	 */
	public float getThresholds() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThresholds(float threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * After passing in risk, this method will compare risk against the established 
	 * threshold and determine if a play is low or high risk. It also places safe or 
	 * risky probabilities (and their associated cards) in separate maps for use if needed.
	 * 
	 * @param test Card that probability was created with
	 * @param threshold The currently set threshold
	 * @param risk A double which contains probability of a card to win
	 * @param safeMap A SortedMap for safe probabilities
	 * @param riskyMap A SortedMap for risky probabilities
	 * @param lowRisk A boolean that's true if risk is low
	 */
	//TODO Determine if this is a viable starter method to compare risk and if it appropriately passed all arguments back to the caller.
	//SortedMaps can be used in the caller for more advanced probability searching
	public void evaluateRisk(Card test, float threshold, double risk, SortedMap<Double,Card> safeMap, SortedMap<Double,Card> riskyMap, boolean lowRisk)
	{
		
		 // key = probability (risk)
		 // value = card (test)
		if (risk < getThresholds())
		{
			safeMap.put(risk, test); //safe probabilities in their own map
			lowRisk = true;
		}
		else //there is a risk of getting hearts
		{
			riskyMap.put(risk, test); //risky probabilities in their own map
			lowRisk = false;
		}
		
		/* Creating the map in the caller
		SortedMap<Double,Card> safeMap = new TreeMap<Double,Card>();
		SortedMap<Double,Card> riskyMap = new TreeMap<Double,Card>();
		*/
        
        /* Possible Implementation:
        	Display Highest Key: map.lastKey()
        	Display Lowest Key: map.firstKey()
        	Can also iterate through entire set by setting an Iterator object to map.keySet().iterator():
        		key is the key
        		map.get(key) is the value */
	}
		
}
