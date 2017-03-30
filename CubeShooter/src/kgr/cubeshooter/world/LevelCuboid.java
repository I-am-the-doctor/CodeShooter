/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import kgr.cubeshooter.world.entities.ICollideable;
import kgr.cubeshooter.world.entities.IDrawable;
import kgr.cubeshooter.world.entities.TurningMoment;
import kgr.cubeshooter.world.entities.Velocity;
import kgr.cubeshooter.world.entities.boundingBoxes.BoundingBox;
import org.joml.Vector3f;

/**
 *
 * @author Benjamin
 */
public class LevelCuboid implements ICollideable, IDrawable {
	
	protected Vector3f position;

	public LevelCuboid(Vector3f position) {
		this.position = position;
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
	
}
