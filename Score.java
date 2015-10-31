/**
 * A single score for a single game, which has a 
 * numerical score, name of the user, difficulty, 
 * and type of mode it belongs to
 * @author Ben Sieben
 * @version 14 May 2013
 */
public class Score{
	
	/**
	 * Integer value representing this score was from Arcade Mode
	 */
	public static final int ARCADE_SCORE = 1;
	
	/**
	 * Integer value representing this score was from TimeAttack Mode
	 */
	public static final int TIMEATTACK_SCORE = 2;
	
	private int myScore, myDifficulty, myMode;
	private String myName;
	
	/**
	 * Creates a new Score, which has both a 
	 * score number, name (of the person that
	 * made the score), difficulty the score was made
	 * at, and the mode the score was achieved in
	 * @param score How many points were made by the user
	 * @param name Name of the user
	 * @param difficulty the difficulty played for the score
	 * @param mode the mode that this Score belongs to
	 * @pre mode is a valid mode value, difficulty is a valid difficulty
	 */
	public Score(int mode, int difficulty, String name, int score){
		myScore = score;
		myName = name;
		myDifficulty = difficulty;
		myMode = mode;
	}
	
	/**
	 * Retrieves the number score in this <code>Score</code>
	 * @return the number score in this <code>Score</code>
	 */
	public int getScore(){
		return myScore;
	}
	
	/**
	 * Retrieves the name of the user that made this <code>Score</code>
	 * @return the name of the user that made this <code>Score</code>
	 */
	public String getName(){
		return myName;
	}
	
	/**
	 * Retrieves the difficulty that this Score was achieved on
	 * @return the difficulty level of this Score
	 */
	public int getDifficulty(){
		return myDifficulty;
	}
	
	/**
	 * Retrieves the mode that this Score was achieved on
	 * @return the mode this score was achieved on
	 */
	public int getMode(){
		return myMode;
	}
	
	/**
	 * Returns a string representation of this Score in
	 * the format of [mode],[difficulty],[user],[score]
	 */
	public String toString(){
		String answer = "";
		answer += String.valueOf(myMode) + ",";
		answer += String.valueOf(myDifficulty) + ",";
		answer += myName + ",";
		answer += String.valueOf(myScore);
		return answer;
	}
	
}
