package guii;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;




public class GUIPanel extends JPanel{

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Image box;
	private Image player1unit;
	private Image player2unit;

	private final int boxID = 0;
	private final int player1ID = 1;
	private final int player2ID = 2;

	private int boxSize = 100;
	private int iconSize = (boxSize*100*2)/3/100;
	public Dimension windowSize;

	public int[][] currMatrix;
	private int[][] stateMatrix;


	public GUIPanel( int[][] currMatrix, int[][] stateMatrix) {

		this.currMatrix = currMatrix;
		this.stateMatrix = stateMatrix;


		int width = (int) ((this.currMatrix[0].length+0.14)*boxSize);
		int height = (int) ((this.currMatrix.length+0.3)*boxSize);
		windowSize = new Dimension();
		this.windowSize.setSize(width+5,height+20);

		readImages();




	}


	private void readImages(){

		box = new ImageIcon(this.getClass().getResource("/gui/res/box.png")).getImage().getScaledInstance(boxSize, boxSize, Image.SCALE_SMOOTH);
		player1unit = new ImageIcon(this.getClass().getResource("/gui/res/coin.png")).getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
		player2unit = new ImageIcon(this.getClass().getResource("/gui/res/coin2.png")).getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);


	}

	public void tick(){


	}



	public void setMatrix(int[][] matrix){
		this.currMatrix=matrix;

	}






	private void updateBoard(Graphics g){
		for(int i = 0; i < currMatrix.length; i++){
			for (int j = 0; j < currMatrix[i].length; j++){
				int value = currMatrix[i][j];
				int valueState = stateMatrix[i][j];

				if (value==boxID){

				}else if(value==player1ID){
					g.drawImage(player1unit, j*boxSize+iconSize/4, i*boxSize+iconSize/4, null);
				}
				else if(value==player2ID){
					g.drawImage(player2unit, j*boxSize+iconSize/4, i*boxSize+iconSize/4, null);
				}
				else{
					System.out.printf("Unknown ID number for image tile:%d\n",value);
				}
				g.drawImage(box, j*boxSize, i*boxSize, null);
				setBackground(Color.white);
				setForeground(Color.darkGray);
				Font trbi = new Font("TimesRoman", Font.BOLD, 24);
				g.setFont(trbi);
				g.drawString(Integer.toString(valueState),j*boxSize+iconSize/2+5, i*boxSize+iconSize*3/4+5);

			}
		}


	}




	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		updateBoard(g);

	}

}