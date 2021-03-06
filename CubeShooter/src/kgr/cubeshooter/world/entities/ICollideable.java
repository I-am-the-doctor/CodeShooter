/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world.entities;

import kgr.cubeshooter.world.entities.boundingBoxes.BoundingBox;

/**
 *
 * @author Benjamin
 */
public interface ICollideable {
	
	public abstract BoundingBox getBoundingBox();
	
	public abstract boolean hasVeclocity();
	
	default public Velocity getMoveVelocity() {
		if (hasVeclocity()) {
			throw new UnsupportedOperationException("You have to implement getMoveVelocity() if you want to have a velocity!");
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	default public void setMoveVelocity(Velocity moveVelocity) {
		if (hasVeclocity()) {
			throw new UnsupportedOperationException("You have to implement setMoveVelocity() if you want to have a velocity!");
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	default public TurningMoment getTurningMoment() {
		if (hasVeclocity()) {
			throw new UnsupportedOperationException("You have to implement getTurningMoment() if you want to have a velocity!");
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	default public void setTurningMoment(TurningMoment turningMoment) {
		if (hasVeclocity()) {
			throw new UnsupportedOperationException("You have to implement setTurningMoment() if you want to have a velocity!");
		} else {
			throw new UnsupportedOperationException();
		}
	}
}
