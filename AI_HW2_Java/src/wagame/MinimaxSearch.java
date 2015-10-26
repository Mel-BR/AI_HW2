package wagame;

import java.util.Queue;

import wagame.BuildTree.Node;

public class MinimaxSearch {
	
	
	public void searchMaxMove(int[][] board, int[][] currMatrix, int depth,int player){
		BuildTree tree = new BuildTree(board, currMatrix, depth, player);
		
		Object[] solution = maxValue(tree.root);
		Node solutionNode = (Node) solution[1];
		System.out.printf("row=%d,col=%d\n",solutionNode.x,solutionNode.y);
		HelpFunc.makeMove(currMatrix, solutionNode.x, solutionNode.y, player);
//		currMatrix[solutionNode.x][solutionNode.y]=player;
	}
	
	
	
	public  Object[] maxValue(Node inNode){
		Object[] values = new Object[2];
		
		Queue<Node> list = (Queue<Node>) inNode.children;
		if (!list.isEmpty()){
			
			int value = -9999;
			int valueOld = -9999;
			Node bestNode = null;
			while(!list.isEmpty()){
				int tempV = value;
				Node tempNode = list.remove();
				Object[] temp = minValue(tempNode);
				int tempvalue = (int) temp[0];
				
				if(tempvalue>valueOld){
					value = tempvalue;
					bestNode = tempNode;
				}
				valueOld = tempV;
				
			}
			values[0] = value;
			values[1] = bestNode;
			
			return values;
			
		}else{
			values[0] = inNode.utility;
			values[1] = null;
			
			return values;
		}
	}



	private Object[] minValue(Node inNode) {
		Object[] values = new Object[2];
		
		Queue<Node> list = (Queue<Node>) inNode.children;
		if (!list.isEmpty()){
			
			int value = 9999;
			int valueOld = 9999;
			Node bestNode = null;
			while(!list.isEmpty()){
				int tempV = value;
				Node tempNode = list.remove();
				Object[] temp = maxValue(tempNode);
				int tempvalue = (int)temp[0];
				if(tempvalue<valueOld){
					value = tempvalue;
					bestNode = tempNode;
				}
				valueOld = tempV;
				
			}
			values[0] = value;
			values[1] = bestNode;
			
			return values;
			
		}else{
			values[0] = inNode.utility;
			values[1] = null;
			
			return values;
		}
	}
	
	
	
	
	
	
}


