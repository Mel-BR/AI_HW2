package wagame;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import gui.GUI;

public class BuildTree2 {
	int[][] boardNums;
	int[][]  boardState;
	Node root;
	public int count = 0;
	int playerSuper;

	public BuildTree2(int[][] boardNums, int[][] boardState, int depth, int player){
		
		this.boardNums = boardNums;
		this.boardState = boardState;
		this.playerSuper = player;
		//int[][] emptyMatrix = new int[6][6];
		this.root = new Node(null,boardState, playerSuper);
		recursiveBuild(root, depth);
	}

	public void recursiveBuild(Node node, int depth){
		if (depth <= 0){
			node.evaluateUtility(this.root.player);
			return;
		}
		for (int x = 0; x<boardNums.length; x++){
			for (int y= 0; y<boardNums[0].length; y++){
				if (node.state[x][y] == 0){
					
					
					
					int[][] newState = HelpFunc.copy(node.state);
					HelpFunc.makeMove(newState, x, y, node.player);
					
					recursiveBuild(new Node(node, newState,(node.player==1)?2:1), depth-1);
				}
			}
		}
	}


	
	public class Node {
		Node parent;
		int utility;
		int[][] state;
		public int player; // p1 1, p2 2
		List<Node> children = new LinkedList<Node>();

		public Node(Node par, int[][] state, int pl){
			this.state=state;
			this.player = pl;
			this.parent=par;
			if (this.parent != null){
				this.parent.children.add(this);}
		}



		public void evaluateUtility(int player){
			utility = 0;
			for (int x = 0; x<this.state.length; x++){
				for (int y= 0; y<this.state[0].length; y++){
					if (this.state[x][y] ==player){
						utility += boardNums[x][y];
					}
					else if(this.state[x][y] == ((player ==1)? 2:1)){
						utility -= boardNums[x][y];
					}
				}
			}
//			int [][] tempBoard = HelpFunc.copy(boardState);
			utility += HelpFunc.extraUtility(this.state,boardNums,player);
		}
		
		
	}
}

