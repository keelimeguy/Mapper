import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Room {
	public int worldWidth = 0;
	public int worldHeight = 0;
	public int worldFrame = 0;
	public int curFrame = 0;
	public int roomWidth = 0, roomHeight = 0;
	public int roomX = 0, roomY = 0;
	public int xOff = 0, yOff = 0;
	public int blockSize = 52;
	public int maxSize = 208, minSize = 8;

	public boolean selected = false;

	public Block[][] block;

	public Room() {
		define(0, 0);
	}

	public void setDimensions(int width, int height) {
		worldWidth = width;
		worldHeight = height;
		define(0, 0);
	}

	public void define(int xOffset, int yOffset) {
		xOff = xOffset;
		yOff = yOffset;
		block = new Block[worldHeight][worldWidth];
		roomX = Screen.store.buttonSize * (Screen.store.tileGroupWidth + 1) + Screen.store.largeCellSpace + Screen.store.cellSpace * 2;
		roomY = 0;
		roomHeight = Screen.myHeight - roomY;
		roomWidth = Screen.myWidth - roomX;

		for (int y = 0; y < block.length; y++)
			for (int x = 0; x < block[0].length; x++)
				block[y][x] = new Block((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, 0, -1);

	}

	public void redefine(int xOffset, int yOffset, Block[][] blocks) {
		xOff = xOffset;
		yOff = yOffset;
		block = new Block[worldHeight][worldWidth];
		roomX = Screen.store.buttonSize * (Screen.store.tileGroupWidth + 1) + Screen.store.largeCellSpace + Screen.store.cellSpace * 2;
		roomY = 0;
		roomHeight = Screen.myHeight - roomY;
		roomWidth = Screen.myWidth - roomX;

		for (int y = 0; y < block.length; y++)
			for (int x = 0; x < block[0].length; x++)
				if (y < blocks.length && x < blocks[0].length)
					block[y][x] = new Block((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, blocks[y][x].groundId, -1);
				else
					block[y][x] = new Block((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, 0, -1);
	}

	public void physics() {
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (block[y][x] != null) block[y][x].physics();
			}
		}
	}

	public void click(int mouseButton) {
		if (mouseButton == 1) {
			if (new Rectangle(roomX, roomY, roomWidth, roomHeight).contains(Screen.mse)) {
				selected = true;
				drag(Screen.mse);
			} else
				selected = false;

		}
	}

	public void release(int mouseButton) {
		if (mouseButton == 1) {
			selected = false;
		}
	}

	public void drag(Point mseOld) {
		if (!Screen.store.holdsItem && selected) {
			int dX = Screen.mse.x - mseOld.x;
			int dY = Screen.mse.y - mseOld.y;

			for (int y = 0; y < block.length; y++) {
				for (int x = 0; x < block[0].length; x++) {
					block[y][x].x += dX;
					block[y][x].y += dY;
				}
			}
			yOff += dY;
			xOff += dX;
		} else if (Screen.store.holdsItem && selected && new Rectangle(roomX, roomY, roomWidth, roomHeight).contains(Screen.mse)) {
			for (int y = 0; y < Screen.room.block.length; y++) {
				for (int x = 0; x < Screen.room.block[0].length; x++) {
					if (Screen.room.block[y][x].contains(Screen.mse)) {
						if (Screen.room.worldFrame > 0 && Screen.room.worldFrame < Screen.room.block[y][x].groundId.length)
							Screen.room.block[y][x].groundId[Screen.room.worldFrame] = Screen.store.heldId;
						else
							Screen.room.block[y][x].groundId[0] = Screen.store.heldId;
					}
				}
			}
		}
	}

	public void draw(Graphics g) {
		for (int y = 0; y < block.length; y++)
			for (int x = 0; x < block[0].length; x++)
				if (block[y][x].x + blockSize > roomX && block[y][x].x < roomX + roomWidth && block[y][x].y + blockSize > roomY && block[y][x].y < roomHeight) block[y][x].draw(g);
	}
}
