import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class Frame extends JFrame {
	public static String title = "Mapper";
	public static Dimension size = new Dimension(1200, 700);
	public Screen screen;
	
	public Frame() {
		setTitle(title);
		setSize(size);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		init();
	}
	
	public void init() {
		setLayout(new GridLayout(1, 1, 0, 0));

		screen = new Screen(this);
		add(screen);

		setVisible(true);
	}

	public static void main(String[] args) {
		Frame frame = new Frame();
	}
}
