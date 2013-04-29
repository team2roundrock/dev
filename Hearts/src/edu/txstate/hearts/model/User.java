package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

import edu.txstate.hearts.controller.Hearts.Passing;
import edu.txstate.hearts.model.Achievements;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;
import edu.txstate.hearts.utils.ReadFiles;

/**
 * User class holds implementation for human player input
 * @author Neil Stickels, I Gede Sutapa, Jonathan Shelton
 *
 */
public class User extends Player 
{
	private Achievements achievements; //this may not be needed
	
	/**
	 * Constructor
	 * 
	 * @param name	user name
	 * @param num	position index
	 */
	public User(String name, int num) 
	{
		super(name, num);
		try 
		{
			List<String> readAchievements = ReadFiles.readAchievements(name);
			this.achievements = new Achievements(name, readAchievements);
		} 
		catch (FileNotFoundException e) 
		{
			this.achievements = new Achievements(name);
		}
	}
	
	/**
	 * @return user's achievements
	 */
	public Achievements getAchievements() 
	{
		return achievements;
	}
	
	/**
	 * Set user's achievements
	 * 
	 * @param achievements	the achievements to set
	 */
	public void setAchievements(Achievements achievements) 
	{
		this.achievements = achievements;
	}
	
	/**
	 * Try to play card selected by user
	 * 
	 * @param card			card selected to play
	 * @param cardsPlayed	cards played for the current turn
	 * @param heartsBroken	indicator whether hearts has been broken
	 * @param veryFirstTurn	indicator whether this is a very first turn
	 * @throws Exception	if card is not valid to play
	 */
	public void TryPlayCard(Card card, List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn) throws Exception
	{
		if(veryFirstTurn)
		{
			if(card.getSuit() != Suit.Clubs || card.getFace() != Face.Deuce)
				throw new Exception("Must play deuce of clubs");
		} 
		else
		{
			List<Card> playable = getLegalCards(cardsPlayed, heartsBroken);
			if(!playable.contains(card))
				throw new Exception("Invalid card to play. Try other card!");
			
			getHand().remove(card);
		}
	}
	
