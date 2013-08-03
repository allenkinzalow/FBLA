package com.fbla.game.states.impl;

import java.awt.Graphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.fbla.game.Constants;
import com.fbla.game.Game;
import com.fbla.game.cache.Cache;
import com.fbla.game.cache.Question;
import com.fbla.game.graphics.button.AnswerButton;
import com.fbla.game.graphics.button.Button;
import com.fbla.game.graphics.button.OptionButton;
import com.fbla.game.states.State;

public class GameState extends State {
	
	/**
	 * The current question
	 */
	private Question currentQuestion;
	
	/**
	 * ArrayList of completed questions.
	 */
	private ArrayList<Question> questions;
	
	/**
	 * Random
	 */
	private Random random;
	
	/**
	 * The time the player stated a question, and the time that the player has to finish the question.
	 */
	private long timeStarted = 0, timeTillFinish = 0, timeAnswered = 0;
	
	/**
	 * The time per question -- used for average time per question.
	 */
	private ArrayList<Integer> timePerQuestion = new ArrayList<Integer>();
	
	/**
	 * The level the player is on.
	 */
	private int level = 1;
	
	/**
	 * The amount of hints the player has left.
	 */
	private int hints = 3;
	
	/**
	 * The player's score.
	 */
	private int totalScore;
	
	/**
	 * The player's chosen answer id
	 */
	private int chosenAnswer;
	
	/**
	 * Total answers correct
	 */
	private int totalCorrect;
	
	/**
	 * The predicted score that will be rewarded if a question is answered
	 * correctly.
	 */
	private int predictedScore;
	
	/**
	 * Is the game displaying the results of an answered question?
	 */
	private boolean showResults = false;
	
	/**
	 * Was the player correct on their last answer?
	 */
	private boolean correct = false;
	
	/**
	 * Has a hint been used on the current question?
	 */
	private boolean hintUsed = false;
	
	/**
	 * A list of answer buttons.
	 */
	private ArrayList<Button> answerButtons = new ArrayList<Button>();
	
	/**
	 * A list of option buttons.
	 */
	private ArrayList<Button> optionButtons = new ArrayList<Button>();
	
	/**
	 * Constructor for the GameState
	 * @param game	The game object.
	 */
	public GameState(final Game game) {
		super(game);
		random = new Random();
		questions = new ArrayList<Question>(Cache.getInstance().getQuestionList());
		optionButtons.add(new OptionButton("Hint", 620, 419) {
			public void action() {
				if(!showResults)
					giveHint();
			}
		});
		optionButtons.add(new OptionButton("Next Question", 620, 475) {
			public void action() {
				if(showResults)
					nextQuestion();
			}
		});
		optionButtons.add(new OptionButton("Quit", 620, 531) {
			public void action() {
				game.quit();
			}
		});
		nextQuestion();
	}
	
	/**
	 * Answer the question.
	 * @param id	The answer id of the clicked button.
	 */
	public synchronized void answerQuestion(int id) {
		timeAnswered = System.currentTimeMillis();
		correct = (id+1) == currentQuestion.getAnswer();
		if(questions.size() % 5 == 0)
			level++;
		if(id != -2) {
			chosenAnswer = id;
			for(int index = 0; index < answerButtons.size(); index++) {	// Buttons fade away
				if(((AnswerButton) answerButtons.get(index)).getAnswerId() == id)
					continue;
				((AnswerButton)answerButtons.get(index)).fade();
			}
			if(correct) {
				//Cache.getInstance().getSoundList().get("correct").play();
				totalScore += predictedScore;
				totalCorrect++;
			}/* else
				Cache.getInstance().getSoundList().get("incorrect").play();*/
			showResults = true;
		} else {
			//Cache.getInstance().getSoundList().get("incorrect").play();	// Disabled
			answerButtons.clear();
			nextQuestion();
		}
		timePerQuestion.add((int) ((timeAnswered - timeStarted) / 1000));
		hintUsed = false;
	}
	
	/**
	 * Go to the next question.
	 */
	public synchronized void nextQuestion() {
		if(questions.size() == 0) {
			getGame().setCurrentState(new ResultState(getGame(), totalScore, totalCorrect, getTotalTime(), getAverageTime()));
			return;
		}
		timeStarted = System.currentTimeMillis();
		timeTillFinish = System.currentTimeMillis() + 20000;
		int questionNumber = random.nextInt(questions.size());
		currentQuestion = questions.get(questionNumber);
		questions.remove(questionNumber);
		int x = 30;
		int y = 256;
		int answerId = 0;
		answerButtons.clear();
		for(String answer : currentQuestion.getAnswers()) {
			answerButtons.add(new AnswerButton(answer, x, y, answerId) {
				public void action() {
					if(showResults)
						return;
					answerQuestion(this.getAnswerId());
				}
			});
			answerId++;
			y += 65;
		}
		showResults = false;
	}
	
