package wagame;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import gui.GUI;

public class BuildTree {
	int[][] boardNums = new int [6][6];
	int[][]  boardPlayer = new int [6][6];
	Node root;
	public int count = 0;
	int playerSuper;

	public BuildTree(int[][] boardIn, int[][] boardS, int depth, int player){
		
		boardNums = boardIn;
		boardPlayer = boardS;
		playerSuper = player;
		root = new Node(null,-1, -1, playerSuper);
		recursiveBuild(root, depth);
	}

	public void recursiveBuild(Node node, int depth){
		if (depth <= 0){
			node.evaluateUtility(this.root.player);
			return;
		}
		for (int x = 0; x<boardNums.length; x++){
			for (int y= 0; y<boardNums[0].length; y++){
				if (node.checkParents(x,y) == 0){
					//System.out.println("Depth: " + depth + "	X:	" +x + "	Y:	" +y);
					if(playerSuper==1){
					GUI.player1Nodes++;}
					else{GUI.player2Nodes++;}
					
					recursiveBuild(new Node(node, x,y,(node.player==1)?2:1), depth-1);
				}
			}
		}
	}

	public Node searchChildren(int val){
		//System.out.println(root.children);
		ListIterator<Node> it = root.children.listIterator();
		while (it.hasNext()){
			Node here = it.next();
			if (here.utility == val){
				return here;
			}
		}
		return null;
	}

	public class Node {
		Node parent;
		int utility;
		public int x;
		public int y;
		public int player; // p1 1, p2 2
		char moveType; // Blitz b, Parachute p
		List<Node> children = new LinkedList<Node>();

		public Node(Node par, int xx, int yy, int pl){
			x = xx;
			y = yy;
			player = pl;
			moveType = '-';
			parent=par;
			if (parent != null){
				parent.children.add(this);
				evaluateType();}
		}

		public int checkParents(int x, int y){
			if (x < 0 || x>5 || y < 0 || y > 5)
				return 0;
			if (parent == null)
				return boardPlayer[x][y];
			else if (boardPlayer[x][y] != 0)
				return boardPlayer[x][y];
			else if (parent.x != x && parent.y != y)
				return parent.checkParents(x, y);
			else
				return (parent.player);
		}

		public void evaluateType(){
			if (checkParents(x+1, y) == player || checkParents(x-1, y) == player || checkParents(x, y+1) == player || checkParents(x, y-1) == player)
			{
				moveType = 'b';
				if (x+1 < 6 && checkParents(x+1, y) == ((player == 1)? 2:1)){
				}
				if (x-1 > -1 && checkParents(x-1, y) == ((player == 1)? 2:1)){
				}
				if (y+1 < 6 && checkParents(x, y+1) == ((player == 1)? 2:1)){
				}
				if (y-1 > -1 && checkParents(x, y-1) == ((player == 1)? 2:1)){
				}
			}
			else 
				moveType = 'p';
		}

		public void evaluateUtility(int player){
			utility = 0;
			for (int x = 0; x<boardNums.length; x++){
				for (int y= 0; y<boardNums[0].length; y++){
					if (boardNums[x][y] ==player){
						utility += boardNums[x][y];
					}
					else if(boardNums[x][y] == ((player ==1)? 2:1)){
						utility -= boardNums[x][y];
					}
				}
			}
			int [][] tempBoard = HelpFunc.copy(boardPlayer);
			utility += recursiveUtility(tempBoard);
			utility += HelpFunc.extraUtility(tempBoard,boardNums,player);
		}
		
		public int recursiveUtility (int [][]board){
			if (parent.parent == null){
				return HelpFunc.addPos(board, boardNums, this);
			}
			return parent.recursiveUtility(board)+HelpFunc.addPos(board, boardNums, this);
		}
	}
}

