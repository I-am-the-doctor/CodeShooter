/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kgr.engine.graph;

import org.joml.Vector3f;


/**
 * Class that holds the parameters of a material with diffuse and specular texture.
 *
 * @author kgr.10.pfarr.va
 */
public class Material {

    private static final Vector3f DEFAULT_COLOUR = new Vector3f(1.0f, 1.0f, 1.0f);

    private Vector3f colour;

    private float reflectance;

    private Texture texture;

    /**
     *
     */
    public Material() {
        colour = DEFAULT_COLOUR;
        reflectance = 0;
    }


    /**
     *
     * @param colour
     * @param reflectance
     */
    public Material(Vector3f colour, float reflectance) {
        this();
        this.colour = colour;
        this.reflectance = reflectance;
    }


    /**
     *
     * @param texture
     * @param reflectance
     */
    public Material(Texture texture, float reflectance) {
        this();
        this.texture = texture;
        this.reflectance = reflectance;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public boolean isTextured() {
        return this.texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}