


public class RoomChecker {
	
	//INSTALLATION:
	//Place the methods "checkAroundTheCurrentRoomFor..." under the "Map" class that's
	//found in the RogueExecutor.
	
	//EXECUTION:
	//Bind these methods under the keyPressed() method this way: 
	/*					case KeyEvent.VK_UP:
						if(movable){
							controller.moveNorth();
							updateCellExploredStatus(player.getX(), player.getY());
							updateCurrentCell(player.getX(), player.getY(), player.getX()+1, player.getY());
							map.checkAroundTheRoomForLV1Enemies(map.mapArray, player.getX(), player.getY());
							map.checkAroundTheRoomForLV2Enemies(map.mapArray, player.getX(), player.getY());
							map.checkAroundTheRoomForLV3Enemies(map.mapArray, player.getX(), player.getY());
						}
						movable = false;
						break;
	*/
	
	public void checkAroundTheRoomForLV1Enemies(Room[][] rooms, int x, int y){
		if(rooms[x-1][y].getRoomContent().equals("E")){
			RogueExecutor.consoleTxt += "\n" + "1-Level enemy at: " + (x-1) + "," + (y);
		} else if(rooms[x+1][y].getRoomContent().equals("E")){ 
			RogueExecutor.consoleTxt += "\n" + "1-Level enemy at: " + (x+1) + "," + (y);
		} else if(rooms[x][y-1].getRoomContent().equals("E")){
			RogueExecutor.consoleTxt += "\n" + "1-Level enemy at: " + (x) + "," + (y-1);
		} else if(rooms[x][y+1].getRoomContent().equals("E")){
			RogueExecutor.consoleTxt += "\n" + "1-Level enemy at: " + (x) + "," + (y+1);
		} else if(rooms[x-1][y-1].getRoomContent().equals("E")){
			RogueExecutor.consoleTxt += "\n" + "1-Level enemy at: " + (x-1) + "," + (y-1);
		} else if(rooms[x+1][y-1].getRoomContent().equals("E")){
			RogueExecutor.consoleTxt += "\n" + "1-Level enemy at: " + (x+1) + "," + (y-1);
		} else if(rooms[x-1][y+1].getRoomContent().equals("E")){
			RogueExecutor.consoleTxt += "\n" + "1-Level enemy at: " + (x-1) + "," + (y+1);
		} else if(rooms[x+1][y+1].getRoomContent().equals("E")){
			RogueExecutor.consoleTxt += "\n" + "1-Level enemy at: " + (x-1) + "," + (y+1);
		}
	}
	
	public void checkAroundTheRoomForLV2Enemies(Room[][] room, int x, int y){
		if(room[x-1][y].getRoomContent().equals("EE")){
			RogueExecutor.consoleTxt += "\n" + "2-Level enemy at: " + (x-1) + "," + (y);
		} else if(room[x+1][y].getRoomContent().equals("EE")){ 
			RogueExecutor.consoleTxt += "\n" + "2-Level enemy at: " + (x+1) + "," + (y);
		} else if(room[x][y-1].getRoomContent().equals("EE")){
			RogueExecutor.consoleTxt += "\n" + "2-Level enemy at: " + (x) + "," + (y-1);
		} else if(room[x][y+1].getRoomContent().equals("EE")){
			RogueExecutor.consoleTxt += "\n" + "2-Level enemy at: " + (x) + "," + (y+1);
		} else if(room[x-1][y-1].getRoomContent().equals("EE")){
			RogueExecutor.consoleTxt += "\n" + "2-Level enemy at: " + (x-1) + "," + (y-1);
		} else if(room[x+1][y-1].getRoomContent().equals("EE")){
			RogueExecutor.consoleTxt += "\n" + "2-Level enemy at: " + (x+1) + "," + (y-1);
		} else if(room[x-1][y+1].getRoomContent().equals("EE")){
			RogueExecutor.consoleTxt += "\n" + "2-Level enemy at: " + (x-1) + "," + (y+1);
		} else if(room[x+1][y+1].getRoomContent().equals("EE")){
			RogueExecutor.consoleTxt += "\n" + "2-Level enemy at: " + (x-1) + "," + (y+1);
		}
	}
	
	public void checkAroundTheRoomForLV3Enemies(Room[][] room, int x, int y){
		if(room[x-1][y].getRoomContent().equals("EEE")){
			RogueExecutor.consoleTxt += "\n" + "3-Level enemy at: " + (x-1) + "," + (y);
		} else if(room[x+1][y].getRoomContent().equals("EEE")){ 
			RogueExecutor.consoleTxt += "\n" + "3-Level enemy at: " + (x+1) + "," + (y);
		} else if(room[x][y-1].getRoomContent().equals("EEE")){
			RogueExecutor.consoleTxt += "\n" + "3-Level enemy at: " + (x) + "," + (y-1);
		} else if(room[x][y+1].getRoomContent().equals("EEE")){
			RogueExecutor.consoleTxt += "\n" + "3-Level enemy at: " + (x) + "," + (y+1);
		} else if(room[x-1][y-1].getRoomContent().equals("EEE")){
			RogueExecutor.consoleTxt += "\n" + "3-Level enemy at: " + (x-1) + "," + (y-1);
		} else if(room[x+1][y-1].getRoomContent().equals("EEE")){
			RogueExecutor.consoleTxt += "\n" + "3-Level enemy at: " + (x+1) + "," + (y-1);
		} else if(room[x-1][y+1].getRoomContent().equals("EEE")){
			RogueExecutor.consoleTxt += "\n" + "3-Level enemy at: " + (x-1) + "," + (y+1);
		} else if(room[x+1][y+1].getRoomContent().equals("EEE")){
			RogueExecutor.consoleTxt += "\n" + "3-Level enemy at: " + (x-1) + "," + (y+1);
		}
	}
	
}
