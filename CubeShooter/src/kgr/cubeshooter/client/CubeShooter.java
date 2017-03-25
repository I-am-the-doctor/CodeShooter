package kgr.cubeshooter.client;

import java.util.HashSet;
import java.util.Set;
import kgr.engine.GraphItem;
import kgr.engine.IGameLogic;
import kgr.engine.MouseInput;
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
public class CubeShooter implements IGameLogic
{
   /**
    * Impact of mouse movement on rotation.
    */
   private static final float MOUSE_SENSITIVITY = 0.3f;

   /**
    * Camera movement vector.
    */
   private final Vector3f cameraInc;


   /**
    * The renderer instance.
    */
   private final Renderer renderer;


   /**
    * The camera instance.
    */
   private final Camera camera;

   /**
    * The color of the ambient light.
    */
   private Vector3f ambientLight;

   /**
    * A test light source.
    */
   private PointLight pointLight;

   /**
    * Temporary blockMesh to show some action.
    */
   private GraphItem suzanne;

   /**
    * Temporary list of all objects to render.
    */
   private Set<GraphItem> graphItems;

   /**
    * Velocity of the camera (temporary)
    */
   private static final float CAMERA_POS_STEP = 0.05f;


   /**
    * Creates a new instance.
    */
   public CubeShooter()
   {
      renderer = new Renderer();
      camera = new Camera(new Vector3f(-5, 10, -5), new Vector3f(20, 135, 0));
      cameraInc = new Vector3f(0, 0, 0);
   }


   /**
    * @param window
    *
    * @throws Exception
    */
   @Override
   public void init(Window window) throws Exception
   {
      graphItems = new HashSet<>();
      renderer.init(window);

      // Load the cube blockMesh and texture it.
      Mesh blockMesh = ObjImporter.loadMesh("/kgr/cubeshooter/data/models/block.obj");
      Texture texture = new Texture("/kgr/cubeshooter/data/textures/blockUV.png");
      Material mat = new Material(texture, 0.25f);
      blockMesh.setMaterial(mat);

      // Load the suzanne model.
      Mesh suzanneMesh = ObjImporter.loadMesh("/kgr/cubeshooter/data/models/suzanne.obj");
      texture = new Texture("/kgr/cubeshooter/data/textures/suzanneUV.png");
      mat = new Material(texture, 10f);
      suzanneMesh.setMaterial(mat);

      suzanne = new GraphItem(suzanneMesh);
      suzanne.setPosition(5, 10, 5);
      suzanne.setRotation(0, 150, 0);
      graphItems.add(suzanne);

      // Create a simple cube floor (for test purposes).
      for (float x = 0; x < 100; x+=2) {
         for (float z = 0; z < 100; z+=2) {
            GraphItem graphItem = new GraphItem(blockMesh);
            graphItem.setPosition(x, 0, z);
            graphItems.add(graphItem);
         }
      }

      // Set up the scene light.
      ambientLight = new Vector3f(0.5f, 0.5f, 0.45f);
      Vector3f lightColour = new Vector3f(1, 1, 1);
      Vector3f lightPosition = new Vector3f(2, 8, 2);
      float lightIntensity = 10f;
      pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
      PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
      pointLight.setAttenuation(att);
   }


   /**
    * Handle all inputs.
    *
    * @param window     Window, from which the input is coming from.
    * @param mouseInput
    */
   @Override
   public void input(Window window, MouseInput mouseInput)
   {
      cameraInc.set(0, 0, 0);
      if (window.isKeyPressed(GLFW_KEY_W)) {
         cameraInc.z = -1;
      }
      else if (window.isKeyPressed(GLFW_KEY_S)) {
         cameraInc.z = 1;
      }
      if (window.isKeyPressed(GLFW_KEY_A)) {
         cameraInc.x = -1;
      }
      else if (window.isKeyPressed(GLFW_KEY_D)) {
         cameraInc.x = 1;
      }
      if (window.isKeyPressed(GLFW_KEY_Z)) {
         cameraInc.y = -1;
      }
      else if (window.isKeyPressed(GLFW_KEY_X)) {
         cameraInc.y = 1;
      }
   }


   /**
    * Update one frame/tick.
    * @param delta      Time since last frame.
    * @param mouseInput Input data from the mouse.
    */
   @Override
   public void update(float delta, MouseInput mouseInput)
   {
      // Update camera position.
      camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

      // Update camera based on mouse.
      if (mouseInput.isRightButtonPressed()) {
         Vector2f rotVec = mouseInput.getDisplVec();
         camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
      }

      // Rotate Suzanne a bit. :-)
      suzanne.setRotation(suzanne.getRotation().x, suzanne.getRotation().y + 1, suzanne.getRotation().z);
   }


   /**
    * Tell the renderer to render.
    * @param window
    */
   @Override
   public void render(Window window)
   {
      renderer.render(window, camera, graphItems, ambientLight, pointLight);
   }


   /**
    * Clean everything up.
    */
   @Override
   public void cleanup()
   {
      renderer.cleanup();
      for (GraphItem gameItem : graphItems) {
         gameItem.getMesh().cleanUp();
      }
   }
}
