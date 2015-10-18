
public class BuildTree {
	int[][] board = new int [6][6];
	int[][]  boardState = new int [6][6];
	
	public BuildTree(String boardName, int deapth){
		board = HelpFunc.textToMatrix(boardName);
		Node root = new Node(-1, -1, 1);
		recursiveBuild(root, deapth);
	}

	public void recursiveBuild(Node root, int deapth){
		for (int x = 0; x<board.length; x++){
			for (int y= 0; y<board[0].length; y++){
				if (root.checkParents(x,y) == 0){
					recursiveBuild(new Node(x,y,(root.player==1)?2:1), deapth-1);
				}
			}
		}
	}
	
	class Node {
		Node parent;
		int utility;
		int x;
		int y;
		int player; // p1 1, p2 2
		char moveType; // Blitz b, Parachute p
		
		public Node(int xx, int yy, int pl){
			x = xx;
			y = yy;
			player = pl;
			moveType = '-';
			
			evaluateType();
			evaluateUtility();
		}
		
		public int checkParents(int x, int y){
			if (x == -1 && y == -1)
				return boardState[x][y];
			else if (parent.x != x && parent.y != y)
				return parent.checkParents(x, y);
			else
				return (parent.player);
		}
		
		public void evaluateType(){
			if (checkParents(x+1, y) == player || checkParents(x-1, y) == player || checkParents(x, y+1) == player || checkParents(x, y-1) == player){
				moveType = 'b';
			}
			else 
				moveType = 'p';
		}
		
		public void evaluateUtility(){
			utility = board[x][y];
			if (moveType=='b'){
				if (checkParents(x+1, y) != player && checkParents(x+1, y) != 0)
					utility += board[x+1][y];
				if (checkParents(x-1, y) != player && checkParents(x-1, y) != 0)
					utility += board[x-1][y];
				if (checkParents(x, y+1) != player && checkParents(x, y+1) != 0)
					utility += board[x][y+1];
				if (checkParents(x, y-1) != player && checkParents(x, y-1) != 0)
					utility += board[x][y-1];
			}
		}
	}
}

