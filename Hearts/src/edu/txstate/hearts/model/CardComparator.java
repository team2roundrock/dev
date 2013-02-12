package edu.txstate.hearts.model;

import java.util.Comparator;

public class CardComparator implements Comparator<Card>
{
	@Override
	public int compare(Card card1, Card card2) 
	{
		return card1.getFace().compareTo(card2.getFace());
	}
}
