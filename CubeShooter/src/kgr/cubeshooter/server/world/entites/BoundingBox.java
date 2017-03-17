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
public abstract class BoundingBox {
	public enum Type {
		SPHERE,
		CUBE,
		CYLINDER,
		PRISMA,
		CONE,
		PYRAMID
	}
	
	/**
	 * The center point of the bounding box.
	 * If this bounding box is used to specify an entity, Entity.position() and BoundingBox.position() should be the same. 
	 */
	Vector3f position;
	
	/**
	 * The smallest sphere around the real bounding box.
	 * @return The maximal radius of the bounding box.
	 */
	float radius;
	
	public BoundingBox(Vector3f position, float radius) {
		this.position = position;
		this.radius = radius;
	}

	public boolean collide(Collideable collider) {
		if (position.distance(collider.getBoundingBox().getPosition()) < radius + collider.getBoundingBox().radius) {
			
			// TODO
			return false;
		}
		
		return false;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public float getRadius() {
		return this.radius;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
}
