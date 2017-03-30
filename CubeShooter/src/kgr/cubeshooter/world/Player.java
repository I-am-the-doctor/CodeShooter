/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import kgr.cubeshooter.world.Physics;
import kgr.cubeshooter.world.entities.ICollideable;
import kgr.cubeshooter.world.entities.ITickable;
import kgr.cubeshooter.world.entities.Orientation;
import kgr.cubeshooter.world.entities.TurningMoment;
import kgr.cubeshooter.world.entities.Velocity;
import kgr.cubeshooter.world.entities.boundingBoxes.BoundingBox;
import org.joml.Vector3f;

/**
 *
 * @author Benjamin
 */
public class Player implements ICollideable, ITickable, IDrawable {
	
	private Velocity moveVelocity;
	
	private Vector3f position;
	
	private Vector3f orientation;
	
	private static final float MOVE_SPEED = 1;
	
	private Input input;
	
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
	public Velocity getMoveVelocity() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void setMoveVelocity(Velocity moveVelocity) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public TurningMoment getTurningMoment() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void setTurningMoment(TurningMoment turningMoment) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void tick(Physics physics, float milliseconds) {
		float moveSpeed = 0;
		Vector3f moveDirection = new Vector3f();
		
		if (this.input.isKeyPressed()) {
			moveSpeed = MOVE_SPEED;
			moveDirection.z += 1;
		}

		this.moveVelocity.setVelocity(moveDirection, moveSpeed);
	}
	
}
