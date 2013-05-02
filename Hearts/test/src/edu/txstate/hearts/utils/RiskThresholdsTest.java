package edu.txstate.hearts.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.txstate.hearts.utils.RiskThresholds.Threshold;

public class RiskThresholdsTest {

	
	RiskThresholds riskThresholds;
	@Before
	public void setUp() throws Exception {
		riskThresholds = new RiskThresholds();
	}

	@Test
	public void testGetThreshold() {
		Threshold t = riskThresholds.getThreshold(0, false);
		// the default for the GoFirstWithQosThreshold is 3.2654
		assertEquals(t.getThreshold(), 3.2654, 0.001);
	}

	@Test
	public void testIncreaseThreshold() {
		Threshold t = riskThresholds.getThreshold(0, false);
		// the default for the GoFirstWithQosThreshold is 3.2654
		double curThreshold = t.getThreshold();
		double curRiskThreshold = t.getRiskThreshold();
		double modifiedRT = curRiskThreshold*10d;
		// increasing the threshold should do curThreshold * (1/(1+e^-(modifiedRT-5)) / 100d+1)
		double exponent = -1d*(modifiedRT-5d);
		double ePart = Math.pow(Math.E, exponent);
		double logisticPart = 1d/(1d+ePart);
		double factor = (logisticPart/100d)+1d;
		double expected = curThreshold * factor;
		riskThresholds.increaseThreshold(t);
		double newThreshold = t.getThreshold();
		assertEquals(expected, newThreshold, 0.001);
	}

	@Test
	public void testDecreaseThreshold() {
		Threshold t = riskThresholds.getThreshold(0, false);
		// first do a decrease with 0 points, which should actually do an increase
		// by half the amount that a normal increase would increase
		double curThreshold = t.getThreshold();
		double curRiskThreshold = t.getRiskThreshold();
		double modifiedRT = curRiskThreshold*10d;
		// increasing the threshold should do curThreshold * ((1/(1+e^-(modifiedRT-5)) / 100)/2+1)
		double exponent = -1d*(modifiedRT-5d);
		double ePart = Math.pow(Math.E, exponent);
		double logisticPart = 1d/(1d+ePart);
		double factor = ((logisticPart/200d)+1d);
		double expected = curThreshold * factor;
		riskThresholds.decreaseThreshold(t, 0);
		double newThreshold = t.getThreshold();
		assertEquals(expected, newThreshold, 0.001);
		
		// now do a decrease by 5 points
		// this should do Math.pow((1-(1/(1+e^(5-x))/100)),pointsWon)*currentThreshold
		curThreshold = t.getThreshold();
		curRiskThreshold = t.getRiskThreshold();
		modifiedRT = curRiskThreshold*10d;
		exponent = 5d-modifiedRT;
		ePart = Math.pow(Math.E, exponent);
		logisticPart = 1d/(1d+ePart);
		factor = 1d-(logisticPart/100d);
		double factor2 = Math.pow(factor, 5);
		expected = curThreshold*factor2;
		riskThresholds.decreaseThreshold(t, 5);
		newThreshold = t.getThreshold();
		assertEquals(expected, newThreshold, 0.001);
		
		
	}

}
