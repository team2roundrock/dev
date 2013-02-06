/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import edu.txstate.hearts.controller.Hearts;

/**
 * @author Neil Stickels, I Gede Sutapa
 * 
 */
public abstract class Player {

	private String name;
	private int score;
	private List<Card> hand;
	private Random rand = new Random();

	public Player(String playerName) {
		hand = new ArrayList<Card>();
		this.name = playerName;
	}

	public String getName() {
		return this.name;
	}

	public int getScore() {
		return this.score;
	}

	public void addScore(int point) {
		this.score += point;
	}

	public void addCard(Card card) {
		this.hand.add(card);
	}

	public void printHand() {
		for (int i = 0; i < hand.size(); i++) {
			System.out.printf("%-19s%s", hand.get(i), ((i + 1) % 4 == 0) ? "\n"
					: "");
		}
	}

	public boolean hasTwoOfClubs() {
		Iterator<Card> iterator = hand.iterator();
		while (iterator.hasNext()) {
			Card c = iterator.next();
			if ((c.getSuit() == Card.Suit.Clubs)
					&& (c.getFace() == Card.Face.Deuce))
				return true;
		}
		return false;
	}

	public abstract Card playCard(List<Card> cardsPlayed, boolean heartsBroken,
			boolean veryFirstTurn);

	protected List<Card> getHand() {
		return hand;
	}
	
	protected List<Card> getLegalCards(List<Card> cardsPlayed, boolean heartsBroken)
	{
		List<Card> playable = new ArrayList<Card>();
		if(cardsPlayed.size() > 0)
		{
			Card ledCard = cardsPlayed.get(0);
			List<Card> otherSuits = new ArrayList<Card>();
			for(int i = 0; i < getHand().size(); i++)
			{
				Card c = getHand().get(i);
				if(c.getSuit() == ledCard.getSuit())
				{
					playable.add(c);
				} else
				{
					otherSuits.add(c);
				}
			}
			if(playable.size() == 0)
				playable.addAll(otherSuits);
		} else
		{
			playable.addAll(getHand());
		}
		return playable;
	}
	
	protected Random getRand()
	{
		return rand;
	}

}
