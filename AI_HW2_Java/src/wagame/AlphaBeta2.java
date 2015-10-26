package wagame;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import gui.GUI;
import wagame.BuildTree2.Node;

public class AlphaBeta2 {
	
	public void searchMaxMove(int[][] board, int[][] currMatrix, int depth,int player, GUI gui){
		BuildTree2 tree = new BuildTree2(board, currMatrix, depth, player);
		
		Object[] solution = maxValue(tree.root, 9999);
		Node solutionNode = (Node) solution[1];
		
		
		
		for (int y = 0; y < solutionNode.state.length; y++){
			for (int x= 0; x< solutionNode.state[0].length; x++){
				currMatrix [x][y] = solutionNode.state [x][y];
			}
		}
		
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
				int tempvalue = (Integer) temp[0];
				
				if(tempNode.player==1){
					GUI.player1Nodes++;}
					else{GUI.player2Nodes++;}
				
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
				int tempvalue = (Integer)temp[0];
				if(tempvalue<bestValue){
					bestValue = tempvalue;
					bestNode = tempNode;
				}
				
				if(tempNode.player==1){
					GUI.player1Nodes++;}
					else{GUI.player2Nodes++;}
				
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


