
public static class HelpFunc {
	
	public int[][] textToMatrix(String textFile){
		int [][] board = new int [6][6];
		
		FileReader input = new FileReader("/HW2project/src/wargame/res" + textFile);
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;

		while ( (myLine = bufRead.readLine()) != null)
		{    
		    String[] array1 = myLine.split(":");
		    // check to make sure you have valid data
		    String[] array2 = array1[1].split(" ");
		    for (int i = 0; i < array2.length; i++)
		        function(array1[0], array2[i]);
		}
		return board;
	}
	
	
	
	
	

}
