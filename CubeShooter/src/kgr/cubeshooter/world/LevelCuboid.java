/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import com.sun.media.jfxmedia.logging.Logger;
import kgr.cubeshooter.world.entities.boundingBoxes.AlignedCuboid;
import kgr.cubeshooter.world.entities.boundingBoxes.BoundingBox;
import org.joml.Vector3f;
import kgr.engine.IGraphItem;
import kgr.engine.graph.Material;
import kgr.engine.graph.Mesh;
import kgr.engine.graph.ObjImporter;
import kgr.engine.graph.Texture;
import kgr.cubeshooter.world.entities.ICollideable;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Benjamin
 */
public class LevelCuboid implements ICollideable, IGraphItem, IXmlSerializeable {
	
	private final AlignedCuboid boundingBox;
	
	private static Mesh mesh;

	public LevelCuboid(Vector3f position) {		
		this.boundingBox = new AlignedCuboid(position, 1, 1, 1);
	}
	
	public LevelCuboid() {		
		this.boundingBox = new AlignedCuboid(new Vector3f(), 1, 1, 1);
	}

	@Override
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	@Override
	public boolean hasVeclocity() {
		return false;
	}

	@Override
	public void init() {
		if (mesh == null) {
			try {
				mesh = ObjImporter.loadMesh("/kgr/cubeshooter/data/models/block.obj");
				Texture texture = new Texture("/kgr/cubeshooter/data/textures/blockUV.png");
				Material mat = new Material(texture, 0.3f);
				mesh.setMaterial(mat);
			} catch (Exception e) {
				Logger.logMsg(Logger.ERROR, "Cant't load mesh (/kgr/cubeshooter/data/models/block.obj) or texture (/kgr/cubeshooter/data/textures/blockUV.png)");
			}
		}
	}

	@Override
	public void deinit() {
		if (mesh != null) {
			mesh.cleanUp();
			mesh = null;
		}
	}

	@Override
	public Vector3f getPosition() {
		return this.boundingBox.getPosition();
	}

	@Override
	public Vector3f getScale() {
		return new Vector3f(this.boundingBox.getA(), this.boundingBox.getB(), this.boundingBox.getC());
	}

	@Override
	public Vector3f getRotation() {
		return new Vector3f();
	}

	@Override
	public Mesh getMesh() {
		return mesh;
	}

	@Override
	public DefaultHandler getXmlHandler() {
		return new DefaultHandler() {
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				if (qName.equals("Position")) {
					boundingBox.setPosition(new Vector3f(Float.parseFloat(attributes.getValue("x")),
														 Float.parseFloat(attributes.getValue("y")),
														 Float.parseFloat(attributes.getValue("z"))));
				}
			}
		};
	}

	@Override
	public void writeXml(ContentHandler handler) throws SAXException {
		AttributesImpl attributes = new AttributesImpl();
		attributes.addAttribute("", "", "x", "", Float.toString(this.boundingBox.getPosition().x));
		attributes.addAttribute("", "", "y", "", Float.toString(this.boundingBox.getPosition().y));
		attributes.addAttribute("", "", "z", "", Float.toString(this.boundingBox.getPosition().z));
		handler.startElement("", "", "Position", attributes);
		handler.endElement("", "", "Position");
	}
	
}
