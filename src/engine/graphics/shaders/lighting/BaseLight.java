package engine.graphics.shaders.lighting;

import engine.math.Vector3;

/**
 * Created by vesel on 01.11.2015.
 */
public class BaseLight {

    private Vector3 mColor;
    private float mIntensity;

    public BaseLight(Vector3 color, float intensity) {
        mColor = color;
        mIntensity = intensity;
    }

    public Vector3 getColor() {
        return mColor;
    }

    public void setColor(Vector3 color) {
        mColor = color;
    }

    public float getIntensity() {
        return mIntensity;
    }

    public void setIntensity(float intensity) {
        mIntensity = intensity;
    }
}
