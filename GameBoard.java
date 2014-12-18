
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameBoard extends JFrame {
    
    public GameBoard() {
    	JPanel consolePanel = new JPanel();
		JPanel gamePanel = new JPanel();
		
		add(consolePanel);
		consolePanel.setBackground(Color.BLUE);
		consolePanel.setBounds(20, 20, 500, 520);

		add(gamePanel);
		gamePanel.setBackground(Color.YELLOW);
		gamePanel.setLayout(null);
		gamePanel.setBounds(530, 20, 230, 520);
		
		
		JButton buton1 = new JButton("Buton1");
		gamePanel.add(buton1);
		buton1.setBounds(20 ,30 ,150 ,40);
		
    }
    
}