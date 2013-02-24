package edu.txstate.hearts.utils;

import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class is designed to house risk thresholds and make it easier 
 * to track and store them for use between sessions.
 * 
 * @author Jonathan Shelton
 *
 */
public class RiskThresholds {

	Random rand = new Random();
	float threshold = rand.nextFloat(); //should it be private? Force getRisk() to be used?
	/**
	 * 
	 *  This is a threshold which can be used for AI to determine 
	 *  what hand to play (based on probabilities the AI has calculated)
	 * 
	 *  Note: For now, this will be a randomly generated value.
	 *  Threshold will ideally/eventually be adjusted based on 
	 *  various gameplay factors in the future.
	 *  
	 *  @return The next pseudorandom, uniformly distributed float 
	 *  value between 0.0 and 1.0 from the random number generator.
	 */
	
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
	 * @return the threshold
	 */
	public float getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * After determining probability, AI can pass the probability to
	 * this method to compare against threshold. This will allow the AI
	 * to determine if a particular card is good enough to be played.
	 * 
	 * @param threshold Holds current risk threshold for agent
	 * @param totalProbability Passed in from agent
	 * @param cardValue Placeholder for whatever holds value of the card agent 
	 * is evaluating for safe play
	 * @author Jonathan Shelton
	 */
	public void evaluateRisk(float threshold, float totalProbability, int cardValue)
	{
		/**
		 * probability = key
		 * card = value
		 * 
		 * note: threshold & totalProbability may not stay as type float.
		 */
		float riskyProb = 0, safeProb = 0;
		if(totalProbability <= threshold)
		{
			safeProb = totalProbability; //holds probabilities deemed to be "safe"
		}
		else
		{
			riskyProb = totalProbability; //holds probabilities deemed to be "risky", may collect heart(s)
		}
		
		SortedMap<Float,Integer> safeMap = new TreeMap<Float,Integer>(); //Assuming card val as an int
		SortedMap<Float,Integer> riskyMap = new TreeMap<Float,Integer>(); //Assuming card val as an int

        safeMap.put(safeProb, cardValue); //safe probabilities in their own map
        riskyMap.put(riskyProb, cardValue); //risky probabilities in their own map
        
//        Possible Implementation:
//        	Display Highest Key: map.lastKey()
//        	Display Lowest Key: map.firstKey()
//        	Can also iterate through entire set by setting an Iterator object to map.keySet().iterator():
//        		key is the key
//        		map.get(key) is the value
        

	}
		
}
