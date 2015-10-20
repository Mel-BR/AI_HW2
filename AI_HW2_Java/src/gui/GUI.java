package gui;

import javax.swing.JFrame;

import wagame.AlphaBeta;
import wagame.BuildTree.Node;
import wagame.HelpFunc;

public class GUI implements Runnable {


	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 3;
	public static final String NAME = "Wargame";

	public static final int SIZE =1680;
	
	public boolean running = false;
	
	private long tickCount = 0;
	
	public static JFrame frame;
	public static GUIPanel panel;

	public static int windowW = SIZE;

	public static int windowH = SIZE*10/16;

	public static float scaleW = 1;
	public static float scaleH = 1;
	
	int ticks = 0;
	int frames = 0;
	
	int[][] boardState = new int[6][6];
	int[][] currBoard = new int[6][6];
	
	public GUI(){
		

		boardState = HelpFunc.textToMatrix("Keren.txt");
		
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new GUIPanel(currBoard,boardState);
		frame.add(panel);
		
		frame.setSize(panel.windowSize);
		frame.add(panel);
		frame.setVisible(true);
		
			
		
	
	}
	
	public synchronized void start(){
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop(){
		running = false;
		
	}
	
	
	public void run() {
		long lastTime = System.nanoTime();
		double nanoPerTick = 1000000000D/1; //1/60 sec
		

		
		double delta = 0D;
		
		while (running){
			long now = System.nanoTime();
			delta += (now - lastTime)/nanoPerTick;
			lastTime = now;
			boolean shouldRender = false;
					
			while (delta >= 1){
		    ticks++;
			tick();
			delta -= 1;
			shouldRender = true;
			} 
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (shouldRender){
			frames++;
			render();
			}
			
			/*if (System.currentTimeMillis()-lastTimer >= 1000){
				lastTimer += 1000;
				System.out.println(frames+", "+ticks);
				frames = 0;
				ticks = 0;
			}*/
			
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void tick(){
		//Do some shit here
		
		AlphaBeta alf = new AlphaBeta(this.boardState, this.currBoard,3 ,1);
		Node sol = alf.getSol();
		this.currBoard[sol.x][sol.y]=sol.player;
		panel.setMatrix(this.currBoard);
		
		alf = new AlphaBeta(this.boardState, this.currBoard,3 ,2);
		sol = alf.getSol();
		this.currBoard[sol.x][sol.y]=sol.player;
		panel.setMatrix(this.currBoard);
		
		tickCount++;


	}

	public void render(){
		//paint some things you already know
		frame.repaint();
		//frame.panel.repaint();
		
	}
	
	
	public static void main(String[] args){
		GUI gui = new GUI();
		gui.start();

	}
}
