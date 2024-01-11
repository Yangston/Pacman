import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

public class Menu extends JPanel implements ActionListener{
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	
	public static int PANEL_HEIGHT = 600;
	public static int PANEL_WIDTH = 1000;	
	public static Timer timer;
	public static JButton buttons[] = new JButton[4];
	public static boolean gameStart = false, showHelp = false, showScore = false, showMenu = true;
	private int animCounter = 0, redCounter, greenCounter, blueCounter;
	private static ImageIcon menuIcons[] = new ImageIcon[4];
	private static Point mousePos;
	private static int highScore, pelletsEaten, ghostsEaten;

    public Menu() {
		timer = new Timer(10, this);	
		mousePos = MouseInfo.getPointerInfo().getLocation();
	
		menuIcons[0] = new ImageIcon("Help.png");		
		menuIcons[1] = new ImageIcon("Play.png");		
		menuIcons[2] = new ImageIcon("Scoreboard.png");		
		menuIcons[3] = new ImageIcon("Menu.png");
		
		for(int i = 0; i < menuIcons.length; i++){
			Image img = menuIcons[i].getImage();
			Image newImg = img.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
			menuIcons[i] = new ImageIcon(newImg); 
			buttons[i] = new JButton(menuIcons[i]);
		}

		this.setLayout(null);
		
		for(int i = 0; i < buttons.length; i++){
			buttons[i].setBounds(380+i*150,314,90,90);
		}
		
		for(int i = 0; i < buttons.length; i++){
			buttons[i].addActionListener(this);
			buttons[i].setOpaque(false);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorderPainted(false);
			buttons[i].setFocusPainted(false);
			this.add(buttons[i]);
		}
    }
	
	public void run(){			
		timer.start();	
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon titleImage = new ImageIcon("Menu Image " + (Integer.toString(animCounter/50%3+1)) + ".png");
		ImageIcon menuPicture1 = new ImageIcon("Menu Picture.jpg");		
		ImageIcon menuPicture2 = new ImageIcon("Menu Picture 2.png");
		ImageIcon menuBackground = new ImageIcon("Menu Background.png");
		ImageIcon ring = new ImageIcon("Fire Ring.png");
		
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

		if(animCounter%4 == 0){
			redCounter=(redCounter+1)%255;		
			greenCounter=(greenCounter+5)%255;
			blueCounter=(blueCounter+2)%255;
		}
		
		for(int i = 0; i < 4; i ++){
			g.setColor(new Color(redCounter, greenCounter, blueCounter));
			g.fillOval(380+i*150,314,90,90);
		}	
		for(int i = 0; i < 4; i ++){
			if((int)(mousePos.x-(dim.width-PANEL_WIDTH)/2.0) > (385+i*150) && (int)(mousePos.x-(dim.width-PANEL_WIDTH)/2.0) < (480+i*150) && (int)(mousePos.y-(dim.height-PANEL_HEIGHT)/2.0) > 350 && (int)(mousePos.y-(dim.height-PANEL_HEIGHT)/2.0) < 440){
				g.drawImage(ring.getImage(), (370+i*150), 304, 110, 110, null);
			}
		}
		
		g.drawImage(menuPicture1.getImage(), -110, -60, PANEL_WIDTH/2, PANEL_HEIGHT/2, null);			
		g.drawImage(menuPicture2.getImage(), 600, -60, PANEL_WIDTH/2, PANEL_HEIGHT/2, null);			
		g.drawImage(titleImage.getImage(), 282, -50, 421, 306, null);
		
		if(showHelp){//Help screen
			g.setColor(new Color(255, 255, 255));
			g.fillRect(40, 210, 300, 300);
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("SansSerif: ", 1, 24));
			g.drawString("--> Use the arrow keys or", 35, 260);	
			g.drawString("     the wasd keys to move!", 35, 290);	
			g.drawString("--> Try to eat all the pellets", 35, 350);				
			g.drawString("     and avoid the ghosts!", 35, 380);						
			g.drawString("--> Eat the large pellets to",  35, 440);				
			g.drawString("     eat the ghosts!",  35, 470);	
			
			for (int i = 0; i< 3; i++) 
				g.drawRect(25+i, 230+i, 330-2*i, 255-2*i);
		}		
		
		if(showScore){//Score screen
			g.setColor(new Color(255, 255, 255));
			g.fillRect(40, 210, 300, 300);
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("SansSerif: ", 1, 24));
			g.drawString("Highscore: " + Integer.toString(highScore), 35, 260);	
			g.drawString("Pellets eaten: " + Integer.toString(pelletsEaten), 35, 350);
			g.drawString("Ghosts eaten: " + Integer.toString(ghostsEaten), 35, 440);				
			
			for (int i = 0; i< 3; i++) 
				g.drawRect(25+i, 230+i, 330-2*i, 255-2*i);
		}
		
		if(showMenu)
			g.drawImage(menuBackground.getImage(), 40, 210, 300, 300, null);	
	}
	
	public void actionPerformed(ActionEvent ev){	
		mousePos = MouseInfo.getPointerInfo().getLocation();
		buttonChecker(ev);
			    
		animCounter++;
		repaint();
	}
	
	public static void buttonChecker(ActionEvent ev){
		if(ev.getSource() == buttons[0]){
			showHelp = true;		
			showScore = false;	
			showMenu = false;
		}
		else if(ev.getSource() == buttons[1]){
			gameStart = true;	
			showScore = false;
			showHelp = false;	
			showMenu = true;
		}
		else if(ev.getSource() == buttons[2]){
			showScore = true;
			showHelp = false;	
			showMenu = false;
		}		
		else if(ev.getSource() == buttons[3]){
			showMenu = true;
			showScore = false;
			showHelp = false;	
		}
	}
	
	public static void updatePelletsEaten(int numOfPellets){
		pelletsEaten += numOfPellets;
	}	
	
	public static void updateGhostsEaten(int numOfGhosts){
		ghostsEaten += numOfGhosts;
	}
	
	public static void updateHighScore(int score){
		highScore = score;
	}
	
	public static int getHighScore(){
		return highScore;
	}
}