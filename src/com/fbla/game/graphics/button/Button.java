package com.fbla.game.graphics.button;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

/**
 * A single representation of a button.
 * @author Allen Kinzalow
 *
 */
public abstract class Button {

	/**
	 * The text that is rendered onto the button.
	 */
	protected String text;
	
	/**
	 * The x coordinate of the button.
	 */
	protected int x;
	
	/**
	 * The y coordinate of the button.
	 */
	protected int y;
	
	/**
	 * Is the mouse hovering over the button?
	 */
	protected boolean hover;
	
	/**
	 * The sub-image of the buttons sprite.
	 */
	protected BufferedImage button;
	
	/**
	 * The sub-image of the buttons sprite; hover.
	 */
	protected BufferedImage buttonHover;
	
	public Button(String text, int x, int y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Render the button.
	 * @param graphics
	 */
	public abstract void render(Graphics2D graphics);
	
	/**
	 * Update the button.
	 * @param x	The x position of the map.
	 * @param y	The y position of the map.
	 */
	public void update(int x, int y) {
		if(x > this.x && x < (this.x + getWidth()) && y > this.y && y < (this.y + getHeight()))
			hover = true;
		else
			hover = false;
	}
	
	/**
	 * Execute a certain task when the button is clicked.
	 */
	public abstract void action();
	
	/**
	 * Width of the button.
	 * @return
	 */
	public abstract int getWidth();
	
	/**
	 * Height of the button
	 * @return
	 */
	public abstract int getHeight();
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
