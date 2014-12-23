import java.util.Random;
import java.util.Scanner;

public class RogueExecutor {

	static Map[][] map;
	static int boundarySize = 10;
	int x;
	int y;
	static boolean isGameOver = false;
	static String consoleTxt = "";
	static boolean hasInteracted = false;
	
	
	public RogueExecutor(){
		getMapSize();
		generateMap();
	}
	
	public static void main(String[] args){
		
		
		if(isGameOver)
			System.exit(0);
		
	}



	private static void gettingMapSize() {
		
	JFrame messageFrame = new JFrame();
		messageFrame.setTitle("Rogue Game");
		
		/*Scanner sc = new Scanner(System.in);

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
        consoleTxt = "START!";*/
	}

	private static void generateMap(){
		map = new Map[boundarySize][boundarySize];
	}
	
	

	
}

class Player {
	
	static int level;
	static int score;
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
		Player.level = level;
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

class Enemy {
	
	static int level;
	int x;
	int y;
	
	public Enemy(int x, int y){
		
		this.x = x;
		this.y = y;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		Enemy.level = level;
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
	
	int mapSize = RogueExecutor.boundarySize;

	public Map[][] getMap(){
		return RogueExecutor.map;
	}
	
	public boolean isExplored(){
		return explored;
	}
	
	public void placer(){
		
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				
				//
				
				
			
			}
			
		}
		
	}
	
	public void checkRoom(){
		
		if(isTrap){
			
			RogueExecutor.consoleTxt += "\nYOU ACTIVATED A TRAP AND DIED!\nGAME OVER!";
			RogueExecutor.isGameOver = true;
		}
		
		if(isEnemy){
			
			RogueExecutor.consoleTxt += "\nYOU HAVE ENCOUNTERED AN ENEMY! GET READY FOR BATTLE!";
			if(RogueExecutor.hasInteracted){
				
				if(Player.level >= Enemy.level){
					
					RogueExecutor.consoleTxt += "\nYOU WON .........";
					Player.score += 10 * Enemy.level;
					
				}else{
					
					RogueExecutor.consoleTxt += "\nYOU LOST, DIED .........";
					RogueExecutor.isGameOver = true;
					
				}
			}
			
		}
		
		if(isSword){
			
			if(Player.level < 4){
				
				RogueExecutor.consoleTxt += "\nYOU HAVE FOUND A SWORD. NOW YOU CAN FACE THE TOUGHER MONSTERS!";
				Player.level++;
			}
			else{
				RogueExecutor.consoleTxt += "\nYOU HAVE FOUND SOME GOLD! 'SHINY!'";
				Player.score += 10;
			}
			
		}
		
		if(isGold){
			
			RogueExecutor.consoleTxt += "\nYOU HAVE FOUND SOME GOLD! 'SHINY!'";
			Player.score += 10;
		}
	}
	
	
}
