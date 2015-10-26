package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import wagame.HelpFunc;

public class InterfacePanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton[] buttons;
	JRadioButton[] radioButtons1,radioButtons2;
	JComboBox player1Depth;
	JComboBox player2Depth;
	GUI gui;
	
	public InterfacePanel(GUI gui){
		
		this.setOpaque(false);
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5, true), "Buttons"));
		this.setLayout(null);
		this.gui = gui;
 
		gui.player1mode = 0;
		gui.player2mode = 2;
		makeButtons();
		
	}
	
	private void makeButtons(){
		buttons = new JButton[5];
		for (int i = 0; i < 2 ; i++){
			buttons[i] = new JButton();
			buttons[i].setBounds(15, 20+45*i, 150, 40);
			this.add(buttons[i]);
			buttons[i].addActionListener(this);
		}
		
		buttons[0].setText("New Game");
		buttons[1].setText("Exit");

		
		JLabel subtitle1 = new JLabel("Player 1:");
		subtitle1.setBounds(15, 240, 100, 20);
		this.add(subtitle1);
		
		radioButtons1 = new JRadioButton[5];
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < 5 ; i++){
			radioButtons1[i] = new JRadioButton();
			radioButtons1[i].setBounds(15, 260+30*i, 150, 30);
			this.add(radioButtons1[i]);
			group.add(radioButtons1[i]);
			radioButtons1[i].addActionListener(this);
		}
		
		radioButtons1[0].setText("Human");
		radioButtons1[1].setText("Minimax");
		radioButtons1[2].setText("Alphabeta");
		radioButtons1[3].setText("Alphabeta2");
		radioButtons1[4].setText("MinimaxMax");
		radioButtons1[0].setSelected(true);
		
		JLabel subtitle2 = new JLabel("Player 2:");
		subtitle2.setBounds(15, 420, 100, 20);
		this.add(subtitle2);
		
		
		radioButtons2 = new JRadioButton[5];
		ButtonGroup group2 = new ButtonGroup();
		for (int i = 0; i < 5 ; i++){
			radioButtons2[i] = new JRadioButton();
			radioButtons2[i].setBounds(15, 440+30*i, 150, 30);
			this.add(radioButtons2[i]);
			group2.add(radioButtons2[i]);
			radioButtons2[i].addActionListener(this);
		}
		
		radioButtons2[0].setText("Human");
		radioButtons2[1].setText("Minimax");
		radioButtons2[2].setText("Alphabeta");
		radioButtons2[3].setText("Alphabeta2");
		radioButtons2[4].setText("MinimaxMax");
		radioButtons2[2].setSelected(true);
		
		
		
		String[] depthStrings = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		player1Depth = new JComboBox(depthStrings);
		player2Depth = new JComboBox(depthStrings);
		this.add(player1Depth);
		this.add(player2Depth);
		player1Depth.setBounds(15, 130, 150, 30);
		player2Depth.setBounds(15, 175, 150, 30);
		player1Depth.setSelectedIndex(3);
		player2Depth.setSelectedIndex(3);
		player1Depth.addActionListener(this);
		player2Depth.addActionListener(this);
		
	
	}
	
	
	

	public void actionPerformed(ActionEvent e) {
		
		if( e.getSource() == buttons[0]){
			HelpFunc.clearMatrix(gui.currBoard);
//			radioButtons1[0].setSelected(true);
//			radioButtons2[2].setSelected(true);
//			 
//			gui.player1mode = 0;
//			gui.player2mode = 2;
			gui.player1Time = 0;
			gui.player2Time = 0;
			gui.playersIDTurn = 1;
			gui.player1Nodes = 0;
			gui.player2Nodes = 0;


		}else if( e.getSource() == buttons[1]){
			System.exit(0);
			
		}else if(e.getSource() == radioButtons1[0]){
			gui.player1mode = 0;
		}else if(e.getSource() == radioButtons1[1]){
			gui.player1mode = 1;
		}else if(e.getSource() == radioButtons1[2]){
			gui.player1mode = 2;
		}else if(e.getSource() == radioButtons1[3]){
			gui.player1mode = 3;
		}else if(e.getSource() == radioButtons1[4]){
			gui.player1mode = 4;
		}else if(e.getSource() == radioButtons2[0]){
			gui.player2mode = 0;
		}else if(e.getSource() == radioButtons2[1]){
			gui.player2mode = 1;
		}else if(e.getSource() == radioButtons2[2]){
			gui.player2mode = 2;
		}else if(e.getSource() == radioButtons2[3]){
			gui.player2mode = 3;
		}else if(e.getSource() == radioButtons2[4]){
			gui.player2mode = 4;
		}else if(e.getSource()==player1Depth){
			JComboBox cb = (JComboBox)e.getSource();
	        String depth = (String)cb.getSelectedItem();
	        gui.player1Depth = Integer.parseInt(depth);
		}else if(e.getSource()==player2Depth){
			JComboBox cb = (JComboBox)e.getSource();
	        String depth = (String)cb.getSelectedItem();
	        gui.player2Depth = Integer.parseInt(depth);
		}

	}
}
