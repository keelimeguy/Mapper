import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class Screen extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	public Thread thread = new Thread(this);
	public static Frame frame;

	public static double fps = 60.0;

	public static Image[] tilesetGround = new Image[100];
	public static Image[] tilesetRes = new Image[10];
	public static Image[] tilesetCol = new Image[16];

	public static int myWidth, myHeight;
	public static String tileBaseRes, tileBaseGround;
	public static int tileSizeGround = 26, tileSizeRes = 26, tileSizeCol = 26;
	public static int lastId = 99;

	public static boolean isFirst = true;
	public static int mode = 0;

	public static Point mse = new Point(0, 0);
	public JTextField inWidth = null, inHeight = null, inFrame = null;
	public JScrollPane frameListScrollPane, viewListScrollPane;
	public JButton bWidth, bHeight, bFrame, bView;
	public JCheckBox bBase, bOver, bTop, bCollide;

	public static Room room;
	public static Save save;
	public static Store store;

	public Screen(Frame frame) {
		Screen.frame = frame;
		frame.addMouseListener(new KeyHandler());
		frame.addMouseMotionListener(new KeyHandler());

		inHeight = new JTextField(3);
		bHeight = new JButton("Set Height");
		inWidth = new JTextField(3);
		bWidth = new JButton("Set Width");
		bFrame = new JButton("Set Frame");
		bView = new JButton("Edit Tiles");

		inWidth.setVisible(true);
		inWidth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldWidth = Integer.parseInt(inWidth.getText());
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block, Screen.room.over, Screen.room.top, Screen.room.collide, Screen.room.showTile, Screen.room.curView);
			}
		});

		bWidth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldWidth = Integer.parseInt(inWidth.getText());
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block, Screen.room.over, Screen.room.top, Screen.room.collide, Screen.room.showTile, Screen.room.curView);
			}
		});

		add(inWidth);
		add(bWidth);

		inHeight.setVisible(true);
		inHeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldHeight = Integer.parseInt(inHeight.getText());
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block, Screen.room.over, Screen.room.top, Screen.room.collide, Screen.room.showTile, Screen.room.curView);
			}
		});

		bHeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldHeight = Integer.parseInt(inHeight.getText());
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block, Screen.room.over, Screen.room.top, Screen.room.collide, Screen.room.showTile, Screen.room.curView);

			}
		});

		add(inHeight);
		add(bHeight);

		DefaultListModel<String> frameName = new DefaultListModel<String>();

		frameName.addElement("9");
		frameName.addElement("8");
		frameName.addElement("7");
		frameName.addElement("6");
		frameName.addElement("5");
		frameName.addElement("4");
		frameName.addElement("3");
		frameName.addElement("2");
		frameName.addElement("1");
		frameName.addElement("0");

		final JList<String> frameList = new JList<String>(frameName);
		frameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frameList.setSelectedIndex(3);
		frameList.setVisibleRowCount(1);

		frameListScrollPane = new JScrollPane(frameList);
		frameListScrollPane.getVerticalScrollBar().setValue(frameListScrollPane.getVerticalScrollBar().getMaximum());

		bFrame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.worldFrame = 9 - frameListScrollPane.getVerticalScrollBar().getValue() / 18;
				frameList.setSelectedIndex((Screen.room.worldFrame + 2) % 10);
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block, Screen.room.over, Screen.room.top, Screen.room.collide, Screen.room.showTile, Screen.room.curView);
			}
		});

		add(frameListScrollPane);
		add(bFrame);

		bBase = new JCheckBox("Base");
		bBase.setSelected(true);

		bBase.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED)
					Screen.room.showTile[0] = false;
				else if (e.getStateChange() == ItemEvent.SELECTED) Screen.room.showTile[0] = true;
			}
		});

		add(bBase);

		bOver = new JCheckBox("Over");
		bOver.setSelected(true);

		bOver.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED)
					Screen.room.showTile[1] = false;
				else if (e.getStateChange() == ItemEvent.SELECTED) Screen.room.showTile[1] = true;
			}
		});

		add(bOver);

		bTop = new JCheckBox("Top");
		bTop.setSelected(true);

		bTop.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED)
					Screen.room.showTile[2] = false;
				else if (e.getStateChange() == ItemEvent.SELECTED) Screen.room.showTile[2] = true;
			}
		});

		add(bTop);

		bCollide = new JCheckBox("Collide");
		bCollide.setSelected(true);

		bCollide.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED)
					Screen.room.showTile[3] = false;
				else if (e.getStateChange() == ItemEvent.SELECTED) Screen.room.showTile[3] = true;
			}
		});

		add(bCollide);

		DefaultListModel<String> viewName = new DefaultListModel<String>();

		viewName.addElement("Base");
		viewName.addElement("Over");
		viewName.addElement("Top");
		viewName.addElement("Collide");

		final JList<String> viewList = new JList<String>(viewName);
		viewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		viewList.setSelectedIndex(0);
		viewList.setVisibleRowCount(1);

		viewListScrollPane = new JScrollPane(viewList);
		viewListScrollPane.getVerticalScrollBar().setValue(viewListScrollPane.getVerticalScrollBar().getMaximum());

		bView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.room.curView = viewListScrollPane.getVerticalScrollBar().getValue() / 18;
				viewList.setSelectedIndex((Screen.room.curView + 2) % 4);
				Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block, Screen.room.over, Screen.room.top, Screen.room.collide, Screen.room.showTile, Screen.room.curView);
			}
		});

		add(viewListScrollPane);
		add(bView);

		thread.start();
	}

	@SuppressWarnings("unused")
	private void setFrame(int val) {
		Screen.room.worldFrame = val;
		Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block, Screen.room.over, Screen.room.top, Screen.room.collide, Screen.room.showTile, Screen.room.curView);
	}

	@SuppressWarnings("unused")
	private void setView(int val) {
		Screen.room.curView = val;
		Screen.room.redefine(Screen.room.xOff, Screen.room.yOff, Screen.room.block, Screen.room.over, Screen.room.top, Screen.room.collide, Screen.room.showTile, Screen.room.curView);
	}

	public void define(String file) {
		save = new Save();
		room = new Room();

		store = new Store();

		save.loadSave(new File("Save/" + file + ".kmap"));

		inHeight.setText("" + room.worldHeight);
		inWidth.setText("" + room.worldWidth);

		store.define();

		defineTile();
	}

	public void defineTile() {
		for (int i = 0; i < tilesetGround.length; i++) {
			tilesetGround[i] = new ImageIcon("res/" + tileBaseGround + ".png").getImage();
			tilesetGround[i] = createImage(new FilteredImageSource(tilesetGround[i].getSource(), new CropImageFilter(tileSizeGround * (i % Store.tileGroupWidth), tileSizeGround * Math.floorDiv(i, Store.tileGroupWidth), tileSizeGround, tileSizeGround)));
			tilesetGround[i] = createImage(new FilteredImageSource(tilesetGround[i].getSource(), new RGBImageFilter() {

				// the color we are looking for... Alpha bits are set to opaque
				public int markerRGB = 0xffff00ff;

				public final int filterRGB(int x, int y, int rgb) {
					if ((rgb | 0xff000000) == markerRGB) {
						// Mark the alpha bits as zero - transparent
						return 0x00ffffff & rgb;
					} else {
						// nothing to do
						return rgb;
					}
				}
			}));
		}

		for (int i = 0; i < tilesetRes.length; i++) {
			tilesetRes[i] = new ImageIcon("res/" + tileBaseRes + ".png").getImage();
			tilesetRes[i] = createImage(new FilteredImageSource(tilesetRes[i].getSource(), new CropImageFilter(0, tileSizeRes * i, tileSizeRes, tileSizeRes)));
		}

		for (int i = 0; i < tilesetCol.length; i++) {
			tilesetCol[i] = new ImageIcon("res/tileset_col.png").getImage();
			tilesetCol[i] = createImage(new FilteredImageSource(tilesetCol[i].getSource(), new CropImageFilter(0, tileSizeCol * i, tileSizeCol, tileSizeCol)));
		}
	}

	public static void resizeTileset() {
		tilesetGround = new Image[Store.shopSize * Store.tileGroupWidth * Store.tileGroupHeight];
		lastId = Store.shopSize * Store.tileGroupWidth * Store.tileGroupHeight - 1;
	}

	public void paintComponent(Graphics g) {

		if (isFirst) {
			myWidth = frame.getContentPane().getWidth();
			myHeight = frame.getContentPane().getHeight();
			define("test_ground");
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

		g.fillRect(store.scroll[2].x + store.scroll[2].width / 2 - 1, myHeight / 2 - Store.shopWidth * (Store.buttonSize * Store.tileGroupHeight + Store.cellSpace) / 2 + Store.buttonSize * Store.shopWidth, 3, Store.buttonSize * 6 + Store.cellSpace);

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
				frame.setTitle(Frame.title + " | ups: " + updates + " | fps: " + frames);
				updates = 0;
				frames = 0;
			}
		}
	}
}
