import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Sprite{
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	
	private static int PANEL_HEIGHT = 600, PANEL_WIDTH = 1000;
	protected static int[][] ptMap;
	protected static PathTile pathTiles[];
	public int size, imgX1, imgY1, imgX2, imgY2, x1, y1, x2, y2, velX, velY, mapX, mapY, turnPoint, animInit, animEnd, animCounter;
	public String turnDirection = "";
	
	public Sprite(){
		this(463, 285, 845, 0, 895, 50, 24);
	}
	
	public Sprite(int x1, int y1, int imgX1, int imgX2, int size){
		this(x1, y1, imgX1, 0, imgX2, 50, size);
	}	
	
	public Sprite(int x1, int y1, int imgX1, int imgY1, int imgX2, int imgY2, int size){
		this.size = size;
		this.x1 = x1;
		this.y1 = y1;
		x2 = x1+size;
		y2 = y1+size;
		this.imgX1 = imgX1;
		this.imgY1 = imgY1;
		this.imgX2 = imgX2;
		this.imgY2 = imgY2;
		velX = 0;
		velY = 0;
		mapX = 0;
		mapY = 0;
		animInit = 0;
		animEnd = 50;
		animCounter = 0;
	}
	
	public static void mapSet(int[][] map){
		ptMap = map;
	}
	
	public static void pathTilesSet(PathTile[] tiles){
		pathTiles = tiles;
	}
	
	public boolean portalDetector(){
		if(mapX < 0 && mapY == 9 && velX < 0){//Left to Right
			x1 = PANEL_WIDTH;
			x2 = PANEL_WIDTH + size;
			return true;
		}
		else if(mapX > 34 && mapY == 9 &&velX > 0){//Right to Left
			x1 = -50 - size;
			x2 = -50;
			return true;
		}
		else if(mapY < 0 && (mapX == 16 || mapX == 17) && velY < 0){//Top to Bottom
			y1 = PANEL_HEIGHT;
			y2 = PANEL_HEIGHT + size;
			return true;
		}
		else if(mapY > 20 && (mapX == 16 || mapX == 17) && velY > 0){//Bottom to Top
			y1 = 0 - size;
			y2 = 0;
			return true;
		}
		return false;
	}
	
	public int movementChecker(String direction){	
		if(!portalDetector() && mapY > 0 && mapY < 19 && mapX > 0 && mapX < 33){	
			if(direction.equals("right")){
				if(velX < 0 || velX == 0 && velY == 0){//Previous Direction Left
					if(ptMap[mapY][mapX+1] != 0)
						return mapY;
					else
						return 0;
				}			
				else if(velX == 0 && velY > 0){//Previous Direction Down
					for(int i = mapY; i <19; i++){
						if(ptMap[i][mapX+1] != 0)
							return i;
					}
					return 0;
				}			
				else if(velX == 0 && velY < 0){//Previous Direction Up
					for(int i = mapY; i > 0; i--){
						if(ptMap[i][mapX+1] != 0)
							return i;
					}
					return 0;
				}
				return 0;
			}		
			else if(direction.equals("left")){
				if(velX > 0 || velX == 0 && velY == 0){//Previous Direction Right
					if(ptMap[mapY][mapX-1] != 0)
						return mapY;
					else
						return 0;
				}			
				else if(velX == 0 && velY > 0){//Previous Direction Down
					for(int i = mapY; i <20; i++){
						if(ptMap[i][mapX-1] != 0)
							return i;
					}
					return 0;
				}			
				else if(velX == 0 && velY < 0){//Previous Direction Up
					for(int i = mapY; i > 0; i--){
						if(ptMap[i][mapX-1] != 0)
							return i;
					}
					return 0;
				}
				return 0;
			}				
			else if(direction.equals("down")){
				if(velY < 0 || velX == 0 && velY == 0){//Previous Direction Up
					if(ptMap[mapY+1][mapX] != 0){
						return mapX;
					}
					else
						return 0;
				}			
				else if(velX > 0 && velY == 0){//Previous Direction Right
					for(int i = mapX; i <ptMap[0].length-1; i++){
						if(ptMap[mapY+1][i] != 0){
							return i;
						}	
					}
					return 0;
				}			
				else if(velX < 0 && velY == 0){//Previous Direction Left
					for(int i = mapX; i > 0; i--){
						if(ptMap[mapY+1][i] != 0)
							return i;
					}
					return 0;
				}
				return 0;
			}				
			else if(direction.equals("up")){
				if(velY > 0 || velX == 0 && velY == 0){//Previous Direction Down
					if(ptMap[mapY-1][mapX] != 0)
						return mapX;
					else
						return 0;
				}			
				else if(velX > 0 && velY == 0){//Previous Direction Right
					for(int i = mapX; i <ptMap[0].length-1; i++){
						if(ptMap[mapY-1][i] != 0){
							return i;
						}	
					}
					return 0;
				}			
				else if(velX < 0 && velY == 0){//Previous Direction Left
					for(int i = mapX; i > 0; i--){
						if(ptMap[mapY-1][i] != 0)
							return i;
					}
					return 0;
				}
				return 0;
			}	
		}
		return 0;
	}
	
	public void execTurn(String direction){//Executes turns once verified
		if(direction.equals("right")){
			if(mapY == turnPoint && ptMap[turnPoint][mapX] != 0 && y1 >= pathTiles[ptMap[turnPoint][mapX]-1].y1 && y2 <= pathTiles[ptMap[turnPoint][mapX]-1].y1 + PathTile.size){	
				velX = 1;
				velY = 0;
				animInit = 0;
				animEnd = 50;
				turnPoint = 0;
			}
		}		
		else if(direction.equals("left")){
			if(mapY == turnPoint && ptMap[turnPoint][mapX] != 0 && y1 >= pathTiles[ptMap[turnPoint][mapX]-1].y1 && y2 <= pathTiles[ptMap[turnPoint][mapX]-1].y1 + PathTile.size){	
				velX = -1;
				velY = 0;
				animInit = 300;
				animEnd = 350;
				turnPoint = 0;
			}
		}		
		else if(direction.equals("down")){
			if(mapX == turnPoint && ptMap[mapY][turnPoint] != 0 && x1 >= pathTiles[ptMap[mapY][turnPoint]-1].x1 && x2 <= pathTiles[ptMap[mapY][turnPoint]-1].x1 + PathTile.size){	
				velX = 0;
				velY = 1;
				animInit = 150;
				animEnd = 200;
				turnPoint = 0;
			}
		}		
		else if(direction.equals("up")){
			if(mapX == turnPoint && ptMap[mapY][turnPoint] != 0 && x1 >= pathTiles[ptMap[mapY][turnPoint]-1].x1 && x2 <= pathTiles[ptMap[mapY][turnPoint]-1].x1 + PathTile.size){	
				velX = 0;
				velY = -1;
				animInit = 450;
				animEnd = 500;
				turnPoint = 0;
			}
		}
	}
	
	public void collisionChecker(){
		if(!portalDetector() && mapY > 0 && mapY < 19 && mapX > 0 && mapX < 33){
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
}