package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class infoPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GUI gui;
	JLabel[] labels1,labels2, labels3, labels4;


	public infoPanel(GUI gui){
		this.setOpaque(false);
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5, true), "Info"));
		this.setLayout(null);
		this.gui = gui;
		
		
		labels1 = new JLabel[5];
		for (int i = 0; i < 5 ; i++){
			labels1[i] = new JLabel();
			labels1[i].setBounds(20+150*i, 10, 150, 40);
			this.add(labels1[i]);
		}
		
		labels1[0].setText("Current player:");
		labels1[1].setText("Player 1 Score:");
		labels1[2].setText("Player 2 Score:");
		labels1[3].setText("Player 1 Acc Time:");
		labels1[4].setText("Player 2 Acc Time:");
		
		labels2 = new JLabel[5];
		for (int i = 0; i < 5 ; i++){
			labels2[i] = new JLabel();
			labels2[i].setBounds(20+150*i, 30, 150, 40);
			this.add(labels2[i]);
		}
		
		labels2[0].setText("hej");
		labels2[1].setText("hej");
		labels2[2].setText("hej");
		labels2[3].setText("hej");
		labels2[4].setText("hej");
		
		
		labels3 = new JLabel[5];
		for (int i = 0; i < 5 ; i++){
			labels3[i] = new JLabel();
			labels3[i].setBounds(20+150*i, 70, 150, 40);
			this.add(labels3[i]);
		}
		
		labels3[0].setText(" ");
		labels3[1].setText("Player 1 Exp Nodes:");
		labels3[2].setText("Player 2 Exp Nodes:");
		labels3[3].setText("Avg Time per move");
		labels3[4].setText("Avg Nodes per move ");
		
		labels4 = new JLabel[5];
		for (int i = 0; i < 5 ; i++){
			labels4[i] = new JLabel();
			labels4[i].setBounds(20+150*i, 90, 150, 40);
			this.add(labels4[i]);
		}
		
		labels4[0].setText(" ");
		labels4[1].setText(" ");
		labels4[2].setText(" ");
		labels4[3].setText(" ");
		labels4[4].setText(" ");
		 
		
	}
}
