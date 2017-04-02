/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world.entities.boundingBoxes;

import org.joml.Vector3f;

/**
 *
 * @author Benjamin
 */
public abstract class BoundingBox {
	public enum Type {
		SPHERE,
		CUBOID,
		CYLINDER,
		PRISMA,
		CONE,
		PYRAMID
	}

	/**
	 * The center point of the bounding box.
	 * If this bounding box is used to specify an entity, Entity.position() and BoundingBox.position() should be the same.
	 */
	protected Vector3f position;

	/**
	 * The smallest sphere around the real bounding box.
	 */
	protected float radiusSquared;

	public BoundingBox(Vector3f position, float radiusSquared) {
		this.position = position;
		this.radiusSquared = radiusSquared;
	}

	public abstract Type getType();

	public abstract float getSquaredDistanceToSurface(Vector3f direction);
	
	public abstract Vector3f getNormalToSurface(Vector3f direction);
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRadiusSquared() {
		return radiusSquared;
	}
}
