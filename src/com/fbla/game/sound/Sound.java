package com.fbla.game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * A sound that can be played throughout the game.
 * @author Allen Kinzalow
 *
 */
public class Sound {

	/**
	 * Audio input stream.
	 */
	private AudioInputStream stream;
	private Clip music;
	
	/**
	 * Constructor
	 * @param fileName	The sound file's directory
	 */
	public Sound(String fileName) {
		try {
			stream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));
			music = AudioSystem.getClip();
		} catch (Exception e) {
			System.out.println("Error setting up sound...");
			System.out.println(fileName);
			e.printStackTrace();
		}
	}
	
	/**
	 * Play a sound.
	 */
	public void play() {
		try {
			music.open(stream);
			music.start();
		} catch (Exception e) {
			System.out.println("Error playing sound...");
		}
	}
	
}
