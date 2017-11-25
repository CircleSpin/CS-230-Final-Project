/* CS230 final project--Choose your own adventure
 * File: Story panel GUI object
 * Written: Sadie Thompson
 * Modified: Jingyao Liu
 */

//creates a panel object for the main GUI that contains the story content and the 
//two buttons that advance the choices

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class StoryPanel extends JPanel{
  //loads of instance variables
  private JLabel text;
  private StoryTree story;
  
  private JPanel choicesPanel;
  private Player player;
  
  private JLabel opt1;
  private JLabel opt2;
  private String grabText;
  private JLabel choices;
  
  private JButton choose1, choose2;
  
  //constructor takes in a story tree and a player and returns the tabbed pane of the 
  //relevant GUI elements
  
  public StoryPanel(StoryTree s, Player p){
    //variables needed later
    player= p;
    grabText="";
    story= s;
    
    //creating the UI elements
    JTabbedPane pane= new JTabbedPane();
    JPanel panel= new JPanel();
    choicesPanel= choicesPanel();
    
    text= new JLabel(story.showCurrent(),JLabel.CENTER);
    opt1= new JLabel(story.getOpt1(), JLabel.CENTER);
    opt2= new JLabel(story.getOpt2(), JLabel.CENTER);
    
    choose1= new JButton("Choose option 1");
    choose2= new JButton("Choose option 2");
    
    choose1.addActionListener(new ButtonListener());
    choose2.addActionListener(new ButtonListener());
    
    panel.setLayout(new BorderLayout());
    panel.add(text, BorderLayout.NORTH);
    
    //making option panel
    JPanel optionPanel= new JPanel();
    
    optionPanel.setLayout(new BorderLayout());
    optionPanel.add(opt1, BorderLayout.CENTER);
    optionPanel.add(opt2, BorderLayout.SOUTH);
    
    panel.add(optionPanel);
    
    //making choices panel
    JPanel choices= new JPanel();
    choices.add(choose1);
    choices.add(choose2);
    panel.add(choices,BorderLayout.SOUTH);
    
    pane.addTab("Story", panel);
    pane.addTab("Choices Made", choicesPanel());
    
    this.add(pane);
  }
  
    //resets story panel related variables to reflect choices
    //made while loading
  
  public void updateStoryPanel() {
    text.setText(story.showCurrent());
    opt1.setText(story.getOpt1());
    opt2.setText(story.getOpt2());
    choose1.setEnabled(true);
    choose2.setEnabled(true);
  }
  
  private JPanel choicesPanel(){
    //creates a panel for choice history, mostly to hold the choices label
    
    JPanel p= new JPanel();
    choices= new JLabel("<html> choices will appear here: <br> </html>");
    p.add(choices);
    return p;
  }
  
  //getters for everything
  
  public JLabel getOpt1() {
    return opt1;
  }
  
  public JLabel getOpt2() {
    return opt2;
  }
  
  public String getGrabText() {
    return grabText;
  }
  
  public JLabel getChoices() {
    return choices;
  }
  
  public JButton getChoose1() {
    return choose1;
  }
  
  public JButton getChoose2() {
    return choose2;
  }
  
  //button listeners for the two choice buttons, advance the story according to the button
  //pressed by the user
  
  private class ButtonListener implements ActionListener{
    String s;
    public void actionPerformed(ActionEvent e){
      //checks to see which button was pressed in addition to if the story tree is at a leaf
      if(e.getSource()== choose1 && !story.isEnd()){
        s=player.advance("left");
        
        //gets the appropriate text to add to choices history and adds it
        grabText= story.getShortString();
        String prevChoices= choices.getText();
        choices.setText(prevChoices.substring(0, prevChoices.length()-6) + grabText + "<br> </html>");
        
        //updates option labels
        opt1.setText(story.getOpt1());
        opt2.setText(story.getOpt2());
      }
      
      if(e.getSource()== choose2 && !story.isEnd()){
        //same as before
        s=player.advance("right");
        
        grabText= story.getShortString();
        String prevChoices= choices.getText();
        choices.setText(prevChoices.substring(0, prevChoices.length()-6) + grabText + "<br> </html>");
        
        opt1.setText(story.getOpt1());
        opt2.setText(story.getOpt2());
        
      }
      if(story.isEnd()){
        //disables parts of the UI when the game has finished
        s=story.showCurrent();
        choose1.setEnabled(false);
        choose2.setEnabled(false);
        opt1.setText("");
        opt2.setText("You've completed your Wellesley adventure!");
        
      }
      
      text.setText(s);
      
    }
  }
}