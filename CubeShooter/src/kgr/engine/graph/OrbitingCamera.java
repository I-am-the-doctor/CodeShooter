package kgr.engine.graph;

import org.joml.Vector3f;


/**
 * Camera that orbits, in a certain radius, around a point.
 *
 * @author Val
 */
public class OrbitingCamera extends Camera
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


    /**
     * @return The radius around which is being orbited.
     */
    public float getRadius()
    {
        return radius;
    }


    /**
     * @param newRadius
     */
    public void setRadius(float newRadius)
    {
        radius = newRadius;
    }


    /**
     * @param newCenter Sets the new center point.
     */
    public void setCenterPoint(Vector3f newCenter)
    {
        center = newCenter;
    }


    /**
     * @return The center point around which this camera is orbiting.
     */
    public Vector3f getCenterPoint()
    {
        return center;
    }
}
