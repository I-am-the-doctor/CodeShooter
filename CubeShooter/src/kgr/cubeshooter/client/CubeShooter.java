package kgr.cubeshooter.client;

import java.util.HashSet;
import java.util.Set;
import kgr.engine.GraphItem;
import kgr.engine.IGameLogic;
import kgr.engine.MouseInput;
import kgr.engine.Window;
import kgr.engine.graph.Camera;
import kgr.engine.graph.Mesh;
import kgr.engine.graph.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;


/**
 * The class with the game loop in it.
 * Updating, rendering etc…
 *
 * @author Val
 */
public class CubeShooter implements IGameLogic
{

   private static final float MOUSE_SENSITIVITY = 0.3f;

   /**
    * Camera movement vector.
    */
   private final Vector3f cameraInc;

   private final Renderer renderer;

   private final Camera camera;


   /**
    * Temporary list of all objects to render.
    */
   private Set<GraphItem> graphItems;

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
    *
    * @param window
    *
    * @throws Exception
    */
   @Override
   public void init(Window window) throws Exception
   {
      graphItems = new HashSet<>();
      renderer.init(window);
      // Create the Mesh.
      float[] positions = new float[] {
         // V0
         -0.5f, 0.5f, 0.5f,
         // V1
         -0.5f, -0.5f, 0.5f,
         // V2
         0.5f, -0.5f, 0.5f,
         // V3
         0.5f, 0.5f, 0.5f,
         // V4
         -0.5f, 0.5f, -0.5f,
         // V5
         0.5f, 0.5f, -0.5f,
         // V6
         -0.5f, -0.5f, -0.5f,
         // V7
         0.5f, -0.5f, -0.5f,
         // For text coords in top face
         // V8: V4 repeated
         -0.5f, 0.5f, -0.5f,
         // V9: V5 repeated
         0.5f, 0.5f, -0.5f,
         // V10: V0 repeated
         -0.5f, 0.5f, 0.5f,
         // V11: V3 repeated
         0.5f, 0.5f, 0.5f,
         // For text coords in right face
         // V12: V3 repeated
         0.5f, 0.5f, 0.5f,
         // V13: V2 repeated
         0.5f, -0.5f, 0.5f,
         // For text coords in left face
         // V14: V0 repeated
         -0.5f, 0.5f, 0.5f,
         // V15: V1 repeated
         -0.5f, -0.5f, 0.5f,
         // For text coords in bottom face
         // V16: V6 repeated
         -0.5f, -0.5f, -0.5f,
         // V17: V7 repeated
         0.5f, -0.5f, -0.5f,
         // V18: V1 repeated
         -0.5f, -0.5f, 0.5f,
         // V19: V2 repeated
         0.5f, -0.5f, 0.5f,};

      float[] textCoords = new float[] {
         0.0f, 0.0f,
         0.0f, 0.5f,
         0.5f, 0.5f,
         0.5f, 0.0f,
         0.0f, 0.0f,
         0.5f, 0.0f,
         0.0f, 0.5f,
         0.5f, 0.5f,
         // For text coords in top face
         0.0f, 0.5f,
         0.5f, 0.5f,
         0.0f, 1.0f,
         0.5f, 1.0f,
         // For text coords in right face
         0.0f, 0.0f,
         0.0f, 0.5f,
         // For text coords in left face
         0.5f, 0.0f,
         0.5f, 0.5f,
         // For text coords in bottom face
         0.5f, 0.0f,
         1.0f, 0.0f,
         0.5f, 0.5f,
         1.0f, 0.5f,};
      int[] indices = new int[] {
         // Front face
         0, 1, 3, 3, 1, 2,
         // Top Face
         8, 10, 11, 9, 8, 11,
         // Right face
         12, 13, 7, 5, 12, 7,
         // Left face
         14, 15, 6, 4, 14, 6,
         // Bottom face
         16, 18, 19, 17, 16, 19,
         // Back face
         4, 6, 7, 5, 4, 7,};

      // Now we can create a texture and the cube mesh from the data.
      Texture blockTex = new Texture("/kgr/cubeshooter/data/textures/test.png");
      Mesh mesh = new Mesh(positions, textCoords, indices, blockTex);

      // Create a simple cube floor (for test purposes).
      for (int x = 0; x < 100; x++) {
         for (int z = 0; z < 100; z++) {
            GraphItem graphItem = new GraphItem(mesh);
            graphItem.setPosition(x, 0, z);
            graphItems.add(graphItem);
         }
      }
   }


   /**
    * Handle all inputs.
    * @param window Window, from which the input is coming from.
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
    *
    * @param delta
    * @param mouseInput
    */
   @Override
   public void update(float delta, MouseInput mouseInput)
   {
      // Update camera position
      camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

      // Update camera based on mouse
      if (mouseInput.isRightButtonPressed()) {
         Vector2f rotVec = mouseInput.getDisplVec();
         camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
      }
   }


   @Override
   public void render(Window window)
   {
      renderer.render(window, camera, graphItems);
   }


   @Override
   public void cleanup()
   {
      renderer.cleanup();
      for (GraphItem gameItem : graphItems) {
         gameItem.getMesh().cleanUp();
      }
   }

}
