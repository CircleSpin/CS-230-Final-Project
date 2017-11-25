/* CS230 final project--Choose your own adventure
 * File: player class
 * Written: Jingyao Liu
 */

/*Player class: Contains the queue of the decisions made, takes in a tree 
 * Saves by popping the queue/linkedlist into a text file that can be read later by load. 
 * The load option reads from a text file and then 
 * pops the choices made back into a queue. The playing continues! :D
 */


import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class Player {
  private LinkedList<String> choicesMade;
  private StoryTree tree;
  
  public Player(StoryTree st) {
    choicesMade = new LinkedList<String>();
    tree = st;
  }
  
  //brings the advance method up from the story tree object inputted in its creation
  
  public String advance(String direction) {
    //adds left or right to linked list that holds choices made and advances tree
    choicesMade.add(direction);
    return tree.advance(direction);
  }
  
  //returns the list of left and right traversals of the tree that the player has made
  
  public LinkedList getChoicesMade() {
    return choicesMade;
  }
  
  //saves the choices the player made into a text file, to be accessed later by the load method
  //only one save available to the player
  public void save() {
    //dequeues from linkedlist to write into a text file
    try{      
      PrintWriter writer = new PrintWriter (new File("playerChoices.txt"));
      LinkedList<String> temp= new LinkedList<String>();
      
      while (!choicesMade.isEmpty()) {
        String first= choicesMade.removeFirst();
        temp.add(first);
        writer.println(first);         
      }
      writer.close();
      choicesMade=temp;
    }catch (IOException ex) {
      System.out.println(ex);
    }
    
  }
  
  //loads the choices the player made and advances the game accordingly,
  //JLabel input is used to display the choices as the game loads
  public void load(JLabel l) {
    try {
      //first resets the tree to work with a blank slate and
      //empties the choicesmade linkedList
      tree.startOver();
      choicesMade = new LinkedList<String>();
      
      Scanner fileScan = new Scanner (new File("playerChoices.txt"));
      
      //sets the text of the choices label to be its default state
      l.setText("<html> choices will appear here: <br> </html>");
      while (fileScan.hasNext()) {
        //reads from file and advances the tree every line it encounters, updates choices made
        //and updates the choice history tab each loop
        String line = fileScan.nextLine();
        
        if (line.equals("left")) {
          choicesMade.add("left");
          tree.advance("left");
        }
        else if (line.equals("right")) {
          choicesMade.add("right");
          tree.advance("right");
        }
        
        String grabText= tree.getShortString();
        String prevChoices= l.getText();
        System.out.println(prevChoices.substring(0, prevChoices.length()-6) + grabText + "<br> </html>");
        l.setText(prevChoices.substring(0, prevChoices.length()-6) + grabText + "<br> </html>");
      }   
      fileScan.close();
    } catch (IOException ex) {
        System.out.println("Saved file does not exist.");
      }
  }
}