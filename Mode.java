import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Superclass for all game modes that defines
 * what each mode needs at a minimum
 * @author Ben Sieben
 * @version 23 May 2013
 */
public class Mode extends JPanel{
	
	/**
	 * Number that represents the difficulty should be
	 * short words
	 */
	public static final int SHORT_DIF = 0;
	/**
	 * Number that represents the difficulty should
	 * be medium length words
	 */
	public static final int MED_DIF = 1;
	/**
	 * Number that represents the difficulty should 
	 * be longer words
	 */
	public static final int LONG_DIF = 2;
	
	private int width, height, difficulty;
	private TypeGame myFrame;
	
	public Mode(TypeGame w){
		super();
		difficulty = 0;
		width = getWidth();
		height = getHeight();
		myFrame = w;
		requestFocus();
	}
	
	/**
	 * Returns the TypeGame this Mode is associated with
	 * @return the TypeGame this Mode is associated with
	 */
	public TypeGame getFrame(){
		return myFrame;
	}
	
	/**
	 * Changes the difficulty of the Mode
	 * @param i the difficulty to change this Mode to
	 */
	public void setDifficulty(int i){
		difficulty = i;
	}
	
	/**
	 * Returns the current difficulty setting of this Mode
	 * @return the current difficulty setting
	 */
	public int getDifficulty(){
		return difficulty;
	}
	
	/**
	 * Submits a new Score to the Leaderboards
	 * by taking in arguments 
	 * @param points numerical score earned in the game played
	 * @param dif the difficulty of the game played
	 * @param mode the mode of the game played
	 */
	public void submitScore(int points,int dif,int mode){
		myFrame.firstPanel();
		Object o = JOptionPane.showInputDialog(null,"Please enter your username without commas \nor semicolons (or else name is defaulted)\n("+points+" points)",
				"Add Score to Leaderboards",JOptionPane.QUESTION_MESSAGE);
		if(o == null){//saving score cancelled
			JOptionPane.showMessageDialog(null,"Saving score to leaderboards was cancelled.",
					"Adding Score to Leaderboards Cancelled",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String s = (String)o;
		if(s.equals("")) s = "default";
		s = s.replaceAll(";", "").replaceAll(",", ""); //remove bad characters
		if(s.equals("")) { // if the user entered nothing set their username to default
			s = "default";
		}
		if(s.length() >= 20){
			s = "default";
		}
		Score score = new Score(mode,dif,s,points);
		myFrame.addScore(score);
	}

}
