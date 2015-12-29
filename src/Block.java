import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle {
	public int[] ResId;
	public int[] groundId;

	public int shotMob = -1;
	public boolean isShooting = false;

	public Block(int x, int y, int width, int height, int groundId, int ResId) {
		setBounds(x, y, width, height);
		this.groundId = new int[10];
		this.ResId = new int[10];
		for (int f = 0; f < 10; f++)
			this.groundId[f] = this.ResId[f] = -1;
		this.ResId[0] = ResId;
		this.groundId[0] = groundId;
	}

	public Block(int x, int y, int width, int height, int[] groundId, int ResId) {
		setBounds(x, y, width, height);
		this.groundId = new int[10];
		this.ResId = new int[10];
		for (int f = 0; f < 10; f++)
			this.groundId[f] = this.ResId[f] = -1;
		this.ResId[0] = ResId;
		this.groundId = groundId;
	}

	public Block(int x, int y, int width, int height, int groundId, int ResId[]) {
		setBounds(x, y, width, height);
		this.groundId = new int[10];
		this.ResId = new int[10];
		for (int f = 0; f < 10; f++)
			this.groundId[f] = this.ResId[f] = -1;
		this.ResId = ResId;
		this.groundId[0] = groundId;
	}

	public Block(int x, int y, int width, int height, int[] groundId, int ResId[]) {
		setBounds(x, y, width, height);
		this.groundId = new int[10];
		this.ResId = new int[10];
		for (int f = 0; f < 10; f++)
			this.groundId[f] = this.ResId[f] = -1;
		this.ResId = ResId;
		this.groundId = groundId;
	}

	public void draw(Graphics g) {
		int _groundId = Screen.room.worldFrame, _resId = Screen.room.worldFrame;
		if (Screen.room.worldFrame < 0 || Screen.room.worldFrame >= groundId.length) _groundId = 0;
		if (Screen.room.worldFrame >= ResId.length) _resId = 0;

		if (groundId[_groundId] < Screen.tilesetGround.length) g.drawImage(Screen.tilesetGround[groundId[_groundId]], x, y, width, height, null);
		if (ResId[_resId] != -1 && groundId[_groundId] < Screen.tilesetGround.length) {
			g.drawImage(Screen.tilesetRes[ResId[_resId]], x, y, width, height, null);

		}
	}

	public void physics() {

	}
}
