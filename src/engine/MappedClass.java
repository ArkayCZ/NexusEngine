package engine;

import engine.graphics.Material;
import engine.graphics.Texture;
import engine.math.Matrix;
import engine.math.Quaternion;
import engine.math.Vector2;
import engine.math.Vector3;

import java.util.HashMap;

/**
 * Created by vesel on 10.01.2016.
 */
public abstract class MappedClass
{

    private HashMap<String, Integer> mIntegers;
    private HashMap<String, Float> mFloats;
    private HashMap<String, Vector2> mVector2s;
    private HashMap<String, Vector3> mVector3s;
    private HashMap<String, Quaternion> mQuaternions;
    private HashMap<String, Matrix> mMatrices;
    private HashMap<String, Texture> mTextures;
    private HashMap<String, Material> mMaterials;

    /**
     * Initialise all of the maps.
     */
    public MappedClass()
    {
        mIntegers = new HashMap<>();
        mFloats = new HashMap<>();
        mVector2s = new HashMap<>();
        mVector3s = new HashMap<>();
        mQuaternions = new HashMap<>();
        mMaterials = new HashMap<>();
        mMatrices = new HashMap<>();
        mTextures = new HashMap<>();
    }

    public void addInteger(String name, int value)
    {
        mIntegers.put(name, value);
    }

    public int getInteger(String name)
    {
        return mIntegers.get(name);
    }

    public void addFloat(String name, float value)
    {
        mFloats.put(name, value);
    }

    public float getFloat(String name)
    {
        return mFloats.get(name);
    }

    public void addVector2(String name, Vector2 value)
    {
        mVector2s.put(name, value);
    }

    public Vector2 getVector2(String name)
    {
        return mVector2s.get(name);
    }

    public void addVector3(String name, Vector3 value)
    {
        mVector3s.put(name, value);
    }

    public Vector3 getVector3(String name)
    {
        return mVector3s.get(name);
    }

    public void addQuaternion(String name, Quaternion value)
    {
        mQuaternions.put(name, value);
    }

    public Quaternion getQuaternion(String name)
    {
        return mQuaternions.get(name);
    }

    public void addMaterial(String name, Material value)
    {
        mMaterials.put(name, value);
    }

    public Material getMaterial(String name)
    {
        return mMaterials.get(name);
    }

    public void addMatrix(String name, Matrix value)
    {
        mMatrices.put(name, value);
    }

    public Matrix getMatrix(String name)
    {
        return mMatrices.get(name);
    }

    public void addTexture(String name, Texture value)
    {
        mTextures.put(name, value);
    }

    public Texture getTexture(String name)
    {
        return mTextures.get(name);
    }

    public HashMap<String, Integer> getIntegers()
    {
        return mIntegers;
    }

    public void setIntegers(HashMap<String, Integer> integers)
    {
        mIntegers = integers;
    }

    public HashMap<String, Float> getFloats()
    {
        return mFloats;
    }

    public void setFloats(HashMap<String, Float> floats)
    {
        mFloats = floats;
    }

    public HashMap<String, Vector2> getVector2s()
    {
        return mVector2s;
    }

    public void setVector2s(HashMap<String, Vector2> vector2s)
    {
        mVector2s = vector2s;
    }

    public HashMap<String, Vector3> getVector3s()
    {
        return mVector3s;
    }

    public void setVector3s(HashMap<String, Vector3> vector3s)
    {
        mVector3s = vector3s;
    }

    public HashMap<String, Quaternion> getQuaternions()
    {
        return mQuaternions;
    }

    public void setQuaternions(HashMap<String, Quaternion> quaternions)
    {
        mQuaternions = quaternions;
    }

    public HashMap<String, Matrix> getMatrices()
    {
        return mMatrices;
    }

    public void setMatrices(HashMap<String, Matrix> matrices)
    {
        mMatrices = matrices;
    }

    public HashMap<String, Texture> getTextures()
    {
        return mTextures;
    }

    public void setTextures(HashMap<String, Texture> textures)
    {
        mTextures = textures;
    }

    public HashMap<String, Material> getMaterials()
    {
        return mMaterials;
    }

    public void setMaterials(HashMap<String, Material> materials)
    {
        mMaterials = materials;
    }

    public abstract void onMap();
}
