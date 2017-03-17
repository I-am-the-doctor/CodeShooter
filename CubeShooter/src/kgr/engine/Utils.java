package kgr.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


/**
 * Class which contains various utils in it.
 *
 * @author Val
 */
public class Utils
{
   /**
    * @param filename Name of the file to load.
    *
    * @return The contents of the file.
    * @throws IOException If the file couldn't be loaded.
    */
   public static String readFile(String filename) throws IOException
   {
      String result;
      try (InputStream in = Utils.class.getClass().getResourceAsStream(filename);
           Scanner scanner = new Scanner(in, "UTF-8")) {
         result = scanner.useDelimiter("\\A").next();
      }
      return result;
   }


   /**
    * Singleton.
    */
   private Utils()
   {  }
}
