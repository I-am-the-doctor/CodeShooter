package kgr.engine;

import kgr.engine.graph.Mesh;
import org.joml.Vector3f;

public class GraphItem implements IGraphItem {

    private final Mesh mesh;

    private final Vector3f position;

    private final Vector3f scale;

    private final Vector3f rotation;

    public GraphItem(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        scale = new Vector3f(1, 1, 1);
        rotation = new Vector3f(0, 0, 0);
    }

	@Override
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }


    public void setPosition(Vector3f pos) {
       setPosition(pos.x, pos.y, pos.z);
    }

	@Override
    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
		setScale(scale.x, scale.y, scale.z);
    }
	
    public void setScale(float scaleX, float scaleY, float scaleZ) {
        scale.x = scaleX;
        scale.y = scaleY;
        scale.z = scaleZ;
    }

	@Override
    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void setRotation(Vector3f rot) {
       setRotation(rot.x, rot.y, rot.z);
    }

	@Override
    public Mesh getMesh() {
        return mesh;
    }

	@Override
	public void init() {
	}

	@Override
	public void deinit() {
		mesh.cleanUp();
	}
}