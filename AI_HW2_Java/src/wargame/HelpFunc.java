import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HelpFunc {
	
	public int[][] textToMatrix(String textFile) throws IOException{
		int [][] board = new int [6][6];
		
		FileReader input = new FileReader("/HW2project/src/wargame/res/" + textFile);
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;

		for (int i = 0; (myLine = bufRead.readLine()) != null; i++)
		{    
		    String[] myString= myLine.split("\t");
		    for (int j = 0; j < myString.length; j++)
		        board[j][i]= Integer.parseInt(myString[j]);
		}
		return board;
	}
	
	
	
	
	

}
