package com.fbla.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.fbla.game.Game;

/**
 * A keyboarding input listener(mainly for esc key)
 * @author Allen Kinzalow
 *
 */
public class KeyboardInput implements KeyListener {

	private Game game;
	
	public KeyboardInput(Game game) {
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		game.getCurrentState().handleKeyInput(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
