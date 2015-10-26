package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.GUIPanel;
import wagame.AlphaBeta;
import wagame.AlphaBeta2;
import wagame.BuildTree.Node;
import wagame.HelpFunc;
import wagame.MinimaxSearch;
import wagame.MinimaxSearch2;

public class GUI implements Runnable {


	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 3;
	public static final String NAME = "Wargame";

	public static final int SIZE =1680;

	public boolean running = false;
	public boolean tack = true;

	private long tickCount = 0;

	public static JFrame frame;
	public static GUIPanel panel;
	public static InterfacePanel interPanel;
	public static infoPanel infoPanel;
	
	public static int windowW = SIZE;

	public static int windowH = SIZE*10/16;

	public static float scaleW = 1;
	public static float scaleH = 1;

	int ticks = 0;
	int frames = 0;

	int[][] boardState = new int[6][6];
	int[][] currBoard = new int[6][6];
	
	int boxSize = 100;
	int player1mode = 0; //0 = human, 1 = minmax, 2 = alphabeta
	int player2mode = 1;
	int playersIDTurn = 1;
	
	public int player1Depth = 4;
	public int player2Depth = 4;
	
	public static int player1Nodes = 0;
	public static int player2Nodes = 0;
	
	long startTime;
	long player1Time;
	long player2Time;

	public GUI(){


		boardState = HelpFunc.textToMatrix("Westerplatte.txt");
		
		int boardWidth = (int) ((boardState[0].length)*boxSize);
		int boardHeight = (int) ((boardState.length)*boxSize);



		panel = new GUIPanel(this,currBoard,boardState,boxSize);
		Eventhandler events = new Eventhandler(panel);
		panel.addMouseListener(events);
		panel.setBounds(0, 0, boardWidth,boardHeight);


		
		interPanel = new InterfacePanel(this);
		interPanel.setBounds(boardWidth, 0, 180,boardHeight);
		interPanel.setBackground(Color.white);

		
		
		infoPanel = new infoPanel(this);
		infoPanel.setBounds(0, boardHeight, boardWidth+interPanel.getWidth(), 150);
		infoPanel.setBackground(Color.white);
		
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);

		
		Dimension frameSize = new Dimension(panel.getWidth()+interPanel.getWidth()+30,panel.getHeight()+infoPanel.getHeight()+50);
		frame.setSize(frameSize);
		frame.add(panel);
		frame.add(interPanel);
		frame.add(infoPanel);
		frame.setVisible(true);

		startTime = System.currentTimeMillis();
		

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
		double nanoPerTick = 1000000000D/60; //1/60 sec



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
		updateLabels();
		if (!HelpFunc.isGameFinished(currBoard)){

			if(playersIDTurn == 1){

				if(player1mode==0){


				}else if(player1mode==1){
					MinimaxSearch minmax = new MinimaxSearch();
					minmax.searchMaxMove(this.boardState, this.currBoard,player1Depth ,1);


					this.player1Time+= System.currentTimeMillis()-startTime;
					playersIDTurn = 2;
					startTime = System.currentTimeMillis();


				}else if(player1mode==2){
					AlphaBeta alf = new AlphaBeta(this.boardState, this.currBoard,player1Depth ,2);
					alf.applySol(alf.getSol());
					panel.setMatrix(this.currBoard);

					this.player1Time+= System.currentTimeMillis()-startTime;
					playersIDTurn = 2;
					startTime = System.currentTimeMillis();

				}else if(player1mode==3){
					AlphaBeta2 alf = new AlphaBeta2();
					alf.searchMaxMove(this.boardState, this.currBoard,player1Depth ,1);

					this.player1Time+= System.currentTimeMillis()-startTime;
					playersIDTurn = 2;
					startTime = System.currentTimeMillis();

				}else if(player1mode==4){
					MinimaxSearch2 alf = new MinimaxSearch2(this.boardState, this.currBoard,player1Depth ,2);
					alf.applySol(alf.getSol());

					this.player1Time+= System.currentTimeMillis()-startTime;
					playersIDTurn = 2;
					startTime = System.currentTimeMillis();

				}



			}else{

				if(player2mode==0){


				}else if(player2mode==1){
					MinimaxSearch minmax = new MinimaxSearch();
					minmax.searchMaxMove(this.boardState, this.currBoard,player2Depth ,2);

					this.player2Time+= System.currentTimeMillis()-startTime;
					playersIDTurn = 1;
					startTime = System.currentTimeMillis();

				}else if(player2mode==2){
					AlphaBeta alf = new AlphaBeta(this.boardState, this.currBoard,player2Depth ,1);
					alf.applySol(alf.getSol());
					//panel.setMatrix(this.currBoard);

					this.player2Time+= System.currentTimeMillis()-startTime;
					playersIDTurn = 1;
					startTime = System.currentTimeMillis();

				}else if(player2mode==3){
					AlphaBeta2 alf = new AlphaBeta2();
					alf.searchMaxMove(this.boardState, this.currBoard,player2Depth ,2);


					this.player2Time+= System.currentTimeMillis()-startTime;
					playersIDTurn = 1;
					startTime = System.currentTimeMillis();

				}else if(player2mode==4){
					MinimaxSearch2 alf = new MinimaxSearch2(this.boardState, this.currBoard,player2Depth ,1);
					alf.applySol(alf.getSol());

					this.player2Time+= System.currentTimeMillis()-startTime;
					playersIDTurn = 1;
					startTime = System.currentTimeMillis();

				}

			}
		}

		tickCount++;
		

	}
	
	public void makeMove(int col, int row){
		if (playersIDTurn==1 && player1mode == 0){
			HelpFunc.makeMove(this.currBoard, row, col, playersIDTurn);
			
			this.player1Time+= System.currentTimeMillis()-startTime;
			playersIDTurn = 2;
			startTime = System.currentTimeMillis();
		}else if(playersIDTurn==2 && player2mode == 0){
			
			HelpFunc.makeMove(this.currBoard, row, col, playersIDTurn);
			
			this.player2Time+= System.currentTimeMillis()-startTime;
			playersIDTurn = 1;
			startTime = System.currentTimeMillis();
			}
		render();
	}
	
	public void updateLabels(){
		infoPanel.labels2[0].setText(Integer.toString(this.playersIDTurn));
		
		int[] score = HelpFunc.calculateScore(this.currBoard, this.boardState, 1, 2);
		infoPanel.labels2[1].setText(Integer.toString(score[0]));
		infoPanel.labels2[2].setText(Integer.toString(score[1]));
		
		infoPanel.labels2[3].setText(Float.toString((float) (this.player1Time/1000.0))+"[s]");
		infoPanel.labels2[4].setText(Float.toString((float) (this.player2Time/1000.0))+"[s]");
		
		infoPanel.labels4[1].setText(Integer.toString(this.player1Nodes));
		infoPanel.labels4[2].setText(Integer.toString(this.player2Nodes));
		
		infoPanel.labels4[3].setText(Float.toString((float) ((this.player1Time+this.player2Time)/1000.0/(36)))+"[s]");
		infoPanel.labels4[4].setText(Integer.toString((int) ((this.player1Nodes+this.player2Nodes)/(36.0))));

//		System.out.printf("Player1: Depth=%d, Mode=%d, playerIDturn?=%d\n",player1Depth,player1mode,this.playersIDTurn);
//		System.out.printf("Player2: Depth=%d, Mode=%d, playerIDturn?=%d\n",player2Depth,player2mode,this.playersIDTurn);

	}

	public void render(){
		//paint some things you already know
		panel.repaint();

	}


	public static void main(String[] args){
		GUI gui = new GUI();
		gui.start();

	}
}
