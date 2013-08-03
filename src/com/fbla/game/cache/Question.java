package com.fbla.game.cache;

/**
 * A single question in the trivia game.
 * @author Allen Kinzalow
 *
 */
public class Question {

	/**
	 * The question
	 */
	private String question;
	
	/**
	 * The number of the correct answer.
	 */
	private int answer;
	
	/**
	 * The available answers.
	 */
	private String[] answers;
	
	/**
	 * The constructor of the Question object.
	 * @param question	The question.
	 * @param answer	The number of the correct answer.
	 * @param answers	The available answers.
	 */
	public Question(String question, int answer, String[] answers) {
		this.question = question;
		this.answer = answer;
		this.answers = answers;
	}
	
	/**
	 * Get the question.
	 * @return	question
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * Get the number of the correct answer.
	 * @return	answer
	 */
	public int getAnswer() {
		return answer;
	}
	
	/**
	 * Get the available answers.
	 * @return	answers
	 */
	public String[] getAnswers() {
		return answers;
	}
	
}
