/**
 *
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.txstate.hearts.controller.Hearts.Passing;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;

/**
 * @author Neil Stickels, I Gede Sutapa
 * 
 */
public abstract class Player
{
	private String name;
	private int score;
	private List<Card> hand;
	private Random rand = new Random();
	private List<Card> inPlayCards; // keep track cards that are being played on the table
	private Set<Card> playedCards; // keep track cards that have been played in the past
	private List<Card> takenCards; // keep track cards that the player took
	private boolean qosPlayed = false;
	private List<Set<Card>> knownCards = new ArrayList<Set<Card>>(3);
	private List<Set<Suit>> knownEmpties = new ArrayList<Set<Suit>>(3);
	private int num;

	/**
	 * Constructor
	 * 
	 * @param name		name of the player
	 * @param num		position index
	 */
	public Player(String name, int num)
	{
		this.num = num;
		hand = new ArrayList<Card>();
		inPlayCards = new ArrayList<Card>();
		playedCards = new HashSet<Card>();
		takenCards = new ArrayList<Card>();
		this.name = name;
		for (int i = 0; i < 3; i++)
		{
			knownCards.add(new HashSet<Card>());
			knownEmpties.add(new HashSet<Suit>());
		}
	}

	/**
	 * @return player's name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * @return player's score
	 */
	public int getScore()
	{
		return this.score;
	}

	/**
	 * @param point	score to add to player's total score
	 */
	public void addScore(int point)
	{
		this.score += point;
	}

	/**
	 * @param card	card to add to player's hand
	 */
	public void addCard(Card card)
	{
		this.hand.add(card);
	}

	/**
	 * @param cards	cards to add to player's hand
	 */
	public void addCards(List<Card> cards)
	{
		for (int i = 0; i < cards.size(); i++)
			addCard(cards.get(i));
	}

	/**
	 * Print player's hand
	 */
	public void printHand()
	{
		int counter = 0;
		counter = showCardsOfSuit(Suit.Clubs, counter);
		counter = showCardsOfSuit(Suit.Diamonds, counter);
		counter = showCardsOfSuit(Suit.Spades, counter);
		counter = showCardsOfSuit(Suit.Hearts, counter);
	}

	private int showCardsOfSuit(Suit suit, int counter)
	{
		for (int i = 0; i < hand.size(); i++)
		{
			if (hand.get(i).getSuit() == suit)
				System.out.printf("%-19s%s", hand.get(i), ((++counter) % 4 == 0) ? "\n" : "");
		}
		return counter;

	}

	/**
	 * @return	true if player has the two of club, false otherwise
	 */
	public boolean hasTwoOfClubs()
	{
		Iterator<Card> iterator = hand.iterator();
		while (iterator.hasNext())
		{
			Card c = iterator.next();
			if ((c.getSuit() == Card.Suit.Clubs) && (c.getFace() == Card.Face.Deuce))
				return true;
		}
		return false;
	}

	/**
	 * @return true if queen of spade has been played, false otherwise
	 */
	protected boolean isQosPlayed()
	{
		return qosPlayed;
	}

