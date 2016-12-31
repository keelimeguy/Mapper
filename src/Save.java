import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Save {
	public void saveMap(File loadPath) {
		FileWriter fw = null;
		try {
			if (!loadPath.exists()) {
				loadPath.createNewFile();
			}
			fw = new FileWriter(loadPath);
			fw.write(Screen.tileBaseGround + " ");
			fw.write(Screen.tileSizeGround + " ");
			fw.write(Store.shopSize + " ");
			fw.write(Store.tileGroupWidth + " ");
			fw.write(Store.tileGroupHeight + "  " + System.getProperty("line.separator"));
			fw.write(Screen.tileBaseRes + " ");
			fw.write(Screen.tileSizeRes + "  " + System.getProperty("line.separator"));
			fw.write(Screen.room.blockSize + "  " + System.getProperty("line.separator"));
			fw.write(Screen.room.worldWidth + " " + Screen.room.worldHeight + "  " + System.getProperty("line.separator"));
			for (int y = 0; y < Screen.room.block.length; y++) {
				fw.write(System.getProperty("line.separator"));
				for (int x = 0; x < Screen.room.block[0].length; x++) {
					fw.write((int) (Screen.room.block[y][x].groundId) + " ");
				}
			}
			fw.write(System.getProperty("line.separator"));
			
			for (int f = 0; f < 10; f++) {
				for (int y = 0; y < Screen.room.over.length; y++) {
					fw.write(System.getProperty("line.separator"));
					for (int x = 0; x < Screen.room.over[0].length; x++) {
						fw.write((int) (Screen.room.over[y][x].groundId[f]) + " ");
					}
				}
				fw.write(System.getProperty("line.separator"));
			}
			
			for (int f = 0; f < 10; f++) {
				for (int y = 0; y < Screen.room.top.length; y++) {
					fw.write(System.getProperty("line.separator"));
					for (int x = 0; x < Screen.room.top[0].length; x++) {
						fw.write((int) (Screen.room.top[y][x].groundId[f]) + " ");
					}
				}
				fw.write(System.getProperty("line.separator"));
			}
			
			for (int y = 0; y < Screen.room.collide.length; y++) {
				fw.write(System.getProperty("line.separator"));
				for (int x = 0; x < Screen.room.collide[0].length; x++) {
					fw.write((int) (Screen.room.collide[y][x].groundId) + " ");
				}
			}
			fw.write(System.getProperty("line.separator"));
			
			fw.flush();
			fw.close();
			System.out.println("File written Succesfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSave(File loadPath) {
		
		try {
			Scanner loadScanner = new Scanner(loadPath);
			if (loadScanner.hasNext()) {
				Screen.tileBaseGround = loadScanner.next();
				Screen.tileSizeGround = loadScanner.nextInt();
				Store.shopSize = loadScanner.nextInt();
				Store.tileGroupWidth = loadScanner.nextInt();
				Store.tileGroupHeight = loadScanner.nextInt();
				if (Store.shopSize * Store.tileGroupWidth * Store.tileGroupHeight > Screen.tilesetGround.length) Screen.resizeTileset();
				Store.shopWidth = 12 / Store.tileGroupHeight;
				Screen.tileBaseRes = loadScanner.next();
				Screen.tileSizeRes = loadScanner.nextInt();
				Screen.room.blockSize = loadScanner.nextInt();
				Screen.room.setDimensions(loadScanner.nextInt(), loadScanner.nextInt());
				for (int y = 0; y < Screen.room.block.length; y++)
					for (int x = 0; x < Screen.room.block[0].length; x++) {
						Screen.room.block[y][x].groundId = loadScanner.nextInt();
					}
			
				for (int f = 0; f < 10; f++) {
					for (int y = 0; y < Screen.room.over.length; y++)
						for (int x = 0; x < Screen.room.over[0].length; x++) {
							Screen.room.over[y][x].groundId[f] = loadScanner.nextInt();
						}
				}
			
				for (int f = 0; f < 10; f++) {
					for (int y = 0; y < Screen.room.top.length; y++)
						for (int x = 0; x < Screen.room.top[0].length; x++) {
							Screen.room.top[y][x].groundId[f] = loadScanner.nextInt();
						}
				}
			
				for (int y = 0; y < Screen.room.collide.length; y++)
					for (int x = 0; x < Screen.room.collide[0].length; x++) {
						Screen.room.collide[y][x].groundId = loadScanner.nextInt();
					}
			}
			
			loadScanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
