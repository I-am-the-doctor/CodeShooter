/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import static kgr.cubeshooter.Constants.MODELS_ROOT;
import static kgr.cubeshooter.Constants.TEXTURES_ROOT;
import kgr.cubeshooter.world.entities.ICollideable;
import kgr.cubeshooter.world.entities.boundingBoxes.AlignedCuboid;
import kgr.cubeshooter.world.entities.boundingBoxes.BoundingBox;
import kgr.engine.IGraphItem;
import kgr.engine.graph.Material;
import kgr.engine.graph.Mesh;
import kgr.engine.graph.ObjImporter;
import kgr.engine.graph.Texture;
import org.joml.Vector3f;
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
            String meshPath = MODELS_ROOT + "block.obj";
            String texPath = TEXTURES_ROOT + "blockUV.png";
            try {
                mesh = ObjImporter.loadMesh(meshPath);
                Texture texture = new Texture(texPath);
                Material mat = new Material(texture, 0.3f);
                mesh.setMaterial(mat);
            } catch (Exception ex) {
                System.err.println("Cant't load mesh (" + meshPath + ") or texture (" + texPath + ")");
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
