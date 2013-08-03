package com.fbla.game;

import java.awt.Canvas;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.fbla.game.cache.Cache;
import com.fbla.game.input.KeyboardInput;
import com.fbla.game.input.MouseInput;
import com.fbla.game.states.State;
import com.fbla.game.states.impl.StartupState;

/**
 * The main class for the game.
 * @author Allen Kinzalow 
 *
 */
public class Game extends Canvas implements Runnable {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -6016403110285502556L;

	private Graphics2D graphics;
	private State currentState;
	
	// Fps variables
	private int trueFps = 0;
	private int lastFps = 0;
	private int thisFps = 0;
	private long lastFrameUpdate = 0L;
	
	private Thread thread;
	
	/**
	 * Constructor of the game object.
	 */
	public Game() {
		
		// initialize the cache instance.
		Cache.getInstance();
		
		JFrame frame = new JFrame(); // Create a JFrame to put the canvas on
        frame.setTitle(Constants.NAME);	// Set the title of the frame
        frame.setSize((int)Constants.FRAME_DIMENSION.getWidth(), (int)Constants.FRAME_DIMENSION.getHeight()); // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation(exit)
        frame.add(this); // Add the game canvas to the frame.
        frame.setResizable(false); // Do not allow the frame to be resized

		// Show the frame
		frame.validate();
		frame.pack();
		frame.setVisible(true);
		frame.toFront();
		
		// Update the dimensions of the window to allot for the overlap prevention
		final Insets insets = frame.getInsets();
		frame.setSize((int)Constants.FRAME_DIMENSION.getWidth()+insets.left+insets.right, (int)Constants.FRAME_DIMENSION.getHeight()+insets.top+insets.bottom);
		
		// Set up double buffering
		createBufferStrategy(2);
		this.graphics = (Graphics2D) super.getGraphics();
		
		// Add the input listeners
		MouseInput mouseInput = new MouseInput(this);
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
		addKeyListener(new KeyboardInput(this));
		
		requestFocus();
		
		//	Load the resources
		Cache.getInstance().loadCache();
		
		// Set the current state to the startup/options state.
		currentState = new StartupState(this);
		
		// Request the garbage collector.
		Runtime.getRuntime().gc();
		
	}
	
	/**
	 * Startup method.
	 * @param args	Program arguments
	 */
	public static void main(String[] args) {
		if(Constants.DEBUG)
			System.out.println("Starting up...");
		Game game = new Game();
		Thread thd = new Thread(game);
		game.setThread(thd);
		game.getThread().start();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				thisFps++; // Update frames per second
				render();	// Render the graphics
				currentState.update();	// Update the current state
				delta--;
			}

			/*
			 * Update FPS values..
			 */
			if (lastFrameUpdate <= System.currentTimeMillis() - 1000) { // 1 second delay
				trueFps = (thisFps + lastFps) / 2 > 60 ? 60 : (thisFps + lastFps) / 2; 
				lastFps = thisFps;
				thisFps = 0;
				
				lastFrameUpdate = System.currentTimeMillis();
			}
		}
	}
	
	/**
	 * Render graphics to the screen.
	 */
	private void render() {
		if(getBufferStrategy() == null) {
			createBufferStrategy(2);
			return;
		}
		graphics = (Graphics2D) getBufferStrategy().getDrawGraphics();
		
		if (Constants.ANTI_ALIASING)
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Clear the screen
        graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, super.getWidth(), super.getHeight());

		// Set the default font
		graphics.setFont(Cache.getInstance().getFontList().get("forgotten").getFont());
		
		// Render the Current state
		currentState.render(graphics);

		if (Constants.ANTI_ALIASING)
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

		graphics.dispose();
		
		// Display the graphics
		getBufferStrategy().show();
        Toolkit.getDefaultToolkit().sync();
	}
	
	/**
	 * Immediately end the game.
	 */
	public void quit() {
		System.exit(0);
	}
	
	/**
	 * Get the current state.
	 * @return	currentState
	 */
	public State getCurrentState() {
		return currentState;
	}
	
	/**
	 * Set the current state of the game.
	 * @param state
	 */
	public void setCurrentState(State state) {
		currentState = state;
	}
	
	/**
	 * Set the current running thread for this game instance.
	 * @param thread
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	/**
	 * Get the current running thread for this game instance.
	 * @return
	 */
	public Thread getThread() {
		return thread;
	}
	
}
