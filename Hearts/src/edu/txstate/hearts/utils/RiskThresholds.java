package edu.txstate.hearts.utils;

//import java.util.List;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
//import java.util.TreeMap;

import edu.txstate.hearts.model.Card;

/**
 * Class is designed to house risk thresholds and make it easier to track and
 * store them for use between sessions.
 * 
 * @author Jonathan Shelton
 */
public class RiskThresholds implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -54667351852545143L;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return thresholdMap.toString();
	}

	public class Threshold implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4664712297768069997L;
		double threshold = 0.5;

		public Threshold(double t) {
			threshold = t;
		}

		/**
		 * @return the threshold
		 */
		public double getThreshold() {
			return threshold;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return ""+threshold;
		}

		/**
		 * @param threshold
		 *            the threshold to set
		 */
		public void setThreshold(double threshold) {
			this.threshold = threshold;
		}
		
		public double getRiskThreshold()
		{
			return logisticFunction(threshold);
		}

	}

	//private Random rand = new Random();
	// float threshold = rand.nextFloat();
	private Map<Integer, Threshold> thresholdMap = new HashMap();

	public RiskThresholds() {
		Threshold goFirstQos = new Threshold(3.2654);
		Threshold goSecondQos = new Threshold(3.9104);
		Threshold goThirdQos = new Threshold(4.3810);
		Threshold goFirstNoQos = new Threshold(4.5945);
		Threshold goSecondNoQos = new Threshold(5);
		Threshold goThirdNoQos = new Threshold(5.405);
		thresholdMap.put(0, goFirstQos);
		thresholdMap.put(1, goSecondQos);
		thresholdMap.put(2, goThirdQos);
		thresholdMap.put(3, goFirstNoQos);
		thresholdMap.put(4, goSecondNoQos);
		thresholdMap.put(5, goThirdNoQos);
	}

	// /**
	// * Risk thresholds are generated in the this method. These
	// * thresholds indicate the level of "risk tolerance" that
	// * is acceptable.
	// *
	// * @return The next pseudorandom, uniformly distributed float
	// * value between 0.0 and 1.0 from the random number generator.
	// */
	// private float calculateRisk()
	// {
	// if (this.Player == player1){ //Placeholder until I figure out how to get
	// player numbers
	// threshold = rand.nextFloat();
	// } else if (this.Player == player2) {
	// threshold = rand.nextFloat();
	// } else if (this.Player == player3) {
	// threshold = rand.nextFloat();
	// } else if (this.Player == player4) {
	// threshold = rand.nextFloat();
	// } else
	// threshold = rand.nextFloat();
	//
	// return threshold;
	// }

	/**
	 * @return threshold
	 */
	public Threshold getThreshold(int numPlayed, boolean qos) {
		int num = numPlayed;
		if (!qos)
			num += 3;
		Threshold t = thresholdMap.get(num);
		// return thresholdMap.get(num).getThreshold();
		return t;
	}

	public void increaseThreshold(Threshold t) {
		if (t == null)
			return;
		increaseThreshold(t, 1.001);
		// System.out.println("logisticFunction("+(newValue*10d)+") of newValue is "+logisticFunction(newValue*10d));
	}

	private void increaseThreshold(Threshold t, double adjustAmount) {
		double oldValue = t.getThreshold();
		double newValue = adjustAmount * oldValue;
		//System.out.println("doing increase oldValue is " + oldValue
		//		+ " newValue is " + newValue);
		t.setThreshold(newValue);
	}

	public void decreaseThreshold(Threshold t, int points) {
		if (t == null)
			return;
		if (points == 0)
			increaseThreshold(t, 1.0005);
		else {
			double oldValue = t.getThreshold();
			double newValue = Math.pow(0.99, points)*oldValue;
			//System.out.println("doing decrease oldValue is " + oldValue
			//		+ " newValue is " + newValue);
			t.setThreshold(newValue);
		}
		// System.out.println("logisticFunction("+(newValue*10d)+") of newValue is "+logisticFunction(newValue*10d));
	}

	private double logisticFunction(double x) {
		// 1/(1+e^-(x-5))
		double e = Math.E;
		double denominator = 1d + Math.pow(e, (5 - x));
		return 1d / denominator;
	}

	// /**
	// * @param threshold the threshold to set
	// */
	// public void setThresholds(float threshold) {
	// this.threshold = threshold;
	// }

	/**
	 * After passing in risk, this method will compare risk against the
	 * established threshold and determine if a play is low or high risk. It
	 * also places safe or risky probabilities (and their associated cards) in
	 * separate maps for use if needed.
	 * 
	 * @param test
	 *            Card that probability was created with
	 * @param threshold
	 *            The currently set threshold
	 * @param risk
	 *            A double which contains probability of a card to win
	 * @param safeMap
	 *            A SortedMap for safe probabilities
	 * @param riskyMap
	 *            A SortedMap for risky probabilities
	 * @param lowRisk
	 *            A boolean that's true if risk is low
	 */
	// TODO Determine if this is a viable starter method to compare risk and if
	// it appropriately passed all arguments back to the caller.
	// SortedMaps can be used in the caller for more advanced probability
	// searching
	// public void evaluateRisk(Card test, float threshold, double risk,
	// SortedMap<Double,Card> safeMap, SortedMap<Double,Card> riskyMap, boolean
	// lowRisk)
	// {
	//
	// // key = probability (risk)
	// // value = card (test)
	// if (risk < getThresholds())
	// {
	// safeMap.put(risk, test); //safe probabilities in their own map
	// lowRisk = true;
	// }
	// else //there is a risk of getting hearts
	// {
	// riskyMap.put(risk, test); //risky probabilities in their own map
	// lowRisk = false;
	// }
	//
	// /* Creating the map in the caller
	// SortedMap<Double,Card> safeMap = new TreeMap<Double,Card>();
	// SortedMap<Double,Card> riskyMap = new TreeMap<Double,Card>();
	// */
	//
	// /* Possible Implementation:
	// Display Highest Key: map.lastKey()
	// Display Lowest Key: map.firstKey()
	// Can also iterate through entire set by setting an Iterator object to
	// map.keySet().iterator():
	// key is the key
	// map.get(key) is the value */
	// }

}
