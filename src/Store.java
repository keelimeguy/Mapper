import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Store {
	public static int shopWidth = 4, shopSize = 9;
	public static int tileGroupWidth = 3, tileGroupHeight = 3;
	public static int buttonSize = 48;
	public static int cellSpace = 2;
	public static int largeCellSpace = 21;
	public static int iconSize = 20;
	public static int iconSpace = 6;
	public static int iconTextY = 15;
	public static int itemIn = 4;
	public static int heldId = -1, realId = -1;
	public static int buttonId = 0;

	public Rectangle[] button;
	public Rectangle[] res = new Rectangle[3];
	public Rectangle[] scroll = new Rectangle[3];

	public boolean holdsItem = false;
	public boolean scrollSelected = false;

	public Store() {
		define();
	}

	public void click(int mouseButton) {

		if (mouseButton == 1) {
			for (int i = 0; i < button.length * (tileGroupWidth * tileGroupHeight); i += (tileGroupWidth * tileGroupHeight)) {
				for (int k = i; k < i + (tileGroupWidth * tileGroupHeight); k++) {
					if (new Rectangle(button[i / (tileGroupWidth * tileGroupHeight)].x + button[i / (tileGroupWidth * tileGroupHeight)].width * ((k % (tileGroupWidth * tileGroupHeight)) % tileGroupWidth) / tileGroupWidth, button[i / (tileGroupWidth * tileGroupHeight)].y + button[i / (tileGroupWidth * tileGroupHeight)].height * (int) (Math.floor((k % (tileGroupWidth * tileGroupHeight)) / tileGroupWidth)) / tileGroupHeight, button[i / (tileGroupWidth * tileGroupHeight)].width / tileGroupWidth, button[i / (tileGroupWidth * tileGroupHeight)].height / tileGroupHeight).contains(Screen.mse)) {
						int flagId = buttonId;
						if (flagId + i / (tileGroupWidth * tileGroupHeight) >= shopSize) flagId -= shopSize;
						heldId = flagId * (tileGroupWidth * tileGroupHeight) + i + (k % (tileGroupWidth * tileGroupHeight));
						realId = i;
						holdsItem = true;
					}
				}
			}
			if (res[1].contains(Screen.mse)) {
				Object[] possibilities = { "Yes", "No" };
				String file = (String) JOptionPane.showInputDialog(Screen.frame, "Are you sure you want to save this map and tileset?", "Save", JOptionPane.QUESTION_MESSAGE, null, possibilities, 0);
				if (file != null && !file.isEmpty() && file.equals("Yes")) {
					file = JOptionPane.showInputDialog(Screen.frame, "Enter a name for your save:");
					Screen.save.saveMap(new File("Save/" + file + ".kmap"));
				}
			} else if (res[2].contains(Screen.mse)) {
				Object[] possibilities = { "Load Map", "Load Tileset", "Cancel" };
				String file = (String) JOptionPane.showInputDialog(Screen.frame, "What would you like to load?", "Load", JOptionPane.QUESTION_MESSAGE, null, possibilities, 0);
				if (file != null && !file.isEmpty() && file.equals("Load Map")) {
					file = JOptionPane.showInputDialog(Screen.frame, "Enter the name of the map to load:");
					if (file != null && !file.isEmpty()) Screen.frame.screen.define(file);
				} else if (file != null && !file.isEmpty() && file.equals("Load Tileset")) {
					file = JOptionPane.showInputDialog(Screen.frame, "Enter the name of the tileset to load:");
					Screen.tileBaseGround = file;

					Screen.frame.screen.defineTile();
				}
			} else if (res[0].contains(Screen.mse))
				holdsItem = false;
			else if (scroll[0].contains(Screen.mse)) {
				buttonId++;
				if (buttonId >= shopSize) buttonId = 0;
			} else if (scroll[1].contains(Screen.mse)) {
				buttonId--;
				if (buttonId < 0) buttonId = shopSize - 1;
			} else if (scroll[2].contains(Screen.mse)) {
				scrollSelected = true;
			} else
				scrollSelected = false;

		}

	}

	public void define() {

		button = new Rectangle[shopWidth];

		for (int i = 0; i < button.length; i++)
			button[i] = new Rectangle(largeCellSpace, Screen.myHeight / 2 - shopWidth * (buttonSize * tileGroupHeight + cellSpace) / 2 + (buttonSize * tileGroupHeight + cellSpace) * i, tileGroupWidth * buttonSize, tileGroupHeight * buttonSize);
		res[0] = new Rectangle(largeCellSpace + tileGroupWidth * buttonSize + cellSpace, Screen.myHeight / 2 - shopWidth * (buttonSize * tileGroupHeight + cellSpace) / 2, buttonSize, buttonSize);
		for (int i = 1; i < res.length; i++)
			res[i] = new Rectangle(largeCellSpace + tileGroupWidth * buttonSize + cellSpace, Screen.myHeight / 2 - shopWidth * (buttonSize * tileGroupHeight + cellSpace) / 2 + (buttonSize * tileGroupHeight + cellSpace) * (shopWidth - 1) + buttonSize * tileGroupHeight - buttonSize * 3 + (buttonSize + cellSpace) * i, buttonSize, buttonSize);
		scroll[0] = new Rectangle(largeCellSpace, Screen.myHeight / 2 - shopWidth * (buttonSize * tileGroupHeight + cellSpace) / 2 - buttonSize * tileGroupHeight - cellSpace, tileGroupWidth * buttonSize, tileGroupHeight * buttonSize);
		scroll[1] = new Rectangle(largeCellSpace, Screen.myHeight / 2 - shopWidth * (buttonSize * tileGroupHeight + cellSpace) / 2 + (buttonSize * tileGroupHeight + cellSpace) * shopWidth, tileGroupWidth * buttonSize, tileGroupHeight * buttonSize);
		scroll[2] = new Rectangle(largeCellSpace + tileGroupWidth * buttonSize + cellSpace, Screen.myHeight / 2 - shopWidth * (buttonSize * tileGroupHeight + cellSpace) / 2 + buttonSize * 3 + (int) (((buttonSize * 6.0 + cellSpace) / Screen.room.maxSize) * (Screen.room.maxSize - Screen.room.blockSize)), buttonSize, buttonSize);
	}

	public void drag(Point mseOld) {
		if (!holdsItem && scrollSelected) {
			int dy = Screen.mse.y - mseOld.y;
			if (scroll[2].y + dy >= Screen.myHeight / 2 - shopWidth * (buttonSize * tileGroupHeight + cellSpace) / 2 + buttonSize * 3 - buttonSize / 2 && scroll[2].y + dy <= buttonSize * 6 + cellSpace + (Screen.myHeight / 2 - shopWidth * (buttonSize * tileGroupHeight + cellSpace) / 2 + buttonSize * 3) - buttonSize / 2) scroll[2].y += dy;
			int oldBlock = Screen.room.blockSize;
			Screen.room.blockSize = Screen.room.maxSize - (int) ((scroll[2].y - (Screen.myHeight / 2 - shopWidth * (buttonSize * tileGroupHeight + cellSpace) / 2 + buttonSize * 3 - buttonSize / 2)) / (buttonSize * 6.0 + cellSpace) * (Screen.room.maxSize - Screen.room.minSize));
			Screen.room.redefine(Screen.room.xOff * Screen.room.blockSize / oldBlock, Screen.room.yOff * Screen.room.blockSize / oldBlock, Screen.room.block);
		}
	}

	public void release(int mouseButton) {
		if (mouseButton == 1) scrollSelected = false;
	}

	public void draw(Graphics g) {

		for (int i = 0; i < button.length * (tileGroupWidth * tileGroupHeight); i += (tileGroupWidth * tileGroupHeight)) {
			for (int k = i; k < i + (tileGroupWidth * tileGroupHeight); k++) {
				if (new Rectangle(button[i / (tileGroupWidth * tileGroupHeight)].x + button[i / (tileGroupWidth * tileGroupHeight)].width * ((k % (tileGroupWidth * tileGroupHeight)) % tileGroupWidth) / tileGroupWidth, button[i / (tileGroupWidth * tileGroupHeight)].y + button[i / (tileGroupWidth * tileGroupHeight)].height * (int) (Math.floor((k % (tileGroupWidth * tileGroupHeight)) / tileGroupWidth)) / tileGroupHeight, button[i / (tileGroupWidth * tileGroupHeight)].width / tileGroupWidth, button[i / (tileGroupWidth * tileGroupHeight)].height / tileGroupHeight).contains(Screen.mse)) {
					g.setColor(new Color(255, 255, 255, 100));
					g.fillRect(button[i / (tileGroupWidth * tileGroupHeight)].x + button[i / (tileGroupWidth * tileGroupHeight)].width * ((k % (tileGroupWidth * tileGroupHeight)) % tileGroupWidth) / tileGroupWidth - itemIn, button[i / (tileGroupWidth * tileGroupHeight)].y + button[i / (tileGroupWidth * tileGroupHeight)].height * (int) (Math.floor((k % (tileGroupWidth * tileGroupHeight)) / tileGroupWidth)) / tileGroupHeight - itemIn, button[i / (tileGroupWidth * tileGroupHeight)].width / tileGroupWidth + 2 * itemIn, button[i / (tileGroupWidth * tileGroupHeight)].height / tileGroupHeight + 2 * itemIn);
				}
				int flagId = buttonId;
				if (flagId + i / (tileGroupWidth * tileGroupHeight) >= shopSize) flagId -= shopSize;
				if (flagId * (tileGroupWidth * tileGroupHeight) + i + (k % (tileGroupWidth * tileGroupHeight)) < Screen.tilesetGround.length)
					g.drawImage(Screen.tilesetGround[flagId * (tileGroupWidth * tileGroupHeight) + i + (k % (tileGroupWidth * tileGroupHeight))], button[i / (tileGroupWidth * tileGroupHeight)].x + button[i / (tileGroupWidth * tileGroupHeight)].width * ((k % (tileGroupWidth * tileGroupHeight)) % tileGroupWidth) / tileGroupWidth, button[i / (tileGroupWidth * tileGroupHeight)].y + button[i / (tileGroupWidth * tileGroupHeight)].height * (int) (Math.floor((k % (tileGroupWidth * tileGroupHeight)) / tileGroupWidth)) / tileGroupHeight, button[i / (tileGroupWidth * tileGroupHeight)].width / tileGroupWidth, button[i / (tileGroupWidth * tileGroupHeight)].height / tileGroupHeight, null);
			}
		}

		for (int i = 0; i < res.length; i++) {
			if (res[i].contains(Screen.mse)) {
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(res[i].x, res[i].y, res[i].width, res[i].height);
			}
			g.drawImage(Screen.tilesetRes[i], res[i].x + itemIn, res[i].y + itemIn, res[i].width - itemIn * 2, res[i].height - itemIn * 2, null);
		}

		if (scroll[0].contains(Screen.mse)) {
			g.setColor(new Color(255, 255, 255, 30));
			g.fillRect(scroll[0].x, scroll[0].y, scroll[0].width, scroll[0].height);
		}

		if (scroll[1].contains(Screen.mse)) {
			g.setColor(new Color(255, 255, 255, 30));
			g.fillRect(scroll[1].x, scroll[1].y, scroll[1].width, scroll[1].height);
		}

		g.drawImage(Screen.tilesetRes[3], scroll[0].x + itemIn, scroll[0].y + itemIn, scroll[0].width - itemIn * 2, scroll[0].height - itemIn * 2, null);
		g.drawImage(Screen.tilesetRes[4], scroll[1].x + itemIn, scroll[1].y + itemIn, scroll[1].width - itemIn * 2, scroll[1].height - itemIn * 2, null);

		g.drawImage(Screen.tilesetRes[5], scroll[2].x + itemIn, scroll[2].y + itemIn, scroll[2].width - itemIn * 2, scroll[2].height - itemIn * 2, null);

		if (holdsItem && Screen.room.blockSize < buttonSize) if (heldId < Screen.tilesetGround.length)
			g.drawImage(Screen.tilesetGround[heldId], Screen.mse.x - (Screen.room.blockSize - itemIn * 2) / 2 + itemIn, Screen.mse.y - (Screen.room.blockSize - itemIn * 2) / 2 + itemIn, Screen.room.blockSize - itemIn * 2, Screen.room.blockSize - itemIn * 2, null);
		else if (holdsItem && heldId < Screen.tilesetGround.length) g.drawImage(Screen.tilesetGround[heldId], Screen.mse.x - (buttonSize - itemIn * 2) / 2 + itemIn, Screen.mse.y - (buttonSize - itemIn * 2) / 2 + itemIn, buttonSize - itemIn * 2, buttonSize - itemIn * 2, null);

	}
}
