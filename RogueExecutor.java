import java.util.Random;
import java.util.Scanner;

public class RogueExecutor {

	static Map map;
	static int boundarySize = 10;
	int x;
	int y;
	static boolean isGameOver = false;
	static String consoleTxt = "";
	static boolean hasInteracted = false;
	
	
	public static void execute(){
		getMapSize();
		callMapGenerator();
		if(isGameOver)
			System.exit(0);
	}

	private static void getMapSize() {
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
	
	public static void callMapGenerator(){
		map = new Map();
		map.generateMap();
	}

}

class Player {
	
	static int level;
	static int score;
	static int x;
	static int y;
	
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

class Room {
	
	boolean isExplored;
	String roomContent = ""; //T -> trap, E -> enemy, S -> sword, G -> gold, 0 -> empty
	
	public boolean getIsExplored(){
		return isExplored;
	}
	
}

class Map {
	
	int mapSize;
	Room[][] mapArray;
	
	public Map(){
		mapSize = RogueExecutor.boundarySize;
		mapArray = new Room[mapSize][mapSize];
	}
	
	public void generateMap(){
		initRooms();
		placeTraps();
		placeEnemies();
		placeGold();
		placeSword();
	}
	
	public void initRooms(){
		for (int i = 0; i<mapSize; i++){
			for(int j = 0; j<mapSize; j++){
				mapArray[i][j].isExplored = false;
				mapArray[i][j].roomContent = "0";
			}
		}
	}
		
	public void placeTraps(){ //assumes that the dungeon has "mapSize" amount of traps.
		Random random = new Random();
		int trapCount = 0;
		while(trapCount < mapSize){
			int x = random.nextInt(mapSize);
			int y = random.nextInt(mapSize);
			if(mapArray[x][y].roomContent.equals("0")){ //if there is nothing in the room
				mapArray[x][y].roomContent = "E";
			} else {
				continue;
			}
			trapCount++;
		}
	}
	
	public void placeEnemies(){ //assumes that the dungeon has "mapSize" amount of enemies.
		Random random = new Random();
		int enemiesCount = 0;
		while(enemiesCount < mapSize){
			int x = random.nextInt(mapSize);
			int y = random.nextInt(mapSize);
			if(mapArray[x][y].roomContent.equals("0")){ //if there is nothing in the room
				mapArray[x][y].roomContent = "E";
			} else {
				continue;
			}
			enemiesCount++;
		}
	}
	
	public void placeGold(){ //assumes that the dungeon has "mapSize" amount of traps.
		Random random = new Random();
		int goldCount = 0;
		while(goldCount < mapSize){
			int x = random.nextInt(mapSize);
			int y = random.nextInt(mapSize);
			if(mapArray[x][y].roomContent.equals("0")){ //if there is nothing in the room
				mapArray[x][y].roomContent = "G";
			} else {
				continue;
			}
			goldCount++;
		}
	}
	
	public void placeSword(){ //places only one sword.
		Random random = new Random();
			int x = random.nextInt(mapSize);
			int y = random.nextInt(mapSize);
			if(mapArray[x][y].roomContent.equals("0")){ //if there is nothing in the room
				mapArray[x][y].roomContent = "S";
			}
	}
	
	public void checkRoom(){
		int x = Player.x;
		int y = Player.y;
		if(mapArray[x][y].roomContent.equals("T")){
			RogueExecutor.consoleTxt += "\nYOU ACTIVATED A TRAP AND DIED!\nGAME OVER!";
			RogueExecutor.isGameOver = true;
		}
		if(mapArray[x][y].roomContent.equals("E")){	
			RogueExecutor.consoleTxt += "\nYOU HAVE ENCOUNTERED AN ENEMY! GET READY FOR BATTLE!";
			if(RogueExecutor.hasInteracted){
				if(Player.level >= Enemy.level){
					RogueExecutor.consoleTxt += "\nYOU WON .........";
					Player.score += 10 * Enemy.level;
				} else {
					RogueExecutor.consoleTxt += "\nYOU LOST, DIED .........";
					RogueExecutor.isGameOver = true;
				}
			}
		}
		if(mapArray[x][y].roomContent.equals("S")){	
			if(Player.level<4){
				RogueExecutor.consoleTxt += "\nYOU HAVE FOUND A SWORD. NOW YOU CAN FACE THE TOUGHER MONSTERS!";
				Player.level++;
			} else {
				RogueExecutor.consoleTxt += "\nYOU HAVE FOUND SOME GOLD! 'SHINY!'";
				Player.score += 10;
			}
		}
		if(mapArray[x][y].roomContent.equals("G")){	
			RogueExecutor.consoleTxt += "\nYOU HAVE FOUND SOME GOLD! 'SHINY!'";
			Player.score += 10;
		}
	}
	
}

