/* CS230 final project--Choose your own adventure
 * File: story node class
 * Written: Sadie Thompson
 */

//node class, contains different elements needed for accessing in other files.
//not all methods/fields are used but were added in for possible future implementation

public class StoryTreeNode{
  String content;
  Boolean visited;
  String shortString;
  String opt1;
  String opt2;
  
  public StoryTreeNode(){
    content= "nothing here";
    visited= false;
  }
  
  //alternate constructor if needed
  public StoryTreeNode(String s){
    content= s;
    visited= false;
  }
  //getters and setters for different content
  public String getShortText(){
    return shortString;
  }
  
  public void setShortText(String s){
    shortString=s;
  }
  
  public String getOpt1(){
    return opt1;
  }
  
  public void setOpt1(String s){
    opt1=s;
  }
  
  public String getOpt2(){
    return opt2;
  }
  
  public void setOpt2(String s){
    opt2=s;
  }
  
  public String toString(){
    return this.content;
  }
  
  //not used but possibly useful in the future, sets the visited boolean
  //to true where it is set by default to false
  public void visit(){
    this.visited=true;
  }
}