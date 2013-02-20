package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;

public class AgentDetermined extends Agent {

	public AgentDetermined(String playerName) {
		super(playerName);
		// TODO Auto-generated constructor stub
	}
	
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
			if(!heartsBroken)
				cardToPlay = playable.get(playable.size() - 1);
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
