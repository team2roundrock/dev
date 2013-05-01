/**
 * 
 */
package edu.txstate.hearts.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Agent test
 * @author Jonathan
 *
 */
public class AgentTest {
	
	private Agent agent = new AgentGoofy("Test", 0);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		agent = new AgentGoofy("Test", 0);
	}

	@Test
	public void playTwoOfClub() {
		
		agent.addCard(new Card(Card.Face.Deuce, Card.Suit.Clubs));
		Card agentReturn = agent.playTwoOfClub();
		if((agentReturn.getSuit() != Card.Suit.Clubs) && 
				agentReturn.getFace() != Card.Face.Deuce)
		{
			fail("Card not the deuce of clubs");
		}
	}
}
