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
public final class Orientation {
	/**
	 * The rotationVector described as the normal or front vector of the entity.
	 * For example:
	 * -Player: view vector
	 * -Cuboid: normal vector (the y-axis if the co#uboid is aligned to the grid)
	 * -cylinder: cylinder axis (axis between the the two ground planes)
	 */
	private Vector3f rotationVector;
	
	/**
	 * The rotation around the rotation-vector.
	 */
	private float rotationAngle;
	
	public Orientation() {
		this.rotationVector = new Vector3f(0, 1, 0);
		this.rotationAngle = 0;
	}
	
	public Orientation(Vector3f rotationVector, float rotationAngle) {
		this.rotationVector = rotationVector;
		this.rotationAngle = rotationAngle;
	}
	
	public Vector3f getRotationVector() {
		return this.rotationVector;
	}
	
	public void setRotationVector(Vector3f rotationVector) {
		this.rotationVector = rotationVector;
	}
	
	public float getRotationAngle() {
		return this.rotationAngle;
	}

	public void setRotationAngle(float rotationAngle) {
		this.rotationAngle = rotationAngle;
	}
	
	public void rotateVector(Vector3f rotationVector) {
		this.rotationVector.add(rotationVector);
	}
	
	public void rotateAngle(float angle) {
		this.rotationAngle += angle;
	}

	public void rotate(Vector3f rotationVector, float rotationAngle) {
		rotationVector.add(rotationVector);
		rotationAngle += rotationAngle;
	}
}
