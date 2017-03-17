/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.server.world;

import java.util.Iterator;
import java.util.List;
import kgr.cubeshooter.server.world.entites.Entity;
import kgr.cubeshooter.server.world.entites.ICollideable;
import kgr.cubeshooter.server.world.entites.ITickable;

/**
 *
 * @author Benjamin
 */
public class World {
	String file;
	
	List<Entity> entities;
	List<ICollideable> collideables;
	List<ITickable> tickables;
	
	public void load(String file) {
		this.file = file;
	}
	
	public void unload() {
		file = "";
		
		entities.clear();
		collideables.clear();
		tickables.clear();
	}
	
	public void reload() {
		
	}
	
	String getFile() {
		return this.file;
	}
	
	public void tick(int milliseconds) {
		this.tickables.forEach((tickable) -> {
			tickable.tick(milliseconds);
		});
		
		for (Iterator<ICollideable> it_1 = this.collideables.iterator(); it_1.hasNext();) {
			ICollideable collidable = it_1.next();
			for (Iterator<ICollideable> it_2 = it_1; it_2.hasNext();) {
				collidable.collide(it_2.next());
			}
		}
		
		// TODO send data to clients
	}
	
	/**
	 * Adds the entity to the world and checks if it implements ICollideable or ITickable and adds to the needed lists.
	 * @param entity 
	 */
	public void addEntity(Entity entity) {
		this.entities.add(entity);
		
		if (entity instanceof ICollideable) {
			addCollideable((ICollideable) entity);
		}
		
		if (entity instanceof ITickable) {
			addTickable((ITickable) entity);
		}
	}
	
	public void addCollideable(ICollideable collideable) {
		this.collideables.add(collideable);
	}
	
	public void addTickable(ITickable tickable) {
		this.tickables.add(tickable);
	}
}
