package wagame;
import java.util.Iterator;

import wagame.BuildTree.Node;

public class AlphaBeta {
	int move;
	BuildTree tree;
	int[][]boardPlayer;
	public AlphaBeta(int[][] board, int[][]state, int depth, int player){
		boardPlayer=state;
		tree = new BuildTree(board, boardPlayer, depth, player);
		move = recursiveSearch(tree.root, depth, -10000, 10000);
	}

	public int recursiveSearch(BuildTree.Node node, int depth, int alpha, int beta){
		if (node.children.size()==0){
			//System.out.println("Leaf with utility: " + root.utility);
			return node.utility;
		}
		else if(node.player==tree.playerSuper){
			int val = -10000;
			Iterator<BuildTree.Node> it = node.children.iterator();
			while (it.hasNext()){
				val = Math.max(val, recursiveSearch(it.next(), depth-1, alpha, beta));
				alpha = Math.max(val, alpha);
				if (beta <= alpha)
					break;
			}
			node.utility = val;
			return val;
		}
		else{
			int val = 10000;
			Iterator<BuildTree.Node> it = node.children.iterator();
			while (it.hasNext()){
				val = Math.min(val, recursiveSearch(it.next(), depth-1, alpha, beta));
				alpha = Math.min(val, alpha);
				if (beta <= alpha)
					break;
			}
			node.utility = val;
			return val;			
		}
	}
	
	public Node getSol(){
		return tree.searchChildren(move);
	}
	
	public void applySol(Node sol){
		boardPlayer[sol.x][sol.y]=sol.player;
		//System.out.println("Move executed type: " + sol.moveType + " with utility: " + sol.utility);
		//if (sol.moveType == 'b'){
			if (sol.x+1 < 6 && boardPlayer[sol.x+1][sol.y] == ((sol.player == 1)? 2:1))
				boardPlayer[sol.x+1][sol.y] = sol.player;
			if (sol.x-1 > -1 && boardPlayer[sol.x-1][sol.y] == ((sol.player == 1)? 2:1))
				boardPlayer[sol.x-1][sol.y] = sol.player;
			if (sol.y+1 < 6 && boardPlayer[sol.x][sol.y+1] == ((sol.player == 1)? 2:1))
				boardPlayer[sol.x][sol.y+1] = sol.player;
			if (sol.y-1 > -1 && boardPlayer[sol.x][sol.y-1] == ((sol.player == 1)? 2:1))
				boardPlayer[sol.x][sol.y-1] = sol.player;
		//}
	}
	
}
