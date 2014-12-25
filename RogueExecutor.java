import javax.swing.JFrame;

public class RogueExecutor {

	static Map[][] map;
	static int boundarySize = 10;
	int x;
	int y;
	static boolean isGameOver = false;
	static String consoleTxt = "";
	static boolean hasInteracted = false;
	
	
	public static void main(String[] args){
		
		gettingMapSize();
		generateMap();
		
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

class Player implements MapObjects {

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
		Player.score = score;
	}

	public void addScore(int point) {
		Player.score = score + point;
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

	public MapObjects setLocations(int x, int y) {
		return new Player(x,y);
	}
}

class Enemy implements MapObjects{

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

	public MapObjects setLocation(int x, int y){ return new Enemy(x,y);}


}

class Sword implements MapObjects {
	int locX;
	int locY;

	public Sword(int locX, int locY){
		this.locX = locX;
		this.locY = locY;

	}


	@Override
	public void setX(int x) {
		this.locX = x;
	}

	@Override
	public void setY(int y) {
		this.locY = y;
	}

	@Override
	public int getX() {
		return locX;
	}

	@Override
	public int getY() {
		return locY;
	}

	public MapObjects setLocation(int x, int y){ return new Sword(x,y); }
}

class Gold implements MapObjects {
	int locX;
	int locY;

	public Gold(int locX, int locY){
		this.locX = locX;
		this.locY = locY;
	}

	@Override
	public void setX(int x) {
		this.locX = x;
	}

	@Override
	public void setY(int y) {
		this.locY = y;
	}

	@Override
	public int getX() {
		return locX;
	}

	@Override
	public int getY() {
		return locY;
	}

	public MapObjects setLocation(int x, int y){ return new Gold(x,y); }
}

//class Player {
//
//	static int level;
//	static int score;
//	int x;
//	int y;
//
//	public Player(int x, int y){
//
//		level = 0;
//		score = 0;
//		this.x = x;
//		this.y = y;
//	}
//
//	public int getLevel() {
//		return level;
//	}
//
//	public void setLevel(int level) {
//		Player.level = level;
//	}
//
//	public int getScore() {
//		return score;
//	}
//
//	public void setScore(int score) {
//		Player.score = score;
//	}
//
//	public void addScore(int point) {
//		Player.score = score + point;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//
//
//}
//
//class Enemy {
//
//	static int level;
//	int x;
//	int y;
//
//	public Enemy(int x, int y){
//
//		this.x = x;
//		this.y = y;
//	}
//
//	public static int getLevel() {
//		return level;
//	}
//
//	public static void setLevel(int level) {
//		Enemy.level = level;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public static void setLocation(int x, int y){ new Enemy(x,y);}
//
//
//}

class Map {
	
	boolean explored;
	Map isTrap;
	Enemy enemy;
	Map isSword;
	Map isGold;
	
	int mapSize = RogueExecutor.boundarySize;

	public Map[][] getMap(){
		return RogueExecutor.map;
	}
	
	public void setMap(int i, int j){
		enemy = new Enemy(i,j);
		RogueExecutor.map[i][j] = enemy.setLocation(i,j);
	}
	
	public boolean isExplored(){
		return explored;
	}
	
	public void placer(){
		
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				
				// 
				
				double d = Math.random();
                if (d <= 0.15)
                	setMap(i, j, enemy);
                else if (d <= 0.2)
                	setMap(i, j, isTrap);
                else if (d <= 0.35)
                	setMap(i, j, isGold);
                else if (d <= 0.5)
                	setMap(i, j, isSword);
				
				
			
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
