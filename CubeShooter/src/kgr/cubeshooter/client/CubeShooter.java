package kgr.cubeshooter.client;

import kgr.engine.IGameLogic;
import kgr.engine.Input;
import kgr.engine.Window;
import kgr.engine.graph.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static kgr.cubeshooter.Constants.MAX_POINT_LIGHTS;
import kgr.cubeshooter.Network;
import kgr.cubeshooter.world.World;
import kgr.cubeshooter.world.Physics;
import kgr.cubeshooter.world.entities.RotatingEntity;
import static org.lwjgl.glfw.GLFW.*;


/**
 * The class with the game loop in it. Updating, rendering etc…
 *
 * @author Val
 */
public class CubeShooter implements IGameLogic
{
    /**
     * The renderer instance.
     */
    private final Renderer renderer;

    /**
     * The camera instance.
     */
    private Camera camera;

    /**
     * The color of the ambient light.
     */
    private Vector3f ambientLight;

    /**
     * A list of all point light sources.
     */
    private PointLight[] pointLights;

    /**
     * A test directional lightVisible source.
     */
    private DirectionalLight directionalLight;
    private float lightAngle;

	
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
    public CubeShooter()
    {
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
    public void init(Window window) throws Exception
    {
        renderer.init(window);
		
		world.load("/kgr/cubeshooter/data/world.xml");
		this.player = new ClientPlayer(new Vector3f(0, 1, 0), 135);
		world.addEntity(player);
		
		camera = new OrbitingCamera(player, 0, 5);
		world.addEntity(camera);

        // Load the suzanne model.
        Mesh suzanneMesh = ObjImporter.loadMesh("/kgr/cubeshooter/data/models/suzanne.obj");
        Texture texture = new Texture("/kgr/cubeshooter/data/textures/suzanneUV.png");
        Material mat = new Material(texture, 5f);
        suzanneMesh.setMaterial(mat);

        RotatingEntity suzanne = new RotatingEntity(suzanneMesh);
        suzanne.setPosition(5, 10, 5);
        suzanne.setRotation(0, 150, 0);
        world.addEntity(suzanne);

        // Set up the ambient light and a few point lights.
        ambientLight = new Vector3f(0.5f, 0.5f, 0.45f);
        Vector3f lightColour = new Vector3f(0.7f, 0.5f, 0.5f);
        Vector3f lightPosition = new Vector3f(2, 6, 2);
        pointLights = new PointLight[MAX_POINT_LIGHTS];
        for (int i = 0; i < MAX_POINT_LIGHTS - 5; i++) {
            pointLights[i] = new PointLight(lightColour, lightPosition, 10f);
            PointLight.Attenuation att = new PointLight.Attenuation(0f, 0f, 1f);
            pointLights[i].setAttenuation(att);
            lightPosition = new Vector3f(4f * i, 6, 4f * i);
            lightColour = new Vector3f((float) (0.7 + i / 10), 0.5f, 0.5f);
        }

        // Initialize the directional (sun) light.
        lightColour = new Vector3f(0.5f, 0.4f, 0.4f);
        directionalLight = new DirectionalLight(lightColour, new Vector3f(0, 0, 0), 1.5f);
		
		// Initialize IGraphItems of the world
		world.init();
    }


    /**
     * Handle all inputs.
     *
     * @param window     Window, from which the input is coming from.
     * @param input
     */
    @Override
    public void input(Window window, Input input)
    {
    }


    /**
     * Update one frame/tick.
     *
     * @param delta      Time since last frame.
     * @param input		Input data from the mouse and keyboard.
     */
    @Override
    public void update(float delta, Input input)
    {
		world.tick(input, delta);

        // Update directional lightVisible direction, intensity and colour like a day/night cycle.
        lightAngle += 1.1f;
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 360) {
                lightAngle = -90;
            }
        }
        else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColour().y = Math.max(factor, 0.9f);
            directionalLight.getColour().z = Math.max(factor, 0.5f);
        }
        else {
            directionalLight.setIntensity(1);
            directionalLight.getColour().x = 1;
            directionalLight.getColour().y = 1;
            directionalLight.getColour().z = 1;
        }
        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);
    }


    /**
     * Tell the renderer to render.
     *
     * @param window
     */
    @Override
    public void render(Window window)
    {
        renderer.render(window, camera, world.getGraphItems(), ambientLight, pointLights, directionalLight);
    }


    /**
     * Clean everything up.
     */
    @Override
    public void cleanup()
    {
		world.deinit();
        renderer.cleanup();
    }
}
