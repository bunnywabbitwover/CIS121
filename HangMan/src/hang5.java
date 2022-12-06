
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;



public class hang5 {
	static ArrayList<Character> guessed = new ArrayList<Character>();
	public static void main(String[] arg) {

		new Dictionary("res/dictionary.txt");
		String word = Dictionary.word();
		char[] letters = new char[word.length()];
		ArrayList<Character> wrongGuess = new ArrayList<Character>();
		ArrayList<Character> rightGuess = new ArrayList<Character>();
		ArrayList<Player> players = new ArrayList<Player>(5);

		System.out.println(word);

		for (int i = 0; i < letters.length; i++) {
			letters[i] = '-';
		}
		System.out.print("Hidden word: " );
		for (int i = 0; i < letters.length; i++) {
			System.out.print(letters[i]); 

		}
		System.out.println();


		String name = "";
		int score = 0;
		int live = 7;
		char letter = 0;
		Scanner input = new Scanner (System.in);

		while(live > 0) {
			boolean isValid = false;


			getWrongGuess(wrongGuess);

			System.out.println("Guesses left: " + live);
			System.out.println("Score: " + score);
			System.out.println("Enter letter: ");
			while(!isValid) {
				letter = getValidLetter(input);
				if(guessed.contains(Character.toUpperCase(letter))) {
					System.out.println("You have already entered the letter " + letter);

				}else {
					guessed.add(letter);
					isValid = true;
				}
			}

			boolean correctGuess = false;
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if(Character.toUpperCase(c) == Character.toUpperCase(letter)) {
					letters[i] = c;
					correctGuess = true;
					score+=10;
				}
			}

			if(!correctGuess){
				wrongGuess.add(letter);
				System.out.println("Sorry, there were no "+ letter + "’s");
				live--;
			}


			boolean gameGoing = true;
			System.out.print("Hidden word: ");
			for (int i = 0; i < letters.length; i++) {
				if(letters[i] == '-') {
					gameGoing = false;
				}
				System.out.print(letters[i]); 

			}
			System.out.println();

			if(gameGoing){
				System.out.println("You win!!");
				score = 100 + (live * 30) + score;
				System.out.println(score);

				word = Dictionary.word();
				letters = new char[word.length()];
				System.out.println(word);
				live = 7;
				//score = 0;
				guessed.clear();
				wrongGuess.clear();
				
				for (int i = 0; i < letters.length; i++) {
					letters[i] = '-';
				}
				System.out.print("Hidden word: " );
				for (int i = 0; i < letters.length; i++) {
					System.out.print(letters[i]); 

				}
				System.out.println();


			}


		}
		if(live == 0) {
			System.out.println("You lose!! The word was: " + word);
			
			System.out.println("Your score was: " + score);
			players = HighScore.readDataFile();
			if(checkIfTopFive(players, score)){
				System.out.println("Please Enter your name: ");
				name = getValidName(input);	
				Player a = new Player(score, name);
				
				if(players.size() < 5) {
					players.add(a);
				}else {
					players.set(4,  a);
				}
			}
			File Allplayer = new File("res/Allplayer.txt");
			players.sort((Player p1, Player p2)->p2.getScore()-p1.getScore()); //Lambda by Jim
			HighScore.printFile(players);
			HighScore.printTopFive(Allplayer);
		}
	}

	public static boolean checkIfTopFive(ArrayList<Player> player, int score) {
		if(player.size() < 5) return true;
		for (int i = 0; i < player.size(); i++) {
			if(score > player.get(i).getScore()){
				return true;

			}
		}

		return false;
	}

	private static void getWrongGuess(ArrayList<Character> wrongGuess) {

		Collections.sort(wrongGuess);
		System.out.print("Incorrect Guesses: ");
		for(Character c: wrongGuess) {
			System.out.print(Character.toUpperCase(c) + ",");
		}
		System.out.println();
	}



	public static String getValidName(Scanner input){
		String name = "";
		boolean isTrue = true;
		while(isTrue) {
			try {
				name = input.nextLine();
				if (name.equals("") || name.charAt(0) == ' ') {
					throw new InputMismatchException();
				}
				else {
					for(int i = 0; i < name.length(); i ++){
						char c = name.charAt(i);

						if(!Character.isLetter(c) && !Character.isWhitespace(c) && name.charAt(i) != '-') {
							throw new InputMismatchException();
						}
					}
					isTrue = false;
				}
			}catch (InputMismatchException e) {
				System.out.println("Please enter a name with no symbols or numbers:");
			}
		}
		return name;
	}

	public static char getValidLetter(Scanner input){
		String word = "";
		boolean isTrue = true;

		while(isTrue) {
			try {
				word = (input.nextLine());
				if (word.equals("") || word.charAt(0) == ' ') {
					throw new InputMismatchException();

				}else if(word.length() > 1) {
					throw new IllegalArgumentException();
				}else {

					char c = word.charAt(0);

					if(!Character.isLetter(c) && !Character.isWhitespace(c)) {
						throw new InputMismatchException();
					}


				}
				isTrue = false;

			}catch (InputMismatchException e) {
				System.out.println("Please enter a letter with no symbols or numbers:");
			}catch (IllegalArgumentException e) {
				System.out.println("Please enter a single letter: ");
			}
		}
		return word.toUpperCase().charAt(0);
	}
}



