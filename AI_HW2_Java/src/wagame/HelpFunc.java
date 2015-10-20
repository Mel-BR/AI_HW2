package wagame;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HelpFunc {

	public static int[][] textToMatrix(String textFile){
		int [][] board = new int [6][6];

		FileReader input;
		try {
			input = new FileReader("C:/Users/Owner/Desktop/CS/Classes/CS440/AI_HW2/AI_HW2_Java/src/maps/"+textFile);
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

}