	/**
	 * Remove one of the incorrect answers for the user.
	 */
	public void giveHint() {
		if(hints <= 0)
			return;
		int answerIndex = currentQuestion.getAnswer() - 1;
		int rn = answerIndex;
		while(rn == answerIndex)
			rn = random.nextInt(answerButtons.size());
		((AnswerButton)answerButtons.get(rn)).fade();
		//answerButtons.remove(rn);
		hints--;
	}
	
	@Override
	public synchronized void render(Graphics2D graphics) {
		Cache.getInstance().getSpriteList().get("background").renderSprite(graphics, 0, 0);
		Cache.getInstance().getSpriteList().get("topbar").renderSprite(graphics, 0, 0);
		Cache.getInstance().getSpriteList().get("frame").renderSprite(graphics, 22, 152);
		
		Cache.getInstance().getSpriteList().get("timerframe").renderSprite(graphics, 164, 16);
		Cache.getInstance().getSpriteList().get("timerback").renderSprite(graphics, 174, 85);
		
		// Render the time left progress bar.
		int width = timeTillFinish == 0 ? 594 : (int) ((double)((double)(timeTillFinish - (showResults ? timeAnswered : System.currentTimeMillis())) / 20000) * (double)590);
		width = width <= 0 ? 594 : width;
		BufferedImage bf = Cache.getInstance().getSpriteList().get("timerfull").getImage().getSubimage(0, 0, width, 30);
		graphics.drawImage(bf, 174, 85, null);
		
		// Render the time left and predicted score text.
		int timeLeft = (int)((timeTillFinish - (showResults ? timeAnswered : System.currentTimeMillis())) / 1000);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Time Left: " + timeLeft + " seconds", 185, 70, 36.0f);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Predicted Score: " + predictedScore, 495, 70, 36.0f);
		
		Cache.getInstance().getFontList().get("forgotten").render(graphics, currentQuestion.getQuestion(), 30, 201, currentQuestion.getQuestion().length() > 60 ? 20.0f : 22.0f);
		
		// Player Info
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Score: " + totalScore, 623, 180, 26.0f);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Correct: " + totalCorrect, 623, 205, 26.0f);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Level: " + level, 623, 225, 26.0f);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "AVG Time: " + getAverageTime() + " sec.", 623, 250, 26.0f);
		Cache.getInstance().getFontList().get("forgotten").render(graphics, "Hints: " + hints, 623, 275, 26.0f);
		
		// Render the option buttons
		for(Button button : optionButtons)
			button.render(graphics);
		
		// Render the answer buttons with fading
		for(int index = 0; index < answerButtons.size(); index++) {
			if(showResults && ((AnswerButton) answerButtons.get(index)).getAnswerId() == chosenAnswer)
				((AnswerButton)answerButtons.get(index)).render(graphics, correct);
			else
				answerButtons.get(index).render(graphics);	
		}
		
		if(Constants.DEBUG) {
			graphics.drawString("Mouse X: " + mouseX, 5, 515);
			graphics.drawString("Mouse Y: " + mouseY, 5, 530);
		}
	}

	@Override
	public void update() {
		if(!showResults) {
			predictedScore = (int)(((timeTillFinish - System.currentTimeMillis()) / 1000.00) * 100);
			if(System.currentTimeMillis() > timeTillFinish)
				answerQuestion(-2);
		}
		
		for(int index = 0; index < answerButtons.size(); index++) {
			AnswerButton ab = ((AnswerButton)answerButtons.get(index));
			if(ab.getTransparency() <= 0.1f)
				answerButtons.remove(index);
		}
		
		//	Update the mouse position for all buttons(for hovering).
		for(Button button : answerButtons)
			button.update(mouseX, mouseY);
		for(Button button : optionButtons)
			button.update(mouseX, mouseY);
	}

	@Override
	public void handleMouseInput(MouseEvent e, boolean click) {
		if(!click) {
			mouseX = e.getX();
			mouseY = e.getY();
			return;
		}
		
		//	Provess button clicking for the answer buttons.
		for(Button button : answerButtons) {
			if(mouseX > button.getX() && mouseX < button.getX() + button.getWidth()
					&& mouseY > button.getY() && mouseY < button.getY() + button.getHeight()) {
				button.action();
				return;
			}
		}
		
		//	Process button clicking for the option buttons.
		for(Button button : optionButtons) {
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
	
	/**
	 * Retrieve the average time per question.
	 * @return	The average time.
	 */
	private int getAverageTime() {
		int totalTime = 0;
		for(int time : timePerQuestion)
			totalTime += time;
		int avgTime = (int) ((totalTime) / (timePerQuestion.size() == 0 ? 1 : timePerQuestion.size())) + 1;	// Add 1 to account for rounding...
		return avgTime;
	}
	
	/**
	 * Retrieve the total time.
	 * @return	totalTime
	 */
	private int getTotalTime() {
		int totalTime = 0;
		for(int time : timePerQuestion)
			totalTime += time;
		return totalTime;
	}

}
