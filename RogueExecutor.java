import java.util.Random;
import java.util.Scanner;

public class RogueExecutor {

	static Map[][] map;
	static int boundarySize = 10;
	int x;
	int y;
	static boolean isGameOver = false;
	static String consoleTxt = "";
	
	
	
	public static void main(String[] args){
		
		gettingMapSize();
		generateMap();
		
		
		if(isGameOver)
			System.exit(0);
		
	}


	private static void gettingMapSize() {
		
		Scanner sc = new Scanner(System.in);

        while (boundarySize < 10 || boundarySize > 20) {
        	consoleTxt += "\nMAP SIZE (10-20): ";
            while(!sc.hasNextInt()) {
            	consoleTxt += "\nTRY TO USE NUMBERS IF YOU CAN: ";
                sc.next();
            }
            boundarySize = sc.nextInt();
            if (boundarySize < 10 || boundarySize > 20) {
            	consoleTxt += "\n'(10-20)' MEANS; YOU CAN ONLY USE NUMBERS BETWEEN 10 TO 20. SO PLEASE... ";
            }
        }
        sc.close();
        consoleTxt = "START!";
	}

	private static void generateMap(){
		map = new Map[boundarySize][boundarySize];
	}
	
	

	
}

class Player {
	
	int level;
	int score;
	int x;
	int y;
	
	public Player(int x, int y){
		
		level = 0;
		score = 0;
		this.x = x;
		this.y = y;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore(int point) {
		this.score = score + point;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}

class Map {
	
	boolean explored;
	boolean isTrap;
	boolean isEnemy;
	boolean isSword;
	boolean isGold;

	public Map[][] getMap(){
		return RogueExecutor.map;
	}
	
	public boolean isExplored(){
		return explored;
	}
	
	
}

