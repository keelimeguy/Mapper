import java.awt.Graphics;
import java.awt.Image;


public class AnimatedBlock extends Block{

	private static final long serialVersionUID = 1L;

	public int[] groundId;

	public AnimatedBlock(int x, int y, int width, int height, int[] groundId) {
		super(x, y, width, height, groundId[0]);
		this.groundId = groundId;
	}
	public AnimatedBlock(int x, int y, int width, int height, int[] groundId, Image[] tileset) {
		super(x, y, width, height, groundId[0], tileset);
		this.groundId = groundId;
	}
	
	public void draw(Graphics g) {
		int _groundId = Screen.room.worldFrame, _resId = Screen.room.worldFrame;
		if (Screen.room.worldFrame < 0 || Screen.room.worldFrame >= groundId.length) _groundId = 0;
		if (groundId[_groundId] < tileset.length) g.drawImage(tileset[groundId[_groundId]], x, y, width, height, null);
	}
	
	public void phyics(){
		
	}
}
