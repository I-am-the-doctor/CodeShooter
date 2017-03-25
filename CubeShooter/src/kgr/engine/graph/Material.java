/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kgr.engine.graph;

import org.joml.Vector3f;


/**
 * Class that holds the parameters of a material with diffuse and specular textures.
 *
 * @author kgr.10.pfarr.va
 */
public class Material {

   /**
    * The default color for materials that don't specify it.
    */
    private static final Vector3f DEFAULT_COLOUR = new Vector3f(1.0f, 1.0f, 1.0f);

    /**
     * The base colour of this material (if not textured).
     */
    private Vector3f colour;

    /**
     * Reflectance factor of incoming light (the specular component).
     */
    private float reflectance;

    /**
     * The diffuse (color) texture of this material.
     */
    private Texture diffuseTexture;

    /**
     * The specular (reflectance intensity) texture of this material.
     */
    private Texture specularTexture;

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
     * @param diffuse
     * @param reflectance
     */
    public Material(Texture diffuse, float reflectance) {
        this();
        this.diffuseTexture = diffuse;
        this.reflectance = reflectance;
    }


    /**
     * @return The base colour of this material.
     */
    public Vector3f getColour() {
        return colour;
    }


    /**
     * @param colour Set a new colour for this material.
     */
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }


    /**
     * @return
     */
    public float getReflectance() {
        return reflectance;
    }


    /**
     * @param reflectance The new specular reflectance for this material.
              That is the intensity of reflected light.
     */
    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }


    /**
     * @return Wether this material has a diffuse texture.
     */
    public boolean hasDiffuseTexture() {
        return this.diffuseTexture != null;
    }


    /**
     * @return The diffuse texture of this material.
     */
    public Texture getDiffuseTexture() {
        return diffuseTexture;
    }


    /**
     * @param diffuse The new diffuse texture for this material.
     */
    public void setDiffuseTexture(Texture diffuse) {
        this.diffuseTexture = diffuse;
    }

    /**
     * @return Wether this material has a specular texture.
     */
    public boolean hasSpecularTexture() {
        return this.specularTexture != null;
    }

    /**
     * @return The specular texture of this material.
     */
    public Texture getSpecularTexture() {
        return specularTexture;
    }

    /**
     * @param specular The new specular texture for this material.
     */
    public void setSpecularTexture(Texture specular) {
        this.specularTexture = specular;
    }
}