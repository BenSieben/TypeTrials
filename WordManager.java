import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Manages a .txt file for reading 
 * & writing (ie loading & saving)
 * @author Ben Sieben
 * @version 11 May 2013
 */
public class WordManager {
	
	private String fileName;
	
	/**
	 * Makes a new DataManager for the file argument
	 * @param fileName name of the file to be managed
	 * @pre filename is a valid name
	 */
	public WordManager(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Sets the filename of this WordManager to the input String
	 * @param fileName name of the file to be managed
	 * @pre the WordManager has not done any work before this 
	 * method is called
	 */
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	/**
	 * Adds an Integer to the file
	 * @param data Integer to add to the file
	 */
	public void addInt(Integer data) {
		try {
			addString(Integer.toString(data));
		}
		catch(Exception e) {
			System.err.println("Error adding int: "+e.getMessage());
		}
	}
	
	/**
	 * Adds a String to the file
	 * @param data the String to add to the file
	 */
	public void addString(String data) {
		try {
			saveData(getContents()+" "+data);
		}
		catch(Exception e) {
			System.err.println("Error adding string: "+e.getMessage());
		}
	}
	
	/**
	 * Returns the contents of this file
	 * @return the contents of this file
	 */
	public String getContents() {
		try {
			String data = "";
			Scanner s = new Scanner(new File(fileName));
			while(s.hasNext()) {
				data += s.next()+" ";
			}
			s.close();
			return data.trim();
		}
		catch(Exception e) {
			System.err.println("Error loading data: "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Returns the number of values in the file
	 * @return the number of values in the file
	 */
	public int getNumOfValues() {
		try {
			int numOfValues = 0;
			Scanner s = new Scanner(getContents());
			while(s.hasNext()) {
				s.next();
				numOfValues++;
			}
			s.close();
			return numOfValues;
		}
		catch(Exception e) {
			System.err.println("Error getting number of values: "+e.getMessage());
			return -1;
		}
	}
	
	/**
	 * Returns the filename of the file this WordManager is managing
	 * @return the filename of the file this WordManager is managing
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Returns the String at the input position of the file
	 * @param position the position to retrieve from
	 * @return the String at the line position
	 * @pre position < number of words in the file
	 */
	public String getString(int position) {
		try {
			String data = "";
			Scanner s = new Scanner(getContents());
			for(int i = 1; i <= position; i++) {
				data = s.next();
			}
			return data;
		}
		catch(Exception e) {
			System.err.println("Error getting string: "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Replaces a part of the file with the input string
	 * @param position part of file to replace
	 * @param data the data to replace the line of the file with
	 */
	public void replaceString(int position, String data) {
		try {
			saveData(getContents().replace(getString(position), data));
		}
		catch(Exception e) {
			System.err.println("Error replacing string: "+e.getMessage());
		}
	}
	
	/**
	 * Saves the file with the String data in it
	 * @param data the content to put inside the file
	 */
	public void saveData(String data) {
		try {
			FileWriter f = new FileWriter(fileName);
			BufferedWriter b = new BufferedWriter(f);
			b.write(data);
			b.close();
		}
		catch(Exception e) {
			System.err.println("Error saving data: "+e.getMessage());
		}
	}
	
	/**
	 * Returns the integer at the position
	 * @param position where to get integer from
	 * @return Integer at the position
	 * @pre position is a line in the file that has an Integer
	 */
	public Integer getInt(int position) {
		try {
			return Integer.parseInt(getString(position));
		}
		catch(Exception e) {
			System.err.println("Error getting int: "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Adds a hash code to the file at the end of the file; 
	 * use this when saving the file
	 */
	public void addHash() {
		addString(" " + getContents().hashCode());
	}
	
	/**
	 * Checks the hash code of the file and removes the hash that is currently
	 * in the file; use this when loading the file
	 * @return true if hash code matches, false if hash code does not match
	 * @pre there is already a hashcode at the end of the file
	 */
	public boolean checkHash() {
		try {
			int numOfValues = getNumOfValues();
			int oldHash = getInt(numOfValues);
			replaceString(numOfValues, "");
			String contents = getContents();
			saveData(contents);
			if(oldHash == contents.hashCode()) {
				return true;
			}
			else {
				return false;
			}

		}
		catch(Exception e) {
			System.err.println("Error checking hash: "+e.getMessage());
			return false;
		}
	}
	
}
