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
			fw.write(Screen.store.shopSize + "\n");
			fw.write(Screen.store.tileGroupWidth + " ");
			fw.write(Screen.store.tileGroupHeight + " ");
			fw.write(Screen.tileBaseRes + " ");
			fw.write(Screen.tileSizeRes + "\n");
			fw.write(Screen.room.blockSize + "\n");
			fw.write(Screen.room.worldWidth + " " + Screen.room.worldHeight + "\n");
			for (int y = 0; y < Screen.room.block.length; y++) {
				fw.write("\n");
				for (int x = 0; x < Screen.room.block[0].length; x++) {
					fw.write((int) (Screen.room.block[y][x].groundId) + " ");
				}
			}
			fw.write("\n");
			for (int y = 0; y < Screen.room.block.length; y++) {
				fw.write("\n");
				for (int x = 0; x < Screen.room.block[0].length; x++) {
					fw.write((int) (Screen.room.block[y][x].ResId) + " ");
				}
			}
			fw.write("\n");
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

			while (loadScanner.hasNext()) {
				Screen.tileBaseGround = loadScanner.next();
				Screen.tileSizeGround = loadScanner.nextInt();
				Screen.store.shopSize = loadScanner.nextInt();
				Screen.store.tileGroupWidth = loadScanner.nextInt();
				Screen.store.tileGroupHeight = loadScanner.nextInt();
				if (Screen.store.shopSize * Screen.store.tileGroupWidth * Screen.store.tileGroupHeight > Screen.tilesetGround.length) Screen.resizeTileset();
				Screen.store.shopWidth = 12 / Screen.store.tileGroupHeight;
				Screen.tileBaseRes = loadScanner.next();
				Screen.tileSizeRes = loadScanner.nextInt();
				Screen.room.blockSize = loadScanner.nextInt();
				Screen.room.setDimensions(loadScanner.nextInt(), loadScanner.nextInt());
				for (int y = 0; y < Screen.room.block.length; y++)
					for (int x = 0; x < Screen.room.block[0].length; x++) {
						Screen.room.block[y][x].groundId = loadScanner.nextInt();
					}

				for (int y = 0; y < Screen.room.block.length; y++)
					for (int x = 0; x < Screen.room.block[0].length; x++) {
						Screen.room.block[y][x].ResId = loadScanner.nextInt();
					}
			}

			loadScanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
