/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world.entities;

import org.joml.Vector3f;

/**
 *
 * @author Benjamin
 */
public final class Velocity {
	private Vector3f direction;
	
	private float speed;
	
	public Velocity(Vector3f direction, float speed) {
		this.direction = direction;
		this.speed = speed;
	}
	
	public Velocity(Vector3f velocity) {
		this.direction = velocity;
		this.speed = velocity.length();
	}
	
	public Vector3f getDirection() {
		return this.direction;
	}
	
	public float getSpeed() {
		if (this.speed != 0) {
			return this.speed;
		} else {
			this.speed = this.direction.length();
			return this.speed;
		}
	}
	
	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setVelocity(Vector3f direction, float speed) {
		this.direction = direction;
		this.speed = speed;
	}
	
	public void setVelocity(Vector3f velocity) {
		this.direction = velocity;
		this.speed = velocity.length();
	}
}
