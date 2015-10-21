package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
		for (int i = 0; i < 5 ; i++){
			buttons[i] = new JButton();
			buttons[i].setBounds(15, 20+45*i, 150, 40);
			this.add(buttons[i]);
			buttons[i].addActionListener(this);
		}
		
		buttons[0].setText("New Game");
		buttons[1].setText("");
		buttons[2].setText("Add tile ");
		buttons[3].setText(" ");
		buttons[4].setText("Exit");

		
		JLabel subtitle1 = new JLabel("Player 1:");
		subtitle1.setBounds(15, 270, 100, 20);
		this.add(subtitle1);
		
		radioButtons1 = new JRadioButton[3];
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < 3 ; i++){
			radioButtons1[i] = new JRadioButton();
			radioButtons1[i].setBounds(15, 300+30*i, 150, 30);
			this.add(radioButtons1[i]);
			group.add(radioButtons1[i]);
			radioButtons1[i].addActionListener(this);
		}
		
		radioButtons1[0].setText("Human");
		radioButtons1[0].setSelected(true);
		radioButtons1[1].setText("Minimax");
		radioButtons1[2].setText("Alphabeta");
		
		JLabel subtitle2 = new JLabel("Player 2:");
		subtitle2.setBounds(15, 420, 100, 20);
		this.add(subtitle2);
		
		
		radioButtons2 = new JRadioButton[3];
		ButtonGroup group2 = new ButtonGroup();
		for (int i = 0; i < 3 ; i++){
			radioButtons2[i] = new JRadioButton();
			radioButtons2[i].setBounds(15, 450+30*i, 150, 30);
			this.add(radioButtons2[i]);
			group2.add(radioButtons2[i]);
			radioButtons2[i].addActionListener(this);
		}
		
		radioButtons2[0].setText("Human");
		radioButtons2[1].setText("Minimax");
		radioButtons2[2].setText("Alphabeta");
		radioButtons2[2].setSelected(true);
		
	
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getSource() == buttons[0]){
			HelpFunc.clearMatrix(gui.currBoard);
			radioButtons1[0].setSelected(true);
			radioButtons2[2].setSelected(true);
			 
			gui.player1mode = 0;
			gui.player2mode = 2;
			gui.playersIDTurn = 1;


		}else if( e.getSource() == buttons[1]){
			
		}else if(e.getSource() == buttons[2]){
				
		}else if(e.getSource() == buttons[4]){
			System.exit(0);
		}else if(e.getSource() == radioButtons1[0]){
			gui.player1mode = 0;
		}else if(e.getSource() == radioButtons1[1]){
			gui.player1mode = 1;
		}else if(e.getSource() == radioButtons1[2]){
			gui.player1mode = 2;
		}else if(e.getSource() == radioButtons2[0]){
			gui.player2mode = 0;
		}else if(e.getSource() == radioButtons2[1]){
			gui.player2mode = 1;
		}else if(e.getSource() == radioButtons2[2]){
			gui.player2mode = 2;
		}

	}
}