	/**
	 * Human player is able to play a card
	 * 
	 * @param cardsPlayed		cards played for the current turn
	 * @param heartsBroken		indicator whether hearts has been broken
	 * @param veryFirstTurn		indicator whether this is a very first turn
	 * @return cardToPlay		card selected to play
	 */
	@Override
	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn) 
	{	
		Card cardToPlay = null;
		String cardStr = null; //holds user input
		List<Card> myHand = this.getHand();
		Collections.sort(myHand, new CardComparator());
		
		System.out.println("\nIt is " + this.getName()+ "'s turn to play");
		System.out.println("\nDISPLAYING CURRENT HAND....");
		this.printHand();
		
		boolean validPlay = false;
		while (!validPlay ) //while no valid play has been made
		{
			System.out.println("\n=======================================");
			System.out.println("(JACK = 11, QUEEN = 12, KING = 13, ACE = 14)");
			System.out.println("Choose card to play:");

			//Notify user which suit must be played
			notifyPlayableSuit(cardsPlayed, veryFirstTurn, myHand);
			
			//Capture user input
			cardStr = getConsoleInput(cardStr);
			
			//Ensure user input is valid & matches current hand
			cardToPlay = tokenizeString(cardStr, myHand, cardToPlay);
			if (cardToPlay != null)
				validPlay = true;
		}
		return cardToPlay;
	}

	/**
	 * Human player is able to pass a card
	 * 
	 * @param passing		passing direction
	 * @return cardsToPass	cards to pass
	 */
	@Override
	public List<Card> getCardsToPass(Passing passing) 
	{	
		Card cardToPass = null;
		String cardStr = null; //holds user input
		List<Card> cardsToPass = new ArrayList<Card>();
		List<Card> myHand = this.getHand();
		Collections.sort(myHand, new CardComparator());
		
		for (int i = 1; i <= 3; i++)
		{
			System.out.println("\nDISPLAYING CURRENT HAND....");
			this.printHand();
			System.out.println("\n=======================================");
			System.out.println("(JACK = 11, QUEEN = 12, KING = 13, ACE = 14)");
			System.out.println("Choose card to pass (" +i+ " of 3):");

			//Capture user input
			cardStr = getConsoleInput(cardStr);
			
			//Skip the passing of remaining cards (dev testing option - pass highest card at any point)
			if (cardStr.equalsIgnoreCase("skip")) //user types "skip"
			{
				for (int j = i; j <= 3; j++)
				{					
					cardToPass = devSkipPass(i);
					cardsToPass.add(cardToPass);
					i++;
					System.out.println("Automatically choosing next top card ("+cardToPass+") to pass...");
				}
				return cardsToPass;
			}
			
			//Ensure user input is valid & matches current hand
			cardToPass = tokenizeString(cardStr, myHand, cardToPass);
			if (cardToPass != null)
				cardsToPass.add(cardToPass);
			else //passed value is invalid
				i--;
		}
		return cardsToPass;
	}
	
	/**
	 * @param cardStr	input string
	 * @return		card string
	 */
	private String getConsoleInput(String cardStr) 
	{
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
		try {
			cardStr = cin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cardStr;
	}
	
	/**
	 * Notify user which suit must be played
	 * 
	 * @param cardsPlayed	cards played for current turn
	 * @param veryFirstTurn	indicator whether this is a very first turn
	 * @param myHand		
	 */
	private void notifyPlayableSuit(List<Card> cardsPlayed, boolean veryFirstTurn, List<Card> myHand) 
	{
		Iterator<Card> searchHand = myHand.iterator();
		boolean found = false;
		while(searchHand.hasNext() && found == false)
		{
			Card card = searchHand.next();
			if (veryFirstTurn) //player 1 in first trick of a round
			{
				found = true;
				System.out.println("(You must lead with the Duece of Clubs)");
			}
			else
			{
				if (cardsPlayed.size() > 0) //players 2-4
				{
					Suit suitLed = cardsPlayed.get(0).getSuit();
					if (card.getSuit().equals(suitLed)) //user holds a suit matching first suit played
					{
						found = true;
						System.out.println("(You must play " + suitLed + ")");
					}
					
					//TODO Figure out why this doesn't work
					//When uncommented, the else statement below executes first 
					//followed by the if statement above
//						else
//						{
//							match = true;
//							System.out.println("You can play any card");
//						}
				}
				else
				{
					found = true;
					System.out.println("(You can lead with any card)");
				}
			}
		}
	}
	
	/**
	 * User input is converted from string into tokens. Requires input 
	 * in the form of "[number] space [first letter of suit]"
	 * 
	 * Both tokens are then compared against card suits and faces
	 * in hand to determine if they match.
	 * 
	 * @param cardStr
	 * @param myHand
	 * @param potentialPlay
	 * @return potentialPlay, which returns either a valid card or null
	 */
	private Card tokenizeString(String cardStr, List<Card> myHand, Card potentialPlay) 
	{
		StringTokenizer st = new StringTokenizer(cardStr); //turn individual characters/words into tokens
		String numStr = st.nextToken();
		int myFace = Integer.parseInt(numStr);
		String suitStr = st.nextToken();
		Suit mySuit = null;
		if (suitStr.equalsIgnoreCase("c")) //clubs
			mySuit = Suit.Clubs;
		else if (suitStr.equalsIgnoreCase("d")) //diamonds
			mySuit = Suit.Diamonds;
		else if (suitStr.equalsIgnoreCase("h")) //hearts
			mySuit = Suit.Hearts;
		else if (suitStr.equalsIgnoreCase("s")) //spades
			mySuit = Suit.Spades;
		else
		{
			System.out.println("Invalid character typed. Must be the first letter of a suit");
			return potentialPlay;
		}
		Iterator<Card> iterator = myHand.iterator();
		Card card = null;
		boolean match = false;
		while(iterator.hasNext() && match == false)
		{
			card = iterator.next();
			if (card.getFace().ordinal() == myFace-2) //num - 2 is offset in ordinal. 4 is actually 2.
			{
				if (card.getSuit() == mySuit)
				{
					match = true;
					potentialPlay = card;
					myHand.remove(card);
					return potentialPlay;
				}
			}
		}
		if (!match)
		{
			System.out.println("Seriously? Choose a VALID card");
		}
		return potentialPlay;
	}
	
	/**
	 * Exists to implement a dev testing option of skipping
	 * card passing. Works by automatically passing the next 
	 * highest card in user's hand
	 * 
	 * @param numCardsToPass tells method how many cards are left
	 * @return 			highest card in hand
	 */
	private Card devSkipPass(int numCardsToPass) 
	{
		Card cardToPass = null;
		List<Card> myHand = this.getHand();
		Collections.sort(myHand, new CardComparator());
		
		for(int i = myHand.size() - 1; i >= 13 - numCardsToPass; i--)
		{
			cardToPass = myHand.get(i);
			getHand().remove(myHand.get(i));
		}
		return cardToPass;
	}	
}