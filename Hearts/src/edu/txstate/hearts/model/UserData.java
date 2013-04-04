/**
 * 
 */
package edu.txstate.hearts.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



/**
 * Holds implementation for the creation and modification
 * of data belonging to an individual user
 * @author Jonathan Shelton, Maria Poole,,,
 *
 */
public class UserData 
{
	private FileWriter output;
	private boolean found = false;	
	private File name;
	private String userName;
			
	public void CreateUserData(String userFileName)
	{		
			userName = userFileName;
			name = new File(userFileName + ".txt");
			if(name.exists())
				found = true;
			try
			{
				output = new FileWriter(name, true);//open/create file
				
			}
			catch(IOException ioe)
			{
			    System.err.println("IOException: " + ioe.getMessage());
			}
					
	}//end of function
	
	public void addUserNameToFile(){
		if(found){
			try
			{
			    String filename= "Users.txt";
			    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			    fw.write(userName);//appends the string to the file
			    fw.close();
			}
			catch(IOException ioe)
			{
			    System.err.println("IOException: " + ioe.getMessage());
			}
		}
	}
	
	public void writeAchievement(String info){
		try{
			output.write(info + "\n");
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	
	public void closeFile(){
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}//end of class
