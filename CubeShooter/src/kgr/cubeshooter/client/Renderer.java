package kgr.cubeshooter.client;

import kgr.engine.GraphItem;
import kgr.engine.Utils;
import kgr.engine.Window;
import kgr.engine.graph.Camera;
import kgr.engine.graph.ShaderProgram;
import kgr.engine.graph.Transformation;
import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;


public class Renderer
{

   /**
    * The Field of View in Radians.
    */
   private static final float FOV = (float) Math.toRadians(60.0f);

   private static final float Z_NEAR = 0.01f;

   private static final float Z_FAR = 1000.f;

   private final Transformation transformation;

   /**
    * A test shader program.
    */
   private ShaderProgram shaderProgram;


   /**
    *
    */
   public Renderer()
   {
      transformation = new Transformation();
   }


   /**
    * @param window
    *
    * @throws Exception
    */
   public void init(Window window) throws Exception
   {
      // Create shader.
      shaderProgram = new ShaderProgram();

      // Load the shader programs.
      shaderProgram.createVertexShader(Utils.readFile("kgr/cubeshooter/data/shaders/vertex.vs"));
      shaderProgram.createFragmentShader(Utils.readFile("kgr/cubeshooter/data/shaders/fragment.fs"));
      // Link it.
      shaderProgram.link();

      // Create uniforms for modelView and projection matrices and texture
      shaderProgram.createUniform("projectionMatrix");
      shaderProgram.createUniform("modelViewMatrix");
      shaderProgram.createUniform("texture_sampler");
   }


   /**
    * Clears the viewport with the prevously specified glClearColor.
    */
   public void clear()
   {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
   }


   /**
    *
    * @param window The window in which to render.
    * @param camera The camera.
    * @param graphItems Render a list of graphical items.
    */
   public void render(Window window, Camera camera, GraphItem[] graphItems)
   {
      clear();

      if (window.isResized()) {
         glViewport(0, 0, window.getWidth(), window.getHeight());
         window.setResized(false);
      }

      shaderProgram.bind();

      // Update projection Matrix.
      Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
      shaderProgram.setUniform("projectionMatrix", projectionMatrix);

      // Update the view Matrix.
      Matrix4f viewMatrix = transformation.getViewMatrix(camera);

      shaderProgram.setUniform("texture_sampler", 0);
      // Render each graphItem
      for (GraphItem graphItem : graphItems) {
         // Set model view matrix for this item
         Matrix4f modelViewMatrix = transformation.getModelViewMatrix(graphItem, viewMatrix);
         shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
         // Render the mes for this game item
         graphItem.getMesh().render();
      }

      shaderProgram.unbind();
   }


   /**
    *
    */
   public void cleanup()
   {
      if (shaderProgram != null) {
         shaderProgram.cleanup();
      }
   }
}
