import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener{
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	
	private static int PANEL_HEIGHT = 600;
	private static int PANEL_WIDTH = 1000;	
	private static int MAP_HEIGHT = 540;
	private static int MAP_WIDTH = 960;
	private static Timer timer;
	private static JButton retryBtn, menuBtn;
	private static ImageIcon retryImg, menuImg;
	private static Point mousePos;
	public static Sprite pacman;
	public static Ghost ghosts[] = new Ghost[6];
	public static PathTile pathTiles[] = new PathTile[342];
	public static int[][] ptMap = {
								   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								   {0,3,4,5,6,7,0,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,0,28,29,30,31,32,0},
								   {0,33,0,0,0,34,0,35,0,0,0,0,0,36,0,0,0,0,0,0,37,0,0,0,0,0,38,0,39,0,0,0,40,0},
								   {0,41,42,43,0,44,0,45,46,47,48,49,50,51,52,0,0,0,0,53,54,55,56,57,58,59,60,0,61,0,62,63,64,0},
								   {0,65,0,66,0,67,0,68,0,0,0,0,0,0,69,70,71,72,73,74,0,0,0,0,0,0,75,0,76,0,77,0,78,0},
								   {0,79,0,80,0,81,82,83,84,85,86,87,88,0,89,0,0,90,0,91,0,92,93,94,95,96,97,98,99,0,100,0,101,0},
								   {0,102,103,104,0,105,0,0,0,0,0,0,106,0,107,0,108,109,0,110,0,111,0,0,0,0,0,0,112,0,113,114,115,0},
								   {0,0,0,116,117,118,119,120,121,122,123,0,124,125,126,0,127,0,0,128,129,130,0,131,132,133,134,135,136,137,138,0,0,0},
								   {0,0,0,0,0,0,0,0,0,0,139,0,140,0,0,0,141,142,0,0,0,143,0,144,0,0,0,0,0,0,0,0,0,0},
								   {145,146,147,148,149,150,151,152,153,154,155,0,156,157,158,0,0,159,0,160,161,162,0,163,164,165,166,167,168,169,170,171,172,173},
								   {0,0,0,0,0,0,174,0,0,0,0,0,175,0,176,177,178,179,180,181,0,182,0,0,0,0,0,183,0,0,0,0,0,0},
								   {0,184,185,186,187,0,188,189,190,191,192,193,194,0,195,0,196,0,0,197,0,198,199,200,201,202,203,204,0,205,206,207,208,0},
								   {0,209,0,0,210,0,211,0,0,0,212,0,0,0,213,0,214,215,0,216,0,0,0,217,0,0,0,218,0,219,0,0,220,0},
								   {0,221,222,0,223,0,224,0,225,226,227,228,229,0,230,0,0,231,0,232,0,233,234,235,236,237,0,238,0,239,0,240,241,0},
								   {0,0,0,0,242,0,243,0,244,0,0,0,245,0,246,0,247,248,0,249,0,250,0,0,0,251,0,252,0,253,0,0,0,0},
								   {0,254,255,256,257,258,259,260,261,262,263,264,265,266,267,0,268,0,0,269,270,271,272,273,274,275,276,277,278,279,280,281,282,0},
								   {0,283,0,0,0,284,0,285,0,0,0,0,0,286,0,287,288,289,290,0,291,0,0,0,0,0,292,0,293,0,0,0,294,0},
								   {0,295,0,296,0,297,0,298,0,299,0,300,0,301,0,302,0,0,303,0,304,0,305,0,306,0,307,0,308,0,309,0,310,0},	
								   {0,311,312,313,314,315,0,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,0,336,337,338,339,340,0},
								   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,341,342,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								  };
	public static int[][] ghostPos = { {433, 255}, {523, 255}, {523, 315},  {433, 315}, {373,285}, {583,285}};
	public static boolean gameExit = false, gameOver = false, menuScreen = false, lost = true, moveExecuted = true, frightnedGhosts = false, winRound = false;
	public static int randBg, counter, score, numOfPelletsEaten, moveSequence, frightenCounter, overlapGhost, numOfGhostsEaten;

    public Game() {
		timer = new Timer(10, this);	
		mousePos = MouseInfo.getPointerInfo().getLocation();
		addKeyListener(this);
		
		retryImg = new ImageIcon("Retry Button.png");		
		menuImg = new ImageIcon("Menu Button.png");	
		
		Image img1 = retryImg.getImage();
		Image newImg1 = img1.getScaledInstance(203, 75,  java.awt.Image.SCALE_SMOOTH);
		retryImg = new ImageIcon(newImg1); 
		retryBtn = new JButton(retryImg);		
		
		Image img2 = menuImg.getImage();
		Image newImg2 = img2.getScaledInstance(203, 75,  java.awt.Image.SCALE_SMOOTH);
		menuImg = new ImageIcon(newImg2); 
		menuBtn = new JButton(menuImg);
		
		this.setLayout(null);
		
		retryBtn.setBounds(260,350,203,75);
		menuBtn.setBounds(530,350,203,75);
		
		retryBtn.addActionListener(this);
		retryBtn.setOpaque(false);
		retryBtn.setContentAreaFilled(false);
		retryBtn.setBorderPainted(false);
		retryBtn.setFocusPainted(false);
		this.add(retryBtn);		

		menuBtn.addActionListener(this);
		menuBtn.setOpaque(false);
		menuBtn.setContentAreaFilled(false);
		menuBtn.setBorderPainted(false);
		menuBtn.setFocusPainted(false);
		this.add(menuBtn);		
    }
	
	public static void run(){		
		retryBtn.setVisible(false);
		menuBtn.setVisible(false);
		randBg = (int)(Math.random()*7+1);
		counter = 0;
		
		pacman = new Sprite();
		
		Sprite.mapSet(ptMap);
		for(int j = 0; j < 20; j++){//Sets up map
			for(int i = 0; i < ptMap[j].length; i++){
				if(ptMap[j][i] != 0){
					pathTiles[counter] = new PathTile();		
					pathTiles[counter].x1 = (i-1)*PathTile.size+10;
					pathTiles[counter].y1 = j*PathTile.size+10-PathTile.size; 
					pathTiles[counter].pelletX = (i-1)*PathTile.size+23; 
					pathTiles[counter].pelletY = j*PathTile.size+23-PathTile.size;
					pathTiles[counter].pelletSize = 4;
					if(counter == 177 || counter == 0 || counter == 1 || counter == 340 || counter == 341 || counter == 144 || counter == 172)
						pathTiles[counter].pelletEaten = true;
					if(counter == 42 || counter == 61 || counter == 295 || counter == 308)
						pathTiles[counter].spawnSuperPellet((i-1)*PathTile.size+20, j*PathTile.size+20-PathTile.size);
					counter++;
				}
			}		
		}
		Sprite.pathTilesSet(pathTiles);
		for(int i = 0; i < ghosts.length; i ++){
			ghosts[i] = new Ghost(ghostPos[i][0], ghostPos[i][1], 0+i*50, 50+i*50, 26);
		}
		Ghost.ghostSet(ghosts); 
		timer.start();	
	}
  
	public void keyTyped(KeyEvent e) {
    }
	
    public void keyPressed(KeyEvent e) {	
		if(!gameOver){//Detects key presses
			if(e.getKeyCode() =='w' || e.getKeyCode() =='W' || e.getKeyCode() == KeyEvent.VK_UP){
				pacman.turnDirection = "up";
				pacman.turnPoint = pacman.movementChecker(pacman.turnDirection);
			}
			else if(e.getKeyCode() =='a' || e.getKeyCode() =='A' || e.getKeyCode() == KeyEvent.VK_LEFT){
				pacman.turnDirection = "left";
				pacman.turnPoint = pacman.movementChecker(pacman.turnDirection);
			}		 
			else if(e.getKeyCode() =='s' || e.getKeyCode() =='S' || e.getKeyCode() == KeyEvent.VK_DOWN){
				pacman.turnDirection = "down";
				pacman.turnPoint = pacman.movementChecker(pacman.turnDirection);
			}
			else if(e.getKeyCode() =='d' || e.getKeyCode() =='D' || e.getKeyCode() == KeyEvent.VK_RIGHT){
				pacman.turnDirection = "right";
				pacman.turnPoint = pacman.movementChecker(pacman.turnDirection);
			}
		}
	}
	
    public void keyReleased(KeyEvent e) {
    }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int[] rectColour = new int[3];
		ImageIcon animations = new ImageIcon("Pacman_Animations.png");
		ImageIcon endGame = new ImageIcon("Game Over.png");
		ImageIcon retryBtnGlow = new ImageIcon("Retry Button Glow.png");
		ImageIcon menuBtnGlow = new ImageIcon("Menu Button Glow.png");
		ImageIcon scoreImg = new ImageIcon("Score Image.png");
		
		
		for(int j = 0; j <= 50; j ++){//Generates random background gradients
			for(int i = 0; i <= 51; i ++){
				if(randBg%2 == 0){
					if(randBg == 6){
						g.setColor(new Color(100+i*3-j, 20+j*2, 255-i-j));
						rectColour[0] = 102;
						rectColour[1] = 255;
						rectColour[2] = 102;
					}
					if(randBg == 4){
						g.setColor(new Color(50+i*2+j,235-i-j, 100+i*3-j));
						rectColour[0] = 51;
						rectColour[1] = 255;
						rectColour[2] = 255;
					}
					if(randBg == 2){
						g.setColor(new Color(j,255-j*3, 100+i*2-j));
						rectColour[0] = 255;
						rectColour[1] = 102;
						rectColour[2] = 255;
					}
				}
				else{
					if(randBg == 1){
						g.setColor(new Color(255, 50+i*2-j/2, 56+i*3-j/2));
						rectColour[0] = 175;
						rectColour[1] = 175;
						rectColour[2] = 175;
					}					
					if(randBg == 3){
						g.setColor(new Color(0, 250-i*2-j/2, 56+i*3-j/2));
						rectColour[0] = 255;
						rectColour[1] = 102;
						rectColour[2] = 102;
					}					
					if(randBg == 5){
						g.setColor(new Color(30+i*3-j/2, 175-i*2-j/2, 255));
						rectColour[0] = 153;
						rectColour[1] = 255;
						rectColour[2] = 51;
					}					
					if(randBg == 7){
						g.setColor(new Color(180-i*3-j/2, 255-i/2, 0+j/2));
						rectColour[0] = 185;
						rectColour[1] = 125;
						rectColour[2] = 255;
					}		
				}					
				g.fillRect(i*(PANEL_WIDTH/50), j*(PANEL_HEIGHT/50), (PANEL_WIDTH/50), (PANEL_HEIGHT/50));
			}
		}
		
		for(int i =0; i <pathTiles.length; i++){//Paints path tiles
			g.setColor(new Color(0, 0, 0));
			g.fillRect(pathTiles[i].x1, pathTiles[i].y1, PathTile.size, PathTile.size);
			if(!pathTiles[i].pelletEaten){
				g.setColor(new Color(255, 255, 255));
				g.fillOval(pathTiles[i].pelletX, pathTiles[i].pelletY, pathTiles[i].pelletSize, pathTiles[i].pelletSize);
			}
			g.setColor(new Color(rectColour[0], rectColour[1], rectColour[2]));
			for (int j = 0; j < 2; j++) 
				g.drawRect(pathTiles[i].x1+j, pathTiles[i].y1+j, pathTiles[i].size-2*j, pathTiles[i].size-2*j);
		}
		
		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font("SansSerif: ", 0, 17));
		g.drawString("Score: "+Integer.toString(score), 448, 63);	
		g.drawRect(443, 70, 97, 2);
		
		if(!gameOver){
			//Ghosts
			for(int i = 0; i < ghosts.length; i ++){
				g.drawImage(animations.getImage(), ghosts[i].x1+3, ghosts[i].y1+1, ghosts[i].x2+3, ghosts[i].y2+1, ghosts[i].imgX1, ghosts[i].imgY1, ghosts[i].imgX2, ghosts[i].imgY2, null);
			}
			//Pacman
			g.drawImage(animations.getImage(), pacman.x1, pacman.y1, pacman.x2, pacman.y2, pacman.imgX1, pacman.imgY1, pacman.imgX2, pacman.imgY2, null);
		}
		else{//Paints game over screen
			g.drawImage(endGame.getImage(),310, 30, 356, 356, null);
			g.drawImage(retryBtnGlow.getImage(), 260, 350, 203, 75, null);	
			g.drawImage(menuBtnGlow.getImage(), 530, 350, 203, 75, null);
			g.drawImage(scoreImg.getImage(), 310, 425, 159, 70, null);
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("SansSerif: ", 1, 50));
			g.drawString(Integer.toString(score), 500, 480);
			if((int)(mousePos.x-(dim.width-PANEL_WIDTH)/2.0) > 270 && (int)(mousePos.x-(dim.width-PANEL_WIDTH)/2.0) < 470 && (int)(mousePos.y-(dim.height-PANEL_HEIGHT)/2.0) > 390 && (int)(mousePos.y-(dim.height-PANEL_HEIGHT)/2.0) < 430)
				retryBtn.setVisible(true);
			else
				retryBtn.setVisible(false);
			if((int)(mousePos.x-(dim.width-PANEL_WIDTH)/2.0) > 540 && (int)(mousePos.x-(dim.width-PANEL_WIDTH)/2.0) < 740 && (int)(mousePos.y-(dim.height-PANEL_HEIGHT)/2.0) > 390 && (int)(mousePos.y-(dim.height-PANEL_HEIGHT)/2.0) < 430)
				menuBtn.setVisible(true);
			else
				menuBtn.setVisible(false);
		}
	}
	
	public void actionPerformed(ActionEvent ev){	
		mousePos = MouseInfo.getPointerInfo().getLocation();
		buttonChecker(ev);
		if(!pacman.portalDetector() && pacman.mapY > 0 && pacman.mapY < 19 && pacman.mapX > 0 && pacman.mapX < 33){//Verifies and executes pacman movement
			pacman.collisionChecker();
			if(pacman.turnPoint > 0)
				pacman.execTurn(pacman.turnDirection);
		}
		for(int i = 0; i < ghosts.length; i ++){//Verifies and executes ghost movement
			if(!ghosts[i].portalDetector() && ghosts[i].mapY > 0 && ghosts[i].mapY < 19 && ghosts[i].mapX > 0 && ghosts[i].mapX < 33){
				if(ghosts[i].turnPoint > 0)
					ghosts[i].execTurn(ghosts[i].turnDirection);
				ghosts[i].collisionChecker();
			}
		}
		for(int i = 0; i < pathTiles.length; i++){//Detects if pellets are eaten
			if(!pathTiles[i].pelletEaten && pacman.x1+5 < pathTiles[i].pelletX && pacman.x2-5 > pathTiles[i].pelletX && pacman.y1+5 < pathTiles[i].pelletY && pacman.y2-5 > pathTiles[i].pelletY){
				if(pathTiles[i].superPellet){
					score+= 50;
					frightnedGhosts = true;
				}
				else
					score+= 10;
				
				numOfPelletsEaten++;
				pathTiles[i].pelletEaten = true;
			}
		}
		for(int i = 0; i < pathTiles.length; i++){
			winRound = true;
			if(!pathTiles[i].pelletEaten){
				winRound = false;
				break;
			}
		}
		if(winRound)
			restart();
		
		posUpdate();
		
		for(int i = 0; i < ghosts.length; i ++){
			if(!ghosts[i].enterMaze)
				Ghost.ghostSetup(i);
		}
		
		if(frightnedGhosts && frightenCounter < 4000){//Frightened mode
			for(int i = 0; i < ghosts.length; i++){
				if(ghosts[i].enterMaze && ghosts[i].interval == 150 || ghosts[i].velX == 0 && ghosts[i].velY == 0 && ghosts[i].enterMaze){
					ghosts[i].turnDirection = ghostRandMovement(i);
					ghosts[i].turnPoint = ghosts[i].movementChecker(ghosts[i].turnDirection);
					ghosts[i].interval = 0;
				}
			frightenCounter++;
			}
			
			if(ghostPacmanOverlap()){
				score+=500;
				numOfGhostsEaten++;
				try	{
					TimeUnit.SECONDS.sleep(1);
				}
				catch(InterruptedException ex){
				}
				ghosts[overlapGhost].x1 = ghostPos[overlapGhost][0];
				ghosts[overlapGhost].y1 = ghostPos[overlapGhost][1];
				ghosts[overlapGhost].x2 = ghostPos[overlapGhost][0]+ghosts[overlapGhost].size;
				ghosts[overlapGhost].y2 = ghostPos[overlapGhost][1]+ghosts[overlapGhost].size;
				ghosts[overlapGhost].velX = 0;
				ghosts[overlapGhost].velY = 0;
				Ghost.resetGhostCounter();
				ghosts[overlapGhost].enterMaze = false;
				Ghost.ghostSetup(overlapGhost);
			}
		}
		
		else{//Ghost behaviours
			frightnedGhosts = false;
			frightenCounter = 0;
			if(ghosts[0].enterMaze && ghosts[0].interval == 200){//Orange ghost movement (The Agressive One) - Chases pacman when seen 
				ghosts[0].turnDirection = ghostRandMovement(0);
				ghosts[0].turnPoint = ghosts[0].movementChecker(ghosts[0].turnDirection);
				ghosts[0].interval = 0;
			}		
			if(ghosts[1].enterMaze && ghosts[1].interval == 150 || ghosts[1].velX == 0 && ghosts[1].velY == 0 && ghosts[1].enterMaze){//Blue ghost movement (The Smart One) - Sense Pacman and Attempts to Ambush 
				ghosts[1].turnDirection = ghostRandMovement(1);
				ghosts[1].turnPoint = ghosts[1].movementChecker(ghosts[1].turnDirection);
				ghosts[1].interval = 0;
			}		
			if(ghosts[2].enterMaze){//Pink ghost movement (The Normal One) - Random Movements
				ghosts[2].turnDirection = ghostAmbushMovement(2);
				ghosts[2].turnPoint = ghosts[2].movementChecker(ghosts[2].turnDirection);
			}		
			if(ghosts[3].enterMaze){//Red ghost movement (The Stupid One) - Random Slow Movements
				ghosts[3].turnDirection = ghostChaseMovement(3);
				ghosts[3].turnPoint = ghosts[3].movementChecker(ghosts[3].turnDirection);
			}		
			if(ghosts[4].enterMaze){//Green ghost movement (The Very Smart One) - Tracks pacman down from across the map
				ghosts[4].turnDirection = ghostTrackMovement(4);
				ghosts[4].turnPoint = ghosts[4].movementChecker(ghosts[4].turnDirection);
			}		
			if(ghosts[5].enterMaze){//Purple ghost movement (The Strange One) - Behaviour based off of pacman's relative position
				if(ghosts[5].mapX > pacman.mapX && ghosts[5].mapY < pacman.mapY)//Top right
					ghosts[5].turnDirection = ghostTrackMovement(5);
				else if(ghosts[5].mapX < pacman.mapX && ghosts[5].mapY < pacman.mapY)//Top left
					ghosts[5].turnDirection = ghostAmbushMovement(5);
				else if(ghosts[5].mapX < pacman.mapX && ghosts[5].mapY > pacman.mapY  && ghosts[0].interval == 150 || ghosts[1].velX == 0 && ghosts[1].velY == 0 && ghosts[1].enterMaze)//Bottom left
					ghosts[5].turnDirection = ghostRandMovement(5);
				else if(ghosts[5].mapX > pacman.mapX && ghosts[5].mapY > pacman.mapY)//Bottem right
					ghosts[5].turnDirection = ghostTrackMovement(5);
				else
					ghosts[5].turnDirection = ghostChaseMovement(5);
				ghosts[5].turnPoint = ghosts[5].movementChecker(ghosts[5].turnDirection);
			}

			if(!gameOver && ghostPacmanOverlap()){//Detects if game is over
				try	{
					TimeUnit.SECONDS.sleep(2);
				}
				catch(InterruptedException ex){
				}
				gameOver = true;
			}
		}
		
		for(int i = 0; i < ghosts.length; i ++){
			if(ghosts[i].enterMaze)  //Checks if first ghost has entered maze
				ghosts[i].interval++;
		}
		animationUpdate();
		repaint();
	}
	
	public void posUpdate(){
		if(pacman.velX == 0 && pacman.velY == 0){//Statinary
			pacman.mapX = (pacman.x1+20)/30;
			pacman.mapY = (pacman.y1+20)/30;
		}
		if(pacman.velX > 0){ //Right
			pacman.mapX = (pacman.x1+17)/30;
			pacman.mapY = (pacman.y1+20)/30;
		}
		else if(pacman.velX < 0){//Left
			pacman.mapX = (pacman.x2+23)/30;
			pacman.mapY = (pacman.y1+20)/30;	
		}			
		if(pacman.velY > 0){//Down
			pacman.mapX = (pacman.x1+20)/30;
			pacman.mapY = (pacman.y1+15)/30;
		}
		else if (pacman.velY < 0){//Up
			pacman.mapX = (pacman.x1+20)/30;
			pacman.mapY = (pacman.y2+20)/30;
		}		
		
		for(int i = 0; i < ghosts.length; i++){
			if(ghosts[i].velX == 0 && ghosts[i].velY == 0){//Statinary
				ghosts[i].mapX = (ghosts[i].x1+20)/30;
				ghosts[i].mapY = (ghosts[i].y1+20)/30;
			}
			if(ghosts[i].velX > 0 || ghosts[i].velX == 0 && ghosts[i].velY == 0){ //Right
				ghosts[i].mapX = (ghosts[i].x1+20)/30;
				ghosts[i].mapY = (ghosts[i].y1+20)/30;
			}
			else if(ghosts[i].velX < 0){//Left
				ghosts[i].mapX = (ghosts[i].x2+20)/30;
				ghosts[i].mapY = (ghosts[i].y1+20)/30;	
			}			
			if(ghosts[i].velY > 0){//Down
				ghosts[i].mapX = (ghosts[i].x1+20)/30;
				ghosts[i].mapY = (ghosts[i].y1+20)/30;
			}
			else if (ghosts[i].velY < 0){//Up
				ghosts[i].mapX = (ghosts[i].x1+20)/30;
				ghosts[i].mapY = (ghosts[i].y2+20)/30;
			}
		}
	}
	
	public void animationUpdate(){
		if(!gameOver){//Updates positions of ghosts and pacman
			pacman.x1+=pacman.velX;
			pacman.y1+=pacman.velY; 
			pacman.x2+=pacman.velX;
			pacman.y2+=pacman.velY;
			
			for(int i = 0; i < ghosts.length; i++){
				ghosts[i].x1 += ghosts[i].velX;
				ghosts[i].y1 += ghosts[i].velY;		
				ghosts[i].x2 += ghosts[i].velX;
				ghosts[i].y2 += ghosts[i].velY;
			}
		}
		
		if(pacman.animCounter == 12){//Updates animation of pacman
			pacman.imgY1 = pacman.animInit;
			pacman.imgY2 = pacman.animEnd;
			pacman.animCounter = 0;
		}
		else if(pacman.animCounter%4 == 0){
			pacman.imgY1 += 50; 
			pacman.imgY2 += 50;
		}
		pacman.animCounter += 1;
		
		for(int i = 0; i < ghosts.length; i ++){//Updates animation of ghosts
			if(frightnedGhosts && ghosts[i].enterMaze){
				ghosts[i].imgX1 = 0;
				ghosts[i].imgX2 = 50;
				ghosts[i].imgY1 = 550;
				ghosts[i].imgY2 = 600;	
			}	
			else{
				ghosts[i].imgX1 = 0+i*50;
				ghosts[i].imgX2 = 50+i*50;
				if(ghosts[i].velX > 0){
					ghosts[i].imgY1 = 0;
					ghosts[i].imgY2 = 50;			
				}				
				else if(ghosts[i].velX < 0){
					ghosts[i].imgY1 = 200;	
					ghosts[i].imgY2 = 250;		
				}				
				else if(ghosts[i].velY > 0){
					ghosts[i].imgY1 = 100;		
					ghosts[i].imgY2 = 150;	
				}				
				else if(ghosts[i].velY < 0){
					ghosts[i].imgY1 = 300;
					ghosts[i].imgY2 = 350;		
				}	
				else{
					if(i == 0){
						ghosts[i].imgY1 = 0;
						ghosts[i].imgY2 = 50;
					}
				}
			}					
		}
	}		
	
	public String ghostRandMovement(int ghostNum){//Ghosts behaves randomly
		int tempRand;
		tempRand = (int)(Math.random()*4);
		
		if(tempRand == 0)
			return "up";		
		else if(tempRand == 1)
			return "down";		
		else if(tempRand == 2)
			return "right";		
		else
			return "left";
	}
	
	public String ghostChaseMovement(int ghostNum){//Ghosts chases pacman once seen
		if(!ghosts[ghostNum].portalDetector() && ghosts[ghostNum].mapY > 0 && ghosts[ghostNum].mapY < 19 && ghosts[ghostNum].mapX > 0 && ghosts[ghostNum].mapX < 33){
			if(ghosts[ghostNum].mapX == pacman.mapX || ghosts[ghostNum].mapY == pacman.mapY){
				if(ghosts[ghostNum].mapX > pacman.mapX && ptMap[ghosts[ghostNum].mapY][ghosts[ghostNum].mapX-1] != 0){
					return "left";
				}			
				else if(ghosts[ghostNum].mapX < pacman.mapX && ptMap[ghosts[ghostNum].mapY][ghosts[ghostNum].mapX+1] != 0){
					return "right";
				}			
				else if(ghosts[ghostNum].mapY > pacman.mapY && ptMap[ghosts[ghostNum].mapY-1][ghosts[ghostNum].mapX] != 0){
					return "up";
				}			
				else if(ghosts[ghostNum].mapY < pacman.mapY && ptMap[ghosts[ghostNum].mapY+1][ghosts[ghostNum].mapX] != 0){
					return "down";
				}
				else 
					return "";
			}
			else if(ghosts[ghostNum].interval == 150 || ghosts[ghostNum].velX == 0 && ghosts[ghostNum].velY == 0){
				ghosts[ghostNum].interval = 0;
				return ghostRandMovement(ghostNum);
			}
			else 
				return "";
		}
		else
			return "";
	}	
	
	public String ghostAmbushMovement(int ghostNum){//Ghosts tries to ambush and cutoff pacman
		if(!ghosts[ghostNum].portalDetector() && ghosts[ghostNum].mapY > 0 && ghosts[ghostNum].mapY < 19 && ghosts[ghostNum].mapX > 0 && ghosts[ghostNum].mapX < 33){
			if(ghosts[ghostNum].mapX > pacman.mapX && ghosts[ghostNum].mapX < pacman.mapX+3 && ghosts[ghostNum].mapY < pacman.mapY && ghosts[ghostNum].mapY > pacman.mapY-3
			   || ghosts[ghostNum].mapX < pacman.mapX && ghosts[ghostNum].mapX > pacman.mapX-3 && ghosts[ghostNum].mapY < pacman.mapY && ghosts[ghostNum].mapY > pacman.mapY-3
			   || ghosts[ghostNum].mapX < pacman.mapX && ghosts[ghostNum].mapX > pacman.mapX-3 && ghosts[ghostNum].mapY > pacman.mapY && ghosts[ghostNum].mapY < pacman.mapY+3
			   || ghosts[ghostNum].mapX > pacman.mapX && ghosts[ghostNum].mapX < pacman.mapX+3 && ghosts[ghostNum].mapY > pacman.mapY && ghosts[ghostNum].mapY < pacman.mapY+3){
				if(ghosts[ghostNum].mapX > pacman.mapX && ptMap[ghosts[ghostNum].mapY][ghosts[ghostNum].mapX-1] != 0){
					return "left";
				}			
				else if(ghosts[ghostNum].mapX < pacman.mapX && ptMap[ghosts[ghostNum].mapY][ghosts[ghostNum].mapX+1] != 0){
					return "right";
				}			
				else if(ghosts[ghostNum].mapY > pacman.mapY && ptMap[ghosts[ghostNum].mapY-1][ghosts[ghostNum].mapX] != 0){
					return "up";
				}			
				else if(ghosts[ghostNum].mapY < pacman.mapY && ptMap[ghosts[ghostNum].mapY+1][ghosts[ghostNum].mapX] != 0){
					return "down";
				}
				else 
					return "";
			}
			else if(ghosts[ghostNum].interval == 150 || ghosts[ghostNum].velX == 0 && ghosts[ghostNum].velY == 0){
				ghosts[ghostNum].interval = 0;
				return ghostRandMovement(ghostNum);
			}
			else 
				return "";
		}
		else
			return "";
	}	
	
	public String ghostTrackMovement(int ghostNum){//Ghosts tracks down pacman from across the map
		if(!ghosts[ghostNum].portalDetector() && ghosts[ghostNum].mapY > 0 && ghosts[ghostNum].mapY < 19 && ghosts[ghostNum].mapX > 0 && ghosts[ghostNum].mapX < 33){
			if(ghosts[ghostNum].velX == 0 && ghosts[ghostNum].velY == 0)
				lost = true;
			if(lost){
				if(ghosts[ghostNum].mapX > pacman.mapX && ghosts[ghostNum].mapY < pacman.mapY){//Movement if ghost is lost
					if(moveSequence == 0){
						moveSequence++;
						return "right";
					}	
					if(moveExecuted && ghosts[ghostNum].velX == 0 && ghosts[ghostNum].velY == 0){
						moveSequence = 0;
						lost = false;
					}
					if(moveSequence == 1){
						moveExecuted = true;
						return "down";
					}
				}				
				else if(ghosts[ghostNum].mapX < pacman.mapX && ghosts[ghostNum].mapY < pacman.mapY){
					if(moveSequence == 0){
						moveSequence++;
						return "left";
					}	
					if(moveExecuted && ghosts[ghostNum].velX == 0 && ghosts[ghostNum].velY == 0){
						moveSequence = 0;
						lost = false;
					}
					if(moveSequence == 1){
						moveExecuted = true;
						return "down";
					}
				}				
				else if(ghosts[ghostNum].mapX < pacman.mapX && ghosts[ghostNum].mapY > pacman.mapY){
					if(moveSequence == 0){
						moveSequence++;
						return "left";
					}	
					if(moveExecuted && ghosts[ghostNum].velX == 0 && ghosts[ghostNum].velY == 0){
						moveSequence = 0;
						lost = false;
					}
					if(moveSequence == 1){
						moveExecuted = true;
						return "up";
					}
				}				
				else if(ghosts[ghostNum].mapX > pacman.mapX && ghosts[ghostNum].mapY > pacman.mapY){
					if(moveSequence == 0){
						moveSequence++;
						return "right";
					}	
					if(moveExecuted && ghosts[ghostNum].velX == 0 && ghosts[ghostNum].velY == 0){
						moveSequence = 0;
						lost = false;
					}
					if(moveSequence == 1){
						moveExecuted = true;
						return "up";
					}
				}		
			}
			
			//Movement if ghost sees pacman
			if(ghosts[ghostNum].interval%5 == 0 && ghosts[ghostNum].mapX == pacman.mapX || ghosts[ghostNum].mapY == pacman.mapY){
				return ghostChaseMovement(ghostNum);
			}
			
			//Movement if ghosts sense pacman
			else if(!lost && ghosts[ghostNum].mapX > pacman.mapX && ghosts[ghostNum].mapY < pacman.mapY
			   || ghosts[ghostNum].mapX < pacman.mapX && ghosts[ghostNum].mapY < pacman.mapY
			   || ghosts[ghostNum].mapX < pacman.mapX && ghosts[ghostNum].mapY > pacman.mapY
			   || ghosts[ghostNum].mapX > pacman.mapX && ghosts[ghostNum].mapY > pacman.mapY){
				if(ghosts[ghostNum].mapX > pacman.mapX && ptMap[ghosts[ghostNum].mapY][ghosts[ghostNum].mapX-1] != 0){
					return "left";
				}			
				else if(ghosts[ghostNum].mapX < pacman.mapX && ptMap[ghosts[ghostNum].mapY][ghosts[ghostNum].mapX+1] != 0){
					return "right";
				}			
				else if(ghosts[ghostNum].mapY > pacman.mapY && ptMap[ghosts[ghostNum].mapY-1][ghosts[ghostNum].mapX] != 0){
					return "up";
				}			
				else if(ghosts[ghostNum].mapY < pacman.mapY && ptMap[ghosts[ghostNum].mapY+1][ghosts[ghostNum].mapX] != 0){
					return "down";
				}
				else 
					return "";
			}
			else 
				return "";
		}
		else
			return "";
	}
	
	public boolean ghostPacmanOverlap(){//Checks if pacman has eaten or is eaten by a ghost
		for(int i = 0; i < ghosts.length; i++){
			if(ghosts[i].enterMaze && (ghosts[i].mapY == pacman.mapY && (pacman.velX != 0 || pacman.velX == 0 && pacman.velY == 0 && ghosts[i].velX != 0)&& (pacman.x1+10 < ghosts[i].x2 && pacman.x2 > ghosts[i].x2 || pacman.x2-10 > ghosts[i].x1 && pacman.x1 < ghosts[i].x1)) ||
			  (ghosts[i].mapX == pacman.mapX && (pacman.velY != 0 || pacman.velX == 0 && pacman.velY == 0 && ghosts[i].velY != 0) && (pacman.y1+10 < ghosts[i].y2 && pacman.y2 > ghosts[i].y2 || pacman.y2-10 > ghosts[i].y1 && pacman.y1 < ghosts[i].y1))){
				overlapGhost = i;
				return true;
			}
		}
		return false;
	}	
	
	public static void restart(){//Resets game
		timer.stop();
		Ghost.resetGhosts();
		run();
	}
	
	public static void buttonChecker(ActionEvent ev){
		if(ev.getSource()== retryBtn){
			retryBtn.setVisible(false);
			gameOver = false;
			score = 0;
			restart();
		}
		else if(ev.getSource()== menuBtn){	
			menuBtn.setVisible(false);
			retryBtn.setVisible(false);		
			menuScreen = true;	
		}
	}
}