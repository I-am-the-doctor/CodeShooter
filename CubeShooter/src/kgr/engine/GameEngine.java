package kgr.engine;


/**
 * The class which calls the game loop etc.
 *
 * @author Val
 */
public class GameEngine implements Runnable {

	public static final int TARGET_FPS = 60;

	public static final int TARGET_UPS = 30;

	private final Window window;

	private final Thread gameLoopThread;

	private final Timer timer;

	private final IGameLogic gameLogic;

	private final MouseInput mouseInput;


	/**
	 * Creates a new instance of the game engine which runs a new thread for the loop.
	 *
	 * @param windowTitle
	 * @param width
	 * @param height
	 * @param vSync
	 * @param gameLogic
	 *
	 * @throws Exception
	 */
	public GameEngine(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic) throws Exception {
		gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
		window = new Window(windowTitle, width, height, vSync);
		mouseInput = new MouseInput();
		this.gameLogic = gameLogic;
		timer = new Timer();
	}


	/**
	 * Starts the game (initializing the game loop, logic etc).
	 */
	public void start() {
		String osName = System.getProperty("os.name");
		if (osName.contains("Mac")) {
			// On Mac it will throw an exception if we run the thread with start().
			gameLoopThread.run();
		} else {
			gameLoopThread.start();
		}
	}


	/**
	 * Fires up everything, first initialization, then the game loop.
	 */
	@Override
	public void run() {
		try {
			init();
			gameLoop();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			cleanup();
		}
	}


	/**
	 * Initialize everything.
	 *
	 * @throws Exception If something bad happens :-)
	 */
	protected void init() throws Exception {
		window.init();
		timer.init();
		mouseInput.init(window);
		gameLogic.init(window);
	}


	/**
    * This is the main game loop:
    * Handle input,
    * process logic,
    * render.
	 */
	protected void gameLoop() {
		float elapsedTime;

		boolean running = true;
		while (running & !window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();

			input();

			update(elapsedTime);

			render();

			if (!window.isVSync()) {
				// We only have to wait if VSync isn't enabled.
				sync();
			}
		}
	}


	/**
	 * Invoke cleanup methods.
	 */
	protected void cleanup() {
		gameLogic.cleanup();
	}


	/**
	 * Sleep until we can continue to the next frame.
	 */
	private void sync() {
		final float loopSlot = 1f / TARGET_FPS;
		final double endTime = timer.getLastLoopTime() + loopSlot;
				
		try {
			Thread.sleep((long) (endTime - timer.getTime()));
		} catch (InterruptedException ex) {
			// Should'nt happen!
		}
	}

	/**
	 * Invoke input methods.
	 */
	protected void input() {
		mouseInput.input(window);
		gameLogic.input(window, mouseInput);
	}


	/**
	 * Invoke update methods.
	 *
	 * @param delta Elapsed time.
	 */
	protected void update(float delta) {
		gameLogic.update(delta, mouseInput);
	}


	/**
	 * Invoke render methods.
	 */
	protected void render() {
		gameLogic.render(window);
		window.update();
	}
}
