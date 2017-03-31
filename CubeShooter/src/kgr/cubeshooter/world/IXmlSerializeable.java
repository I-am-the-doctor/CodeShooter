/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.world;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Benjamin
 */
public interface IXmlSerializeable {
	
	public DefaultHandler getXmlHandler();
	
	public void writeXml(ContentHandler handler) throws SAXException;
}
