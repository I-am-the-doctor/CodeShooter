package kgr.cubeshooter.client;

import kgr.engine.GameEngine;
import kgr.engine.IGameLogic;


/**
 * This class prepares and launches the game.
 *
 * @author Val
 */
public class Main
{
   /**
    * @param args
    */
   public static void main(String[] args)
   {
      try {
         IGameLogic gameLogic = new CubeShooter();
         GameEngine gameEng = new GameEngine("CubeShooter", 800, 600, true, gameLogic);
         gameEng.start();
      }
      catch (Exception ex) {
         // Catch all uncaught exceptions.
         ex.printStackTrace();
         System.exit(-1);
      }
   }
}
