package graphics;

import math.Vector2;
import math.Vector3;

/**
 * Created by vesel on 30.10.2015.
 */
public class Vertex {

    public static final int SIZE = 5;

    private Vector2 mTextureCoordinate;
    private Vector3 mPosition;

    public Vertex(Vector3 position, Vector2 textureCoordinate) {
        mPosition = position;
        mTextureCoordinate = textureCoordinate;
    }

    public Vertex(Vector3 position) {
        this(position, new Vector2(0, 0));
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
