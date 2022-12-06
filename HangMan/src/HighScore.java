import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScore{

	ArrayList<Player> player;

	public HighScore(ArrayList<Player> player) {
		this.player = player;
	}

	//read topFive file and return a Player ArrayList//method completed with help of Ashley C's pseudo code
	public static ArrayList<Player> readDataFile() {
		ArrayList<Player> pList = new ArrayList<>();
		String wholeLine;
		int index = 0;
		String name = "";
		int score = 0;
		try {
			
			File file = new File("res/TopFive.txt");
			
			if(file.exists()) {
				Scanner input = new Scanner(file);
				while(input.hasNext()) {
					wholeLine = input.nextLine();
					index = wholeLine.indexOf(" ....");
					name = wholeLine.substring(0,index);
					score = Integer.parseInt(wholeLine.substring(index + 5));
					pList.add(new Player(score, name));
				}
				input.close();
			}
		}catch(FileNotFoundException e) {
			System.out.println("File not Found");
		}
		return pList;
	}
	//if player qualifies they are added to topFive file
	public static void printFile(ArrayList<Player> players)  {
		File file =new File("res/TopFive.txt");
		try(PrintWriter output = new PrintWriter(file)){

			for(Player x: players) {
				output.println(x.toString());
			}
			//System.out.println("Player added to file."); //confirm player added
		}catch(FileNotFoundException e){
			System.out.println("File Not Found");   	
		}
	}
	//print topFive
	public static void printTopFive(File topFive) {
		System.out.println("\nCongradulations you made the Top Five!");
		System.out.println("High Scores: \n");
		try (Scanner in = new Scanner(topFive)) {
			while(in.hasNext()) {
				System.out.println(in.nextLine());
			}
			System.out.println();
		}catch(FileNotFoundException e) {
			System.out.println("File Not Found.");
		}
	}
}






