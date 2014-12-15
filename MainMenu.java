import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainMenu extends JPanel {
	JPanel    mainMenuPanel = new JPanel();
	JTextArea nameOfTheGame = new JTextArea(10, 40);

	 public static void main(String[] args) {
	      JFrame f = new JFrame();
	      f.getContentPane().add(new MainMenu());
	      f.setSize(800, 600);
	      f.setVisible(true);
	   }
}
