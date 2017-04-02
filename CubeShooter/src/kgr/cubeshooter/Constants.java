package kgr.cubeshooter;

/**
 * A class that contains all the global constants.
 *
 * @author Val
 */
public class Constants {

    /**
     * Impact of mouse movement on rotation.
     */
    public static final float MOUSE_SENSITIVITY = 0.3f;

    /**
     * Velocity of the camera (temporary)
     */
    public static final float CAMERA_POS_STEP = 0.2f;

    /**
     * A constant for the maximum of point light sources. Should be kept in sync with the constant
     * in the fragment shader.
     */
    public static final int MAX_POINT_LIGHTS = 16;

    /**
     * Path to the root folder of game data.
     */
    public static final String DATA_ROOT = "/kgr/cubeshooter/data/";
    /**
     * The root folder for the 3D models.
     */
    public static final String MODELS_ROOT = DATA_ROOT + "models/";
    /**
     * Root path of where the textures are located.
     */
    public static final String TEXTURES_ROOT = DATA_ROOT + "textures/";
    /**
     * Path to the folder with the shaders.
     */
    public static final String SHADERS_ROOT = DATA_ROOT + "shaders/";

}
