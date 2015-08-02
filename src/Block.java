import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle {
	public int ResId;
	public int groundId;

	public int shotMob = -1;
	public boolean isShooting = false;

	public Block(int x, int y, int width, int height, int groundId, int ResId) {
		setBounds(x, y, width, height);
		this.ResId = ResId;
		this.groundId = groundId;
	}

	public void draw(Graphics g) {
		if (groundId < Screen.tilesetGround.length) g.drawImage(Screen.tilesetGround[groundId], x, y, width, height, null);

		if (ResId != -1 && groundId < Screen.tilesetGround.length) {
			g.drawImage(Screen.tilesetRes[ResId], x, y, width, height, null);
		}
	}

	public void physics() {

	}
}
