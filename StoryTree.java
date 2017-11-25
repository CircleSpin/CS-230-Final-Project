/* CS230 final project--Choose your own adventure
 * File: story tree creator
 * Written: Sadie Thompson
 */

//creates story tree objects from inputted text files

import java.util.*;
import java.io.*;

public class StoryTree{
  private Vector<StoryTreeNode> storyHolder;
  private StoryTreeNode currentNode;
  private int currentIndex;
  private String filename;
  
  public StoryTree(String file){
    filename=file;
    storyHolder= new Vector<StoryTreeNode>();
    try{
      Scanner fileReader= new Scanner(new File(filename));
      
      String storyBit= "";
      String shortString="";
      String opt1="";
      String opt2="";
      
      /*how to format story text files:
       * begin with long version of the text
       * after long version have the options on new lines
       * preceeded by 1. for the first option and
       * 2. for the second (first goes to left, second goes to right)
       * 
       * then use --- to denote a shortened version of the choice that led to
       * the current one, on each line it occurs
       * end the story bit with ### to denote the end of the 
       * 
       * 
       * EXAMPLE:
       * ###
       * The professor hands you your midterm paper back, a quick glance
       * tells you that you didn't do so well.
       * 
       * 1. look down to see the actual score before doing anything rash
       * 2. hand the test back to the professor and say "no thanks i'm good"
       * 
       * ---You chose to go to class today!
       * ###
       */
      
      while(fileReader.hasNext()){
        String line= fileReader.nextLine();
        
        
        if(line.contains("---")){
          shortString += line;
        }
        else if(line.contains("1.")){
          opt1 += line;
        }
        else if(line.contains("2.")){
          opt2 += line;
        }
        //order of else if statements might make things a little dicey to read, hopefully it works out
        //but this is where the problem might lie
        else if(!line.contains("###")){
          storyBit += line;
        }
        else if(line.contains("###")){
          StoryTreeNode s= new StoryTreeNode(storyBit);
          s.setShortText(shortString);
          s.setOpt1(opt1);
          s.setOpt2(opt2);
          
          storyHolder.add(s);
          storyBit = opt1 = opt2 = shortString ="";
        }
      }
      fileReader.close();
      this.startOver();
      
    }catch(IOException e){
      System.out.println("cannot create story tree from this file \n" + e);
    }
  }
  
  //getters for things we need later
  public String showCurrent(){
    return currentNode.toString();
  }
  
   public int getCurrentIndex(){
    return currentIndex;
  }
  
  public StoryTreeNode getCurrentNode(){
    return currentNode;
  }
  
  public String getOpt1(){
    return currentNode.getOpt1();
  }
  
  public String getOpt2(){
    return currentNode.getOpt2();
  }
  
  //when choose1/choose2 button is selected, calculates position of next
  //node based on string input and updates variables accordingly
    
  public String advance(String direction){

    try{
      StoryTreeNode pastNode;
      if(direction.equals("left")){
        pastNode=currentNode;
        pastNode.visit();
        int nextIndex= currentIndex *2 +1;
        currentNode= storyHolder.get(nextIndex);
        currentIndex= nextIndex;
        
        return currentNode.toString();
      }
      else{
        pastNode=currentNode;
        pastNode.visit();
        int nextIndex= currentIndex *2 +2;
        currentNode= storyHolder.get(nextIndex);
        currentIndex= nextIndex;
        
        return currentNode.toString();
      }
    }
    catch(ArrayIndexOutOfBoundsException e){
      return "You've completed the Wellesley adventure!";
    }
  }
  
  //returns the content of the current node: i.e. the story text
  public String toString(){
    return (this.filename + ": " + currentNode.toString());
  }
  
  //brings the tree to its base state, setting the current node to the current
  //index at 0
  
  public void startOver(){
    //resets the tree
    currentIndex=0;
    currentNode= storyHolder.get(0);
  }
  
  //returns the shortened version of the story choice that led to the story
  //of the current node
  
  public String getShortString(){
    return this.currentNode.shortString;
  }
  
  //tests if the current node is a leaf
  
  public boolean isEnd(){

    
    if(currentIndex *2 +1 >= storyHolder.size()){
      return true;
    }
    return false;
  }
  public static void main(String[] args){
    //testing
    StoryTree s= new StoryTree("story tree text.txt");
    System.out.println(s.toString());
    System.out.println(s.showCurrent());
    System.out.println(s.advance("left"));
    System.out.println(s.showCurrent());
    System.out.println(s.advance("right"));
  }
}