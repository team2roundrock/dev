package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;

/**
 * This class implements the logic for a medium level AI.
 * @author I Gede Sutapa, Maria Poole
 *
 */
public class AgentDetermined extends Agent 
{
	/**
	 * Constructor
	 * 
	 * @param name	agent name
	 * @param num	position index
	 */
	public AgentDetermined(String name, int num) 
	{
		super(name, num);
	}
	
	/**
	 * This method implements the logic for the way AgentDetermine plays, which corresponds to a medium
	 * difficulty level: AgentDeterming will try to play the highest card it has on his hand while the Hearts haven't 
	 * been broken. After the Hearts have been broken, it is going to play the lowest card it has on its hand, except,
	 * when the suit being played is Hearts. In this case it is going to look at the highest heart being played and try
	 * to find in its hand the next lowest. By doing this, AgentDetermine does not waste its lowest heart card. 
	 */
	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn) 
	{
		Card cardToPlay = null;
		if(veryFirstTurn)
		{
			cardToPlay = super.playTwoOfClub();
		} 
		else
		{
			List<Card> playable = getLegalCards(cardsPlayed, heartsBroken);
			Collections.sort(playable, new CardComparator());
			
			//if heart has not been broken
			if(!heartsBroken){
				if(this.getInPlayCards().size() == 0)
					cardToPlay = playable.get(0);
				else
					cardToPlay = playable.get(playable.size() - 1);
			}
			else //heart is broken
			{
				//start logic to do 'smart' move when heart has been broken and the play is heart
				//*****************
				boolean hasHearts = false;
				for(int i = 0; i < playable.size(); i++)
				{
					if(playable.get(i).getSuit() == Suit.Hearts)
					{
						hasHearts = true;
						break;
					}
				}
				
				//if I am not the first one and the play is Heart
				if(hasHearts && this.getInPlayCards().size() > 0 && this.getInPlayCards().get(0).getSuit() == Suit.Hearts)
				{
					int highestHeartValue = -1;
					//find the highest Heart value
					for(int i = 0; i < this.getInPlayCards().size(); i++)
					{
						Card heartCard = this.getInPlayCards().get(i);
						if(heartCard.getFace().ordinal() > highestHeartValue)
							highestHeartValue = heartCard.getFace().ordinal();
					}
					
					//now we can determine the heart card that we can play
					List<Card> heartCards = new ArrayList<Card>();
					for(int i = 0; i < playable.size(); i++)
					{
						Card currentCard = playable.get(i);
						if(currentCard.getSuit() == Suit.Hearts)
							heartCards.add(currentCard);
					}
					
					//sort
					Collections.sort(heartCards, new CardComparator());
					
					for(int i = heartCards.size() - 1; i >= 0; i--)
					{
						if(heartCards.get(i).getFace().ordinal() < highestHeartValue)
						{
							cardToPlay = heartCards.get(i);
							break;
						}
					}
				}
				//*****************
				//end of logic
				
				//if we can't find any 'smart' move card
				if(cardToPlay == null)
					cardToPlay = playable.get(0);
				
			}//end heart is broken	
		}
		getHand().remove(cardToPlay);
		return cardToPlay;
	}
	
	/**
	 * This method determines how AgentDetermine passes cards at the beginning of a turn. If the Queen of Spades or
	 * the Ace, Ten, Jack, King and Queen of Hearts are in its hand, it is going to pass those cards. Otherwise, it will
	 * pass the highest cards it has. 
	 */
	public List<Card> getCardsToPass() 
	{
		int numCardsToPass = 3;
		List<Card> cardsToPass = new ArrayList<Card>();
		
		//TODO: improve logic to select cards to pass
		//for now, just pick the top 3 highest cards
		List<Card> myHand = this.getHand();
		
		Collections.sort(myHand, new CardComparator());
		
		for(int i = 0; i < myHand.size() && cardsToPass.size() < numCardsToPass; i++)
		{
			Card myCard = myHand.get(i);
			if(myCard.getSuit() == Suit.Spades && myCard.getFace() == Face.Queen)
			{
				cardsToPass.add(myCard);
				getHand().remove(myCard);
			}
			else if(myCard.getSuit() == Suit.Hearts && (myCard.getFace() == Face.Ten || myCard.getFace() == Face.Ace
					|| myCard.getFace() == Face.Jack ||myCard.getFace() == Face.King || myCard.getFace() == Face.Queen))
			{
				cardsToPass.add(myCard);
				getHand().remove(myCard);
			}
		}	
			
		for(int i = myHand.size() - 1; i >= myHand.size() - numCardsToPass && cardsToPass.size() < numCardsToPass ; i--)
		{
			cardsToPass.add(myHand.get(i));
			getHand().remove(myHand.get(i));
		}
		return cardsToPass;
	}
}