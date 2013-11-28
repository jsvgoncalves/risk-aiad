package util;

import java.util.ArrayList;
import java.util.Collections;

public class NameGenerator {
	// declares an array of integers
	private static ArrayList<String> names = new ArrayList<String>(); 

	private static void populate() {
		names.add("Joao");
		names.add("Maria");
		names.add("Manuel");
		names.add("Antonio");
		names.add("Idalina");
		names.add("Ze");
		names.add("Pedro");
	}

	public static ArrayList<String> randomName(int range) {
		populate();
		Collections.shuffle(names);
		return (ArrayList<String>) names;
	}

}