	/**
	 * @param cardsPlayed	list of cards that are currently played this turn
	 * @param heartsBroken	indicator whether hearts has been broken
	 * @param veryFirstTurn	indicator whether this is a very first turn within a game
	 * @return			card that is selected to play
	 */
	public abstract Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn);

	/**
	 * @param passing	direction of passing
	 * @return		cards to pass
	 */
	public abstract List<Card> getCardsToPass(Passing passing);

	/**
	 * @return	player's hand
	 */
	public List<Card> getHand()
	{
		return hand;
	}

	/**
	 * @param cardsPlayed	list of cards that have been played in the current turn
	 * @param heartsBroken	indicator whether hearts has been broken
	 * @return			list of legal cards to play
	 */
	protected List<Card> getLegalCards(List<Card> cardsPlayed, boolean heartsBroken)
	{
		List<Card> playable = new ArrayList<Card>();

		// if I am not the first player
		if (cardsPlayed.size() > 0)
		{
			Card ledCard = cardsPlayed.get(0);
			List<Card> otherSuits = new ArrayList<Card>();
			for (int i = 0; i < getHand().size(); i++)
			{
				Card c = getHand().get(i);
				if (c.getSuit() == ledCard.getSuit())
				{
					playable.add(c);
				}
				else
				{
					otherSuits.add(c);
				}
			}
			// if I cannot find anything within the same suit that the first
			// player did
			if (playable.size() == 0)
				playable.addAll(otherSuits);
		}
		else
		{
			// if hearts is already broken, player can use any cards
			if (heartsBroken)
				playable.addAll(getHand());
			else
			// if heart is not broken
			{
				boolean allHearts = true;
				for (int i = 0; i < this.getHand().size(); i++)
				{
					if (this.getHand().get(i).getSuit() != Suit.Hearts)
					{
						allHearts = false;
						break;
					}
				}

				// but all I have all hearts
				if (allHearts)
					playable.addAll(getHand());
				else
				// I have other suits, not only hearts
				{
					for (int i = 0; i < this.getHand().size(); i++)
					{
						if (this.getHand().get(i).getSuit() != Suit.Hearts)
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

	/**
	 * Add card to in play collection.
	 * 
	 * @param card	card to add
	 */
	public void addInPlayCards(Card card)
	{
		this.inPlayCards.add(card);
	}

	/**
	 * Clear in play collection.
	 */
	public void clearInPlayCards()
	{
		this.inPlayCards.clear();
	}

	/**
	 * @return in play card collection
	 */
	public List<Card> getInPlayCards()
	{
		return this.inPlayCards;
	}

	/** 
	 * @return	known cards
	 */
	public List<Set<Card>> getKnownCards()
	{
		return knownCards;
	}

	/**
	 * @return	known empties
	 */
	public List<Set<Suit>> getKnownEmpties()
	{
		return knownEmpties;
	}

	/**
	 * @return	set of played cards
	 */
	public Set<Card> getPlayedCards()
	{
		return playedCards;
	}

	/**
	 * Add played cards
	 * 
	 * @param cards		cards played
	 * @param tookCards		indicator whether player is the one who take the cards
	 * @param num			player index
	 */
	public void addPlayedCards(Collection<Card> cards, boolean tookCards, int num)
	{
		this.playedCards.addAll(cards);
		if (!qosPlayed)
		{
			Iterator<Card> iterator = cards.iterator();
			while (iterator.hasNext())
			{
				Card c = iterator.next();
				if (c.getFace() == Face.Queen && c.getSuit() == Suit.Spades)
					qosPlayed = true;
			}
		}
	}

	/**
	 * Clear played cards
	 */
	public void clearPlayedCards()
	{
		this.playedCards.clear();
	}

	/**
	 * Add taken card
	 * 
	 * @param card		card to add
	 * @param print	indicator whether to print the card or not
	 */
	public void addTakenCard(Card card, boolean print)
	{
		this.takenCards.add(card);
		if (print)
			System.out.println("Add " + card.toString() + " to " + this.getName());
	}

	/**
	 * Clear taken cards
	 */
	public void clearTakenCards()
	{
		this.takenCards.clear();
	}

	/**
	 * @return collection of taken cards
	 */
	public List<Card> getTakenCards()
	{
		return this.takenCards;
	}

	/**
	 * Sort player's hand based on comparator class
	 */
	public void sortCards()
	{
		Collections.sort(this.hand, new CardComparator());
	}

	/**
	 * Clear player's in play cards, played cards and taken cards
	 */
	public void clearCards()
	{
		clearInPlayCards();
		clearPlayedCards();
		clearTakenCards();
	}

	/**
	 * @return player index
	 */
	public int getMyNum()
	{
		return num;
	}
}