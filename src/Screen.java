import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Screen extends JPanel implements Runnable {
	public Thread thread = new Thread(this);
	public static Frame frame;

	public static double fps = 60.0;

	public static Image[] tilesetGround = new Image[100];
	public static Image[] tilesetRes = new Image[10];

	public static int myWidth, myHeight;
	public static String tileBaseRes, tileBaseGround;
	public static int tileSizeGround = 26, tileSizeRes = 26;

	public static boolean isFirst = true;
	public static int mode = 0;

	public static Point mse = new Point(0, 0);
	public JTextField inWidth = null, inHeight = null, inFrame = null;
	public JButton bWidth, bHeight, bFrame;

	public static Room room;
	public static Save save;
	public static Store store;

	public Screen(Frame frame) {
		this.frame = frame;
		frame.addMouseListener(new KeyHandler());
		frame.addMouseMotionListener(new KeyHandler());

		inHeight = new JTextField(3);
		bHeight = new JButton("Set Height");
		inWidth = new JTextField(3);
		bWidth = new JButton("Set Width");
		inFrame = new JTextField(3);
		bFrame = new JButton("Set Frame");

		inWidth.setVisible(true);
		inWidth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldWidth = Integer.parseInt(inWidth.getText());
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block);
			}
		});
		bWidth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldWidth = Integer.parseInt(inWidth.getText());
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block);
			}
		});
		add(inWidth);
		add(bWidth);

		inHeight.setVisible(true);
		inHeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldHeight = Integer.parseInt(inHeight.getText());
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block);
			}
		});
		bHeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldHeight = Integer.parseInt(inHeight.getText());
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block);

			}
		});
		add(inHeight);
		add(bHeight);

		inFrame.setVisible(true);
		inFrame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldFrame = Integer.parseInt(inFrame.getText());
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block);
			}
		});
		bFrame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Integer.parseInt(inFrame.getText()) >= 0 && Integer.parseInt(inFrame.getText()) < 10) {
					Screen.room.worldFrame = Integer.parseInt(inFrame.getText());
					Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block);
				} else
					inFrame.setText(Screen.room.worldFrame + "");
			}
		});
		add(inFrame);
		add(bFrame);

		thread.start();

	}

	public void define(String file) {
		save = new Save();
		room = new Room();

		store = new Store();

		save.loadSave(new File("Save/" + file + ".kmap"));

		inHeight.setText("" + room.worldHeight);
		inWidth.setText("" + room.worldWidth);
		inFrame.setText("" + room.worldFrame);

		store.define();

		defineTile();
	}

	public void defineTile() {
		for (int i = 0; i < tilesetGround.length; i++) {
			tilesetGround[i] = new ImageIcon("res/" + tileBaseGround + ".png").getImage();
			tilesetGround[i] = createImage(new FilteredImageSource(tilesetGround[i].getSource(), new CropImageFilter(tileSizeGround * (i % store.tileGroupWidth), tileSizeGround * Math.floorDiv(i, store.tileGroupWidth), tileSizeGround, tileSizeGround)));
		}

		for (int i = 0; i < tilesetRes.length; i++) {
			tilesetRes[i] = new ImageIcon("res/" + tileBaseRes + ".png").getImage();
			tilesetRes[i] = createImage(new FilteredImageSource(tilesetRes[i].getSource(), new CropImageFilter(0, tileSizeRes * i, tileSizeRes, tileSizeRes)));
		}
	}

	public static void resizeTileset() {
		tilesetGround = new Image[store.shopSize * store.tileGroupWidth * store.tileGroupHeight];
	}

	public void paintComponent(Graphics g) {

		if (isFirst) {
			myWidth = frame.getContentPane().getWidth();
			myHeight = frame.getContentPane().getHeight();
			define("test_real");
			isFirst = false;
		}

		g.setColor(new Color(110, 110, 110));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(255, 255, 255));
		g.drawLine(room.block[0][0].x - 1, room.block[0][0].y - 1, room.block[0][0].x - 1, room.block[room.worldHeight - 1][0].y + room.blockSize);
		g.drawLine(room.block[0][room.worldWidth - 1].x + room.blockSize, room.block[0][0].y - 1, room.block[0][room.worldWidth - 1].x + room.blockSize, room.block[room.worldHeight - 1][0].y + room.blockSize);
		g.drawLine(room.block[0][0].x - 1, room.block[room.worldHeight - 1][0].y + room.blockSize, room.block[0][room.worldWidth - 1].x + room.blockSize, room.block[room.worldHeight - 1][0].y + room.blockSize);
		g.drawLine(room.block[0][0].x - 1, room.block[0][0].y - 1, room.block[0][room.worldWidth - 1].x + room.blockSize, room.block[0][0].y - 1);

		g.setColor(new Color(0, 0, 0));
		g.drawLine(room.block[0][0].x - 2, room.block[0][0].y - 2, room.block[0][0].x - 2, room.block[room.worldHeight - 1][0].y + room.blockSize + 1);
		g.drawLine(room.block[0][room.worldWidth - 1].x + room.blockSize + 1, room.block[0][0].y - 2, room.block[0][room.worldWidth - 1].x + room.blockSize + 1, room.block[room.worldHeight - 1][0].y + room.blockSize + 1);
		g.drawLine(room.block[0][0].x - 2, room.block[room.worldHeight - 1][0].y + room.blockSize + 1, room.block[0][room.worldWidth - 1].x + room.blockSize + 1, room.block[room.worldHeight - 1][0].y + room.blockSize + 1);
		g.drawLine(room.block[0][0].x - 2, room.block[0][0].y - 2, room.block[0][room.worldWidth - 1].x + room.blockSize + 1, room.block[0][0].y - 2);

		room.draw(g);

		g.setColor(new Color(90, 90, 90));
		g.fillRect(0, 0, room.roomX, room.roomHeight);

		g.setColor(new Color(200, 200, 200));

		g.fillRect(store.scroll[2].x + store.scroll[2].width / 2 - 1, myHeight / 2 - store.shopWidth * (store.buttonSize * store.tileGroupHeight + store.cellSpace) / 2 + store.buttonSize * 3, 3, store.buttonSize * 6 + store.cellSpace);

		store.draw(g);

	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / fps;
		double delta = 0;
		int updates = 0, frames = 0;

		while (true) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			// Update 60 times a second
			while (delta >= 1 && !isFirst) {
				//update();
				updates++;

				room.physics();

				delta--;
			}

			repaint();
			frames++;

			// Keep track of and display the game's ups and fps every second
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle(frame.title + " | ups: " + updates + " | fps: " + frames);
				updates = 0;
				frames = 0;
			}
		}
	}
}
