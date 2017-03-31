/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import kgr.cubeshooter.world.entities.ITickable;
import kgr.cubeshooter.world.entities.Orientation;
import kgr.cubeshooter.world.entities.TurningMoment;
import kgr.cubeshooter.world.entities.Velocity;
import kgr.cubeshooter.world.entities.boundingBoxes.BoundingBox;
import kgr.engine.Input;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import kgr.engine.IGraphItem;
import kgr.engine.graph.Mesh;
import kgr.cubeshooter.world.entities.ICollideable;

/**
 *
 * @author Benjamin
 */
public class Player  implements ICollideable, ITickable, IGraphItem {
	
	private final Velocity moveVelocity;
	
	private final Vector3f position;
	
	private final Orientation orientation;
	
	private static final float MOVE_SPEED = 1;
	
	public Player(Vector3f position, Orientation orientation) {
		this.position = position;
		this.orientation = orientation;
		this.moveVelocity = new Velocity(new Vector3f(), 0.0f);
	}

	@Override
	public BoundingBox getBoundingBox() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean hasVeclocity() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void tick(Physics physics, Input input, float milliseconds) {
		float moveSpeed = 0;
		Vector3f moveDirection = new Vector3f();
		
		if (input.isKeyPressed(GLFW.GLFW_KEY_W)) {
			moveSpeed = MOVE_SPEED;
			moveDirection.z += 1;
		}

		this.moveVelocity.setVelocity(moveDirection, moveSpeed);
	}

	@Override
	public void init() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void deinit() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Vector3f getPosition() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Vector3f getScale() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Vector3f getRotation() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Mesh getMesh() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
