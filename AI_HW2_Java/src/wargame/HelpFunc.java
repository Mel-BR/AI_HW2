
public static class HelpFunc {
	
	public int[][] textToMatrix(String textFile){
		int [][] board = new int [6][6];
		
		FileReader input = new FileReader("/HW2project/src/wargame/res/" + textFile);
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;

		for (int i = 0; (myLine = bufRead.readLine()) != null; i++)
		{    
		    String[i] = myLine.split('TAB');
		    for (int i = 0; i < array2.length; i++)
		        function(array1[0], array2[i]);
		}
		return board;
	}
	
	
	
	
	

}
