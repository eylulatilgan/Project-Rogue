import java.util.Random;
import java.util.Scanner;

public class RogueExecutor {

	Object[][] map;
	int boundarySize = 10;
	int score = 0;
	int level = 0;
	int x;
	int y;
	boolean isGameOver = false;
	String consoleTxt = "";
	
	
	
	public static void main(String[] args){
		
		gettingMapSize();
		
	}



	private static void gettingMapSize() {
		int inputSize = 0;
		
		Scanner sc = new Scanner(System.in);

        while (inputSize < 10 || inputSize > 20) {
            System.out.print("MAP SIZE (10-20): ");
            while(!sc.hasNextInt()) {
                System.out.print("TRY TO USE NUMBERS IF YOU CAN: ");
                sc.next();
            }
            inputSize = sc.nextInt();
            if (inputSize < 10 || inputSize > 20) {
                System.out.println("'(10-20)' MEANS; YOU CAN ONLY USE NUMBERS BETWEEN 10 TO 20. SO PLEASE... ");
            }
        }
        sc.close();
	}
	
	
}
