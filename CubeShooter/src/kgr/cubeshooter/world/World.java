/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import kgr.cubeshooter.INetworkable;
import kgr.cubeshooter.Network;
import kgr.cubeshooter.world.entities.ICollideable;
import kgr.cubeshooter.world.entities.ITickable;
import kgr.engine.IGraphItem;
import kgr.engine.Input;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

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

        graphItemList = new HashSet<>();
        collideableList = new HashSet<>();
        tickableList = new HashSet<>();
        networkableList = new HashSet<>();
    }


    /**
     *
     * @param file
     */
    public void load(String file) {
        this.file = file;

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(getClass().getResource(file).toURI()), new DefaultHandler() {
                private DefaultHandler entityHandler;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (entityHandler != null) {
                        entityHandler.startElement(uri, localName, qName, attributes);
                    } else if (qName.equals("Entity") && attributes.getValue("class") != null) {
                        try {
                            Class<? extends Object> entityClass = Class.forName(attributes.getValue("class"));

                            if (Arrays.asList(entityClass.getInterfaces()).contains(IXmlSerializeable.class)) {
                                IXmlSerializeable entity = (IXmlSerializeable) entityClass.newInstance();
                                addEntity(entity);
                                entityHandler = entity.getXmlHandler();
                                entityHandler.startElement(uri, localName, qName, attributes);
                            } else {
                                System.err.println(entityClass.getName() + " does not implement IXmlSerializeable!");
							}
						} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SAXException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equals("Entity")) {
                        entityHandler = null;
                    } else if (entityHandler != null) {
                        entityHandler.endElement(uri, localName, qName);
                    }
                }
            });
        } catch (IOException | URISyntaxException | ParserConfigurationException | SAXException ex) {
            ex.printStackTrace();
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
            collideableList.forEach(add);
            graphItemList.forEach(add);
            networkableList.forEach(add);
            tickableList.forEach(add);

            SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            TransformerHandler handler = factory.newTransformerHandler();
            handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
            handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
            File f = new File(file);
            f.createNewFile();
            handler.setResult(new StreamResult(new FileOutputStream(f)));

            handler.startDocument();
            handler.startElement("", "", "root", null);

            entities.forEach((IXmlSerializeable entity) -> {
                try {
                    AttributesImpl attributes = new AttributesImpl();
                    attributes.addAttribute("", "", "class", "", entity.getClass().getName());
                    handler.startElement("", "", "Entity", attributes);

                    entity.writeXml(handler);

                    handler.endElement("", "", "Entity");
                } catch (SAXException ex) {
                    ex.printStackTrace();
                }
            });

            handler.endElement("", "", "root");
            handler.endDocument();
        } catch (IOException | IllegalArgumentException | TransformerConfigurationException | SAXException ex) {
            ex.printStackTrace();
        }
    }

    public void unload() {
        file = "";

        graphItemList.clear();
        collideableList.clear();
        tickableList.clear();
        networkableList.clear();
    }

    public void reload() {
        String reloadFile = file;
        unload();
        load(reloadFile);
    }

    String getFile() {
        return file;
    }

    public void init() {
        for (IGraphItem graphItem : graphItemList) {
            graphItem.init();
        }
    }

    public void deinit() {
        for (IGraphItem graphItem : graphItemList) {
            graphItem.deinit();
        }
    }

    public void tick(Input input, float milliseconds) {
        for (ITickable tickable : tickableList) {
            tickable.tick(physics, input, milliseconds);
        }

        for (Iterator<ICollideable> it_1 = collideableList.iterator(); it_1.hasNext();) {
            ICollideable collidable = it_1.next();
            for (Iterator<ICollideable> it_2 = it_1; it_2.hasNext();) {
                physics.collide(collidable, it_2.next());
            }
        }
    }

    @Override
    public void writeNetworkData(Network network) {
        for (INetworkable networkable : networkableList) {
            networkable.writeNetworkData(network);
        }
    }

    public void addGraphItem(IGraphItem graphItem) {
        graphItemList.add(graphItem);
    }

    public void addCollideable(ICollideable collideable) {
        collideableList.add(collideable);
    }

    public void addTickable(ITickable tickable) {
        tickableList.add(tickable);
    }

    public void addNetworkable(INetworkable networkable) {
        networkableList.add(networkable);
    }

    public void addEntity(Object entity) {
        if (entity instanceof IGraphItem) {
            addGraphItem((IGraphItem) entity);
        } else if (entity instanceof ICollideable) {
            addCollideable((ICollideable) entity);
        } else if (entity instanceof ITickable) {
            addTickable((ITickable) entity);
        } else if (entity instanceof INetworkable) {
            addNetworkable((INetworkable) entity);
        }
    }

    public Set<IGraphItem> getGraphItems() {
        return graphItemList;
    }
}
