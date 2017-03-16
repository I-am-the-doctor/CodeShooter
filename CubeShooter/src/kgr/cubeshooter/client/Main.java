package kgr.cubeshooter.client;

import kgr.engine.GameEngine;
import kgr.engine.IGameLogic;

public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new CubeShooter();
            GameEngine gameEng = new GameEngine("CubeShooter", 600, 480, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}