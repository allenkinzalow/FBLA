package com.fbla.game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.fbla.game.Game;

/**
 * A mouse input listener.
 * @author Allen Kinzalow
 *
 */
public class MouseInput implements MouseListener, MouseMotionListener {

	private Game game;
	
	public MouseInput(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		game.getCurrentState().handleMouseInput(e, true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		try {
			game.getCurrentState().handleMouseInput(e, false);
		} catch (Exception exc) {}
	}

}
