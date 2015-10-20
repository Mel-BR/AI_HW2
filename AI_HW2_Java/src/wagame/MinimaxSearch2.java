package wagame;

import java.util.Iterator;

public class MinimaxSearch2 {
	int move;
	BuildTree tree;
	public MinimaxSearch2(int[][] board, int depth){
		tree = new BuildTree(board, depth);
		move = recursiveSearch(tree.root, depth, 0, 10000);
	}

	public int recursiveSearch(BuildTree.Node root, int depth, int alpha, int beta){
		if (root.children.size()==0){
			return root.utility;
		}
		else if(root.player==1){
			int val = 0;
			Iterator<BuildTree.Node> it = root.children.iterator();
			while (it.hasNext()){
				val = Math.max(val, recursiveSearch(it.next(), depth-1, alpha, beta));
			}
			root.utility = val;
			return val;
		}
		else{
			int val = 10000;
			Iterator<BuildTree.Node> it = root.children.iterator();
			while (it.hasNext()){
				val = Math.min(val, recursiveSearch(it.next(), depth-1, alpha, beta));
			}
			root.utility = val;
			return val;			
		}
	}
}
