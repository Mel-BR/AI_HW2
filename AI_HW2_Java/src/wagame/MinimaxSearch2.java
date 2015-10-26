package wagame;
import java.util.Iterator;

import wagame.BuildTree.Node;

public class MinimaxSearch2 {
	int move;
	BuildTree tree;
	int[][]boardPlayer;
	public MinimaxSearch2(int[][] board, int[][]state, int depth, int player){
		boardPlayer=state;
		tree = new BuildTree(board, boardPlayer, depth, player);
		move = recursiveSearch(tree.root, depth);
	}

	public int recursiveSearch(BuildTree.Node root, int depth){
		if (root.children.size()==0){
			
			//System.out.println("Leaf with utility: " + root.utility);
			return root.utility;
		}
		else if(root.player==tree.playerSuper){
			int best = -10000;
			Iterator<BuildTree.Node> it = root.children.iterator();
			while (it.hasNext()){
				best = Math.max(best, recursiveSearch(it.next(), depth-1));
			}
			root.utility = best;
			return best;
		}
		else{
			int best = 10000;
			Iterator<BuildTree.Node> it = root.children.iterator();
			while (it.hasNext()){
				best = Math.min(best, recursiveSearch(it.next(), depth-1));
			}
			root.utility = best;
			return best;	
		}
	}
	
	public Node getSol(){
		return tree.searchChildren(move);
	}
	
	public void applySol(Node sol){
		boardPlayer[sol.x][sol.y]=sol.player;
		System.out.println("Move executed type: " + sol.moveType + " with utility: " + sol.utility);
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
