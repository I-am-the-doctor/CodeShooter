package kgr.engine.graph;

import org.joml.Vector3f;


/**
 * Camera that orbits, in a certain radius, around a point.
 *
 * @author Val
 */
public class OrbitingCamera
{
    private float    radius;
    private Vector3f center;


    /**
     * @param radius The radius at which to orbit around the center point.
     * @param center The center point
     */
    public OrbitingCamera(float radius, Vector3f center)
    {
        this.radius = radius;
        this.center = center;
    }


    public void setCenterPoint(Vector3f newCenter)
    {

    }

    /**
     * @return The center point around which this camera is orbiting.
     */
    public Vector3f getCenterPoint()
    {
        return center;
    }
}
