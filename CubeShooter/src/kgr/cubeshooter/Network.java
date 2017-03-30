/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter;

/**
 *
 * @author Benjamin
 */
public abstract class Network implements Runnable {
	
	protected static Network instance;
	
	protected Network() {
		instance = this;
	}
	
	@Override
	public void run() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public void flush() {
		
	}

	public static Network instance() {
		return instance;
	}
}
