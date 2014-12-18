
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class RogueGame extends JFrame {
    
	private int x;
	private int y;
	
    public RogueGame() {
    	this.setTitle("BoardTest");
		this.setSize(800, 600);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		initUI();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
   
    private void initUI() {
    	GameBoard gb = new GameBoard();
    	
	}

 private void getDimensions(){
    	
    }
    
	public static void main(String[] args)
	{
		new RogueGame();
	}
   
}