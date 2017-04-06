/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import kgr.cubeshooter.world.entities.ICollideable;
import kgr.cubeshooter.world.entities.ITickable;
import kgr.cubeshooter.world.entities.Orientation;
import kgr.cubeshooter.world.entities.TurningMoment;
import kgr.cubeshooter.world.entities.Velocity;
import kgr.cubeshooter.world.entities.boundingBoxes.AlignedCylinder;
import kgr.cubeshooter.world.entities.boundingBoxes.BoundingBox;
import kgr.engine.GraphItem;
import kgr.engine.IGraphItem;
import kgr.engine.Input;
import kgr.engine.graph.Material;
import kgr.engine.graph.Mesh;
import kgr.engine.graph.ObjImporter;
import kgr.engine.graph.Texture;
import org.joml.Vector3f;

/**
 *
 * @author Benjamin
 */
public class Player implements ICollideable, ITickable, IGraphItem {

	protected final Orientation orientation;

	protected Velocity moveVelocity;

	protected final TurningMoment turningMoment;

	protected final AlignedCylinder boundingBox;

	protected static Mesh mesh;

	protected static int instances;

	protected static final float MOVE_SPEED = 0.25f;

	public Player(Vector3f position, float angle) {
		orientation = new Orientation(new Vector3f(0, 1, 0), angle);
		moveVelocity = new Velocity(new Vector3f(), 0.0f);
		turningMoment = new TurningMoment(new Vector3f(), 0);
		boundingBox = new AlignedCylinder(position, 1, 2);
	}

	@Override
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	@Override
	public boolean hasVeclocity() {
		return true;
	}

	@Override
	public Velocity getMoveVelocity() {
		return moveVelocity;
	}

	@Override
	public void setMoveVelocity(Velocity moveVelocity) {
		this.moveVelocity = moveVelocity;
	}

	@Override
	public TurningMoment getTurningMoment() {
		return turningMoment;
	}

	@Override
	public void setTurningMoment(TurningMoment turningMoment) {
	}

	@Override
	public void tick(Physics physics, Input input, float milliseconds) {
	}

	@Override
	public void init() {
		if (mesh == null) {
			try {
				mesh = ObjImporter.loadMesh("/kgr/cubeshooter/data/models/player.obj");
				Texture texture = new Texture("/kgr/cubeshooter/data/textures/playerUV.png");
				Material mat = new Material(texture, 0.3f);
				mesh.setMaterial(mat);
			} catch (Exception e) {
				System.err.println("Cant't load mesh (/kgr/cubeshooter/data/models/player.obj) or texture (/kgr/cubeshooter/data/textures/playerUV.png)");
			}

			instances = 0;
		}

		instances++;
	}

	@Override
	public void deinit() {
		if (instances == 1) {
			mesh.cleanUp();
			mesh = null;
		}

		instances--;
	}

	@Override
	public Vector3f getPosition() {
		return boundingBox.getPosition();
	}

	@Override
	public Vector3f getScale() {
		return new Vector3f(boundingBox.getRadius(), boundingBox.getHeight(), boundingBox.getRadius());
	}

	@Override
	public Vector3f getRotation() {
		return new Vector3f(0, orientation.getRotationAngle(), 0);
	}

	public void setRotationAngle(float angle) {
		orientation.setRotationAngle(angle);
	}

	@Override
	public Mesh getMesh() {
		return mesh;
	}

}
