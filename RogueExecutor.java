import java.util.Random;
import java.util.Scanner;

public class RogueExecutor {

	static Object[][] map;
	static int boundarySize = 10;
	int score = 0;
	int level = 0;
	int x;
	int y;
	boolean isGameOver = false;
	String consoleTxt = "";
	
	
	
	public static void main(String[] args){
		
		gettingMapSize();
		generateMap();
		
	}


	private static void gettingMapSize() {
		
		Scanner sc = new Scanner(System.in);

        while (boundarySize < 10 || boundarySize > 20) {
            System.out.print("MAP SIZE (10-20): ");
            while(!sc.hasNextInt()) {
                System.out.print("TRY TO USE NUMBERS IF YOU CAN: ");
                sc.next();
            }
            boundarySize = sc.nextInt();
            if (boundarySize < 10 || boundarySize > 20) {
                System.out.println("'(10-20)' MEANS; YOU CAN ONLY USE NUMBERS BETWEEN 10 TO 20. SO PLEASE... ");
            }
        }
        sc.close();
	}

	private static void generateMap(){
		map = new Object[boundarySize][boundarySize];
	}
	
	
	
}
