package edu.txstate.hearts.utils;

import java.util.Random;

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
	
	private float calculateRisk()
	{		
		if (this.Player == player1){ //Placeholder until I figure out how to get player numbers
			threshold = rand.nextFloat();
		} else if (this.Player == player2) {
			threshold = rand.nextFloat();
		} else if (this.Player == player3) {
			threshold = rand.nextFloat();
		} else if (this.Player == player4) {
			threshold = rand.nextFloat();
		} else
			threshold = rand.nextFloat();
		
		return threshold;
	}
	
	public float getRisk(float threshold) //needed?
	{
		//threshold = calculateRisk();
		return threshold;
	}
		
}
