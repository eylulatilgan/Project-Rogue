import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class RogueExecutor {


	static int boundarySize = 10;
	int x;
	int y;

	static int startX;
	static int startY;
	static int trapX;
	static int trapY;
	static boolean isGameOver = false;
	static String consoleTxt = "";
	static boolean hasInteracted = false;
	JLabel consoleTxtLabel;
	/*private JFrame dialogFrame;
	private JDialog dialog;*/
	//private JTextField boundarySizeInput;
	private int BoundarySize;
	private JFrame gameFrame;
	private JPanel gamePanel;
	private Map map;
	private Player player;
	private Enemy enemy;
	private JLabel [][] cells;
	private RogueController controller;
	private static final int CELL_SIZE = 30;
	private ImageIcon characterIcon, roomIcon, emptyRoomIcon, enemyIcon, goldIcon, trapIcon, swordPNG; 
	private JLabel character, room, emptyRoom, enemyRoom, gold, trap, sword;
	private static final int CONSOLE_PANEL_SIZE = 200;
	private JPanel consolePanel;

	public RogueExecutor(){
		characterIcon = new ImageIcon("ico-x.png");
		getMapSize();
	}

	public static void main(String args[])
	{
		new RogueExecutor();
	}

	private void getMapSize() {

		JFrame dialogFrame = new JFrame("Rogue Game");
		final JTextField boundarySizeInput = new JTextField(20); // final may not be needed due to an error
		JButton OKbutton = new JButton("OK!");

		dialogFrame.setLayout(new FlowLayout());
		JDialog.setDefaultLookAndFeelDecorated(true);

		final JDialog dialog = new JDialog(dialogFrame, "ROGUE GAME"); // final may not be needed due to an error
		dialog.setSize(500, 100);
		dialog.setLayout(new FlowLayout());

		dialog.add(new JLabel("MAP SIZE (10-20): "));
		dialog.add(boundarySizeInput);
		dialog.add(OKbutton);

		OKbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int size = Integer.parseInt(boundarySizeInput.getText());
				try{
					if(size <= 20 && 10 <= size){
						BoundarySize = size;
						callMapGenerator();
						dialog.dispose();
					}
				}
				catch(NumberFormatException ex){
					dialog.add(new JLabel(" (10-20) MEANS; YOU CAN ONLY USE NUMBERS BETWEEN 10 TO 20. SO PLEASE... "));
					dialog.revalidate();
					dialog.repaint();
				}
			}
		});

		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	public void callMapGenerator(){
		map = new Map(BoundarySize);
		for(int i = 0; i < BoundarySize; i++){
			for(int j = 0; j < BoundarySize; j++){
				//System.out.println(map.getMapArray()[i][j].getRoomContent());
			}
		}	
		initUI();
		play();
	}

	private void play() {

		placeCharacter();
		KeyListener listener = new KeyListener() {
			boolean movable = true;
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch( keyCode ) { 
				case KeyEvent.VK_UP:
					if(movable){
						map.checkRoom(player, enemy);
						controller.moveNorth();
						updateCellExploredStatus(player.getX(), player.getY());
						updateCurrentCell(player.getX(), player.getY(), player.getX()+1, player.getY());	
					}
					movable = false;		    
					break;
				case KeyEvent.VK_DOWN:
					if(movable){
						map.checkRoom(player, enemy);
						controller.moveSouth();
						updateCellExploredStatus(player.getX(), player.getY());
						updateCurrentCell(player.getX(), player.getY(), player.getX()-1, player.getY());
					}
					movable = false;		    
					break;
				case KeyEvent.VK_LEFT:
					if(movable){
						map.checkRoom(player, enemy);
						controller.moveWest();
						updateCellExploredStatus(player.getX(), player.getY());
						updateCurrentCell(player.getX(), player.getY(), player.getX(), player.getY()+1);
					}
					movable = false;		    
					break;
				case KeyEvent.VK_RIGHT :
					if(movable){
						map.checkRoom(player, enemy);
						controller.moveEast();
						updateCellExploredStatus(player.getX(), player.getY());
						updateCurrentCell(player.getX(), player.getY(), player.getX(), player.getY()-1);
					}
					movable = false;		    
					break;
				case KeyEvent.VK_SPACE :
					movable = true;
					if(player.getX() == startX && player.getY() == startY){
						System.exit(0);
					}
					
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("G")){
						player.addScore(10);
						map.mapArray[player.getX()][player.getY()].setRoomContent("0");
						System.out.println(player.getScore());
					} 
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("S")){
						player.lvlUp();
						map.mapArray[player.getX()][player.getY()].setRoomContent("0");
						System.out.println(player.getLevel());
					}  
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("E")){
						if(player.getLevel() == enemy.getLevel()){
							RogueExecutor.consoleTxt += "\nYOU WON .........";
							System.out.println("won");
							player.addScore (10 * enemy.getLevel());
							map.mapArray[player.getX()][player.getY()].setRoomContent("0");
						} else {
							RogueExecutor.consoleTxt += "\nYOU LOST, DIED .........";
							System.out.println("lose");
							System.exit(0);
						}					
					} 
					
					break;
				}

			}

			private void updateCellExploredStatus(int x, int y) {
				map.getMapArray()[x][y].setExplored(true);
			}

			private void updateCurrentCell(int x, int y, int preX, int preY) {
				cells[x][y].setBackground(Color.BLUE);
				cells[preX][preY].setBackground(Color.WHITE);
				
				gameFrame.repaint();
				if(map.getMapArray()[x][y].getRoomContent().equals("G")){
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("goldusama.jpg")));
					consoleTxtLabel.setText(consoleTxt);
					gameFrame.repaint();
				}
				
				if(map.getMapArray()[x][y].getRoomContent().equals("T")){
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("beartrap.png")));
					consoleTxtLabel.setText(consoleTxt);
				 	gameFrame.repaint();
				}
				
				if(map.getMapArray()[x][y].getRoomContent().equals("E")){
					if(enemy.getLevel() == 1){
						cells[preX][preY].setIcon(new ImageIcon(getClass().getResource("Zombie.png")));
						consoleTxtLabel.setText(consoleTxt);
						gameFrame.repaint();
					}
					if(enemy.getLevel() == 2){
						cells[preX][preY].setIcon(new ImageIcon(getClass().getResource("Vampire.png")));
						consoleTxtLabel.setText(consoleTxt);
						gameFrame.repaint();
					}
					if(enemy.getLevel() == 3){
						cells[preX][preY].setIcon(new ImageIcon(getClass().getResource("Imp.png")));
						consoleTxtLabel.setText(consoleTxt);
						gameFrame.repaint();
					}
				}
				
				if(map.getMapArray()[x][y].getRoomContent().equals("S")){
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("Outcast_sword.png")));
					consoleTxtLabel.setText(consoleTxt);
					gameFrame.repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		};

		gameFrame.addKeyListener(listener);
	}

	private void initUI() {
		 consoleTxtLabel = new JLabel();
		 consoleTxtLabel.setSize(CONSOLE_PANEL_SIZE, CONSOLE_PANEL_SIZE);
		 cells = new JLabel[map.getMapBoundary()][map.getMapBoundary()];
		 
		 consolePanel = new JPanel();
		 consolePanel.setBounds(map.getMapBoundary() * CELL_SIZE, 0, CONSOLE_PANEL_SIZE, map.getMapBoundary() * CELL_SIZE);
		 consolePanel.setBackground(Color.GREEN);
		 consolePanel.setLayout(new FlowLayout());
		 consolePanel.add(consoleTxtLabel,0);
		 
		 gamePanel = new JPanel();
		 gamePanel.setBounds(0,0,map.getMapBoundary() * CELL_SIZE, map.getMapBoundary() * CELL_SIZE);
		 gamePanel.setLayout(new GridLayout(map.getMapBoundary(), map.getMapBoundary()));
		 
		 gameFrame = new JFrame();
		 gameFrame.setTitle("Game");
		 gameFrame.setSize(map.getMapBoundary() * CELL_SIZE  + CONSOLE_PANEL_SIZE, map.getMapBoundary() * CELL_SIZE);
		 gameFrame.setLayout(null);
		 Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
		 
		 for(int i = 0; i < map.getMapBoundary(); i ++){
			 for(int j = 0; j < map.getMapBoundary(); j ++){
				 cells[i][j] = new JLabel();
				 cells[i][j].setSize(CELL_SIZE, CELL_SIZE);
				 //cells[i][j].setVisible(true);
				 cells[i][j].setOpaque(true);
				 cells[i][j].setBackground(Color.BLACK);
				 cells[i][j].setBorder(border);
				 gamePanel.add(cells[i][j]);
			 }
		 } 
		 
		 gameFrame.add(gamePanel);
		 gameFrame.add(consolePanel);
		 gameFrame.setLocationRelativeTo(null);
		 gameFrame.setResizable(false);
		 gameFrame.setVisible(true);
	}


	public void placeCharacter(){
		player =  new Player();
		enemy = new Enemy();
		boolean charPlaced = false;
		while(!charPlaced){
			Random r1 = new Random();
			Random r2 = new Random();
			int x = r1.nextInt(BoundarySize);
			int y = r2.nextInt(BoundarySize);
			if(map.getMapArray()[x][y].getRoomContent().equals("0")){
				map.mapArray[x][y].setRoomContent("B");
				player.setX(x);
				player.setY(y);

				startX = x;
				startY = y;

				//System.out.println("player coord: " + player.getX() + "," + player.getY());
				charPlaced = true;
			}


		}



		cells[player.getX()][player.getY()].setText("B");
		gameFrame.repaint();

		cells[trapX][trapY].setText("T");
		gameFrame.repaint();

		controller = new RogueController(player, map.getMapBoundary());
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
		//System.out.println("hello I'm here!! ");
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

	public ImageIcon getImage(Room room){
		ImageIcon image;
		if(room.getIsExplored()){
			image = new ImageIcon("ico-x.png");
			image.getImage();
		} else {
			image = new ImageIcon("question.jpg");
			image.getImage();
		}

		return image;
	}
	public void placeTraps(){ //assumes that the dungeon has "mapSize" amount of traps.
		int trapCount = 0;
		while(trapCount < mapSize){
			Random random = new Random();
			int x = random.nextInt(mapSize);
			int y = random.nextInt(mapSize);
			if(mapArray[x][y].getRoomContent().equals("0")){ //if there is nothing in the room
				mapArray[x][y].setRoomContent("T");	
				RogueExecutor.trapX = x;
				RogueExecutor.trapY = y;
			} else {
				continue;
			}
			trapCount++;
		}
	}

	public void placeEnemies(){ //assumes that the dungeon has "mapSize" amount of enemies.
		int enemiesCount = 0;
		while(enemiesCount < mapSize){
			Random random = new Random();
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
		int goldCount = 0;
		while(goldCount < mapSize){
			Random random = new Random();
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

	public void checkRoom(Player player, Enemy enemy){
		int x = player.getX();
		int y = player.getY();
		if(mapArray[x][y].getRoomContent().equals("T")){
			RogueExecutor.consoleTxt += "\nYOU ACTIVATED A TRAP AND DIED!\nGAME OVER!";
			RogueExecutor.isGameOver = true;
		}
		if(mapArray[x][y].getRoomContent().equals("E")){	
			RogueExecutor.consoleTxt += "\nYOU HAVE ENCOUNTERED AN ENEMY! GET READY FOR BATTLE!";
			if(RogueExecutor.hasInteracted){
				if(player.getLevel() >= enemy.getLevel()){
					RogueExecutor.consoleTxt += "\nYOU WON .........";
					player.addScore (10 * enemy.getLevel());
				} else {
					RogueExecutor.consoleTxt += "\nYOU LOST, DIED .........";
					RogueExecutor.isGameOver = true;
				}
			}
		}
		if(mapArray[x][y].getRoomContent().equals("S")){	
			if(player.getLevel() < 4){
				RogueExecutor.consoleTxt += " \n YOU HAVE FOUND A SWORD. NOW YOU CAN FACE THE TOUGHER MONSTERS!";
				player.setLevel(player.getLevel() + 1);
			} else {
				RogueExecutor.consoleTxt += "YOU HAVE FOUND SOME GOLD! 'SHINY!'";
				player.setScore (player.getScore() + 10);
			}
		}
		if(mapArray[x][y].getRoomContent().equals("G")){	
			RogueExecutor.consoleTxt += "\nYOU HAVE FOUND SOME GOLD! 'SHINY!'";
			player.setScore (player.getScore() + 10);
		}
	}

}


class Player {

	private int level;
	private int score;
	private int x;
	private int y;

	public Player(){
		level = 0;
		score = 0;
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
		this.score += point;
	}

	public void lvlUp() {
		this.level += 1;
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

	private int level;
	private int x;
	private int y;

	public Enemy(){
		level = 1;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

