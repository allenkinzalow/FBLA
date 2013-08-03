package com.fbla.game.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.fbla.game.cache.Cache;

/**
 * Representation of a single image.
 * @author Allen Kinzalow
 *
 */
public class Sprite {

	private String name;
	private BufferedImage image;
	
	public Sprite(String name, BufferedImage image) {
		this.name = name;
		this.image = image;
	}
	
	public void renderSprite(Graphics2D graphics, int x, int y) {
		graphics.drawImage(image, x, y, null);
	}
	
	/**
	 * Get the image.
	 * @return
	 */
	public BufferedImage getImage() {
		return image;
	}
	
}
