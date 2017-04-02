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
public class AlignedCuboid extends BoundingBox {

	protected float a;
	protected float b;
	protected float c;

	/**
	 *
	 * @param position
	 * @param a The length of the cuboid.
	 * @param b The width of the cuboid.
	 * @param c The height of the cuboid.
	 */
	public AlignedCuboid(Vector3f position, float a, float b, float c) {
		super(position, new Vector3f(a, b, c).lengthSquared());

		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public Type getType() {
		return Type.CUBOID;
	}

	/**
	 * Computes the distance between the center point (position) and the interseption of  bounds and the line specified of the direction vector and the center point.
	 * @param direction The direction to wich the intercection will be computed.
	 * @return The computed distance.
	 */
	@Override
	public float getSquaredDistanceToSurface(Vector3f direction) {
		// Mirror the direction, because we need multiple times.
		Vector3f d = direction;
		if (d.x < 0)
			d.x *= -1;
		if (d.y < 0)
			d.y *= -1;
		if (d.z < 0)
			d.z *= -1;

		// At which face is the intercection?
		if (d.x > d.y && d.x > d.z) {
			// r * direction = 0.5 * vectorA + s * vectorB + t * vectorC
			return d.mul(a / (2 * d.x)).lengthSquared();
		} else if (d.y > d.x && d.y > d.z) {
			// r * direction = s * vectorA + 0.5 * vectorB + t * vectorC
			return d.mul(b / (2 * d.x)).lengthSquared();
		} else {
			// r * direction = s * vectorA + t * vectorB + 0.5 * vectorC
			return d.mul(c / (2 * d.x)).lengthSquared();
		}
	}
	
	@Override
	public Vector3f getNormalToSurface(Vector3f direction) {
		// Mirror the direction, because we need multiple times.
		Vector3f d = direction;
		if (d.x < 0)
			d.x *= -1;
		if (d.y < 0)
			d.y *= -1;
		if (d.z < 0)
			d.z *= -1;
		
		// At which face is the intercection?
		if (d.x > d.y && d.x > d.z) {
			return new Vector3f(1, 0, 0);
		} else if (d.y > d.x && d.y > d.z) {
			return new Vector3f(0, 1, 0);
		} else {
			return new Vector3f(0, 0, 1);
		}
	}

	public float getA() {
		return a;
	}

	public float getB() {
		return b;
	}

	public float getC() {
		return c;
	}

	public void setA(float a) {
		this.a = a;
	}

	public void setB(float b) {
		this.b = b;
	}
	
	public void setC(float c) {
		this.c = c;
	}

	public void setSize(float a, float b, float c) {
		this.a = a;
		this.radiusSquared = new Vector3f(a, b, c).lengthSquared();
	}
}
