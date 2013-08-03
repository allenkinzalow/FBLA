package com.fbla.game.graphics.button;

import java.awt.Graphics2D;

import com.fbla.game.cache.Cache;

/**
 * An option button.
 * @author Allen Kinzalow
 *
 */
public class OptionButton extends Button {
	
	public OptionButton(String text, int x, int y) {
		super(text,x,y);
		button = Cache.getInstance().getSpriteList().get("optionbutton").getImage().getSubimage(0, 0, 147, 43);
		buttonHover = Cache.getInstance().getSpriteList().get("optionbutton").getImage().getSubimage(0, 52, 147, 43);
	}
	
	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(hover ? buttonHover : button, x, y, null);
		Cache.getInstance().getFontList().get("forgotten").renderButtonText(graphics, this, text);
	}

	@Override
	public void action() {
	}

	@Override
	public int getWidth() {
		return 147;
	}

	@Override
	public int getHeight() {
		return 43;
	}
	
	
	
}
