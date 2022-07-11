package snakeGame;
import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
	MyPanel gamePanel=new MyPanel();
	ImageIcon icon=new ImageIcon("snake.png");
	MyFrame(){
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Snake");
		this.getContentPane().setBackground(Color.black);
		this.setIconImage(icon.getImage());

		this.add(gamePanel);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
