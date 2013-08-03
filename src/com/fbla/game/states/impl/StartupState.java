package com.fbla.game.states.impl;

import java.awt.Color;

import java.awt.AlphaComposite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.fbla.game.Game;
import com.fbla.game.cache.Cache;
import com.fbla.game.graphics.button.Button;
import com.fbla.game.graphics.button.OptionButton;
import com.fbla.game.states.State;
import com.fbla.game.states.StateConstants;

public class StartupState extends State {
	
	/**
	 * An array list of buttons to render in the current state.
	 */
	ArrayList<OptionButton> buttonList = new ArrayList<OptionButton>();
	
	private float transparency;
	
	public StartupState(final Game game) {
		super(game);
		buttonList.add(new OptionButton("Play Now", 245, 440) {
			public void action() {
				game.setCurrentState(new GameState(game));
			}
		});
		buttonList.add(new OptionButton("Quit", 412, 440) {
			public void action() {
				game.quit();
			}
		});
		transparency = 0.01f;
	}

	@Override
	public void render(Graphics2D graphics) {
		if(transparency < 1.0f && (transparency + 0.1f < 1.0f))
			transparency += 0.01f;
		else
			transparency = 1.0f;
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		// Render the background, top header, and main body background.
		Cache.getInstance().getSpriteList().get("background").renderSprite(graphics, 0, 0);
		Cache.getInstance().getSpriteList().get("topbar").renderSprite(graphics, 0, 0);
		Cache.getInstance().getSpriteList().get("main").renderSprite(graphics, 111, 152);
		
		// Here, a gradient is set to the fonts, instead of having it just plain white for the headers.
		Paint paint = graphics.getPaint();
		GradientPaint gp = new GradientPaint(5, 5, new Color(138,99,6), 220, 220,new Color(200,154,51));
		graphics.setPaint(gp);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "FBLA-Trivia", 136, 108, 136);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "How to Play", 270, 206, 72);
		graphics.setPaint(paint);
		int sX = 135;
		int sY = 230;
		for(String line : StateConstants.OPTION_TEXT) {
			Cache.getInstance().getFontList().get("forgotten").render(graphics, line, sX, sY, 20);
			sY += 20;
		}
		
		for(Button button : buttonList) {
			button.render(graphics);
		}
		
		//graphics.drawString("Mouse X: " + mouseX, 5, 515);
		//graphics.drawString("Mouse Y: " + mouseY, 5, 530);
	}

	@Override
	public void update() {
		for(Button button : buttonList)
			button.update(mouseX, mouseY);
	}

	@Override
	public void handleMouseInput(MouseEvent e, boolean click) {
		if(!click) {
			mouseX = e.getX();
			mouseY = e.getY();
			return;
		}
		for(Button button : buttonList)
			if(mouseX > button.getX() && mouseX < button.getX() + button.getWidth()
					&& mouseY > button.getY() && mouseY < button.getY() + button.getHeight())
				button.action();
	}

	@Override
	public void handleKeyInput(KeyEvent e) {
		// Escape key to quit
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			getGame().quit();
	}

}
