package kgr.engine.graph;


import org.joml.Vector3f;

/**
 * A class that holds a directional light instance.
 * This is any light that is not affected by attenuation because of its huge distance
 * but has a high intensity (such as the sun).
 *
 * @author Val
 */
public class DirectionalLight {

    private Vector3f color;

    private Vector3f direction;

    private float intensity;

    public DirectionalLight(Vector3f color, Vector3f direction, float intensity) {
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }

    public DirectionalLight(DirectionalLight light) {
        this(new Vector3f(light.getColour()), new Vector3f(light.getDirection()), light.getIntensity());
    }


   /**
    * @return the color
    */
   public Vector3f getColour()
   {
      return color;
   }


   /**
    * @return the direction
    */
   public Vector3f getDirection()
   {
      return direction;
   }


   /**
    * @return the intensity
    */
   public float getIntensity()
   {
      return intensity;
   }


   /**
    * @param color the color to set
    */
   public void setColor(Vector3f color)
   {
      this.color = color;
   }


   /**
    * @param direction the direction to set
    */
   public void setDirection(Vector3f direction)
   {
      this.direction = direction;
   }


   /**
    * @param intensity the intensity to set
    */
   public void setIntensity(float intensity)
   {
      this.intensity = intensity;
   }
}