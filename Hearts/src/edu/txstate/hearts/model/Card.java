package edu.txstate.hearts.model;

/**
 * Card class
 * 
 * @author Neil Stickels, I Gede Sutapa
 */
public class Card
{
	@Override
	public boolean equals(Object obj) 
	{
		Card card = (Card)obj;
		return card.getFace() == getFace() && card.getSuit() == getSuit();
	}

	public static enum Face { Deuce, Three, Four, Five, Six
		, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace };
		
	public static enum Suit { Clubs, Diamonds, Hearts, Spades };
	
	private final Face face;
	private final Suit suit;
	
	/**
	 * Constructor
	 * 
	 * @param face	card face
	 * @param suit	card suit
	 */
	public Card(Face face, Suit suit)
	{
		this.face = face;
		this.suit = suit;
	}
	
	/**
	 * @return card face
	 */
	public Face getFace()
	{
		return this.face;
	}
	
	/**
	 * @return card suit
	 */
	public Suit getSuit()
	{
		return this.suit;
	}
	
	public String toString()
	{
		return String.format("%s of %s", face, suit);
	}
}
