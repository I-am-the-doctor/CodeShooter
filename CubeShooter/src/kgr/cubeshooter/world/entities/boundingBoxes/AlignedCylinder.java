/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world.entities.boundingBoxes;

import org.joml.Vector3f;

/**
 * Cylinder which is aligned on the y-axis.
 * @author Benjamin
 */
public class AlignedCylinder extends BoundingBox {
	
	private float radius;
	private float height;
	
	public AlignedCylinder(Vector3f position, float radius, float height) {
		super(position, new Vector3f(radius, radius, height).lengthSquared());
		
		this.radius = radius;
		this.height = height;
	}

	@Override
	public Type getType() {
		return Type.CYLINDER;
	}

	@Override
	public float getSquaredDistanceToSurface(Vector3f direction) {
		float slopeSquared = (direction.z * direction.z) / (direction.x * direction.x * direction.y * direction.y);
		float realRadiusSquared = this.radius * this.radius;
		float heihtSquared = this.height * this.height;
		
		if (slopeSquared <= realRadiusSquared / heihtSquared) {
			return realRadiusSquared + realRadiusSquared * slopeSquared;
		} else {
			return heihtSquared + heihtSquared / slopeSquared;
		}
	}

	@Override
	public Vector3f getNormalToSurface(Vector3f direction) {
		float slopeSquared = (direction.z * direction.z) / (direction.x * direction.x * direction.y * direction.y);
		float realRadiusSquared = this.radius * this.radius;
		float heihtSquared = this.height * this.height;
		
		if (slopeSquared <= realRadiusSquared / heihtSquared) {
			Vector3f normal = new Vector3f(direction);
			normal.y = 0;
			return normal;
		} else {
			return new Vector3f(0, 1, 0);
		}
	}
	
	public float getRadius() {
		return this.radius;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setDimension(float radius, float height) {
		this.radius = radius;
		this.height = height;
	}
}
