public class RogueController {


	Player player;
	Map map;
	int mapSize;

	public RogueController(Player player, int mapSize){
		this.player = player;
		this.mapSize = mapSize;
	}
	public void moveNorth(){

		if(player.getX() > 0)
			player.setX(player.getX()-1);
		else if(player.getX() == 0)
			player.setX(player.getX());
	}

	public void moveSouth(){

		if(player.getX() < mapSize-1)
			player.setX(player.getX()+1);
		else if(player.getX() == mapSize)
			player.setX(player.getX());
	}

	public void moveWest(){

		if(player.getY() > 0)
			player.setY(player.getY()-1);
		else if(player.getY() == 0)
			player.setY(player.getY());
	}

	public void moveEast(){

		if(player.getY() < mapSize-1)
			player.setY(player.getY()+1);
		else if(player.getY() == mapSize)
			player.setY(player.getY());
	}
}