/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.server.world.entities;

import kgr.cubeshooter.entities.Orientation;
import org.joml.Vector3f;


/**
 *
 * @author Benjamin
 */
public abstract class Entity {
	
	/**
	 * The center point of the entity.
	 */
	protected Vector3f position;
	
	protected Orientation orientation;
	
	public Entity(Vector3f position, Orientation orientation) {
		this.position = position;
		this.orientation = orientation;
	}
	
	public Entity(Vector3f position) {
		this.position = position;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
}
