
public class BuildTree {
	int[][] board = new int [6][6];
	int[][]  boardState = new int [6][6];
	
	public BuildTree(String boardName, int deapth){
		board = HelpFunc.textToMatrix(boardName);
		
		
	}

	
	class Node {
		Node parent;
		int utility;
		int x;
		int y;
		int player; // p1 1, p2 2
		char moveType; // Blitz b, Parachute p
		
		public Node(int xx, int yy, int pl, char move){
			x = xx;
			y = yy;
			player = pl;
			moveType = move;
		}
		
		public int checkParents(int x, int y){
			if (x == -1 && y == -1)
				return boardState[x][y];
			else if (parent.x != x && parent.y != y)
				return parent.checkParents(x, y);
			else
				return (parent.player);
		}
		
		public void evaluateUtility(){
			int ret = 0;
			if (checkParents(x+1, y) == player || checkParents(x-1, y) == player || checkParents(x, y+1) == player || checkParents(x, y-1) == player){
				int bonus = 0;
				if (checkParents(x+1, y) != player)
					
				ret = board[][]+bonus ;
			}
			utility = ret;
		}
	}
}

