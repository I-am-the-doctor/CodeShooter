/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.server.world;

import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.*;
import kgr.cubeshooter.INetworkable;
import kgr.cubeshooter.Network;
import kgr.cubeshooter.world.Physics;
import kgr.cubeshooter.world.entities.ICollideable;
import kgr.cubeshooter.world.entities.IDrawable;
import kgr.cubeshooter.world.entities.ITickable;
import org.w3c.dom.Document;

/**
 *
 * @author Benjamin
 */
public class World implements IDrawable, INetworkable {
	
	protected String file;
	
	protected Physics physics;
	
	protected List<IDrawable> drawableList;
	protected List<ICollideable> collideableList;
	protected List<ITickable> tickableList;
	protected List<INetworkable> networkableList; 
	
	public World(Physics physics) {
		this.physics = physics;
	}
	
	public void load(String file) {
		this.file = file;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		
	}
	
	public void unload() {
		this.file = "";
		
		this.drawableList.clear();
		this.collideableList.clear();
		this.tickableList.clear();
		this.networkableList.clear();
	}
	
	public void reload() {
		String file = this.file;
		unload();
		reload(file);
	}
	
	String getFile() {
		return this.file;
	}
	
	@Override
	public void init() {
		for (IDrawable drawable :this.drawableList) {
			drawable.init();
		}
	}
	
	@Override
	public void deinit() {
		for (IDrawable drawable :this.drawableList) {
			drawable.deinit();
		}
	}
	
	public void tick(Network network, float milliseconds) {
		for (ITickable tickable :this.tickableList) {
			tickable.tick(physics, milliseconds);
		}
		
		for (Iterator<ICollideable> it_1 = this.collideableList.iterator(); it_1.hasNext();) {
			ICollideable collidable = it_1.next();
			for (Iterator<ICollideable> it_2 = it_1; it_2.hasNext();) {
				this.physics.collide(collidable, it_2.next());
			}
		}
	}
	
	@Override
	public void writeNetworkData(Network network) {
		for (INetworkable networkable :this.networkableList) {
			networkable.writeNetworkData(network);
		}
	}
	
	@Override
	public void draw() {
		for (IDrawable drawable :this.drawableList) {
			drawable.draw();
		}
	}
	
	/**
	 * Adds the entity to the world and checks if it implements ICollideable or ITickable and adds to the needed lists.
	 * @param drawable 
	 */
	public void addDrawable(IDrawable drawable) {
		this.drawableList.add(drawable);
	}
	
	public void addCollideable(ICollideable collideable) {
		this.collideableList.add(collideable);
	}
	
	public void addTickable(ITickable tickable) {
		this.tickableList.add(tickable);
	}
}
