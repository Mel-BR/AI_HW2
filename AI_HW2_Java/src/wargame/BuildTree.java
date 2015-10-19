import java.util.LinkedList;
import java.util.Queue;

public class BuildTree {
	int[][] board = new int [6][6];
	int[][]  boardState = new int [6][6];
	Node root;
	
	public BuildTree(String boardName, int depth){
		board = HelpFunc.textToMatrix(boardName);
		root = new Node(-1, -1, 1);
		recursiveBuild(root, depth);
	}

	public void recursiveBuild(Node root, int depth){
		if (depth == 0){
			root.evaluateUtility(1);
		}
		for (int x = 0; x<board.length; x++){
			for (int y= 0; y<board[0].length; y++){
				if (root.checkParents(x,y) == 0){
					recursiveBuild(new Node(x,y,(root.player==1)?2:1), depth-1);
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
		Queue<Node> children = new LinkedList<Node>();
		
		public Node(int xx, int yy, int pl){
			x = xx;
			y = yy;
			player = pl;
			moveType = '-';
			parent.children.add(this);
			
			evaluateType();
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
		
		public void evaluateUtility(int player){
			root.utility = 0;
			for (int x = 0; x<board.length; x++){
				for (int y= 0; y<board[0].length; y++){
					if (root.checkParents(x,y) ==player){
						root.utility += board[x][y];
					}
				}
			}
		}
	}
}

