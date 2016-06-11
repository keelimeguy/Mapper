import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Block extends Rectangle {
	public int groundId;

	public int shotMob = -1;
	public boolean isShooting = false;
	public Image[] tileset;

	public Block(int x, int y, int width, int height, int groundId) {
		setBounds(x, y, width, height);
		this.groundId = groundId;
		tileset = Screen.tilesetGround;
	}
	public Block(int x, int y, int width, int height, int groundId, Image[] tileset) {
		setBounds(x, y, width, height);
		this.groundId = groundId;
		this.tileset = tileset;
	}

	public void draw(Graphics g) {
		
		if (groundId < tileset.length) 
			g.drawImage(tileset[groundId], x, y, width, height, null);
			
	}

	public void physics() {

	}
}
