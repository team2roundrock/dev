package edu.txstate.hearts.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;


public abstract class ReadFiles {
	private static List<String> listOfUsers;
	private static Map<String,List<String>> readAchievements;
	
	private static Scanner openFile(String fileName) throws FileNotFoundException{
		try{
			File file = new File(fileName + ".txt");
			Scanner input = new Scanner(file);
			return input;
		}
		catch(FileNotFoundException filenotfound){
			throw filenotfound;
		}
	}//end of open file
	
	public static void addUserRecord(String userName)
	{
		if(listOfUsers == null)
		{
			listOfUsers = new ArrayList<String>();
		}
		listOfUsers.add(userName);
	}
	
	public static List<String> readUserRecords() throws FileNotFoundException {
		if (listOfUsers == null) {
			listOfUsers = new ArrayList<String>();
			Scanner input = openFile("Users");

			try {
				while (input.hasNext()) {
					String user = input.next();
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
	
	public static List readAchievements(String playerName) throws FileNotFoundException {

		if (readAchievements == null) {
			readAchievements = new HashMap<String,List<String>>();
		}
		if(readAchievements.get(playerName) == null)
		{
			List playerAchievements = new ArrayList<String>();
			Scanner input = openFile(playerName);
			try {
				while (input.hasNext()) {
					String achievement = input.nextLine();
					playerAchievements.add(achievement);
				}
				readAchievements.put(playerName, playerAchievements);

			} catch (NoSuchElementException nosuchelement) {
				System.err.println("File improperly formed");
				throw nosuchelement;
			} catch (IllegalStateException stateException) {
				System.err.println("Error reading from file");
				throw stateException;
			} finally {
				closeFile(input);
			}
		}
		return readAchievements.get(playerName);
	}
	
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
	
	public static List<String> getReadAchievements(String playerName){
		if(readAchievements == null || readAchievements.get(playerName) == null)
		{
			try {
				readAchievements(playerName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return readAchievements.get(playerName);
	}
	
	private static void closeFile(Scanner input){
		if(input != null)
			input.close();
	}
}//end of class
