package wagame;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import gui.infoPanel;

public class HelpFunc {

	public static int[][] textToMatrix(String textFile){
		int [][] board = new int [6][6];

		FileReader input;
		try {
			URL res = HelpFunc.class.getResource("/wagame/maps/"+textFile);
			input = new FileReader(res.getPath());
			BufferedReader bufRead = new BufferedReader(input);
			String myLine = null;

			for (int i = 0; (myLine = bufRead.readLine()) != null; i++)
			{    
				String[] myString= myLine.split("\t");
				for (int j = 0; j < myString.length; j++)
					board[j][i]= Integer.parseInt(myString[j]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for (int x = 0; x<6; x++){
//			for (int y = 0; y<6; y++){
//				System.out.print(board[y][x]);
//			}
//			System.out.println();
//		}
		return board;
	}
	
	public static int[][] copy(int[][] orig){
		int[][]cpy = new int[orig.length][orig[0].length];
		for (int y = 0; y < orig.length; y++)
			for (int x= 0; x< orig[0].length; x++)
				cpy [x][y] = orig [x][y];
		return cpy;
	}
	
	public static int[][] clearMatrix(int[][] matrix){
		for (int row = 0; row < matrix[0].length; row++) {
			for (int col = 0; col < matrix.length; col++) {
				matrix[row][col] = 0;
			}
		}
		return matrix;
	}
	
	public static void makeMove(int[][] currBoard, int x, int y, int player){
		int width = currBoard[0].length;
		int hight = currBoard.length;
		boolean blitz = false;
		
		currBoard[x][y]=player;
			if (x+1 < width && currBoard[x+1][y] ==  player){
				blitz = true;
			}else if(x-1 > -1 && currBoard[x-1][y] ==  player){
				blitz = true;
			}else if (y+1 < hight && currBoard[x][y+1] ==  player){
				blitz = true;
			}else if(y-1 > -1 && currBoard[x][y-1] ==  player){
				blitz = true;
			}
			
			if (blitz){
				if (x+1 < width && currBoard[x+1][y] ==  (player==1?2:1) ){
					currBoard[x+1][y] = player;
				}if(x-1 > -1 && currBoard[x-1][y] ==  (player==1?2:1)){
					currBoard[x-1][y] = player;
				}if (y+1 < hight && currBoard[x][y+1] ==  (player==1?2:1)){
					currBoard[x][y+1] = player;;
				}if(y-1 > -1 && currBoard[x][y-1] ==  (player==1?2:1)){
					currBoard[x][y-1] = player;
				}
			}
					
					
					
					
					
					
//					(player+1)%2  ((player == 1)? 2:1))
//				currBoard[x+1][y] = player;
//			if (x-1 > -1 && boardPlayer[sol.x-1][sol.y] == ((sol.player == 1)? 2:1))
//				boardPlayer[sol.x-1][sol.y] = sol.player;
//			if (y+1 < hight && boardPlayer[sol.x][sol.y+1] == ((sol.player == 1)? 2:1))
//				boardPlayer[sol.x][sol.y+1] = sol.player;
//			if (y-1 > -1 && boardPlayer[sol.x][sol.y-1] == ((sol.player == 1)? 2:1))
//				boardPlayer[sol.x][sol.y-1] = sol.player;
	}

}