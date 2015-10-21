package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Eventhandler implements MouseListener{
	GUIPanel panel;

	public Eventhandler(GUIPanel panel) {
		this.panel = panel;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		panel.checkAllButtons(event.getX(),event.getY());

		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
