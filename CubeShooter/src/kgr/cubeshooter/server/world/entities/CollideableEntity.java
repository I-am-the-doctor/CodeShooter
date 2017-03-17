/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.server.world.entities;

import kgr.cubeshooter.entities.ICollideable;
import kgr.cubeshooter.entities.Orientation;
import kgr.cubeshooter.entities.boundingBoxes.BoundingBox;
import org.joml.Vector3f;

/**
 *
 * @author Benjamin
 */
public abstract class CollideableEntity extends Entity implements ICollideable {
	
	protected BoundingBox boundingBox;
	
	public CollideableEntity(Vector3f position, Orientation orientation) {
		super(position, orientation);
	}

	public CollideableEntity(Vector3f position) {
		super(position);
	}

	@Override
	public void collide(ICollideable collider) {
		// TODO
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
}
