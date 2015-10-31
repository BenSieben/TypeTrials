import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Class the has what every 
 * leaderboard for TypeTrials needs
 * and has
 * @author Ben Sieben
 * @version 12 May 2013
 */
public class Leaderboard extends JPanel{
	
	private Score[] myScores;
	private int width, height, addedScores;
	private double ratioX, ratioY;
	private String tag;
	
	/**
	 * Constructs a new Leaderboard
	 * @param tag the tag to add to the beginning of 
	 * the String returned when turning the Leaderboard
	 * data to a String with toString() (acts as an identifier)
	 */
	public Leaderboard(String tag){
		super();
		this.tag = tag;
		myScores = new Score[7];
		addedScores = 0;
		width = getWidth();
		height = getHeight();
		ratioX = 1;
		ratioY = 1;
	}
	
	/**
	 * Adds a new Score to this Leaderboard
	 * @param s the new Score to add to this Leaderboard
	 */
	public void addScore(Score s){
		if(addedScores < 7){//always add score if array is not full yet
			myScores[addedScores] = s;
		} else if(myScores[6].getScore() < s.getScore()){//check if score is high enough to make the array
			myScores[6] = s;
		}
		addedScores++;
		sortByDescending();
		repaint();
	}
	
	/**
	 * Paints the Leaderboard
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		width = getWidth();
		height = getHeight();
		ratioX = width/500.0;
		ratioY = height/500.0;
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		Font f = new Font("Dialog",Font.ITALIC,(int)(30*ratioX));
		g.setFont(f);
		g.drawString("Position",(int)(ratioX*10),(int)(ratioY*60));
		g.drawString("Name",(int)(ratioX*150),(int)(ratioY*60));
		g.drawString("Points",(int)(ratioX*400),(int)(ratioY*60));
		f = new Font("Dialog",Font.PLAIN,(int)(20*ratioX));
		g.setFont(f);
		for(int i = 0; i < myScores.length && i < addedScores; i++){
			g.drawString((i+1)+"",(int)(ratioX*10),(int)((i+2)*ratioY*60)); //draw position
			g.drawString(myScores[i].getName(),(int)(ratioX*150),(int)((i+2)*ratioY*60));//draw user name
			g.drawString(myScores[i].getScore()+"",(int)(ratioX*400),(int)((i+2)*ratioY*60));//draw points
		}
	}
	
	/**
	 * Sorts the Leaderboard Scores by descending order 
	 * (larger Scores go in front). Use this when wanting 
	 * to sort the list of Scores
	 */
	public void sortByDescending(){
		for(int i = 0; i < myScores.length-1 && i < addedScores-1; i++){
			Score original = myScores[i];
			int swapIndex = i;
			for(int j = i+1; j < myScores.length && j < addedScores; j++){
				if(original.getScore() < myScores[j].getScore()){ //original <= testing
					original = myScores[j];
					swapIndex = j;
				}
				if(addedScores < myScores.length){
					if(j == addedScores-1 && i != swapIndex){
						swap(myScores,i,swapIndex);
					}
				}else if(j == myScores.length-1 && i != swapIndex){
					swap(myScores,i,swapIndex);
				}
			}
		}
	}
	
	/**
	   *  Interchanges two elements in an ArrayList
	   *
	   * @param  list  reference to an array of Scores
	   * @param  a     index of Score to be swapped
	   * @param  b     index of Score to be swapped
	   */
	  public void swap(Score[] list, int a, int b){
		Score temp = list[a];
		list[a] = list[b];
		list[b] = temp;
	  }
	  
	  /**
	   * Prints the Leaderboard out to a 
	   * String object
	   */
	  public String toString(){
		  String s = tag;
		  for(int i = 0; i < myScores.length && i < addedScores; i++){
			  s += myScores[i].toString() + ";";
		  }
		  return s;
	  }
	  
	  /**
	   * Resets this Leaderboard to be empty
	   */
	  public void reset(){
		  myScores = new Score[7];
		  addedScores = 0;
		  repaint();
	  }
	
}
