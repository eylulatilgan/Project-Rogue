import java.awt.BorderLayout;
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
	static int startX;
	static int startY;
	static boolean isGameOver = false;
	static boolean hasInteracted = false;
	static String consoleTxt = "";
	
	private static final int CELL_SIZE = 30;
	private static final int CONSOLE_PANEL_SIZE = 200;
	private int boundarySize;
	private JFrame gameFrame;
	private JPanel gamePanel;
	private Map map;
	private Player player;
	private JLabel [][] cells;
	private RogueController controller;
	private JPanel consolePanel;
	private ImageIcon characterIcon, roomIcon, emptyRoomIcon, enemyIcon, goldIcon, trapIcon, swordPNG; 
	private JLabel character, room, emptyRoom, enemy, gold, trap, sword;
	
	public RogueExecutor(){
		characterIcon = new ImageIcon("ico-x.png");
		getMapSize();
	}
	
	public static void main(String args[])
	    {
	       Runnable r  = new Runnable() {
	            @Override
	            public void run() {
	            	new RogueExecutor();
	            }
	        };

	        EventQueue.invokeLater(r);
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
						boundarySize = size;
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
		map = new Map(boundarySize);	
		initUI();
		placeCharacter();
		play();
	}
	
	 private void play() {
		 KeyListener listener = new KeyListener() {
			 int previousX, previousY;
				@Override
				public void keyPressed(KeyEvent e) {
					int keyCode = e.getKeyCode();
					switch( keyCode ) { 
			        case KeyEvent.VK_UP:
			        	previousX = player.getX();
			        	previousY = player.getY();
			        	controller.moveNorth();			   
			        	updateCellExploredStatus(player.getX(), player.getY());
			        	//consoleLabel.setText(consoleTxt);
			        	System.out.println(consoleTxt);
			        	updateCurrentCell(player.getX(), player.getY(), previousX, previousY);		    
			            break;
			        case KeyEvent.VK_DOWN:
			        	previousX = player.getX();
			        	previousY = player.getY();
			            controller.moveSouth();
			            //consoleLabel.setText(consoleTxt);
			            System.out.println(consoleTxt);
			            updateCellExploredStatus(player.getX(), player.getY());
			            //UpdateConsoleMessage(player.getX(), player.getY());
			            updateCurrentCell(player.getX(), player.getY(), previousX, previousY);
			            break;
			            
			        case KeyEvent.VK_LEFT:
			        	previousX = player.getX();
			        	previousY = player.getY();
			            controller.moveWest();
			            //consoleLabel.setText(consoleTxt);
			            System.out.println(consoleTxt);
			            updateCellExploredStatus(player.getX(), player.getY());
			            //UpdateConsoleMessage(player.getX(), player.getY());
			            updateCurrentCell(player.getX(), player.getY(), previousX, previousY);
			            break;
			            
			        case KeyEvent.VK_RIGHT :
			        	previousX = player.getX();
			        	previousY = player.getY();
			            controller.moveEast();
			            //consoleLabel.setText(consoleTxt);
			            System.out.println(consoleTxt);
			            updateCellExploredStatus(player.getX(), player.getY());
			            //UpdateConsoleMessage(player.getX(), player.getY());
			            updateCurrentCell(player.getX(), player.getY(), previousX, previousY);
			            break;
			            
			        case KeyEvent.VK_SPACE :
			        	if(player.getX() == startX && player.getY() == startY){
			        		System.exit(0);
			        	}
			            break;
			     }
					
				}

//				private void UpdateConsoleMessage(int PlayerX, int PlayerY) {
//					if(map.getMapArray()[PlayerX][PlayerY].getRoomContent().equals("G"))
//						consolePanel.add()
//					
//				}

				private void updateCellExploredStatus(int x, int y) {
					map.getMapArray()[x][y].setExplored(true);
				}
				
				private void updateCurrentCell(int x, int y, int preX, int preY) {
					cells[x][y].setBackground(Color.BLUE);
					cells[preX][preY].setBackground(Color.WHITE);
//					gamePanel.repaint();
					if(map.getMapArray()[x][y].getRoomContent().equals("T")){
						System.exit(0);
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
		JLabel dummy = new JLabel();
		dummy.setText("lalala");
		
		cells = new JLabel[map.getMapBoundary()][map.getMapBoundary()];
		 
		 consolePanel = new JPanel();
		 consolePanel.setBounds(map.getMapBoundary() * CELL_SIZE, 0, CONSOLE_PANEL_SIZE, map.getMapBoundary() * CELL_SIZE);
		 consolePanel.setBackground(Color.GREEN);
		 consolePanel.setLayout(new BorderLayout());
		 consolePanel.add(dummy);
		 
		 gamePanel = new JPanel();
		 gamePanel.setBounds(0,0,map.getMapBoundary() * CELL_SIZE, map.getMapBoundary() * CELL_SIZE);
		 gamePanel.setLayout(new GridLayout(map.getMapBoundary(), map.getMapBoundary()));
		 
		 gameFrame = new JFrame();
		 gameFrame.setTitle("Game");
		 gameFrame.setSize(map.getMapBoundary() * CELL_SIZE  + CONSOLE_PANEL_SIZE, map.getMapBoundary() * CELL_SIZE);
		 
		 Border border = BorderFactory.createLineBorder(Color.GRAY, 5);
		 
		 for(int i = 0; i < map.getMapBoundary(); i ++){
			 for(int j = 0; j < map.getMapBoundary(); j ++){
				 cells[i][j] = new JLabel();
				 cells[i][j].setSize(CELL_SIZE, CELL_SIZE);
				 cells[i][j].setVisible(true);
				 cells[i][j].setOpaque(true);
				 cells[i][j].setBackground(Color.BLACK);
				 cells[i][j].setBorder(border);
				 gamePanel.add(cells[i][j]);
			 }
		 }
		 
		 consolePanel.setVisible(true);
		 gameFrame.add(gamePanel);
		 gameFrame.add(consolePanel);
		 gameFrame.setLocationRelativeTo(null);
		 gameFrame.setResizable(false);
		 gameFrame.setVisible(true);
	}


public void placeCharacter(){
	player =  new Player();
	boolean charPlaced = false;
	while(!charPlaced){
		Random r1 = new Random();
		Random r2 = new Random();
		int x = r1.nextInt(boundarySize);
		int y = r2.nextInt(boundarySize);
		if(map.getMapArray()[x][y].getRoomContent().equals("0")){
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
				RogueExecutor.consoleTxt += "\nYOU HAVE FOUND A SWORD. NOW YOU CAN FACE THE TOUGHER MONSTERS!";
				player.setLevel(player.getLevel() + 1);
			} else {
				RogueExecutor.consoleTxt += "\nYOU HAVE FOUND SOME GOLD! 'SHINY!'";
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
	
	public Enemy(int x, int y){
		this.x = x;
		this.y = y;
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

