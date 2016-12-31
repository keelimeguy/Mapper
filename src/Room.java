import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Room {
	public int worldWidth = 0;
	public int worldHeight = 0;
	public int worldFrame = 0;
	public int curFrame = 0;
	public int curView = 3;
	public int roomWidth = 0, roomHeight = 0;
	public int roomX = 0, roomY = 0;
	public int xOff = 0;
	public int yOff = 0;
	public int blockSize = 52;
	public int maxSize = 208, minSize = 8;

	public boolean selected = false;
	public boolean[] showTile = { true, true, true, true };

	public Block[][] block;
	public Block[][] collide;
	public AnimatedBlock[][] over;
	public AnimatedBlock[][] top;

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
		over = new AnimatedBlock[worldHeight][worldWidth];
		top = new AnimatedBlock[worldHeight][worldWidth];
		collide = new Block[worldHeight][worldWidth];
		roomX = Store.buttonSize * (Store.tileGroupWidth + 1) + Store.largeCellSpace + Store.cellSpace * 2;
		roomY = 0;
		roomHeight = Screen.myHeight - roomY;
		roomWidth = Screen.myWidth - roomX;
		curView = 3;

		for (int y = 0; y < block.length; y++)
			for (int x = 0; x < block[0].length; x++)
				block[y][x] = new Block((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, (x % 2) + (y % 2) * Store.tileGroupWidth);
		for (int y = 0; y < over.length; y++)
			for (int x = 0; x < over[0].length; x++)
				over[y][x] = new AnimatedBlock((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, new int[] { Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId });
		for (int y = 0; y < top.length; y++)
			for (int x = 0; x < top[0].length; x++)
				top[y][x] = new AnimatedBlock((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, new int[] { Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId });
		for (int y = 0; y < collide.length; y++)
			for (int x = 0; x < collide[0].length; x++)
				collide[y][x] = new Block((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, 0, Screen.tilesetCol);

	}

	public void redefine(int xOffset, int yOffset, Block[][] blocks, AnimatedBlock[][] overs, AnimatedBlock[][] tops, Block[][] collides, boolean[] showTile, int curView) {
		xOff = xOffset;
		yOff = yOffset;
		block = new Block[worldHeight][worldWidth];
		over = new AnimatedBlock[worldHeight][worldWidth];
		top = new AnimatedBlock[worldHeight][worldWidth];
		collide = new Block[worldHeight][worldWidth];
		roomX = Store.buttonSize * (Store.tileGroupWidth + 1) + Store.largeCellSpace + Store.cellSpace * 2;
		roomY = 0;
		roomHeight = Screen.myHeight - roomY;
		roomWidth = Screen.myWidth - roomX;
		this.showTile = showTile;
		this.curView = curView;
		if (curView == 3) Screen.store.buttonId = 1;

		for (int y = 0; y < block.length; y++)
			for (int x = 0; x < block[0].length; x++)
				if (y < blocks.length && x < blocks[0].length)
					block[y][x] = new Block((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, blocks[y][x].groundId);
				else
					block[y][x] = new Block((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, (x % 2) + (y % 2) * Store.tileGroupWidth);
		for (int y = 0; y < over.length; y++)
			for (int x = 0; x < over[0].length; x++)
				if (y < overs.length && x < overs[0].length)
					over[y][x] = new AnimatedBlock((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, overs[y][x].groundId);
				else
					over[y][x] = new AnimatedBlock((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, new int[] { Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId });
		for (int y = 0; y < top.length; y++)
			for (int x = 0; x < top[0].length; x++)
				if (y < tops.length && x < tops[0].length)
					top[y][x] = new AnimatedBlock((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, tops[y][x].groundId);
				else
					top[y][x] = new AnimatedBlock((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, new int[] { Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId, Screen.lastId });
		for (int y = 0; y < collide.length; y++)
			for (int x = 0; x < collide[0].length; x++)
				if (y < collides.length && x < collides[0].length)
					collide[y][x] = new Block((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, collides[y][x].groundId, Screen.tilesetCol);
				else
					collide[y][x] = new Block((roomWidth / 2) - ((worldWidth * blockSize) / 2) + (x * blockSize) + roomX + xOffset, roomHeight / 2 - (worldHeight * blockSize) / 2 + y * blockSize + roomY + yOffset, blockSize, blockSize, 0, Screen.tilesetCol);
		Screen.store.define();
	}

	public void physics() {
		if (showTile[0]) for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (block[y][x] != null) block[y][x].physics();
			}
		}
		if (showTile[1]) for (int y = 0; y < over.length; y++) {
			for (int x = 0; x < over[0].length; x++) {
				if (over[y][x] != null) over[y][x].physics();
			}
		}

		if (showTile[2]) for (int y = 0; y < top.length; y++) {
			for (int x = 0; x < top[0].length; x++) {
				if (top[y][x] != null) top[y][x].physics();
			}
		}

		if (showTile[3]) for (int y = 0; y < collide.length; y++) {
			for (int x = 0; x < collide[0].length; x++) {
				if (collide[y][x] != null) collide[y][x].physics();
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
			for (int y = 0; y < over.length; y++) {
				for (int x = 0; x < over[0].length; x++) {
					over[y][x].x += dX;
					over[y][x].y += dY;
				}
			}
			for (int y = 0; y < top.length; y++) {
				for (int x = 0; x < top[0].length; x++) {
					top[y][x].x += dX;
					top[y][x].y += dY;
				}
			}
			for (int y = 0; y < collide.length; y++) {
				for (int x = 0; x < collide[0].length; x++) {
					collide[y][x].x += dX;
					collide[y][x].y += dY;
				}
			}
			yOff += dY;
			xOff += dX;
		} else if (Screen.store.holdsItem && selected && new Rectangle(roomX, roomY, roomWidth, roomHeight).contains(Screen.mse)) {
			switch (curView) {
			case 0:
				for (int y = 0; y < Screen.room.block.length; y++) {
					for (int x = 0; x < Screen.room.block[0].length; x++) {
						if (Screen.room.block[y][x].contains(Screen.mse)) {
							Screen.room.block[y][x].groundId = Screen.store.heldId;
						}
					}
				}
				break;
			case 1:
				for (int y = 0; y < Screen.room.over.length; y++) {
					for (int x = 0; x < Screen.room.over[0].length; x++) {
						if (Screen.room.over[y][x].contains(Screen.mse)) {
							if (Screen.room.worldFrame > 0 && Screen.room.worldFrame < Screen.room.over[y][x].groundId.length)
								Screen.room.over[y][x].groundId[Screen.room.worldFrame] = Screen.store.heldId;
							else
								Screen.room.over[y][x].groundId[0] = Screen.store.heldId;
						}
					}
				}
				break;
			case 2:
				for (int y = 0; y < Screen.room.top.length; y++) {
					for (int x = 0; x < Screen.room.top[0].length; x++) {
						if (Screen.room.top[y][x].contains(Screen.mse)) {
							if (Screen.room.worldFrame > 0 && Screen.room.worldFrame < Screen.room.top[y][x].groundId.length)
								Screen.room.top[y][x].groundId[Screen.room.worldFrame] = Screen.store.heldId;
							else
								Screen.room.top[y][x].groundId[0] = Screen.store.heldId;
						}
					}
				}
				break;
			case 3:
				for (int y = 0; y < Screen.room.collide.length; y++) {
					for (int x = 0; x < Screen.room.collide[0].length; x++) {
						if (Screen.room.collide[y][x].contains(Screen.mse)) {
							Screen.room.collide[y][x].groundId = Screen.store.heldId;
						}
					}
				}
				break;
			}
		}
	}

	public void draw(Graphics g) {
		if (showTile[0]) for (int y = 0; y < block.length; y++)
			for (int x = 0; x < block[0].length; x++)
				if (block[y][x].x + blockSize > roomX && block[y][x].x < roomX + roomWidth && block[y][x].y + blockSize > roomY && block[y][x].y < roomHeight) block[y][x].draw(g);
		if (showTile[1]) for (int y = 0; y < over.length; y++)
			for (int x = 0; x < over[0].length; x++)
				if (over[y][x].x + blockSize > roomX && over[y][x].x < roomX + roomWidth && over[y][x].y + blockSize > roomY && over[y][x].y < roomHeight) over[y][x].draw(g);
		if (showTile[2]) for (int y = 0; y < top.length; y++)
			for (int x = 0; x < top[0].length; x++)
				if (top[y][x].x + blockSize > roomX && top[y][x].x < roomX + roomWidth && top[y][x].y + blockSize > roomY && top[y][x].y < roomHeight) top[y][x].draw(g);
		if (showTile[3]) for (int y = 0; y < collide.length; y++)
			for (int x = 0; x < collide[0].length; x++)
				if (collide[y][x].x + blockSize > roomX && collide[y][x].x < roomX + roomWidth && collide[y][x].y + blockSize > roomY && collide[y][x].y < roomHeight) collide[y][x].draw(g);
	}
}
