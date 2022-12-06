import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Dictionary {
	static ArrayList<String> dic;
	
	public Dictionary (String filename) {
		this.dic = Dict(filename);
	}
	public static ArrayList<String> Dict(String filename) {
		ArrayList<String> dic = new ArrayList<>();

		try {
			Scanner input = new Scanner(new File(filename));
			while(input.hasNext()){
				dic.add(input.nextLine());
			}
			input.close();
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return dic;

	}
	//Random util learned from Geeks for Geeks//
	static String word() {
		Random rando = new Random();
		int randomIndex = rando.nextInt(dic.size());
		return dic.get(randomIndex);
	}
}
