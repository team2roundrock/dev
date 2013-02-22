/**
 * 
 */
package edu.txstate.hearts.utils;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import edu.txstate.hearts.model.AgentDetermined;
import edu.txstate.hearts.model.Card;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;
import edu.txstate.hearts.model.Player;

/**
 * @author Neil Stickels
 *
 */
public class ProbabilityUtilsTest {

	/**
	 * Test method for {@link edu.txstate.hearts.utils.ProbabilityUtils#getProbabilityEveryoneHasOneOfASuit(edu.txstate.hearts.model.Card.Suit, java.util.List, java.util.List)}.
	 */
	@Test
	public void testGetProbabilityEveryoneHasOneOfASuit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.txstate.hearts.utils.ProbabilityUtils#getTotalCardCombinationsWithForSuitAtLeastOne(edu.txstate.hearts.model.Card.Suit, java.util.List, java.util.List)}.
	 */
	@Test
	public void testGetTotalCardCombinationsWithForSuitAtLeastOne() {
		Player p = new AgentDetermined("Tester");
		long num;
		p.addCard(new Card(Face.Deuce, Suit.Clubs));
		p.addCard(new Card(Face.Three, Suit.Clubs));
		p.addCard(new Card(Face.Four, Suit.Clubs));
		p.addCard(new Card(Face.Five, Suit.Clubs));
		p.addCard(new Card(Face.Six, Suit.Clubs));
		p.addCard(new Card(Face.Seven, Suit.Clubs));
		p.addCard(new Card(Face.Eight, Suit.Clubs));
		p.addCard(new Card(Face.Nine, Suit.Clubs));
		p.addCard(new Card(Face.Ten, Suit.Clubs));
		p.addCard(new Card(Face.Jack, Suit.Clubs));
		p.addCard(new Card(Face.Queen, Suit.Clubs));
		p.addCard(new Card(Face.King, Suit.Clubs));
		p.addCard(new Card(Face.Ace, Suit.Clubs));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 0, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 0, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 0, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 6, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 36, num);		
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 150, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 540, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 1806, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 5796, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 18150, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 55980, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 171006, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 519156, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 1569750, num);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.utils.ProbabilityUtils#getTotalCardCombinationsForSuit(edu.txstate.hearts.model.Card.Suit, java.util.List, java.util.List)}.
	 */
	@Test
	public void testGetTotalCardCombinationsForSuit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.txstate.hearts.utils.ProbabilityUtils#getProbabilityNoneOfSuitAndHasHearts(java.util.List, edu.txstate.hearts.model.Card.Suit, java.util.List)}.
	 */
	@Test
	public void testGetProbabilityNoneOfSuitAndHasHearts() {
		Player p = new AgentDetermined("Tester");
		double num;
		p.addCard(new Card(Face.Deuce, Suit.Clubs));
		p.addCard(new Card(Face.Three, Suit.Clubs));
		p.addCard(new Card(Face.Four, Suit.Clubs));
		p.addCard(new Card(Face.Five, Suit.Clubs));
		p.addCard(new Card(Face.Six, Suit.Clubs));
		p.addCard(new Card(Face.Seven, Suit.Clubs));
		p.addCard(new Card(Face.Eight, Suit.Clubs));
		p.addCard(new Card(Face.Nine, Suit.Clubs));
		p.addCard(new Card(Face.Ten, Suit.Clubs));
		p.addCard(new Card(Face.Jack, Suit.Clubs));
		p.addCard(new Card(Face.Queen, Suit.Clubs));
		p.addCard(new Card(Face.King, Suit.Clubs));
		p.addCard(new Card(Face.Ace, Suit.Clubs));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, Collections.EMPTY_SET);
		assertEquals(1d, num, 0.0001);
		//assertEquals("Didn't get the right number", 0d, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, Collections.EMPTY_SET);
		assertEquals(1d, num, 0.0001);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, Collections.EMPTY_SET);
		assertEquals(0.9989, num, 0.0001);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, Collections.EMPTY_SET);
		assertEquals(0.8548, num, 0.0001);		

	}



}
