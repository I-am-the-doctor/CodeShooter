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
	
	public BoundingBox getBoundingBox();
	
	public boolean hasVeclocity();
	
	public Velocity getMoveVelocity();
	
	public void setMoveVelocity(Velocity moveVelocity);
	
	public TurningMoment getTurningMoment();
	
	public void setTurningMoment(TurningMoment turningMoment);
}
