package kgr.engine.graph;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.opengl.GL20.*;


public class ShaderProgram
{

   /**
    * A handle for the shader program on the GPU.
    */
   private final int programId;

   /**
    * ID of the vertex shader.
    */
   private int vertexShaderId;

   /**
    * ID of the fragment shader.
    */
   private int fragmentShaderId;

   /**
    * The uniform variables to be set in the shaders,
    */
   private final Map<String, Integer> uniforms;


   /**
    * Creates a new shader program object.
    *
    * @throws Exception
    */
   public ShaderProgram() throws Exception
   {
      programId = glCreateProgram();
      if (programId == 0) {
         throw new Exception("Could not create Shader");
      }
      uniforms = new HashMap<>();
   }


   /**
    * Creates a new uniform variable for the program.
    *
    * @param uniformName Name of the variable (should be contained in one of the shaders).
    *
    * @throws Exception
    */
   public void createUniform(String uniformName) throws Exception
   {
      int uniformLocation = glGetUniformLocation(programId, uniformName);
      if (uniformLocation < 0) {
         throw new Exception("Could not find uniform:" + uniformName);
      }
      uniforms.put(uniformName, uniformLocation);
   }


   /**
    * Sets a matrix uniform.
    *
    * @param uniformName Name of the uniform to be set.
    * @param value       Value of the uniform.
    */
   public void setUniform(String uniformName, Matrix4f value)
   {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         // Dump the matrix into a float buffer
         FloatBuffer fb = stack.mallocFloat(16);
         value.get(fb);
         glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
      }
   }


   /**
    * Sets a float vector uniform.
    *
    * @param uniformName Name of the uniform to be set.
    * @param value       Value of the uniform.
    */
   public void setUniform(String uniformName, Vector3f value)
   {
      glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
   }


   /**
    * Sets a simple int uniform.
    *
    * @param uniformName Name of the uniform to be set.
    * @param value       Value of the uniform.
    */
   public void setUniform(String uniformName, int value)
   {
      glUniform1i(uniforms.get(uniformName), value);
   }


   /**
    * Attaches a vertex shader to the program.
    *
    * @param shaderCode The code from which to create the vertex shader.
    *
    * @throws Exception When it fails.
    */
   public void createVertexShader(String shaderCode) throws Exception
   {
      vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
   }


   /**
    * Attaches a fragment shader to the program.
    *
    * @param shaderCode The code from which to create the fragment shader.
    *
    * @throws Exception When it fails.
    */
   public void createFragmentShader(String shaderCode) throws Exception
   {
      fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
   }


   /**
    * Creates a shader.
    *
    * @param shaderCode The code of the shader.
    * @param shaderType Type: Fragment or Vertex.
    *
    * @throws Exception
    * @return The ID of the created shader.
    */
   protected int createShader(String shaderCode, int shaderType) throws Exception
   {
      int shaderId = glCreateShader(shaderType);
      if (shaderId == 0) {
         throw new Exception("Error creating shader. Type: " + shaderType);
      }

      // Set shader's source and compile it.
      glShaderSource(shaderId, shaderCode);
      glCompileShader(shaderId);

      if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
         throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
      }

      glAttachShader(programId, shaderId);

      return shaderId;
   }


   /**
    * Links the shader program.
    *
    * @throws Exception
    */
   public void link() throws Exception
   {
      glLinkProgram(programId);
      if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
         throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
      }

      if (vertexShaderId != 0) {
         glDetachShader(programId, vertexShaderId);
      }
      if (fragmentShaderId != 0) {
         glDetachShader(programId, fragmentShaderId);
      }

      // Is everything alright?
      glValidateProgram(programId);
      if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
         System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
      }
   }


   /**
    * Binds the shader for use.
    */
   public void bind()
   {
      glUseProgram(programId);
   }


   /**
    * Unbinds the shader.
    */
   public void unbind()
   {
      glUseProgram(0);
   }


   /**
    * Unbinding and deleting the program.
    */
   public void cleanup()
   {
      unbind();
      if (programId != 0) {
         glDeleteProgram(programId);
      }
   }
}
