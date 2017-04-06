package kgr.cubeshooter.client;

import java.util.Collection;
import static kgr.cubeshooter.Constants.FOV;
import static kgr.cubeshooter.Constants.MAX_POINT_LIGHTS;
import static kgr.cubeshooter.Constants.SHADERS_ROOT;
import static kgr.cubeshooter.Constants.Z_FAR;
import static kgr.cubeshooter.Constants.Z_NEAR;
import kgr.engine.IGraphItem;
import kgr.engine.Utils;
import kgr.engine.Window;
import kgr.engine.graph.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import static org.lwjgl.opengl.GL11.*;

/**
 * A helper class for rendering all the game objects, preparing shaders etc.
 *
 * @author Val
 */
public class Renderer {

    /**
     *
     */
    private final Transformation transformation;

    /**
     * The camera instance.
     */
    private Camera camera;

    /**
     * A test shader program.
     */
    private ShaderProgram shaderProgram;


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
     * The global specular power.
     */
    private final float specularPower;

    /**
     *
     */
    public Renderer() {
        transformation = new Transformation();
        specularPower = 5f;
    }

    /**
     * @param window
     *
     * @throws Exception
     */
    public void init(Window window) throws Exception {
        // Create shader.
        shaderProgram = new ShaderProgram();

        // Load the shader program.
        String vsCode = Utils.readFile(SHADERS_ROOT + "vertex.vs");
        String fsCode = Utils.readFile(SHADERS_ROOT + "fragment.fs");

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
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

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
    }

    /**
     * Clears the viewport with the prevously specified glClearColor.
     */
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     *
     * @param window The window in which to render.
     * @param graphItems Render a list of graphical items.
     */
    public void render(Window window, Collection<IGraphItem> graphItems)
    {
        // Clear the screen.
        clear();

        // TEMPORARY: Update directional light direction, intensity and colour like a day/night cycle.
        lightAngle += 1.1f;
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 360) {
                lightAngle = -90;
            }
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColour().y = Math.max(factor, 0.9f);
            directionalLight.getColour().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColour().x = 1;
            directionalLight.getColour().y = 1;
            directionalLight.getColour().z = 1;
        }
        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        // The actual render.
        shaderProgram.bind();

        // Update projection Matrix.
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        // Update the view Matrix.
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);

        shaderProgram.setUniform("texture_sampler", 0);

        // Render all lights.
        renderLights(viewMatrix, ambientLight, pointLights, directionalLight);

        // Get a copy of the directional light object and transform its position to view coordinates.
        DirectionalLight currDirLight = new DirectionalLight(directionalLight);
        Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
        dir.mul(viewMatrix);
        currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
        shaderProgram.setUniform("directionalLight", currDirLight);

        shaderProgram.setUniform("texture_sampler", 0);

        for (IGraphItem graphItem : graphItems) {
            graphItem.render(this);
        }

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
    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }


    /**
     * @return
     */
    public ShaderProgram getShaderProgram()
    {
        return shaderProgram;
    }


    /**
     * @return
     */
    public Transformation getTransformation()
    {
        return transformation;
    }


    /**
     * @param camera
     */
    public void setCamera(Camera camera)
    {
        this.camera = camera;
    }


    /**
     * @return
     */
    public Camera getCamera()
    {
        return camera;
    }
}
