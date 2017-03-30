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
public final class Sphere extends BoundingBox {
	
	public Sphere(Vector3f position, float radius) {
		super(position, radius);
	}
	
	@Override
	public Type getType() {
		return Type.SPHERE;
	}

	@Override
	public float getSquaredDistanceToSurface(Vector3f direction) {
		return this.radiusSquared;
	}
	
	public void setRadius(float radius) {
		this.radiusSquared = radius * radius;
	}
}
