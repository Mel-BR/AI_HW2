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
			input = new FileReader("/maps/"+textFile);
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
		return board;
	}






}