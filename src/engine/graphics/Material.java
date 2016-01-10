package engine.graphics;

import engine.math.Vector3;

/**
 * Created by vesel on 01.11.2015.
 */
public class Material {

    private Texture mTexture;
    private Vector3 mColor;
    private float mSpecularIntensity;
    private float mSpecularExponent;

    public Material(Texture texture, Vector3 color) {
        this(texture, color, 0.5f, 10);
    }

    public Material(Texture texture, Vector3 color, float intensity, float exponent) {
        mTexture = texture;
        mColor = color;
        mSpecularExponent = exponent;
        mSpecularIntensity = intensity;
    }

    public Texture getTexture() {
        return mTexture;
    }

    public Vector3 getColor() {
        return mColor;
    }

    public void setTexture(Texture texture) {
        mTexture = texture;
    }

    public void setColor(Vector3 color) {
        mColor = color;
    }

    public float getSpecularIntensity() {
        return mSpecularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity) {
        mSpecularIntensity = specularIntensity;
    }

    public float getSpecularExponent() {
        return mSpecularExponent;
    }

    public void setSpecularExponent(float specularExponent) {
        mSpecularExponent = specularExponent;
    }
}
