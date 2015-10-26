package wagame;

import java.util.List;
import java.util.Queue;

import wagame.BuildTree.Node;

public class AlphaBeta2 {
	
	
	public void searchMaxMove(int[][] board, int[][] currMatrix, int depth,int player){
		BuildTree tree = new BuildTree(board, currMatrix, depth, player);
		
		Object[] solution = maxValue(tree.root, 9999);
		Node solutionNode = (Node) solution[1];
		System.out.printf("row=%d,col=%d\n",solutionNode.x,solutionNode.y);
		HelpFunc.makeMove(currMatrix, solutionNode.x, solutionNode.y, player);
//		currMatrix[solutionNode.x][solutionNode.y]=player;
	}
	
	
	
	public  Object[] maxValue(Node inNode, int pruningCutOff){
		Object[] values = new Object[2];
		
		List<Node> list = (List<Node>) inNode.children;
		if (!list.isEmpty()){
			
			int bestValue = -9999;
			Node bestNode = null;
			while(!list.isEmpty()){
				Node tempNode = list.remove(0);
				Object[] temp = minValue(tempNode,bestValue);
				int tempvalue = (int) temp[0];
				
				if(tempvalue>bestValue){
					bestValue = tempvalue;
					bestNode = tempNode;
				}if(bestValue>pruningCutOff){
					break;
				}
				
			}
			values[0] = bestValue;
			values[1] = bestNode;
			
			return values;
			
		}else{
			values[0] = inNode.utility;
			values[1] = null;
			
			return values;
		}
	}



	private Object[] minValue(Node inNode, int pruningCutOff) {
		Object[] values = new Object[2];
		
		List<Node> list = (List<Node>) inNode.children;
		if (!list.isEmpty()){
			
			int bestValue = 9999;
			Node bestNode = null;
			while(!list.isEmpty()){
				Node tempNode = list.remove(0);
				Object[] temp = maxValue(tempNode,bestValue);
				int tempvalue = (int)temp[0];
				if(tempvalue<bestValue){
					bestValue = tempvalue;
					bestNode = tempNode;
				}
				if(bestValue<pruningCutOff){
					break;
				}
				
			}
			values[0] = bestValue;
			values[1] = bestNode;
			
			return values;
			
		}else{
			values[0] = inNode.utility;
			values[1] = null;
			
			return values;
		}
	}
	
	
	
	
	
	
}


