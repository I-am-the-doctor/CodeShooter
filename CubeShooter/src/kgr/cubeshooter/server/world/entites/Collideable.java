/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.server.world.entites;

import org.joml.Vector3f;

/**
 *
 * @author Benjamin
 */
public interface Collideable {
	public BoundingBox getBoundingBox();
	
	public boolean hasVeclocity();
	
	public Vector3f getMoveVelocity();
	
	public void setMoveVelocity(Vector3f moveVelocity);
	
	public TurningMoment getTurningMoment();
	
	public void setTurningMoment(TurningMoment turningMoment);
}
