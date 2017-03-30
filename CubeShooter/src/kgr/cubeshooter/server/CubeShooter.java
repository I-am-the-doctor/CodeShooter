/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kgr.cubeshooter.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import kgr.cubeshooter.Network;
import kgr.cubeshooter.world.World;
import kgr.cubeshooter.world.Physics;
import kgr.engine.Timer;


/**
 *
 * @author Val
 */
public class CubeShooter {
	
	private final World world;
	
	private final Network network;
	
	private static final float DELTA_TIME = 1.0f / 30.0f;
	
	public CubeShooter() {
		network = new ServerNetwork();
		
		world = new World(new Physics());
		world.load("Test_World.xml");
	}
	
	public void run() {
		Timer timer = new Timer();
		timer.init();
		
		boolean finished = false;
		while (!finished) {
			float elapsedTime = timer.getElapsedTime();
			
			//this.world.tick(input, elapsedTime);
			this.world.writeNetworkData(this.network);
			this.network.flush();
			
			if (DELTA_TIME - elapsedTime > 0) {
				try {
					Thread.sleep((long) (DELTA_TIME - elapsedTime));
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
			}
		}
	}
}
