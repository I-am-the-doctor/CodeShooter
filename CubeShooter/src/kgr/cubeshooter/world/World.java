/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import kgr.cubeshooter.INetworkable;
import kgr.cubeshooter.Network;
import kgr.cubeshooter.world.entities.ITickable;
import kgr.engine.Input;
import org.xml.sax.*;
import kgr.engine.IGraphItem;
import org.xml.sax.helpers.DefaultHandler;
import kgr.cubeshooter.world.entities.ICollideable;
import org.xml.sax.helpers.AttributesImpl;

/**
 *
 * @author Benjamin
 */
public class World implements INetworkable {
	
	protected String file;
	
	protected Physics physics;
	
	protected Set<IGraphItem> graphItemList;
	protected Set<ICollideable> collideableList;
	protected Set<ITickable> tickableList;
	protected Set<INetworkable> networkableList; 
	
	public World(Physics physics) {
		this.physics = physics;
		
		this.graphItemList = new HashSet<>();
		this.collideableList = new HashSet<>();
		this.tickableList = new HashSet<>();
		this.networkableList = new HashSet<>();
	}
	
	public void load(String file) {
		this.file = file;
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(new File(this.getClass().getResource(file).toURI()), new DefaultHandler() {
				private DefaultHandler entityHandler;
				
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
					if (this.entityHandler != null) {
						this.entityHandler.startElement(uri, localName, qName, attributes);
					} else if (qName.equals("Entity") && attributes.getValue("class") != null) {
						try {
							Class entityClass = Class.forName(attributes.getValue("class"));
							
							if (Arrays.asList(entityClass.getInterfaces()).contains(IXmlSerializeable.class)) {
								IXmlSerializeable entity = (IXmlSerializeable) entityClass.newInstance();
								addEntity(entity);
								this.entityHandler = (DefaultHandler) entity.getXmlHandler();
								this.entityHandler.startElement(uri, localName, qName, attributes);
							} else {
								Logger.getLogger(World.class.getName()).log(Level.SEVERE, entityClass.getName().concat(" does not implements IXmlSerializeable"));
							}
						} catch (Exception ex) {
							Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
				
				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if (qName.equals("Entity")) {
						this.entityHandler = null;
					} else if (this.entityHandler != null) {
						this.entityHandler.endElement(uri, localName, qName);
					}
				}
			});
		} catch (Exception ex) {
			Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	public void save(String file) {
		try {
			Set<IXmlSerializeable> entities = new HashSet<>();
			Consumer<Object> add = (Object entity) -> {
				if (entity instanceof IXmlSerializeable) {
					entities.add((IXmlSerializeable) entity);
				}
			};
			this.collideableList.forEach(add);
			this.graphItemList.forEach(add);
			this.networkableList.forEach(add);
			this.tickableList.forEach(add);
			
			
			SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler handler = factory.newTransformerHandler();
			handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
			handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
			File f = new File(file);
			f.createNewFile();
			handler.setResult(new StreamResult(new FileOutputStream(f)));
			
			handler.startDocument();
			handler.startElement("", "", "root", null);
			
			for (IXmlSerializeable entity : entities) {
				try {
					AttributesImpl attributes = new AttributesImpl();
					attributes.addAttribute("", "", "class", "", entity.getClass().getName());
					handler.startElement("", "", "Entity", attributes);
					
					entity.writeXml(handler);
					
					handler.endElement("", "", "Entity");
				} catch (SAXException ex) {
					Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			handler.endElement("", "", "root");
			handler.endDocument();
		} catch (Exception ex) {
			Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
		}
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
	
	public void addGraphItem(IGraphItem graphItem) {
		this.graphItemList.add(graphItem);
	}
	
	public void addCollideable(ICollideable collideable) {
		this.collideableList.add(collideable);
	}
	
	public void addTickable(ITickable tickable) {
		this.tickableList.add(tickable);
	}
	
	public void addNetworkable(INetworkable networkable) {
		this.networkableList.add(networkable);
	}
	
	public void addEntity(Object entity) {
		if (entity instanceof IGraphItem) {
			addGraphItem((IGraphItem) entity);
		}
		if (entity instanceof ICollideable) {
			addCollideable((ICollideable) entity);
		}
		if (entity instanceof ITickable) {
			addTickable((ITickable) entity);
		}
		if (entity instanceof INetworkable) {
			addNetworkable((INetworkable) entity);
		}
	}
	
	public Set<IGraphItem> getGraphItems() {
		return this.graphItemList;
	}
}



