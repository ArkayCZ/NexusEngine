package engine.graphics;

import engine.MappedClass;
import engine.math.Vector3;
import engine.utils.ContentLoader;

/**
 * Created by vesel on 01.11.2015.
 * Represents a material (texture and lighting parameters) within the engine.
 */
public class Material
{

    private Texture mTexture;
    private Vector3 mColor;

    private float mSpecularExponent;
    private float mSpecularIntensity;

    public Material(String path, Vector3 color)
    {
        this(ContentLoader.loadTexture(path), color);
    }

    public Material(String path, Vector3 color, float intensity, float exponent)
    {
        this(ContentLoader.loadTexture(path), color, intensity, exponent);
    }

    public Material(Texture texture, Vector3 color)
    {
        this(texture, color, 2f, 32);
    }

    public Material(Texture texture, Vector3 color, float intensity, float exponent)
    {
        mTexture = texture;
        mColor = color;
        mSpecularIntensity = intensity;
        mSpecularExponent = exponent;
    }

    public Texture getTexture()
    {
        return mTexture;
    }

    public void setTexture(Texture texture)
    {
        mTexture = texture;
    }

    public Vector3 getColor()
    {
        return mColor;
    }

    public void setColor(Vector3 color)
    {
        mColor = color;
    }

    public float getSpecularExponent()
    {
        return mSpecularExponent;
    }

    public void setSpecularExponent(float specularExponent)
    {
        mSpecularExponent = specularExponent;
    }

    public float getSpecularIntensity()
    {
        return mSpecularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity)
    {
        mSpecularIntensity = specularIntensity;
    }
}
