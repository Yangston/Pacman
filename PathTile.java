import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PathTile{
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	
	private static int PANEL_HEIGHT = 600, PANEL_WIDTH = 1000;
	public static int size;
	public int x1, y1, pelletX, pelletY, pelletSize;
	public boolean pelletEaten, superPellet;
	public PathTile(){
		this(0,0,0,0,0);
	}
	
	public PathTile(int x1, int y1, int pelletX, int pelletY, int pelletSize){
		size = 30;
		this.x1 = x1;
		this.y1 = y1;
		this.pelletX = pelletX;
		this.pelletY = pelletY;
		this.pelletSize = pelletSize;
		pelletEaten = false;
		superPellet = false;
	}
	
	public void spawnSuperPellet(int x, int y){
		pelletX = x;
		pelletY = y;
		pelletSize = 10;
		superPellet = true;
	}
}