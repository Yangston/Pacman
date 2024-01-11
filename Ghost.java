import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ghost extends Sprite{
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	
	private static int PANEL_HEIGHT = 600, PANEL_WIDTH = 1000;
	private static Ghost ghosts[];
	private static int ghostCounter;
	public boolean enterMaze;
	public static boolean allEntered = false;
	public int interval;
	
	public Ghost(){
		this(463, 285, 845, 0, 24);
	}
	
	public Ghost(int x1, int y1, int imgX1, int imgX2, int size){
		super(x1, y1, imgX1, 0, imgX2, 50, size);
		enterMaze = false;
		interval = 0;
	}	
	
	public static void ghostSet(Ghost[] allGhosts){
		ghosts = allGhosts;
	}
	
	public void collisionChecker(){
		if(!portalDetector() && enterMaze && mapY > 0 && mapY < 19 && mapX > 0 && mapX < 33){
			if(velY < 0 && ptMap[mapY-1][mapX] == 0)//Upwards
				velY = 0;		
			else if(velY > 0 && ptMap[mapY+1][mapX] == 0)//Downwards
				velY = 0;				
			else if(velX < 0 && ptMap[mapY][mapX-1] == 0)//Left
				velX = 0;			
			else if(velX > 0 && ptMap[mapY][mapX+1] == 0)//Right
				velX = 0;		
		}
	}
	
	public static void ghostSetup(int ghostNum){//Sets up initial position of ghosts
		if(ghostNum == 0){
			if(ghostCounter/192 < 2){
				if(!ghosts[0].enterMaze && ghosts[0].mapX == 15){
					ghosts[0].velX = 1;	
					ghostCounter++;
				}
				else if(!ghosts[0].enterMaze && ghosts[0].mapX == 16){
					ghosts[0].velX = -1;
					ghostCounter++;
				}
			}
			else if(!ghosts[0].enterMaze){
				if(ptMap[ghosts[0].mapY][ghosts[0].mapX] != 0)
					ghosts[0].enterMaze = true;
			}
		}
		if(ghostNum == 1){
			if(ghostCounter/192 < 5){
				if(!ghosts[1].enterMaze && ghosts[1].mapY == 9){
					ghosts[1].velY = -1;
					ghostCounter++;
				}
				else if(!ghosts[1].enterMaze && ghosts[1].mapY == 8){
					ghosts[1].velY = 1;	
					ghostCounter++;
				}
			}
			else if(!ghosts[1].enterMaze){
				if(ptMap[ghosts[1].mapY][ghosts[1].mapX] != 0)
					ghosts[1].enterMaze = true;
			}
		}
		if(ghostNum == 2){
			if(ghostCounter/192 < 9){
				if(!ghosts[2].enterMaze && ghosts[2].mapX == 18){
					ghosts[2].velX = -1;
					ghostCounter+=2;
				}
				else if(!ghosts[2].enterMaze && ghosts[2].mapX == 17){
					ghosts[2].velX = 1;	
					ghostCounter+=2;
				}
			}
			else if(!ghosts[2].enterMaze){
				if(ptMap[ghosts[2].mapY][ghosts[2].mapX] != 0)
					ghosts[2].enterMaze = true;
			}
		}
		if(ghostNum == 3){
			if(ghostCounter/192 < 10){
				if(!ghosts[3].enterMaze && ghosts[3].mapY == 11){
					ghosts[3].velY = 1;	
					ghostCounter+=2;
				}
				else if(!ghosts[3].enterMaze && ghosts[3].mapY == 12){
					ghosts[3].velY = -1;
					ghostCounter+=2;
				}
			}
			else if(!ghosts[3].enterMaze){
				if(ptMap[ghosts[3].mapY][ghosts[3].mapX] != 0)
					ghosts[3].enterMaze = true;
			}
		}		
		if(ghostNum == 4){
			if(ghostCounter/192 < 11){
				if(!ghosts[4].enterMaze && ghosts[4].mapY == 10){
					ghosts[4].velY = 1;	
					ghostCounter+=2;
				}
				else if(!ghosts[4].enterMaze && ghosts[4].mapY == 14){
					ghosts[4].velY = -1;
					ghostCounter+=2;
				}
			}
			else if(!ghosts[4].enterMaze){
				if(ptMap[ghosts[4].mapY][ghosts[4].mapX] != 0)
					ghosts[4].enterMaze = true;
			}		
		}
		if(ghostNum == 5){
			if(ghostCounter/192 < 12){
				if(!ghosts[5].enterMaze && ghosts[5].mapY == 10){
					ghosts[5].velY = 1;	
					ghostCounter+=5;
				}
				else if(!ghosts[5].enterMaze && ghosts[5].mapY == 14){
					ghosts[5].velY = -1;
					ghostCounter+=5;
				}
			}
			else if(!ghosts[5].enterMaze){
				if(ptMap[ghosts[5].mapY][ghosts[5].mapX] != 0){
					ghosts[5].enterMaze = true;
					allEntered = true;
				}
			}
		}
	}
	
	public static void resetGhosts(){
		allEntered = false;
		ghostCounter = 0;
	}
	
	public static void resetGhostCounter(){
		ghostCounter = 0;
	}
}