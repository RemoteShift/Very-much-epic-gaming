package game.engine.dataloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;

public class DataLoader {
	private static final String TITANS_FILE_NAME = "titans.csv";
	private static final String WEAPONS_FILE_NAME = "weapons.csv";
	private static String line;

	public static HashMap<Integer, TitanRegistry> readTitanRegistry() throws IOException {
		final BufferedReader brT = new BufferedReader(new FileReader(TITANS_FILE_NAME));
		HashMap<Integer, TitanRegistry> result = new HashMap<Integer, TitanRegistry>();

		while ((line = brT.readLine()) != null) {
			String[] column = line.split(",");
			result.put(Integer.parseInt(column[0]),
					new TitanRegistry(Integer.parseInt(column[0]), Integer.parseInt(column[1]),
							Integer.parseInt(column[2]), Integer.parseInt(column[3]), Integer.parseInt(column[4]),
							Integer.parseInt(column[5]), Integer.parseInt(column[6])));
		}

		return result;

	}

	public static HashMap<Integer, WeaponRegistry> readWeaponRegistry() throws IOException {
		final BufferedReader brW = new BufferedReader(new FileReader(WEAPONS_FILE_NAME));
		HashMap<Integer, WeaponRegistry> result = new HashMap<Integer, WeaponRegistry>();

		while ((line = brW.readLine()) != null) {
			String[] column = line.split(",");
			WeaponRegistry weapon;

			if (column.length > 4)
				weapon = new WeaponRegistry(Integer.parseInt(column[0]), Integer.parseInt(column[1]),
						Integer.parseInt(column[2]), column[3], Integer.parseInt(column[4]),
						Integer.parseInt(column[5]));
			else
				weapon = new WeaponRegistry(Integer.parseInt(column[0]), Integer.parseInt(column[1]),
						Integer.parseInt(column[2]), column[3]);

			result.put(Integer.parseInt(column[0]), weapon);
		}

		return result;
	}

	public String getWEAPONS_FILE_NAME() {
		return WEAPONS_FILE_NAME;
	}

	public String getTITANS_FINAL_NAME() {
		return TITANS_FILE_NAME;
	}

}