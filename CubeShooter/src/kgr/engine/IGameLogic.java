package kgr.engine;


/**
 * Interface for the game logic with initialization
 * and the (parts of the) game loop.
 *
 * @author Val
 */
public interface IGameLogic {

    void init(Window window) throws Exception;

    void input(Window window, Input mouseInput);

    void update(float delta, Input mouseInput);

    void render(Window window);

    void cleanup();
}