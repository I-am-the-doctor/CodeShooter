package kgr.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Class which contains various utils in it.
 *
 * @author Val
 */
public class Utils
{
   /**
    * @param filename Path of the file to load.
    * @return The contents of the file.
    * @throws IOException If the file couldn't be loaded.
    */
   public static String readFile(String filename) throws IOException
   {
      String result;
      try (InputStream in = Utils.class.getResourceAsStream(filename);
           Scanner scanner = new Scanner(in, "UTF-8")) {
         result = scanner.useDelimiter("\\A").next();
      }
      return result;
   }


   /**
    * Reads the contents of a text file.
    * @param filename Path to the file.
    * @return A list with the lines.
    * @throws Exception If the file couldn't be loaded.
    */
   public static List<String> readAllLines(String filename) throws Exception
   {
      List<String> list = new ArrayList<>();
      InputStreamReader isr = new InputStreamReader(Utils.class.getClass().getResourceAsStream(filename));
      try (
         BufferedReader br = new BufferedReader(isr)) {
         String line;
         while ((line = br.readLine()) != null) {
            list.add(line);
         }
      }
      return list;
   }


   /**
    * Singleton.
    */
   private Utils()
   {
   }
}
