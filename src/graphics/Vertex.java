package graphics;

import math.Vector2;
import math.Vector3;

/**
 * Created by vesel on 30.10.2015.
 */
public class Vertex {

    public static final int SIZE = 8;

    private Vector2 mTextureCoordinate;
    private Vector3 mPosition;
    private Vector3 mNormal;

    public Vertex(Vector3 position, Vector2 textureCoordinate, Vector3 normal) {
        mTextureCoordinate = textureCoordinate;
        mPosition = position;
        mNormal = normal;
    }

    public Vertex(Vector3 position, Vector2 textureCoordinate) {
        this(position, textureCoordinate, new Vector3(0, 0, 0));
    }

    public Vertex(Vector3 position) {
        this(position, new Vector2(0, 0));
    }

    public Vector3 getNormal() {
        return mNormal;
    }

    public void setNormal(Vector3 normal) {
        mNormal = normal;
    }

    public Vector3 getPosition() {
        return mPosition;
    }

    public void setPosition(Vector3 position) {
        mPosition = position;
    }

    public Vector2 getTextureCoordinate() {
        return mTextureCoordinate;
    }

    public void setTextureCoordinate(Vector2 textureCoordinate) {
        mTextureCoordinate = textureCoordinate;
    }

}
