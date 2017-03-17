/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.entities;

import org.joml.Vector3f;

/**
 * Specifies a physical turning moment.
 * 
 * @author Benjamin
 */
public final class TurningMoment {
	
	/**
	 * Already specifies all properties needed:
	 * - the direction specifies the rotation axis and
	 * - the length specifies the rotation speed in angle per second.
	 * But the length needs a square root to compute, so we cache the length
	 */
	private Vector3f turningMoment;
	
	/**
	 * The cached length of turningMoment.
	 */
	private float rotationSpeed;
	
	/**
	 * The prefered constructor of TurningMoment.
	 * @param turningMoment The actual tuning moment. If roationSpeed is not zero, the length of it will be ignored.
	 * @param rotationSpeed Should be the value of the turning moment, except the turning moment is not needed. In this case set it to zero.
	 */
	public TurningMoment(Vector3f turningMoment, float rotationSpeed) {
		this.turningMoment = turningMoment;
		this.rotationSpeed = rotationSpeed;
	}
	
	/**
	 * Should be avoided whenever the length (rotation speed) of the turning moment is known, or the angle speed is not needed.
	 * @param turningMoment The actual tuning moment.
	 */
	public TurningMoment(Vector3f turningMoment) {
		this.turningMoment = turningMoment;
		this.rotationSpeed = turningMoment.length();
	}
	
	public Vector3f getRotationAxis() {
		return this.turningMoment;
	}
	
	public float getRotationSpeed() {
		if (rotationSpeed != 0) {
			return this.rotationSpeed;
		} else {
			this.rotationSpeed = this.turningMoment.length();
			return this.rotationSpeed;
		}
	}
	
	public void setRotationAxis(Vector3f rotationAxis) {
		this.turningMoment = rotationAxis;
	}
	
	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
	
	public void setTurningMoment(Vector3f turningMoment, float rotationSpeed) {
		this.turningMoment = turningMoment;
		this.rotationSpeed = rotationSpeed;
	}
	
	/**
	 * Avoid whenever possible, because it must compute a square root.
	 * Instead use one of these:
	 * - void setRotationAxis(Vector3f rotationAxis)
	 * - void setRotationSpeed(float rotationSpeed)
	 * - void setTurningMoment(Vector3f turningMoment, float rotationSpeed)
	 * @param turningMoment The new turning moment.
	 */
	public void setTurningMoment(Vector3f turningMoment) {
		this.turningMoment = turningMoment;
		this.rotationSpeed = turningMoment.length();
	}
}
