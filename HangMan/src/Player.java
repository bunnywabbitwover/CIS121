

public class Player implements Comparable<Player>{
	protected int score;
	protected String name;

	public Player(int score, String name) {
		this.name = name;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name + " ...." + score;
	}

	public int compareTo(Player o) {
		return score - o.score;
		
	}

}