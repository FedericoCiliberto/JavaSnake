package snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyPanel extends JPanel implements KeyListener{
	public static final int SCREEN_WIDTH=650;
	public static final int SCREEN_HEIGHT=650;
	public static final int UNIT=50;
	public static final int TIMER_SPEED=50;
	public int snakeDimension=5;
	public int appleX=150;
	public int appleY=150;
	public static final int AREA=(SCREEN_WIDTH/UNIT)*(SCREEN_HEIGHT/UNIT);
	public int[] snakeBodyPartsX=new int[AREA];
	public int[] snakeBodyPartsY=new int[AREA];
	Timer timer;
	char direction='D';  //Up, Down, Right,Left
	Random random=new Random();
	boolean gameOver=false;


	MyPanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setLayout(null);
		for(int i=0;i<AREA;i++) {
			snakeBodyPartsX[i]=-1;
			snakeBodyPartsY[i]=-1;
		}
		for(int i=0;i<snakeDimension;i++) {
			snakeBodyPartsX[i]=(snakeDimension-i)*UNIT;
			snakeBodyPartsY[i]=0;
		}
		this.addKeyListener(this);
		timer=new Timer(TIMER_SPEED,e->{
			move();
			gameOver=checkCollision();
			repaint();
		});
		timer.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		//drawGrid(g2d);
		g2d.setColor(Color.red);
		g2d.fillOval(appleX, appleY, UNIT, UNIT);
		g2d.setColor(new Color(0, 196, 23));
		for(int i=0;i<snakeDimension;i++) {
			g2d.fillRect(snakeBodyPartsX[i], snakeBodyPartsY[i], UNIT, UNIT);
			g2d.setColor(Color.green);
			
		}
		
		if(gameOver) {
			gameOver(g2d);
		}
		
	}
	
	public void drawGrid(Graphics2D g2d) {
		for(int i=0;i<SCREEN_WIDTH;i+=UNIT) {
			g2d.setColor(Color.white);
			g2d.drawLine(i, 0, i, SCREEN_HEIGHT);
		}
		
		for(int i=0;i<SCREEN_HEIGHT;i+=UNIT) {
			g2d.setColor(Color.white);
			g2d.drawLine(0, i, SCREEN_WIDTH, i);
		}
	}
	
	public void move() {
		if(checkCollisionSnakeApple()) {
			snakeDimension++;
			generateApple();
		}
		for(int i=snakeDimension-1;i>0;i--) {
			snakeBodyPartsX[i]=snakeBodyPartsX[i-1];
			snakeBodyPartsY[i]=snakeBodyPartsY[i-1];
		}
		switch(direction) {
		case 'U':
			snakeBodyPartsY[0]-=UNIT;
			break;
		case 'D':
			snakeBodyPartsY[0]+=UNIT;
			break;
		case 'L':
			snakeBodyPartsX[0]-=UNIT;
			break;
		case 'R':
			snakeBodyPartsX[0]+=UNIT;
			break;
		}
		
	}
	
	public void generateApple() {
		boolean ok=false;
		while(!ok) {
			appleX=random.nextInt(SCREEN_WIDTH/UNIT)*UNIT;
			appleY=random.nextInt(SCREEN_HEIGHT/UNIT)*UNIT;
			ok=true;
			for(int i=0;i<snakeDimension;i++) {
				if(snakeBodyPartsX[i]==appleX && snakeBodyPartsY[i]==appleY) {
					ok=false;
				}
			}
		}
	}
	
	public boolean checkCollisionSnakeApple() {
		if(snakeBodyPartsX[0]==appleX && snakeBodyPartsY[0]==appleY) {
			return true;
		}
		return false;
	}
	public boolean checkCollision() {
		int i,j;
		for(i=0;i<snakeDimension;i++) {
			for(j=0;j<snakeDimension;j++) {
				if(i!=j && snakeBodyPartsX[i]==snakeBodyPartsX[j] && snakeBodyPartsY[i]==snakeBodyPartsY[j]) {
					return true;
				}
			}
		}
		
		if(snakeBodyPartsX[0]<0 || snakeBodyPartsX[0]>=SCREEN_WIDTH 
				||snakeBodyPartsY[0]<0 || snakeBodyPartsY[0]>=SCREEN_HEIGHT) {
			return true;
		}
		return false;
	}
	public void gameOver(Graphics2D g2d) {
		timer.stop();
		JButton button=new JButton("Restart");
		button.addActionListener(e->{
			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
			topFrame.dispose();
			new MyFrame();
		});
		button.setFont(new Font("MV Bali",Font.PLAIN,20));
		button.setBackground(Color.green);
		button.setForeground(Color.white);
		button.setBounds(250, 400, 150, 100);
		this.add(button);
		super.paint(g2d);
		g2d.setFont(new Font("MV Bali",Font.BOLD,90));
		g2d.setColor(Color.red);
		g2d.drawString("Game Over!", 80, 250);
		
		g2d.setFont(new Font("MV Bali",Font.BOLD,40));
		g2d.setColor(Color.green);
		g2d.drawString("Final dimension:" + snakeDimension, 150, 300);
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch(e.getKeyChar()) {
		case 'w':
			if(direction!='D') {
				direction='U';
			}
			
			break;
			
		case 'a':
			if(direction!='R') {
				direction='L';
			}
			break;
		
		case 's':
			if(direction!='U') {
				direction='D';
			}
			
			break;
			
		case 'd':
			if(direction!='L') {
				direction='R';
			}
			break;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if(direction!='D') {
				direction='U';
			}
			break;
		case KeyEvent.VK_DOWN:
			if(direction!='U') {
				direction='D';
			}
			break;
			
		case KeyEvent.VK_LEFT:
			if(direction!='R') {
				direction='L';
			}
			break;
		case KeyEvent.VK_R:
			if(direction!='L') {
				direction='R';
			}
			break;
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
}
