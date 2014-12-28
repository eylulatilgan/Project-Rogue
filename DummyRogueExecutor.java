import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DummyRogueExecutor {

	private Map map;
	static int boundarySize = 10;
	int x;
	int y;
	static boolean isGameOver = false;
	static String consoleTxt = "";
	static boolean hasInteracted = false;
	/*private JFrame dialogFrame;
	private JDialog dialog;*/
	//private JTextField boundarySizeInput;
	private int dummyBoundarySize;

	
	/*public static void execute(){
		callMapGenerator();
		if(isGameOver)
			System.exit(0);
	}*/
	
	public DummyRogueExecutor(){
		getMapSize();
	}
	
	public static void main(String args[])
	    {
	        new DummyRogueExecutor();
	    }

	private void getMapSize() {
		
		JFrame dialogFrame 			 = new JFrame("Rogue Game");
		JTextField boundarySizeInput = new JTextField(20);
		JButton OKbutton 			 = new JButton("OK!");
	
		dialogFrame.setLayout(new FlowLayout());
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		JDialog dialog = new JDialog(dialogFrame, "ROGUE GAME", true);
		dialog.setSize(500, 100);
		dialog.setLayout(new FlowLayout());
        
        dialog.add(new JLabel("MAP SIZE (10-20): "));
        dialog.add(boundarySizeInput);
        dialog.add(OKbutton);

        OKbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int size = Integer.parseInt(boundarySizeInput.getText());
				if(size <= 20 && 10 <= size){
					dummyBoundarySize = size;
					callMapGenerator();
				}
				else
					dialog.add(new JLabel(" (10-20) MEANS; YOU CAN ONLY USE NUMBERS BETWEEN 10 TO 20. SO PLEASE... "));
					dialog.revalidate();
					dialog.repaint();
		}
		});
        
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
	}
	
	public void callMapGenerator(){
		map = new Map(dummyBoundarySize);
		System.out.println(map.getMapBoundary());
		initUI(map);
	}

	
	 private void initUI(Map map) {
		 for(int i = 0; i < map.getMapBoundary(); i++){
			 for(int j = 0; j < map.getMapBoundary(); j++){
				 System.out.println(map.getMapArray()[i][j].getRoomContent());
			 }
		 }	
	}
}

class Room {
	
	private boolean isExplored;
	private String roomContent = ""; //T -> trap, E -> enemy, S -> sword, G -> gold, 0 -> empty
	
	public boolean getIsExplored(){
		return isExplored;
	}
	
	public void setExplored(boolean isExplored){
		this.isExplored = isExplored;
	}
	
	public void setRoomContent(String roomContent){
		this.roomContent = roomContent;
	}
	
	public String getRoomContent(){
		return roomContent;
	}
	
	
}

class Map {
	
	private int mapSize;
	protected Room[][] mapArray;
	
	public Map(int mapSize){
		this.mapSize = mapSize;
		mapArray = new Room[mapSize][mapSize];
		generateMap();
	}
	
	public void generateMap(){
		//System.out.println("hello I'm here!! ");
		initRooms();
		placeTraps();
		placeEnemies();
		placeGold();
		placeSword();
	}
	
	public void initRooms(){
		System.out.println("hello I'm here!! ");
		for (int i = 0; i < mapSize; i++){
			for(int j = 0; j < mapSize; j++){
				mapArray[i][j] = new Room();
				mapArray[i][j].setExplored(false);
				mapArray[i][j].setRoomContent("0");
			}
		}
	}
	
	public Room[][] getMapArray(){
		return mapArray;
	}
	
	public int getMapBoundary(){
		return mapSize;
	}
	
	public void placeTraps(){ //assumes that the dungeon has "mapSize" amount of traps.
		Random random = new Random();
		int trapCount = 0;
		while(trapCount < mapSize){
			int x = random.nextInt(mapSize);
			int y = random.nextInt(mapSize);
			if(mapArray[x][y].getRoomContent().equals("0")){ //if there is nothing in the room
				mapArray[x][y].setRoomContent("T");
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
			if(mapArray[x][y].getRoomContent().equals("0")){ //if there is nothing in the room
				mapArray[x][y].setRoomContent("E");
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
			if(mapArray[x][y].getRoomContent().equals("0")){ //if there is nothing in the room
				mapArray[x][y].setRoomContent("G");
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
			if(mapArray[x][y].getRoomContent().equals("0")){ //if there is nothing in the room
				mapArray[x][y].setRoomContent("S");
			}
	}
	
	public void checkRoom(){
		int x = Player.x;
		int y = Player.y;
		if(mapArray[x][y].getRoomContent().equals("T")){
			DummyRogueExecutor.consoleTxt += "\nYOU ACTIVATED A TRAP AND DIED!\nGAME OVER!";
			DummyRogueExecutor.isGameOver = true;
		}
		if(mapArray[x][y].getRoomContent().equals("E")){	
			DummyRogueExecutor.consoleTxt += "\nYOU HAVE ENCOUNTERED AN ENEMY! GET READY FOR BATTLE!";
			if(DummyRogueExecutor.hasInteracted){
				if(Player.level >= Enemy.level){
					DummyRogueExecutor.consoleTxt += "\nYOU WON .........";
					Player.score += 10 * Enemy.level;
				} else {
					DummyRogueExecutor.consoleTxt += "\nYOU LOST, DIED .........";
					DummyRogueExecutor.isGameOver = true;
				}
			}
		}
		if(mapArray[x][y].getRoomContent().equals("S")){	
			if(Player.level<4){
				DummyRogueExecutor.consoleTxt += "\nYOU HAVE FOUND A SWORD. NOW YOU CAN FACE THE TOUGHER MONSTERS!";
				Player.level++;
			} else {
				DummyRogueExecutor.consoleTxt += "\nYOU HAVE FOUND SOME GOLD! 'SHINY!'";
				Player.score += 10;
			}
		}
		if(mapArray[x][y].getRoomContent().equals("G")){	
			DummyRogueExecutor.consoleTxt += "\nYOU HAVE FOUND SOME GOLD! 'SHINY!'";
			Player.score += 10;
		}
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

