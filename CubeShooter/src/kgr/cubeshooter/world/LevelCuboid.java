/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import com.sun.media.jfxmedia.logging.Logger;
import kgr.cubeshooter.world.entities.boundingBoxes.AlignedCuboid;
import kgr.cubeshooter.world.entities.boundingBoxes.BoundingBox;
import org.joml.Vector3f;
import kgr.cubeshooter.world.entities.Collideable;
import kgr.engine.IGraphItem;
import kgr.engine.graph.Material;
import kgr.engine.graph.Mesh;
import kgr.engine.graph.ObjImporter;
import kgr.engine.graph.Texture;

/**
 *
 * @author Benjamin
 */
public class LevelCuboid extends Collideable implements IGraphItem {
	
	private final AlignedCuboid boundingBox;
	
	private static Mesh mesh;

	public LevelCuboid(Vector3f position) {		
		this.boundingBox = new AlignedCuboid(position, 1, 2, 1);
	}

	@Override
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	@Override
	public boolean hasVeclocity() {
		return false;
	}

	@Override
	public void init() {
		if (mesh == null) {
			try {
				mesh = ObjImporter.loadMesh("/kgr/cubeshooter/data/models/block.obj");
				Texture texture = new Texture("/kgr/cubeshooter/data/textures/blockUV.png");
				Material mat = new Material(texture, 0.3f);
				mesh.setMaterial(mat);
			} catch (Exception e) {
				Logger.logMsg(Logger.ERROR, "Cant't load mesh (/kgr/cubeshooter/data/models/block.obj) or texture (/kgr/cubeshooter/data/textures/blockUV.png)");
			}
		}
	}

	@Override
	public void deinit() {
		if (mesh != null) {
			mesh.cleanUp();
			mesh = null;
		}
	}

	@Override
	public Vector3f getPosition() {
		return this.boundingBox.getPosition();
	}

	@Override
	public Vector3f getScale() {
		return new Vector3f(this.boundingBox.getA(), this.boundingBox.getB(), this.boundingBox.getC());
	}

	@Override
	public Vector3f getRotation() {
		return new Vector3f();
	}

	@Override
	public Mesh getMesh() {
		return mesh;
	}
	
}
