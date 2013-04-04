package edu.txstate.hearts.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;


public class ReadFiles {
	private static String user;
	private Scanner input;
	private static String achievement;
	private static ArrayList<String> listOfUsers = new ArrayList<String>();
	private static ArrayList<String> readAchievements = new ArrayList<String>();
	
	public void openFile(String fileName) throws FileNotFoundException{
		try{
			File file = new File(fileName + ".txt");
			input = new Scanner(file);
		}
		catch(FileNotFoundException filenotfound){
			throw filenotfound;
		}
	}//end of open file
	
	public void readUserRecords(){
				
		try{ 
			while(input.hasNext()){
				user = input.next();
				listOfUsers.add(user);
			}//end while
		}//end try
		catch(NoSuchElementException nosuchelement){
			System.err.println("File improperly formed");
			input.close();
			System.exit(1);
		}
		catch(IllegalStateException stateException){
			System.err.println("Error reading from file");
			System.exit(1);
		}
	}//end of readRecords
	
	public void readAchievements(){
		
		try{ 
			while(input.hasNext()){
				achievement = input.nextLine();
				readAchievements.add(achievement);
			}
			
		}//end try
		catch(NoSuchElementException nosuchelement){
			System.err.println("File improperly formed");
			input.close();
			System.exit(1);
		}
		catch(IllegalStateException stateException){
			System.err.println("Error reading from file");
			System.exit(1);
		}
	}
	
	public static Vector<String> getRecords(){
		return new Vector(listOfUsers);
	}
	
	public static ArrayList<String> getReadAchievements(){
		return readAchievements;
	}
	
	public void closeFile(){
		if(input != null)
			input.close();
	}
}//end of class
