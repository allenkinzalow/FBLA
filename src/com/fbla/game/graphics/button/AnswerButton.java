package com.fbla.game.graphics.button;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import com.fbla.game.Constants;
import com.fbla.game.cache.Cache;

/**
 * A button designated to be used for answers.
 * @author Allen Kinzalow
 *
 */
public class AnswerButton extends Button {

	private int answerId;
	
	private boolean hidden;
	
	private float transparency = 1.0f;
	
	private boolean fade = false;
	
	public AnswerButton(String text, int x, int y, int answerId) {
		super(text, x, y);
		this.answerId = answerId;
		button = Cache.getInstance().getSpriteList().get("answerbuttons").getImage().getSubimage(0, 0, 560, 49);
		buttonHover = Cache.getInstance().getSpriteList().get("answerbuttons").getImage().getSubimage(1, 59, 560, 49);
	}

	/**
	 * Render the button with correct/incorrect image.
	 * @param graphics
	 * @param correct
	 */
	public void render(Graphics2D graphics, boolean correct) {
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));	// Assure that the answer chosen does not fade..
		if(correct) {
			button = Cache.getInstance().getSpriteList().get("rightbuttons").getImage().getSubimage(0, 0, 560, 49);
			buttonHover = Cache.getInstance().getSpriteList().get("rightbuttons").getImage().getSubimage(1, 59, 560, 49);
		} else {
			button = Cache.getInstance().getSpriteList().get("wrongbuttons").getImage().getSubimage(0, 0, 560, 49);
			buttonHover = Cache.getInstance().getSpriteList().get("wrongbuttons").getImage().getSubimage(1, 59, 560, 49);
		}
		graphics.drawImage(hover ? buttonHover : button, x, y, null);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, text, x + 15, y + 33, 28.0f);
	}
	
	@Override
	public void render(Graphics2D graphics) {
		if(hidden)
			return;
		if(fade && (transparency - 0.1f) > 0.00f)
			transparency -= 0.1f;
		if(Constants.DEBUG)
			System.out.println(transparency);
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		graphics.drawImage(hover ? buttonHover : button, x, y, null);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, text, x + 15, y + 33, 28.0f);
	}

	@Override
	public void action() {
	}

	@Override
	public int getWidth() {
		return 560;
	}

	@Override
	public int getHeight() {
		return 49;
	}
	
	/**
	 * Get the answer id of the button.
	 * @return
	 */
	public int getAnswerId() {
		return answerId;
	}
	
	/**
	 * Hide the button.
	 */
	public void hide() {
		hidden = true;
	}
	
	/**
	 * Set the opacity of the image.
	 * @param transparency The float value.
	 */
	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}
	
	/**
	 * Get the button's transparency
	 * @return
	 */
	public float getTransparency() {
		return transparency;
	}
	
	/**
	 * Cause the button to fade away.
	 */
	public void fade() {
		this.fade = true;
	}

}
