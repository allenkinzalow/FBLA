package com.fbla.game.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.fbla.game.Game;

/**
 * A state of the game.
 * @author Allen Kinzalow
 *
 */
public abstract class State {

	/**
	 * The mouse coordinates.
	 */
	protected int mouseX = 0;
	protected int mouseY = 0;
	
	private Game game;
	
	public State(Game game) {
		this.game = game;
	}
	
	/**
	 * Render the graphics for the state.
	 * @param graphics	The graphics object.
	 */
	public abstract void render(Graphics2D graphics);
	
	/**
	 * Update the "state" of the state.
	 */
	public abstract void update();
	
	/**
	 * Handle the mouse input from the player/user for the active state.
	 */
	public abstract void handleMouseInput(MouseEvent e, boolean click);
	
	/**
	 * Handle the key board input from the player/user for the active state.
	 */
	public abstract void handleKeyInput(KeyEvent e);
	
	/**
	 * Get the game object.
	 * @return
	 */
	public Game getGame() {
		return game;
	}
	
}
