package com.fbla.game.cache;

import java.awt.Font;

import java.awt.image.BufferedImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.fbla.game.graphics.GraphicFont;
import com.fbla.game.graphics.Sprite;
import com.fbla.game.sound.Sound;

/**
 * A class to load external resources.
 * @author Allen Kinzalow
 *
 */
public class Cache {

	/**
	 * The sprite resources directory.
	 */
	private static final String SPRITE_DIR = "./data/cache/images";
	
	/**
	 * The font resources directory.
	 */
	private static final String FONT_DIR = "./data/cache/fonts/";
	
	/**
	 * The tab delimited questions directory.
	 */
	private static final String QUESTION_DIR = "./data/cache/questions/QUESTIONS.txt";
	
	/**
	 * The sound resources directory.
	 */
	private static final String SOUND_DIR = "./data/cache/sound/";
	
	/**
	 * The default font size.
	 */
	private static final float DEFAULT_FONT_SIZE = 18.0f;
	
	/**
	 * The instance of the cache object.
	 */
	private static final Cache INSTANCE = new Cache();
	
	/**
	 * An hashmap of sprites(images).
	 */
	private HashMap<String, Sprite> spriteList = new HashMap<String, Sprite>();
	
	/**
	 * An hashmap of fonts.
	 */
	private HashMap<String, GraphicFont> fontList = new HashMap<String, GraphicFont>();
	
	/**
	 * And hashmap of sounds.
	 */
	private HashMap<String, Sound> soundList = new HashMap<String, Sound>();
	
	/**
	 * A list of the questions.
	 */
	private ArrayList<Question>	questionList = new ArrayList<Question>();
	
	/**
	 * Load the resources!
	 */
	public void loadCache() {
		System.out.println("Loading the resources...");
		spriteList = loadSprites();
		fontList = loadFonts();
		//soundList = loadSounds();
		questionList = loadQuestions();
	}
	
	/**
	 * Load the sprites from the data/cache/images directory.
	 * @return cachedSprites	The hashmap of the loaded images with the file name as a key.
	 */
	public HashMap<String, Sprite> loadSprites() {
		HashMap<String, Sprite> cachedSprites = new HashMap<String, Sprite>();
		try {
			File dir = new File(SPRITE_DIR);
			for(File file : dir.listFiles()) {
				String name = file.getName().substring(0, file.getName().indexOf('.'));
				BufferedImage image = ImageIO.read(file);
				cachedSprites.put(name, new Sprite(name, image));
			}
		} catch (Exception e) {
			System.out.println("Error loading images!");
			//e.printStackTrace();
		}
		return cachedSprites;
	}
	
	/**
	 * Load fonts from the data/cache/fonts directory.
	 * @return cachedFonts	The hashmap of the loaded fonts with the file name as the key.
	 */
	public HashMap<String, GraphicFont> loadFonts() {
		HashMap<String, GraphicFont> cachedFonts = new HashMap<String, GraphicFont>();
		try {
			File dir = new File(FONT_DIR);
			for(File file : dir.listFiles()) {
				String name = file.getName().substring(0, file.getName().indexOf('.'));
				Font loadedFont = Font.createFont(Font.TRUETYPE_FONT, file);
				loadedFont = loadedFont.deriveFont(DEFAULT_FONT_SIZE);
				cachedFonts.put(name, new GraphicFont(loadedFont));
			}
		} catch (Exception e) {
			System.out.println("Error loading fonts!");
			e.printStackTrace();
		}
		return cachedFonts;
	}
	
	/**
	 * Retrieve a hash map of sounds from the sound resource directory.
	 * @return	cachedSounds	The hash map of sounds with the name as the key.
	 */
	public HashMap<String, Sound> loadSounds() {
		HashMap<String, Sound> cachedSounds = new HashMap<String, Sound>();
		try {
			File dir = new File(SOUND_DIR);
			for(File file : dir.listFiles()) {
				String name = file.getName().substring(0, file.getName().indexOf('.'));
				Sound sound = new Sound(SOUND_DIR + name + ".wav");
				cachedSounds.put(name, sound);
			}
		} catch (Exception e) {
			System.out.println("Error loading sounds!");
			e.printStackTrace();
		}
		return cachedSounds;
	}
	
	/**
	 * Retrieve the list of questions from the tab delimited file.
	 * @return cachedQuestions	Array list of questions.
	 */
	public ArrayList<Question> loadQuestions() {
		ArrayList<Question>	cachedQuestions = new ArrayList<Question>();
		try {
			Scanner in = new Scanner(new File(QUESTION_DIR));
			while(in.hasNext()) {
				String[] line = in.nextLine().split("\t");
				if(line[0].startsWith("//"))
					continue;
				String questions = line[0];
				int answer = Integer.valueOf(line[1]);
				String[] answers = new String[5];
				for(int index = 0; index < answers.length; index++) {
					answers[index] = line[index+2];
				}
				cachedQuestions.add(new Question(questions, answer, answers));
			}
			Question test = cachedQuestions.get(0);
		} catch (Exception e) {
			System.out.println("Error loading questions...");
			e.printStackTrace();
		}
		return cachedQuestions;
	}
	
	/**
	 * Get the cache instance.
	 * @return	INSTANCE
	 */
	public static Cache getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Get the fibt list that was loaded from the cache.
	 * @return fontList	The hashmap of loaded fonts with the file name as the a key.
	 */
	public HashMap<String, GraphicFont> getFontList() {
		return fontList;
	}
	
	/**
	 * Get the sprite list that was loaded from the cache.
	 * @return spriteList	The hashmap of loaded images with the file name as the key.
	 */
	public HashMap<String, Sprite> getSpriteList() {
		return spriteList;
	}
	
	/**
	 * Get the sound list that was loaded from the cache.
	 * @return soundList	The hashmap of loaded sounds with the file name as the key.
	 */
	public HashMap<String, Sound> getSoundList() {
		return soundList;
	}
	
	/**
	 * Get the list of questions.
	 * @return
	 */
	public ArrayList<Question> getQuestionList() {
		return questionList;
	}
	
}
