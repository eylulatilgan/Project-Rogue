import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class RogueExecutor {


	int x;
	int y;

	int startX;
	int startY;
	static int bossX;
	static int bossY;
	static boolean isGameOver = false;
	static String consoleTxt = "";
	static boolean hasInteracted = false;
	JLabel consoleTxtLabel;
	JLabel scoreLabel;
	JLabel healthLabel;
	JLabel levelLabel;
	private int BoundarySize;
	private JFrame gameFrame;
	private JPanel gamePanel;
	private Map map;
	private Player player;
	private JLabel [][] cells;
	private RogueController controller;
	private static final int CELL_SIZE = 30;
	private static final int CONSOLE_PANEL_SIZE = 220;
	private JPanel consolePanel;

	public RogueExecutor(){
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
						controller.moveNorth();
						updateCellExploredStatus(player.getX(), player.getY());
						updateCurrentCell(player.getX(), player.getY(), player.getX()+1, player.getY());	
					}
					movable = false;		    
					break;
				case KeyEvent.VK_DOWN:
					if(movable){
						controller.moveSouth();
						updateCellExploredStatus(player.getX(), player.getY());
						updateCurrentCell(player.getX(), player.getY(), player.getX()-1, player.getY());
					}
					movable = false;		    
					break;
				case KeyEvent.VK_LEFT:
					if(movable){
						controller.moveWest();
						updateCellExploredStatus(player.getX(), player.getY());
						updateCurrentCell(player.getX(), player.getY(), player.getX(), player.getY()+1);
					}
					movable = false;		    
					break;
				case KeyEvent.VK_RIGHT :
					if(movable){
						controller.moveEast();
						updateCellExploredStatus(player.getX(), player.getY());
						updateCurrentCell(player.getX(), player.getY(), player.getX(), player.getY()-1);
					}
					movable = false;		    
					break;
				case KeyEvent.VK_SPACE :
					movable = true;
					
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("G")){
						player.addScore(10);
						map.mapArray[player.getX()][player.getY()].setRoomContent("0");
						scoreLabel.setText("Score: " + player.getScore());
					} 
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("S")){
						if(player.getLevel() <= 3){
							player.lvlUp();
							levelLabel.setText("Level: " + player.getLevel());
							map.mapArray[player.getX()][player.getY()].setRoomContent("0");
						} else {
							player.addScore(10);
							scoreLabel.setText("Score: " + player.getScore()); 
							map.mapArray[player.getX()][player.getY()].setRoomContent("0");
						}
					}  
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("T")){
						healthLabel.setIcon(null);
						JOptionPane.showMessageDialog(null, "GAME OVER!", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
						gameFrame.dispose();							
						new RogueExecutor();					
					}
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("E")){
						if(player.getLevel() >= 1){
							player.addScore (10 * 1);
							scoreLabel.setText("Score: " + player.getScore());
							JOptionPane.showMessageDialog(null, "YOU WON 1", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							map.mapArray[player.getX()][player.getY()].setRoomContent("0");
							if(player.getLevel() == 1){
								player.damage();
							}
						} else {
							healthLabel.setIcon(null);
							JOptionPane.showMessageDialog(null, "YOU LOST 1", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							gameFrame.dispose();							
							new RogueExecutor();
							
						}					
					} 
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("EE")){
						if(player.getLevel() >= 2){
							player.addScore (10 * 2);
							scoreLabel.setText("Score: " + player.getScore());
							JOptionPane.showMessageDialog(null, "YOU WON 2", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							map.mapArray[player.getX()][player.getY()].setRoomContent("0");
							if(player.getLevel() == 2){
								player.damage();
							}
						} else {
							healthLabel.setIcon(null);
							JOptionPane.showMessageDialog(null, "YOU LOST 2", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							gameFrame.dispose();							
							new RogueExecutor();
							
						}					
					} 
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("EEE")){
						if(player.getLevel() >= 3){
							player.addScore (10 * 3);
							scoreLabel.setText("Score: " + player.getScore());
							JOptionPane.showMessageDialog(null, "YOU WON 3", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							map.mapArray[player.getX()][player.getY()].setRoomContent("0");
							if(player.getLevel() == 3){
								player.damage();
							}
						} else {
							healthLabel.setIcon(null);
							JOptionPane.showMessageDialog(null, "YOU LOST 3", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							gameFrame.dispose();							
							new RogueExecutor();
							
						}					
					} 
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("EEEE")){
						if(player.getLevel() >= 4){
							player.addScore (10 * 4);
							scoreLabel.setText("Score: " + player.getScore());
							JOptionPane.showMessageDialog(null, "YOU WON 4", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							map.mapArray[player.getX()][player.getY()].setRoomContent("0");
							if(player.getLevel() == 4){
								player.damage();
							}
						} else {
							healthLabel.setIcon(null);
							JOptionPane.showMessageDialog(null, "YOU LOST 4", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							gameFrame.dispose();							
							new RogueExecutor();
							
						}					
					}
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("B")){
						if(player.getLevel() < 4){
							healthLabel.setIcon(null);
							JOptionPane.showMessageDialog(null, "YOU HAVE KILLED BY THE BOSS", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							gameFrame.dispose();							
							new RogueExecutor();
						}
					}
					
					if(player.getHealth() == 2){
						healthLabel.setIcon(new ImageIcon(getClass().getResource("TwoHearts.png")));
					}
					
					if(player.getHealth() == 1){
						healthLabel.setIcon(new ImageIcon(getClass().getResource("OneHeart.png")));
					}
					
					break;
				case KeyEvent.VK_R :
					//EXIT
					if(player.getX() == startX && player.getY() == startY){
						gameFrame.dispose();							
						new RogueExecutor();
						
					}
					//BOSS
					if(map.mapArray[player.getX()][player.getY()].getRoomContent().equals("B")){
						if(player.getLevel() >= 4){
							
							JOptionPane.showMessageDialog(null, "YOU KILLED THE BOSS! YOU WON THE GAME!!", "Rogue ", JOptionPane.INFORMATION_MESSAGE);
							gameFrame.dispose();							
							new RogueExecutor();
							
						}
						
					}
					
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
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("Gold.png")));
					RogueExecutor.consoleTxt = "Oooh~ shiny! Gold!";
					consoleTxtLabel.setText(consoleTxt);
					gameFrame.repaint();
				}
				
				if(map.getMapArray()[x][y].getRoomContent().equals("T")){
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("Trap.png")));
					RogueExecutor.consoleTxt = "It's a trap!";
					consoleTxtLabel.setText(consoleTxt);
				 	gameFrame.repaint();
				}
				
				if(map.getMapArray()[x][y].getRoomContent().equals("E")){
						cells[x][y].setIcon(new ImageIcon(getClass().getResource("Zombie.png")));
						RogueExecutor.consoleTxt = "Get ready, peasant!";
						consoleTxtLabel.setText(consoleTxt);
						gameFrame.repaint();
				}
				if(map.getMapArray()[x][y].getRoomContent().equals("EE")){
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("Vampire.png")));
					RogueExecutor.consoleTxt = "Get ready, peasant!!";
					consoleTxtLabel.setText(consoleTxt);
					gameFrame.repaint();
				}
				if(map.getMapArray()[x][y].getRoomContent().equals("EEE")){
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("Imp.png")));
					RogueExecutor.consoleTxt = "Get ready, peasant!!!";
					consoleTxtLabel.setText(consoleTxt);
					gameFrame.repaint();
				}
				if(map.getMapArray()[x][y].getRoomContent().equals("EEEE")){
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("Troll.png")));
					RogueExecutor.consoleTxt = "Get ready, peasant!!!!";
					consoleTxtLabel.setText(consoleTxt);
					gameFrame.repaint();
				}
				if(map.getMapArray()[x][y].getRoomContent().equals("B")){
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("Dragon.png")));
					RogueExecutor.consoleTxt = "This is no Smaug!!!!!";
					consoleTxtLabel.setText(consoleTxt);
					gameFrame.repaint();
				}
				
				if(map.getMapArray()[x][y].getRoomContent().equals("S")){
					cells[x][y].setIcon(new ImageIcon(getClass().getResource("Sword.png")));
					RogueExecutor.consoleTxt = "Now you got tougher!";
					consoleTxtLabel.setText(consoleTxt);
					gameFrame.repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {

			}
		};

		gameFrame.addKeyListener(listener);
	}

	private void initUI() {
	     Font statusFont = new Font("Press Start 2P", Font.PLAIN, 30);
	     Font consoleFont = new Font("Press Start 2P", Font.PLAIN, 20);
		 consoleTxtLabel = new JLabel();
		 consoleTxtLabel.setSize(CONSOLE_PANEL_SIZE, CONSOLE_PANEL_SIZE);
		 consoleTxtLabel.setFont(consoleFont);
		 consoleTxtLabel.setForeground(Color.LIGHT_GRAY);
		 cells = new JLabel[map.getMapBoundary()][map.getMapBoundary()];
		 
		 scoreLabel = new JLabel();
		 scoreLabel.setSize(200, 200);
		 scoreLabel.setText("Score: 0");
		 scoreLabel.setFont(statusFont);
		 scoreLabel.setForeground(Color.BLUE);
		 //scoreLabel.setLayout(new BorderLayout());
		 
		 healthLabel = new JLabel();
		 healthLabel.setIcon(new ImageIcon(getClass().getResource("ThreeHearts.png")));
		 
		 levelLabel = new JLabel();
		 levelLabel.setFont(statusFont);
		 levelLabel.setText("Level : 0" );
		 levelLabel.setForeground(Color.BLUE);
		 
		 consolePanel = new JPanel();
		 consolePanel.setBounds(map.getMapBoundary() * CELL_SIZE, 0, CONSOLE_PANEL_SIZE, map.getMapBoundary() * CELL_SIZE);
		 consolePanel.setBackground(Color.BLACK);
		 consolePanel.setLayout(new BoxLayout(consolePanel, BoxLayout.PAGE_AXIS)); 
		
		 consolePanel.add(scoreLabel);
		 consolePanel.add(levelLabel);
		 consolePanel.add(healthLabel);
		 consolePanel.add(consoleTxtLabel);
		 
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
		boolean charPlaced = false;
		while(!charPlaced){
			Random r1 = new Random();
			Random r2 = new Random();
			int x = r1.nextInt(BoundarySize);
			int y = r2.nextInt(BoundarySize);
			if(map.getMapArray()[x][y].getRoomContent().equals("0")){
				map.mapArray[x][y].setRoomContent("C");
				player.setX(x);
				player.setY(y);

				startX = x;
				startY = y;

				charPlaced = true;
			}

			map.setStartX(startX);
			map.setStartY(startY);
		}



		cells[player.getX()][player.getY()].setIcon(new ImageIcon(getClass().getResource("Cave.png")));
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
	Player player = new Player();
	private int startX;
	private int startY;
	

	public Map(int mapSize){
		this.mapSize = mapSize;
		mapArray = new Room[mapSize][mapSize];
		generateMap();
	}

	public void setStartX(int startX){
		this.startX = startX;
	}


	public void setStartY(int startY){
		this.startY = startY;
	}
	
	public void generateMap(){
		initRooms();
		placeTraps();
		placeEnemies();
		placeGold();
		placeSword();
		placeBoss();
	}

	public void initRooms(){
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

	public void placeTraps(){ 
		int trapCount = 0;
		while(trapCount < (int) (0.05 * mapSize * mapSize)){
			Random random = new Random();
			int x = random.nextInt(mapSize);
			int y = random.nextInt(mapSize);
			if(mapArray[x][y].getRoomContent().equals("0")){ //if there is nothing in the room
				if(Math.abs(x-startX) >= 2 || Math.abs(y-startY) >= 2){
					mapArray[x][y].setRoomContent("T");	
				}
			} else {
				continue;
			}
			trapCount++;
		}
	}

	public void placeEnemies(){
		int enemiesCount = 0;
		while(enemiesCount < (int) (0.20 * mapSize * mapSize)){
			Random random = new Random();
			int x = random.nextInt(mapSize);
			int y = random.nextInt(mapSize);
			if(mapArray[x][y].getRoomContent().equals("0")){ //if there is nothing in the room
				int elvl = random.nextInt(4);
				if(elvl == 0){
					if(Math.abs(x-startX) >= 2 || Math.abs(y-startY) >= 2){
						mapArray[x][y].setRoomContent("E");
					}
				}
				if(elvl == 1){
					if(Math.abs(x-startX) >= 2 || Math.abs(y-startY) >= 2){
						mapArray[x][y].setRoomContent("EE");
					}
				}
				if(elvl == 2){
					if(Math.abs(x-startX) >= 3 || Math.abs(y-startY) >= 3){
						mapArray[x][y].setRoomContent("EEE");
					}
				}
				if(elvl == 3){
					if(Math.abs(x-startX) >= 4 || Math.abs(y-startY) >= 4){
						mapArray[x][y].setRoomContent("EEEE");
					}
				}
			} else {
				continue;
			}
			enemiesCount++;
		}
	}
	

	public void placeBoss(){ 
		Random random = new Random();
		int x = random.nextInt(mapSize);
		int y = random.nextInt(mapSize);
		if(mapArray[x][y].getRoomContent().equals("0")){ //if there is nothing in the room
			if(Math.abs(x-startX) >= 4 || Math.abs(y-startY) >= 4)
				mapArray[x][y].setRoomContent("B");	
		} 
	}

	public void placeGold(){ //assumes that the dungeon has "mapSize" amount of traps.
		int goldCount = 0;
		while(goldCount  < (int) (0.15 * mapSize * mapSize)){
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
		int swordCount = 0;
		while(swordCount  < (int) (0.1 * mapSize * mapSize)){
			Random random = new Random();
			int x = random.nextInt(mapSize);
			int y = random.nextInt(mapSize);
			if(mapArray[x][y].getRoomContent().equals("0")){ //if there is nothing in the room
				mapArray[x][y].setRoomContent("S");
			} else {
				continue;
			}
			swordCount++;
		}
	}

}


class Player {

	private int level;
	private int score;
	private int health;
	private int x;
	private int y;

	public Player(){
		level = 0;
		score = 0;
		health = 3;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void damage(){
		health--;
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
		level = 0;
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

