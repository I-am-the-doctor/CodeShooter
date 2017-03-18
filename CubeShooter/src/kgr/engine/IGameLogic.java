package kgr.engine;


/**
 * Interface for the game logic with initialization
 * and the (parts of the) game loop.
 *
 * @author Val
 */
public interface IGameLogic {

    void init(Window window) throws Exception;

    void input(Window window, MouseInput mouseInput);

    void update(float delta, MouseInput mouseInput);

    void render(Window window);

    void cleanup();
}