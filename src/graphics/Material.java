package graphics;

import math.Vector3;

/**
 * Created by vesel on 01.11.2015.
 */
public class Material {

    private Texture mTexture;
    private Vector3 mColor;

    public Material(Texture texture, Vector3 color) {
        mTexture = texture;
        mColor = color;
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
}
