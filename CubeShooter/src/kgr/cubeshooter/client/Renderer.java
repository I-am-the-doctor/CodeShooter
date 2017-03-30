package kgr.cubeshooter.client;

import java.util.Collection;
import kgr.engine.GraphItem;
import kgr.engine.Utils;
import kgr.engine.Window;
import kgr.engine.graph.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import static kgr.cubeshooter.Constants.MAX_POINT_LIGHTS;
import kgr.cubeshooter.world.World;
import static org.lwjgl.opengl.GL11.*;


/**
 * A helper class for rendering all the game objects, preparing shaders etc.
 *
 * @author Val
 */
public class Renderer
{

   /**
    * The Field of View in Radians.
    */
   private static final float FOV = (float) Math.toRadians(60.0f);

   /**
    * Near clip distance.
    */
   private static final float Z_NEAR = 0.01f;

   /**
    * Far clip distance.
    */
   private static final float Z_FAR = 1000.f;

   private final Transformation transformation;

   /**
    * A test shader program.
    */
   private ShaderProgram shaderProgram;

   /**
    * The global specular power.
    */
   private float specularPower;


   /**
    *
    */
   public Renderer()
   {
      transformation = new Transformation();
      specularPower = 5f;
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

      // Load the shader program.
      String vsCode = Utils.readFile("/kgr/cubeshooter/data/shaders/vertex.vs");
      String fsCode = Utils.readFile("/kgr/cubeshooter/data/shaders/fragment.fs");

      shaderProgram.createVertexShader(vsCode);
      shaderProgram.createFragmentShader(fsCode);
      // Link it.
      shaderProgram.link();

      // Create uniforms for modelView and projection matrices and texture
      shaderProgram.createUniform("projectionMatrix");
      shaderProgram.createUniform("modelViewMatrix");
      shaderProgram.createUniform("texture_sampler");

      // Create uniform for material
      shaderProgram.createMaterialUniform("material");
      // Create lighting related uniforms
      shaderProgram.createUniform("specularPower");
      shaderProgram.createUniform("ambientLight");
      shaderProgram.createPointLightListUniform("pointLights", MAX_POINT_LIGHTS);
      shaderProgram.createDirectionalLightUniform("directionalLight");

      glClearColor(0.2f, 0.3f, 0.6f, 1);
      glEnable(GL11.GL_CULL_FACE);
      glCullFace(GL_BACK);
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
    * @param window       The window in which to render.
    * @param camera       The camera.
    * @param graphItems   Render a list of graphical items.
    * @param ambientLight An ambient light "source".
    * @param pointLights
    * @param dirLight     A directional light source.
    */
   public void render(Window window, Camera camera, Collection<GraphItem> graphItems,
                      Vector3f ambientLight, PointLight pointLights[], DirectionalLight dirLight,
					  World world)
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

      // Render all lights.
      renderLights(viewMatrix, ambientLight, pointLights, dirLight);

      // Get a copy of the directional light object and transform its position to view coordinates.
      DirectionalLight currDirLight = new DirectionalLight(dirLight);
      Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
      dir.mul(viewMatrix);
      currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
      shaderProgram.setUniform("directionalLight", currDirLight);

      shaderProgram.setUniform("texture_sampler", 0);

      // Render each game item.
      for (GraphItem gameItem : graphItems) {
         Mesh mesh = gameItem.getMesh();
         // Set model view matrix for this item
         Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
         shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
         // Render the mesh for this game item
         shaderProgram.setUniform("material", mesh.getMaterial());
         mesh.render();
      }
	  
	  world.draw();

      shaderProgram.unbind();
   }


   /**
    *
    * @param viewMatrix
    * @param ambientLight
    * @param pointLightList
    * @param directionalLight
    */
    private void renderLights(Matrix4f viewMatrix, Vector3f ambientLight,
            PointLight[] pointLightList, DirectionalLight directionalLight) {

        shaderProgram.setUniform("ambientLight", ambientLight);
        shaderProgram.setUniform("specularPower", specularPower);

        // Process Point Lights
        int numLights = pointLightList != null ? pointLightList.length : 0;
        for (int i = 0; i < numLights; i++) {
            // Get a copy of the point light object and transform its position to view coordinates
           if (pointLightList[i] == null) {
              continue;
           }
            PointLight currPointLight = new PointLight(pointLightList[i]);
            Vector3f lightPos = currPointLight.getPosition();
            Vector4f aux = new Vector4f(lightPos, 1);
            aux.mul(viewMatrix);
            lightPos.x = aux.x;
            lightPos.y = aux.y;
            lightPos.z = aux.z;
            shaderProgram.setUniform("pointLights", currPointLight, i);
        }

        // Get a copy of the directional light object and transform its position to view coordinates
        DirectionalLight currDirLight = new DirectionalLight(directionalLight);
        Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
        dir.mul(viewMatrix);
        currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
        shaderProgram.setUniform("directionalLight", currDirLight);
   }


   /**
    * Cleanup (shader programs etc.)
    */
   public void cleanup()
   {
      if (shaderProgram != null) {
         shaderProgram.cleanup();
      }
   }
}
