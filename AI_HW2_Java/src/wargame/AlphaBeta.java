package wargame;


import java.util.Iterator;

public class AlphaBeta {
	int move;
	BuildTree tree;
	public AlphaBeta(String board, int depth){
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
				alpha = Math.max(val, alpha);
				if (beta <= alpha)
					break;
			}
			root.utility = val;
			return val;
		}
		else{
			int val = 10000;
			Iterator<BuildTree.Node> it = root.children.iterator();
			while (it.hasNext()){
				val = Math.min(val, recursiveSearch(it.next(), depth-1, alpha, beta));
				alpha = Math.min(val, alpha);
				if (beta <= alpha)
					break;
			}
			root.utility = val;
			return val;			
		}
	}

}
