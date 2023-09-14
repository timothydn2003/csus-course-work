package Input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Data.Click;

public class Mouse implements MouseListener{
	
	private boolean isReady;
	private Click lastClick;
	
	public Mouse() {
		isReady = false;
		lastClick = null;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		lastClick = new Click(e.getX(), e.getY(), e.getButton());
		isReady = true;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public Click pollClick() {
		if(!isReady) return null;
		isReady = false;
		return lastClick;
	}
	
	public boolean isReady() {
		return isReady;
	}
	public static Point getMouseCoords() {
		return MouseInfo.getPointerInfo().getLocation();
	}
	
}