/**
 *
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

//import org.apache.commons.math3.util.ArithmeticUtils;

import edu.txstate.hearts.model.Card.Suit;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public abstract class Player {

	private String name;
	private int score;
	private List<Card> hand;
	private Random rand = new Random();
	private List<Card> inPlayCards; //keep track cards that are being played on the table
	private Set<Card> playedCards; //keep track cards that have been played in the past
	private List<Card> takenCards; //keep track cards that the player took

	public Player(String playerName) {
		hand = new ArrayList<Card>();
		inPlayCards = new ArrayList<Card>();
		playedCards = new HashSet<Card>();
		takenCards = new ArrayList<Card>();
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

	public void addCards(List<Card> cards)
	{
		for(int i = 0; i < cards.size(); i++)
			addCard(cards.get(i));
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

	public abstract List<Card> getCardsToPass();

	protected List<Card> getHand() {
		return hand;
	}

	protected List<Card> getLegalCards(List<Card> cardsPlayed, boolean heartsBroken)
	{
		List<Card> playable = new ArrayList<Card>();

		//if I am not the first player
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
				}
				else
				{
					otherSuits.add(c);
				}
			}
			//if I cannot find anything within the same suit that the first player did
			if(playable.size() == 0)
				playable.addAll(otherSuits);
		}
		else
		{
			//if hearts is already broken, player can use any cards
			if(heartsBroken)
				playable.addAll(getHand());
			else //if heart is not broken
			{
				boolean allHearts = true;
				for(int i = 0; i < this.getHand().size(); i++)
				{
					if(this.getHand().get(i).getSuit() != Suit.Hearts)
					{
						allHearts = false;
						break;
					}
				}

				//but all I have all hearts
				if(allHearts)
					playable.addAll(getHand());
				else //I have other suits, not only hearts
				{
					for(int i = 0; i < this.getHand().size(); i++)
					{
						if(this.getHand().get(i).getSuit() != Suit.Hearts)
							playable.add(this.getHand().get(i));
					}
				}
			}
		}
		return playable;
	}

	protected Random getRand()
	{
		return rand;
	}

	public void addInPlayCards(Card card)
	{
		this.inPlayCards.add(card);
		//also add to the played cards
		addPlayedCards(card);
	}

	public void clearInPlayCards()
	{
		this.inPlayCards.clear();
	}
	
	public List<Card> getInPlayCards()
	{
		return this.inPlayCards;
	}
	

	public void addPlayedCards(Card card)
	{
		this.playedCards.add(card);
	}

	public void clearPlayedCards()
	{
		this.playedCards.clear();
	}

	public void addTakenCard(Card card)
	{
		this.takenCards.add(card);
		System.out.println("Add " + card.toString() + " to " + this.getName());
	}

	public void clearTakenCards()
	{
		this.takenCards.clear();
	}


	public List<Card> getTakenCards()
	{
		return this.takenCards;
	}

	public void sortCards()
	{
		Collections.sort(this.hand, new CardComparator());
	}

	public void clearCards()
	{
		clearInPlayCards();
		clearPlayedCards();
		clearTakenCards();
	}

	public long getTotalCardCombinations(Card.Suit suit, int myCount)
	{
		int available = 13-myCount;
		Iterator<Card> iterator = playedCards.iterator();
		while(iterator.hasNext())
		{
			Card c = iterator.next();
			if(c.getSuit() == suit)
				available--;
		}
		System.out.println("available is "+available);
		return (long)(Math.pow(3, available));
// This algorithm gave us the pattern to be able to use the above math function
//		long totalCombos = 0;
//		for(int x = 0; x < available+1; x++)
//		{
//			for(int y = 0; y < available+1; y++)
//			{
//				for(int z= 0; z < available+1; z++)
//				{
//					if(x+y+z == available)
//					{
//					long xNewCombos, yNewCombos, zNewCombos;
//					xNewCombos = ArithmeticUtils.binomialCoefficient(available, x);
//					//System.out.println("adding "+available+"C"+x+" for player 1 - "+xNewCombos);
//					//totalCombos += newCombos;
//					yNewCombos = ArithmeticUtils.binomialCoefficient(available-x, y);
//					//System.out.println("adding "+(available-x)+"C"+y+" for player 2 - "+yNewCombos);
//					//totalCombos += newCombos;
//					zNewCombos = ArithmeticUtils.binomialCoefficient(available-x-y, z);
//					//System.out.println("adding "+(available-x-y)+"C"+z+" for player 3 - "+zNewCombos);
//					long newCombos = (xNewCombos*yNewCombos*zNewCombos);
//					if(newCombos < 1)
//						newCombos = 1;
//					totalCombos += newCombos;
//					//System.out.println("adding "+newCombos);
//
//					//System.out.println("---");
//
//					}
//				}
//			}
//		}
//		return totalCombos;
	}

}

