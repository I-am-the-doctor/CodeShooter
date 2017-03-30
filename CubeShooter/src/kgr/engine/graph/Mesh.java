package kgr.engine.graph;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


/**
 * A class which stores all definitons for a 3D mesh:
 * Vertices, faces, vertex normals, texture coordinates, etc.
 *
 * @author Val
 */
public class Mesh
{
   /**
    * A default diffuse colour, if not specified by the mesh.
    */
   private static final Vector3f DEFAULT_COLOUR = new Vector3f(1.0f, 0f, 0.9f);

   /**
    * The ID of our Vertex Array Object.
    */
   private final int vaoId;

   /**
    * A list with the IDs of all the Vertex Buffer Objects.
    */
   private final List<Integer> vboIdList;

   private final int vertexCount;


   /**
    *
    */
   private Material material;


   /**
    * Creates a new mesh without a texture.
    *
    * @param positions  The vertices.
    * @param textCoords Texture coordinates.
    * @param normals    The vertex normals.
    * @param indices    The face indicies.
    */
   public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices)
   {
      FloatBuffer posBuffer = null;
      FloatBuffer textCoordsBuffer = null;
      FloatBuffer vecNormalsBuffer = null;
      IntBuffer indicesBuffer = null;
      try {
         vertexCount = indices.length;
         vboIdList = new ArrayList();

         vaoId = glGenVertexArrays();
         glBindVertexArray(vaoId);

         // Position VBO
         int vboId = glGenBuffers();
         vboIdList.add(vboId);
         posBuffer = MemoryUtil.memAllocFloat(positions.length);
         posBuffer.put(positions).flip();
         glBindBuffer(GL_ARRAY_BUFFER, vboId);
         glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
         glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

         // Texture coordinates VBO
         vboId = glGenBuffers();
         vboIdList.add(vboId);
         textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
         textCoordsBuffer.put(textCoords).flip();
         glBindBuffer(GL_ARRAY_BUFFER, vboId);
         glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
         glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

         // Vertex normals VBO
         vboId = glGenBuffers();
         vboIdList.add(vboId);
         vecNormalsBuffer = MemoryUtil.memAllocFloat(normals.length);
         vecNormalsBuffer.put(normals).flip();
         glBindBuffer(GL_ARRAY_BUFFER, vboId);
         glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW);
         glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

         // Index VBO
         vboId = glGenBuffers();
         vboIdList.add(vboId);
         indicesBuffer = MemoryUtil.memAllocInt(indices.length);
         indicesBuffer.put(indices).flip();
         glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
         glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

         glBindBuffer(GL_ARRAY_BUFFER, 0);
         glBindVertexArray(0);
      }
      finally {
         if (posBuffer != null) {
            MemoryUtil.memFree(posBuffer);
         }
         if (textCoordsBuffer != null) {
            MemoryUtil.memFree(textCoordsBuffer);
         }
         if (vecNormalsBuffer != null) {
            MemoryUtil.memFree(vecNormalsBuffer);
         }
         if (indicesBuffer != null) {
            MemoryUtil.memFree(indicesBuffer);
         }
      }
   }


   /**
    * @return The material of this mesh.
    */
   public Material getMaterial() {
      return material;
   }


   /**
    *
    * @param material
    */
   public void setMaterial(Material material)
   {
      this.material = material;
   }

   /**
    * @return The VAO handle of this mesh.
    */
   public int getVaoId()
   {
      return vaoId;
   }


   /**
    * @return Number of vertices for this mesh.
    */
   public int getVertexCount()
   {
      return vertexCount;
   }


   /**
    * Renders this mesh.
    */
   public void render()
   {
      Texture texture = material.getDiffuseTexture();
      if (texture != null) {
         // Activate the first texture bank.
         glActiveTexture(GL_TEXTURE0);
         // Bind the texture.
         glBindTexture(GL_TEXTURE_2D, texture.getId());
      }
	  
      // Draw the mesh itself.
      glBindVertexArray(getVaoId());
      glEnableVertexAttribArray(0);
      glEnableVertexAttribArray(1);
      glEnableVertexAttribArray(2);

      glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

      // Restore the state.
      glDisableVertexAttribArray(0);
      glDisableVertexAttribArray(1);
      glDisableVertexAttribArray(2);
      glBindVertexArray(0);
   }


   /**
    * Cleans everything up (buffers, textures, etc).
    */
   public void cleanUp()
   {
      glDisableVertexAttribArray(0);

      // Delete the VBOs
      glBindBuffer(GL_ARRAY_BUFFER, 0);
      for (int vboId : vboIdList) {
         glDeleteBuffers(vboId);
      }


      Texture texture = material.getDiffuseTexture();
      if (texture != null) {
         // Delete the texture.
         texture.cleanup();
      }

      // Delete the VAO
      glBindVertexArray(0);
      glDeleteVertexArrays(vaoId);
   }
}
