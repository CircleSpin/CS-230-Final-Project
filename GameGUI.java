/* CS230 final project--Choose your own adventure
 * File: game GUI
 * Written: Jocelyn Shiue
 * Modified: Sadie Thompson and Jingyao Liu
 */

//this file exists as the driver class of our program in addition to creating the main GUI

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GameGUI extends JPanel{
  //instance vars
  Button save, load;
  JLabel message; //tells whether successfully loaded or saved
  String txtmsg = ""; //the actual string that contains the message
  Player player;
  StoryPanel storyPanel;
  StoryTree story;
  
//creates the whole game using tree and player objects
  public GameGUI(StoryTree s, Player p){
    player=p;
    
    story=s;
    JFrame frame = new JFrame();
    setLayout(new BorderLayout(0,0)); //hgap, vgap
    
    frame.add(makeNorthPanel(), BorderLayout.NORTH);
    
    storyPanel= new StoryPanel(s, player);
    frame.add(storyPanel);
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    
  }
  
  /**
   * Creates and returns a JPanel. 
   * @return JPanel the top portion of the GUI
   */
  private JPanel makeNorthPanel() {
    
    //creating the JPanel and setting Layout
    JPanel northPanel = new JPanel();
    northPanel.setLayout(new BorderLayout(0,0)); //Create a borderlayout within northpanel
    
    //Making the title with text at center
    JLabel l1 = new JLabel("Choose your own Adventure Wellesley Edition", JLabel.CENTER);
    northPanel.add(l1,BorderLayout.NORTH);
    
    //Save Button with listener
    save = new Button("Save");
    northPanel.add(save, BorderLayout.WEST);
    save.addActionListener(new ButtonListener());
    
    //Load button with listener
    load = new Button("Load");
    northPanel.add(load, BorderLayout.EAST);
    load.addActionListener(new ButtonListener());
    
    //Message in center 
    message = new JLabel(txtmsg, JLabel.CENTER);
    northPanel.add(message, BorderLayout.CENTER);
    
    northPanel.setBackground(Color.white);
    return northPanel;
    
  }
  
  
  //--------------------------------------------------------------------
  // Listener for buttons
  //--------------------------------------------------------------------
  private class ButtonListener implements ActionListener {
    public void actionPerformed (ActionEvent event)
    {
      if (event.getSource() == load){
        player.load(storyPanel.getChoices());
        storyPanel.updateStoryPanel();
        txtmsg = "Successfully Loaded";
        message.setText(txtmsg);
        
        if(story.isEnd()){
          //from storyPanel
          storyPanel.getChoose1().setEnabled(false);
          storyPanel.getChoose2().setEnabled(false);
          storyPanel.getOpt1().setText("");
          storyPanel.getOpt2().setText("You've completed your Wellesley adventure!");
        }

      }else if (event.getSource() == save){
        player.save();
        txtmsg = "Successfully Saved";
        message.setText(txtmsg);

      } else{
        txtmsg = "Nothing Successfully Done";
      }
    }
    
  }
  
  public static void main (String [] args){
    StoryTree t = new StoryTree("final story.txt");
    GameGUI play = new GameGUI(t, new Player(t));
    
    
  }
}