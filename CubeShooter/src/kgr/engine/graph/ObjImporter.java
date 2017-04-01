package kgr.engine.graph;

import java.util.ArrayList;
import java.util.List;
import kgr.engine.Utils;
import org.joml.Vector2f;
import org.joml.Vector3f;


/**
 * Imports a ".obj" file as Mesh.
 *
 * @author Val
 */
public class ObjImporter
{
   /**
    * Imports a Wavefront ".obj" file to a Mesh.
    * @param filename
    * @return The importet mesh.
    * @throws Exception
    */
   public static Mesh loadMesh(String filename) throws Exception
   {
      List<String> lines = Utils.readAllLines(filename);

      List<Vector3f> vertices = new ArrayList<>();
      List<Vector2f> textures = new ArrayList<>();
      List<Vector3f> normals = new ArrayList<>();
      List<Face> faces = new ArrayList<>();

      for (String line : lines) {
         String[] tokens = line.split("\\s+");
         switch (tokens[0]) {
            case "v":
               // A vertex.
               Vector3f vec3f = new Vector3f(
                  Float.parseFloat(tokens[1]),
                  Float.parseFloat(tokens[2]),
                  Float.parseFloat(tokens[3]));
               vertices.add(vec3f);
               break;
            case "vt":
               // Texture coordinate.
               Vector2f vec2f = new Vector2f(
                  Float.parseFloat(tokens[1]),
                  Float.parseFloat(tokens[2]));
               textures.add(vec2f);
               break;
            case "vn":
               // Vertex normal.
               Vector3f vec3fNorm = new Vector3f(
                  Float.parseFloat(tokens[1]),
                  Float.parseFloat(tokens[2]),
                  Float.parseFloat(tokens[3]));
               normals.add(vec3fNorm);
               break;
            case "f":
               Face face = new Face(tokens[1], tokens[2], tokens[3]);
               faces.add(face);
               break;
            case "#":
               // Just a comment.
               break;
            default:
               System.out.println("ObjImporter: skipping line \"" + line + "\": not supported yet.");
               break;
         }
      }
      return reorderLists(vertices, textures, normals, faces);
   }


   /**
    * Reorders all of the data lists of one Mesh.
    *
    * @param posList
    * @param textCoordList
    * @param normList
    * @param facesList
    * @return The Mesh with reordered data lists.
    */
   private static Mesh reorderLists(List<Vector3f> posList, List<Vector2f> textCoordList,
                                    List<Vector3f> normList, List<Face> facesList)
   {

      List<Integer> indices = new ArrayList<>();
      // Create position array in the order it has been declared
      float[] posArr = new float[posList.size() * 3];
      int i = 0;
      for (Vector3f pos : posList) {
         posArr[i * 3] = pos.x;
         posArr[i * 3 + 1] = pos.y;
         posArr[i * 3 + 2] = pos.z;
         i++;
      }
      float[] textCoordArr = new float[posList.size() * 2];
      float[] normArr = new float[posList.size() * 3];

      for (Face face : facesList) {
         IndexGroup[] faceVertexIndices = face.getFaceVertexIndices();
         for (IndexGroup indValue : faceVertexIndices) {
            processFaceVertex(indValue, textCoordList, normList,
                              indices, textCoordArr, normArr);
         }
      }
      int[] indicesArr = new int[indices.size()];
      indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
      Mesh mesh = new Mesh(posArr, textCoordArr, normArr, indicesArr);
      return mesh;
   }


   /**
    * Reorders the data of one face's indicies.
    *
    * @param indices
    * @param textCoordList
    * @param normList
    * @param indicesList
    * @param texCoordArr
    * @param normArr
    */
   private static void processFaceVertex(IndexGroup indices, List<Vector2f> textCoordList,
                                         List<Vector3f> normList, List<Integer> indicesList,
                                         float[] texCoordArr, float[] normArr)
   {
      // Set index for vertex coordinates.
      int posIndex = indices.idxVertex;
      indicesList.add(posIndex);

      // Reorder texture coordinates.
      if (indices.idxTextCoord >= 0) {
         Vector2f textCoord = textCoordList.get(indices.idxTextCoord);
         texCoordArr[posIndex * 2] = textCoord.x;
         texCoordArr[posIndex * 2 + 1] = 1 - textCoord.y;
      }
      if (indices.idxVecNormal >= 0) {
         // Reorder vertex normals.
         Vector3f vecNorm = normList.get(indices.idxVecNormal);
         normArr[posIndex * 3] = vecNorm.x;
         normArr[posIndex * 3 + 1] = vecNorm.y;
         normArr[posIndex * 3 + 2] = vecNorm.z;
      }
   }


   /**
    * Helper class for one of the three index groups of one face.
    */
   private static class IndexGroup
   {
      public static final int NO_VALUE = -1;

      public int idxVertex;

      public int idxTextCoord;

      public int idxVecNormal;


      /**
       * Sets the three indices to -1 to avoid mistakes (if it was 0).
       */
      private IndexGroup()
      {
         idxVertex = NO_VALUE;
         idxTextCoord = NO_VALUE;
         idxVecNormal = NO_VALUE;
      }
   }


   /**
    * A little helper class for one triangle face.
    * "f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3"
    */
   private static class Face
   {
      /**
       * List of the three index groups for a face triangle.
       */
      private IndexGroup[] idxGroups = new IndexGroup[3];


      /**
       * @param v1 String value for the first index group.
       * @param v2 String value for the second index group.
       * @param v3 String value for the third index group.
       */
      private Face(String v1, String v2, String v3)
      {
         idxGroups = new IndexGroup[3];
         // Parse the lines
         idxGroups[0] = parseLine(v1);
         idxGroups[1] = parseLine(v2);
         idxGroups[2] = parseLine(v3);
      }


      /**
       * Parse one index group of a "f"-line.
       *
       * @param line The line which contains the index group.
       * @return The parsed index group.
       */
      private IndexGroup parseLine(String line)
      {
         IndexGroup idxGroup = new IndexGroup();

         String[] lineTokens = line.split("/");
         int length = lineTokens.length;
         idxGroup.idxVertex = Integer.parseInt(lineTokens[0]) - 1;
         if (length > 1) {
            // It can be empty if the obj does not define text coords
            String textCoord = lineTokens[1];
            idxGroup.idxTextCoord = textCoord.length() > 0 ?
                                    Integer.parseInt(textCoord) - 1 :
                                    IndexGroup.NO_VALUE;
            if (length > 2) {
               idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
            }
         }

         return idxGroup;
      }


      /**
       * @return The three index groups.
       */
      public IndexGroup[] getFaceVertexIndices()
      {
         return idxGroups;
      }
   }


   /**
    * Static class, shouldn't be instanced.
    */
   private ObjImporter() { }

}
