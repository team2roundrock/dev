/**
 * 
 */
package edu.txstate.hearts.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Map;
import java.util.Set;


/**
 * Holds implementation for the creation and modification
 * of data belonging to an individual user
 * @author Jonathan Shelton, Maria Poole,,,
 *
 */
public class UserData 
{
	private Player player;
	private Formatter output;
		
	
			
	public void CreateUserData(String userFileName)
	{
		
			try
			{
				output = new Formatter(userFileName + ".txt");//open/create file
				
			}
			catch(FileNotFoundException fileNotFoundException)
			{
				System.err.println("Error opening or creating file.");
				System.exit(1);
			}//End of FileNotFoundException
			catch(FormatterClosedException formatterClosedException)
			{
				System.err.println("Error writing to file.");
				return;
			}
		
		
	}//end of function
	
	public void writeAchievement(int count, Map<Achievements.Achievement, Boolean> table){
		output.format("%d%n", count);
		Set<Achievements.Achievement> keys = table.keySet();
		for(Achievements.Achievement key : keys){
			output.format(key + "   " + table.get(key));
			output.format("%n");
		}
	}
	
	public void closeFile(){
		if(output != null)
			output.close();
	}

}//end of class
