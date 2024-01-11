/*

* Stone Yang
* January 13th 2019
* Final Project: Pacman Game
* ICS3U7

*/
//This program is modeled after the infamous game of pac-man

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

public class Main extends JFrame implements ActionListener, KeyListener{
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();		
	public static int FRAME_HEIGHT = 600;
	public static int FRAME_WIDTH = 1000;
	public static Main frame = new Main();
	public static Game gameInterface = new Game();	
	public static Menu menuInterface = new Menu();
	public static Timer timer;
	public static JPanel panels;
	public static int JframeX, JframeY;

    public Main() {
		super("Pac-man");
		timer = new Timer(175, this);
		timer.start();
		addKeyListener(this);
    }
	
	public static void main(String[] args) {
		ImageIcon frameIcon = new ImageIcon("Icon.png");

        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setIconImage(frameIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		//Add jpanels to Jframe
		panels = new JPanel(new CardLayout());
		panels.add(menuInterface, "Menu Panel");
		panels.add(gameInterface, "Game Panel");		
			
		frame.setContentPane(panels);		
			
		//Sets position of Jframe
        JframeX = (dim.width-FRAME_WIDTH)/2;
        JframeY = (dim.height-FRAME_HEIGHT)/2;
        frame.setLocation(JframeX, JframeY);
		
		menuInterface.run();
	}
  
	public void keyTyped(KeyEvent e) {
    }
	
    public void keyPressed(KeyEvent e) {
	}
	
    public void keyReleased(KeyEvent e) {
    }
	
	public void paint(Graphics g) {
		super.paint(g);		
	}
	
	public void actionPerformed(ActionEvent ev){
		if(menuInterface.gameStart){//Game is accessed form menu
			gameInterface.restart();
			CardLayout cl = (CardLayout)panels.getLayout();
			cl.next(panels);
			gameInterface.requestFocus();
			gameInterface.setFocusable(true);
			gameInterface.score = 0;
			gameInterface.gameOver = false;
			menuInterface.gameStart = false;
		}		
		if(gameInterface.menuScreen){//Menu is accessed from game
			CardLayout cl = (CardLayout)panels.getLayout();
			cl.next(panels);
			menuInterface.updatePelletsEaten(gameInterface.numOfPelletsEaten);			
			menuInterface.updateGhostsEaten(gameInterface.numOfGhostsEaten);
			if(gameInterface.score > menuInterface.getHighScore())
				menuInterface.updateHighScore(gameInterface.score);
			menuInterface.run();
			gameInterface.menuScreen = false;
		}
		repaint();
	}
}