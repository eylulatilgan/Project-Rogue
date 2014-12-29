/* 
 
 Bu dosyanýn içeriðini GUI'yi çizen diðer classlara kopyalarsýnýz. DummyRogueExec çok karýþýk geldiði
 için buraya yazmayý uygun buldum.
 
 Buradaki metodlarýn/fieldlarýn hepsi ayný class içinde bulunmayý gerektirmeyebilir.
 
 */


import javax.swing.*;

public class MapGeneratorForGUI {
	
	Map map;
	int mapSize;
	int characterGraphicsX, characterGraphicsY;
	ImageIcon characterPNG, roomPNG, emptyRoomPNG, enemyPNG, goldPNG, trapPNG, swordPNG; 
	JLabel character, room, emptyRoom, enemy, gold, trap, sword;
	JLabel[][] mapLabel;
	JFrame frame; //bu stub JFrame'i kod hata vermesin diye koydum. Nesneleri draw ettiðimiz frame'i bunun yerine yazarýz.
	
	public void executeThisVeryClass(){ //buradaki metodlar þu þekilde/sýrada çalýþmalý:
		initIMGIcons();
		initLabels();
		formLabelsWithIMGIcons();
		fixLabel(character); //ve diðer labellar için de ayný þeyi uygulayýn
		initAndDrawMapGraphics();
		putStuffOnMapGraphics();
		placeCharacter();
	}
	
	public void initIMGIcons(){
		characterPNG = new ImageIcon("filepath yazýn");
		roomPNG = new ImageIcon("filepath yazýn");
		emptyRoomPNG = new ImageIcon("filepath yazýn");
		enemyPNG = new ImageIcon("filepath yazýn");
		goldPNG = new ImageIcon("filepath yazýn");
		trapPNG = new ImageIcon("filepath yazýn");
		swordPNG = new ImageIcon("filepath yazýn");
	}
	
	public void initLabels(){
		character = new JLabel();
		room = new JLabel();
		emptyRoom = new JLabel();
		enemy = new JLabel();
		gold = new JLabel();
		trap = new JLabel();
		sword = new JLabel();
	}
	
	public void formLabelsWithIMGIcons(){
		character.setIcon(characterPNG);
		room.setIcon(roomPNG);
		emptyRoom.setIcon(emptyRoomPNG);
		enemy.setIcon(enemyPNG);
		gold.setIcon(goldPNG);
		trap.setIcon(trapPNG);
		sword.setIcon(swordPNG);
	}
	
	public void fixLabel(JLabel label){ //label'larýn hepsini teker teker bu metoddan geçirmek gerekli.
		label.setSize(label.getPreferredSize());
		label.setVisible(true);
	}
	
	public void initAndDrawMapGraphics(){
		map = new Map();
		int blockGap = 20; //bloklarýn arasýndaki boþluklar
		int xLoc = 100, yLoc = 100;
		mapSize = map.returnMapSize();
		mapLabel = new JLabel[mapSize][mapSize];
		for(int i = 0; i<mapSize; i++){
			for(int j = 0; j<mapSize; j++){
				mapLabel[i][j] = emptyRoom;
				mapLabel[i][j].setLocation(xLoc + (blockGap * i) , yLoc + (blockGap * j));
				frame.add(mapLabel[i][j], 0);
				frame.repaint();
			}
		}
	}
	
	public void putStuffOnMapGraphics(){
		for(int i = 0; i<mapSize; i++){
			for(int j = 0; j<mapSize; j++){
				if(map.mapArray[i][j].roomContent.equals("S")){ //eðer odada kýlýç var diye tanýmladýysak, diðer itemlar ve boþ oda için bir iki tane daha if lazým
					mapLabel[i][j] = sword;
					frame.repaint();
				}
			}
		}
	}
	
	public void placeCharacter(){
		Player player = new Player();
		for(int i = 0; i<mapSize; i++){
			for(int j = 0; j<mapSize; j++){
				if(map.mapArray[i][j].roomContent.equals("0")){
					player.x = mapLabel[i][j].getX();
					player.y = mapLabel[i][j].getY();
					characterGraphicsX = player.x;
					characterGraphicsY = player.y;
					break;
				}
			}
		}
		character.setLocation(characterGraphicsX, characterGraphicsY); //normal þartlar altýnda
		frame.add(character, 0);
		frame.repaint();
	}
	
}
