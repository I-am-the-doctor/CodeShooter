package kgr.engine;

import kgr.engine.graph.Mesh;
import org.joml.Vector3f;

public interface IGraphItem {
	
	public void init();
	
	public void deinit();

    public Vector3f getPosition();

    public Vector3f getScale();

    public Vector3f getRotation();

    public Mesh getMesh();
}

