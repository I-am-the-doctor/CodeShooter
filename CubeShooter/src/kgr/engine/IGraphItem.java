package kgr.engine;

import kgr.cubeshooter.client.Renderer;
import kgr.engine.graph.Mesh;
import kgr.engine.graph.ShaderProgram;
import kgr.engine.graph.Transformation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface IGraphItem {

	public void init();

	public void deinit();

    public Vector3f getPosition();

    public Vector3f getScale();

    public Vector3f getRotation();

    public Mesh getMesh();

    public default void render(Renderer renderer)
    {
        ShaderProgram shaderProgram = renderer.getShaderProgram();
        Transformation transformation = renderer.getTransformation();
        // Set model view matrix for this item.
        Matrix4f viewMatrix = transformation.getViewMatrix(renderer.getCamera());
        Matrix4f modelViewMatrix = transformation.getModelViewMatrix(this, viewMatrix);
        shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
        // Render the mesh for this game item
        shaderProgram.setUniform("material", getMesh().getMaterial());
        getMesh().render();
    }
}

