package kgr.cubeshooter.client;

import static kgr.cubeshooter.Constants.*;
import kgr.cubeshooter.Network;
import kgr.cubeshooter.world.Physics;
import kgr.cubeshooter.world.World;
import kgr.cubeshooter.world.entities.RotatingEntity;
import kgr.engine.IGameLogic;
import kgr.engine.Input;
import kgr.engine.Window;
import kgr.engine.graph.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

/**
 * The class with the game loop in it. Updating, rendering etc…
 *
 * @author Val
 */
public class CubeShooter implements IGameLogic {

    /**
     * The renderer instance.
     */
    private final Renderer renderer;

	/**
	 * The world with all objects
	 */
	private final World world;

	/**
	 * Represents the network connection.
	 * It must be initialized with a ClientNetwork object.
	 */
	private final Network network;

	private ClientPlayer player;


    /**
     * Creates a new instance.
     */
    public CubeShooter() {
        renderer = new Renderer();
		network = new ClientNetwork();
		world = new World(new Physics());
    }

    /**
     * @param window
     *
     * @throws Exception
     */
    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        world.load(DATA_ROOT + "world.xml");
		player = new ClientPlayer(new Vector3f(0, 1, 0), 135);
		world.addEntity(player);

		OrbitingCamera camera = new OrbitingCamera(player, 0, 5);
        renderer.setCamera(camera);
		world.addEntity(camera);

        // Load the suzanne model.
        Mesh suzanneMesh = ObjImporter.loadMesh(MODELS_ROOT + "suzanne.obj");
        Texture texture = new Texture(TEXTURES_ROOT + "suzanneUV.png");
        Material mat = new Material(texture, 5f);
        suzanneMesh.setMaterial(mat);

        RotatingEntity suzanne = new RotatingEntity(suzanneMesh);
        suzanne.setPosition(5, 10, 5);
        suzanne.setRotation(0, 150, 0);
        world.addEntity(suzanne);

        // Initialize IGraphItems of the world
        world.init();
    }

    /**
     * Handle all inputs.
     *
     * @param window Window, from which the input is coming from.
     * @param input
     */
    @Override
    public void input(Window window, Input input)
    {
    }

    /**
     * Update one frame/tick.
     *
     * @param delta Time since last frame.
     * @param input	Input data from the mouse and keyboard.
     */
    @Override
    public void update(float delta, Input input)
    {
		world.tick(input, delta);
    }

    /**
     * Tell the renderer to render.
     *
     * @param window
     */
    @Override
    public void render(Window window) {
        renderer.render(window, world.getGraphItems());
    }

    /**
     * Clean everything up.
     */
    @Override
    public void cleanup() {
        world.deinit();
        renderer.cleanup();
    }
}
