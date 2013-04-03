package edu.txstate.hearts.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;


public class ReadFiles {
	private static String user;
	private Scanner input;
	private static ArrayList<String> listOfUsers = new ArrayList<String>();
	
	public void openFile(String fileName){
		try{
			input = new Scanner(new File(fileName));
		}
		catch(FileNotFoundException filenotfound){
			System.err.println("Error opening file");
			System.exit(1);
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
	
	public static Vector<String> getRecords(){
		Vector<String> record = new Vector<String>();
		for(String value : listOfUsers)
			record.add(value);
		return record;
	}
	
	public void closeFile(){
		if(input != null)
			input.close();
	}
}//end of class
