
public class RogueController {


	Player player;
	Map map;
	int playerX = player.getX();
	int playerY = player.getY();
	int mapSize = map.mapSize;
	
	public RogueController(Player player, Map map){
		this.player = player;
		this.map = map;
	}
	public void moveNorth(){
		
		if(playerY > 0 && playerY < mapSize)
			player.setY(playerY-1);
	}
	
	public void moveSouth(){
		
		if(playerY > 0 && playerY < mapSize)
			player.setY(playerY+1);
	}
	
	public void moveEast(){

		if(playerX > 0 && playerX < mapSize)
			player.setX(playerX+1);
	}
	
	public void moveWest(){

		if(playerX > 0 && playerX < mapSize)
			player.setX(playerX-1);
	}
}
