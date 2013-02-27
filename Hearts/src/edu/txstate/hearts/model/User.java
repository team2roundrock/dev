/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

import edu.txstate.hearts.controller.Hearts.Passing;
import edu.txstate.hearts.model.Achievements;
import edu.txstate.hearts.model.Card.Suit;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class User extends Player 
{
	private Achievements achievements;
	
	public User(String playerName) 
	{
		super(playerName);
		this.achievements = new Achievements();
	}

	@Override
	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn) {
		// TODO Implement human player play the card
		return null;
	}

	@Override
	public List<Card> getCardsToPass(Passing passing) {
		// TODO Implement human player pick cards to pass
		List<Card> cardsToPass = new ArrayList<Card>();
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\nDisplaying current hand.");
		this.printHand();
		
		for (int i = 0; i < 3; i++)
		{
			System.out.println("\nChoose card to pass (" +i+ " of 3");
			String cardToPass = null;
			try {
				cardToPass = cin.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StringTokenizer st = new StringTokenizer(cardToPass);
			String numStr = st.nextToken();
			int num = Integer.parseInt(numStr);
			String suitStr = st.nextToken();
			Suit suit = null;
			if (suitStr.equalsIgnoreCase("c")) //clubs
			{
				suit = Suit.Clubs;
			} else if (suitStr.equalsIgnoreCase("d")) //diamonds
			{
				suit = Suit.Diamonds;
			} else if (suitStr.equalsIgnoreCase("h")) //hearts
			{
				suit = Suit.Hearts;
			} else if (suitStr.equalsIgnoreCase("s")) //spades
			{
				suit = Suit.Spades;
			} else
			{
				System.out.println("Invalid character typed. Must be the first letter of a suit");
				i--;
				continue;
			}
						
			List<Card> myHand = this.getHand();
			Collections.sort(myHand, new CardComparator());
			Iterator<Card> iterator = myHand.iterator();
			
			boolean match = false;
			while(iterator.hasNext() && match == false)
			{
				Card card = iterator.next();
				if (card.getFace().ordinal() == num-2) //offset in ordinal. 4 is actually 2
				{
					if (card.getSuit() == suit)
					{
						match = true;
						cardsToPass.add(card);
						myHand.remove(card);
					}
				}
			}
			if (!match)
			{
				System.out.println("Seriously? Choose a valid card");
				i--;
			}
		}

		return cardsToPass;
	}
}
