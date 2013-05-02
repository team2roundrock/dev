package edu.txstate.hearts.utils;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

import edu.txstate.hearts.model.Card;

/**
 * Class assists in the reading of various inputs, such
 * as images and text files, needed for the game to run
 * properly
 * 
 * @author Neil Stickels, I Gede Sutapa, Jonathan Shelton, Maria Poole
 *
 */
public abstract class ReadFiles {
	private static List<String> listOfUsers;
	//private static Map<String,List<String>> readAchievements;
	private static String cardImagesFolder = "images\\cards\\";
	private static String mainImagesFolder = "images\\";
	
	public static Image getCardImage(Card card)
	{
		String suitName = "";
		String faceName = "";
		
		switch (card.getSuit())
		{
			case Clubs:
				suitName = "c";
				break;
			case Diamonds:
				suitName = "d";
				break;
			case Hearts:
				suitName = "h";
				break;
			case Spades:
				suitName = "s";
				break;
		}
		
		switch (card.getFace())
		{
			case Deuce:
				faceName = "2";
				break;
			case Three:
				faceName = "3";
				break;
			case Four:
				faceName = "4";
				break;
			case Five:
				faceName = "5";
				break;
			case Six:
				faceName = "6";
				break;
			case Seven:
				faceName = "7";
				break;
			case Eight:
				faceName = "8";
				break;
			case Nine:
				faceName = "9";
				break;
			case Ten:
				faceName = "t";
				break;
			case Jack:
				faceName = "j";
				break;
			case Queen:
				faceName = "q";
				break;
			case King:
				faceName = "k";
				break;
			case Ace:
				faceName = "a";
				break;
		}
		
		String fileName = faceName + suitName + ".png";
		String fullFileName = cardImagesFolder + fileName;
		
		Image image = null;
		try
		{
			image = ImageIO.read(new File(fullFileName));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
	/**
	 * Returns the image specify in fileName
	 * @param fileName: name of image
	 * @return
	 */
	public static Image getImage(String fileName)
	{
		String fullFileName = mainImagesFolder + fileName;
		
		Image image = null;
		try
		{
			image = ImageIO.read(new File(fullFileName));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return image;
	}
	/**
	 * Opens a new file
	 * @param fileName: name of file
	 * @return
	 * @throws FileNotFoundException
	 */
	private static Scanner openFile(String fileName) throws FileNotFoundException{
		try{
			File file = new File(fileName + ".txt");
			
			try
			{
				ensureUsersFileExists(file);
			} 
			catch (IOException io)
			{
				io.printStackTrace();
			}
			
			Scanner input = new Scanner(file);
			return input;
		}
		catch(FileNotFoundException filenotfound){
			throw filenotfound;
		}
	}//end of open file

	/**
	 * If the Users.txt file does not exist for any reason, a new
	 * Users.txt file is created and initialized with "Player 1"
	 * as the default player name
	 * 
	 * @param file
	 * @throws IOException
	 */
	private static void ensureUsersFileExists(File file) throws IOException
	{
		//Users.txt file is created if it doesn't exist
		File userFile = new File("Users.txt");
		if (file.equals(userFile)) {
			// if (!file.isFile() && !file.createNewFile())
			// throw new IOException("Error creating new file: " +
			// file.getAbsolutePath());
			if (file.createNewFile()) {
				try 
				{
					String playerName = "Player 1";
					FileWriter fw = new FileWriter(userFile, true);
					fw.write(playerName + "\n");
					fw.close();
					// addUserRecord(playerName);
				} catch (IOException ioe) {
					System.err.println("Error creating new file: "
							+ file.getAbsolutePath());
					throw ioe;
				}
			}
		}
	}
	/**
	 * populates listOfUsers with a user record
	 * @param userName
	 */
	public static void addUserRecord(String userName)
	{
		if(listOfUsers == null)
		{
			listOfUsers = new ArrayList<String>();
		}
		listOfUsers.add(userName);
	}
	/**
	 * Reads information contained in the Users text file
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<String> readUserRecords() throws FileNotFoundException {
		if (listOfUsers == null) {
			listOfUsers = new ArrayList<String>();
			Scanner input = openFile("Users");

			try {
				while (input.hasNextLine()) {
					String user = input.nextLine();
					listOfUsers.add(user);
				}// end while
			}// end try
			catch (NoSuchElementException nosuchelement) {
				System.err.println("File improperly formed");
				throw nosuchelement;
			} catch (IllegalStateException stateException) {
				System.err.println("Error reading from file");
				throw stateException;
			} finally
			{
			  closeFile(input);
			}
			
		}
		return listOfUsers;
	}// end of readRecords
	/**
	 * reads a player's achievements
	 * @param playerName
	 * @return
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> readAchievements(String playerName) throws FileNotFoundException {

//		if (readAchievements == null) {
//			readAchievements = new HashMap<String,List<String>>();
//		}
//		if(readAchievements.get(playerName) == null)
//		{
			List playerAchievements = new ArrayList<String>();
			Scanner input = openFile(playerName);
			try {
				while (input.hasNext()) {
					String achievement = input.nextLine();
					playerAchievements.add(achievement);
				}
				//readAchievements.put(playerName, playerAchievements);

			} catch (NoSuchElementException nosuchelement) {
				System.err.println("File improperly formed");
				throw nosuchelement;
			} catch (IllegalStateException stateException) {
				System.err.println("Error reading from file");
				throw stateException;
			} finally {
				closeFile(input);
			}
			return playerAchievements;
//		}
//		return readAchievements.get(playerName);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector<String> getRecords(){
		if(listOfUsers == null)
		{
			try {
				readUserRecords();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Vector(listOfUsers);
	}
	
//	public static List<String> getReadAchievements(String playerName){
//		if(readAchievements == null || readAchievements.get(playerName) == null)
//		{
//			try {
//				readAchievements(playerName);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return readAchievements.get(playerName);
//	}
	
	private static void closeFile(Scanner input){
		if(input != null)
			input.close();
	}
}//end of class
