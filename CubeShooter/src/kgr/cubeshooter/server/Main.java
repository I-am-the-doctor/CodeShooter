/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.server;

/**
 *
 * @author Benjamin
 */
public class Main {
	
	public static void main(String[] agrs) {
		try {
			CubeShooter cubeChooter = new CubeShooter();
			cubeChooter.run();
		}
		catch (Exception exception) {
			exception.printStackTrace();
			System.exit(-1);
		}
	}
}
