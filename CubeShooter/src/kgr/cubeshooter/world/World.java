/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.*;
import kgr.cubeshooter.INetworkable;
import kgr.cubeshooter.Network;
import kgr.cubeshooter.world.entities.ITickable;
import kgr.engine.Input;
import org.w3c.dom.Document;
import kgr.cubeshooter.world.entities.Collideable;
import kgr.engine.IGraphItem;

/**
 *
 * @author Benjamin
 */
public class World implements INetworkable {
	
	protected String file;
	
	protected Physics physics;
	
	protected Set<IGraphItem> graphItemList;
	protected Set<Collideable> collideableList;
	protected Set<ITickable> tickableList;
	protected Set<INetworkable> networkableList; 
	
	public World(Physics physics) {
		this.physics = physics;
	}
	
	public void load(String file) {
		this.graphItemList = new HashSet<>();
		this.collideableList = new HashSet<>();
		this.tickableList = new HashSet<>();
		this.networkableList = new HashSet<>();
		
		this.file = file;
		
		//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//DocumentBuilder builder = factory.newDocumentBuilder();
		//Document document = builder.parse(file);
		
	}
	
	public void unload() {
		this.file = "";
		
		this.graphItemList.clear();
		this.collideableList.clear();
		this.tickableList.clear();
		this.networkableList.clear();
	}
	
	public void reload() {
		String reloadFile = this.file;
		unload();
		load(reloadFile);
	}
	
	String getFile() {
		return this.file;
	}
	
	public void init() {
		for (IGraphItem graphItem :this.graphItemList) {
			graphItem.init();
		}
	}
	
	public void deinit() {
		for (IGraphItem graphItem :this.graphItemList) {
			graphItem.deinit();
		}
	}
	
	public void tick(Input input, float milliseconds) {
		for (ITickable tickable :this.tickableList) {
			tickable.tick(physics, input, milliseconds);
		}
		
		for (Iterator<Collideable> it_1 = this.collideableList.iterator(); it_1.hasNext();) {
			Collideable collidable = it_1.next();
			for (Iterator<Collideable> it_2 = it_1; it_2.hasNext();) {
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
	
	public void addGraphItem(IGraphItem graphItem) {
		this.graphItemList.add(graphItem);
	}
	
	public void addCollideable(Collideable collideable) {
		this.collideableList.add(collideable);
	}
	
	public void addTickable(ITickable tickable) {
		this.tickableList.add(tickable);
	}
	
	public void addNetworkable(INetworkable networkable) {
		this.networkableList.add(networkable);
	}
	
	public Set<IGraphItem> getGraphItems() {
		return this.graphItemList;
	}
}
