package com.fbla.game.graphics;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.fbla.game.cache.Cache;
import com.fbla.game.graphics.button.Button;

/**
 * A font to render with the graphics object.
 * @author Allen Kinzalow
 *
 */
public class GraphicFont {

	private Font font;
	
	/**
	 * The GraphicFont constructor
	 * @param font	The font to render.
	 */
	public GraphicFont(Font font) {
		this.font = font;
	}
	
	/**
	 * Render the font to the screen.
	 * @param graphics	The graphics object.
	 * @param string	The string.
	 * @param x	The x coordinate.
	 * @param y	The y coordinate.
	 */
	public void render(Graphics2D graphics, String string, int x, int y) {
		Font temp = graphics.getFont();
		graphics.setFont(font);
		graphics.drawString(string, x, y);
		graphics.setFont(temp);
	}
	
	/**
	 * Render the font to the screen with a specified size.
	 * @param graphics	The graphics object.
	 * @param string	The string.
	 * @param x	The x coordinate.
	 * @param y	The y coordinate.
	 * @param size	The size to set the font too.
	 */
	public void render(Graphics2D graphics, String string, int x, int y, float size) {
		float temp = font.getSize();
		font = font.deriveFont(size);
		render(graphics, string, x, y);
		font = font.deriveFont(temp);
	}
	
	/**
	 * Render a button's text in the center of the button.
	 * @param g
	 * @param button
	 * @param text
	 */
	public void renderButtonText(Graphics2D g, Button button, String text) {
		float size = 20.0f;
		int x = (button.getX() + (147 / 2)) - (getFontWidth(g, text) / 2);
		int y = button.getY() + 25;
		render(g, text, x, y, size);
	}
	
	/**
	 * Get the font.
	 * @return font	The font.
	 */
	public Font getFont() {
		return font;
	}
	
	/**
	 * Get the font width.
	 * @param g
	 * @param text
	 * @return
	 */
	public int getFontWidth(Graphics2D g, String text) {
		FontMetrics fm = g.getFontMetrics();
		return fm.stringWidth(text);
	}
	
	/**
	 * Set the font size of a GraphicFont.
	 * @param name	The font's name in the cache.
	 * @param size	The to set the font to.
	 */
	public static void setFontSize(String name, float size) {
		Cache.getInstance().getFontList().get(name).getFont().deriveFont(size);
	}
	
}
