/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world.entities;

import kgr.cubeshooter.world.Physics;
import kgr.engine.GraphItem;
import kgr.engine.Input;
import kgr.engine.graph.Mesh;


/**
 *
 * @author Benjamin
 */
public class RotatingEntity extends GraphItem implements ITickable {

	public RotatingEntity(Mesh mesh) {
		super(mesh);
	}

	@Override
	public void tick(Physics physics, Input input, float milliseconds) {
		this.setRotation(this.getRotation().add(0, 1, 0));
	}
	
}
