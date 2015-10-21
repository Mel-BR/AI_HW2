package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Eventhandler implements MouseListener{
	GUIPanel panel;

	public Eventhandler(GUIPanel panel) {
		this.panel = panel;
		// TODO Auto-generated constructor stub
	}

	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		panel.checkAllButtons(event.getX(),event.getY());

		
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
