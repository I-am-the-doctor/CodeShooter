package kgr.cubeshooter;


/**
 * A class that contains all the global constants.
 *
 * @author Val
 */
public class Constants
{
   /**
    * Impact of mouse movement on rotation.
    */
   public static final float MOUSE_SENSITIVITY = 0.3f;

   /**
    * Velocity of the camera (temporary)
    */
   public static final float CAMERA_POS_STEP = 0.2f;

   /**
    * A constant for the maximum of point light sources.
    * Should be kept in sync with the constant in the fragment shader.
    */
   public static final int MAX_POINT_LIGHTS = 16;
}
