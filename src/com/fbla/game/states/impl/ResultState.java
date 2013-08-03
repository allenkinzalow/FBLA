package com.fbla.game.states.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
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

public class ResultState extends State {

	/**
	 * The player's total score, based on time.
	 */
	private int totalScore;
	
	/**
	 * The total number of questions the player answered correctly.
	 */
	private int totalCorrect;
	
	/**
	 * The total time it took the player to answer all questions.
	 */
	private int totalTime;
	
	/**
	 * The average time the player answered each question.
	 */
	private int averageTime;
	
	/**
	 * An array list of buttons to render in the current state.
	 */
	private ArrayList<OptionButton> buttonList = new ArrayList<OptionButton>();
	
	/**
	 * The transparency.
	 */
	private float transparency = 0.0f;
	
	public ResultState(final Game game, int totalScore, int totalCorrect, int totalTime, int averageTime) {
		super(game);
		this.totalScore = totalScore;
		this.totalCorrect = totalCorrect;
		this.totalTime = totalTime;
		this.averageTime = averageTime;
		buttonList.add(new OptionButton("Play Again", 245, 440) {
			public void action() {
				game.setCurrentState(new StartupState(game));
			}
		});
		buttonList.add(new OptionButton("Quit", 412, 440) {
			public void action() {
				game.quit();
			}
		});
	}

	@Override
	public void render(Graphics2D graphics) {
		Cache.getInstance().getSpriteList().get("background").renderSprite(graphics, 0, 0);
		Cache.getInstance().getSpriteList().get("topbar").renderSprite(graphics, 0, 0);
		Cache.getInstance().getSpriteList().get("main").renderSprite(graphics, 111, 152);
		
		Paint paint = graphics.getPaint();
		GradientPaint gp = new GradientPaint(5, 5, new Color(138,99,6), 220, 220,new Color(200,154,51));
		graphics.setPaint(gp);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "FBLA-Trivia", 136, 108, 136);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Results", 330, 206, 72);
		graphics.setPaint(paint);
		
		if(transparency < 1.0f && (transparency + 0.1f < 1.0f))
			transparency += 0.01f;
		else
			transparency = 1.0f;
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Score: " + totalScore, 135, 250, 26.0f);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Correct: " + totalCorrect, 135, 275, 26.0f);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Percent Correct: " + ((double)((double)totalCorrect / 25) * 100) + "%", 135, 300, 26.0f);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Average Time(Per Question): " + averageTime + " seconds", 135, 325, 26.0f);
		
		for(Button button : buttonList)
			button.render(graphics);
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
		
		for(Button button : buttonList) {
			if(mouseX > button.getX() && mouseX < button.getX() + button.getWidth()
					&& mouseY > button.getY() && mouseY < button.getY() + button.getHeight()) {
				button.action();
				return;
			}
		}
	}

	@Override
	public void handleKeyInput(KeyEvent e) {
		// Escape key to quit
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			getGame().quit();
	}

}
