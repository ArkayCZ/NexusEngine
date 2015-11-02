package engine.graphics.shaders.lighting;

import engine.math.Vector3;

/**
 * Created by vesel on 02.11.2015.
 */
public class SpotLight {

    private PointLight mPointLight;
    private Vector3 mDirection;
    private float mLength;

    public SpotLight(PointLight pointLight, Vector3 direction, float length) {
        mPointLight = pointLight;
        mDirection = direction.normalize();
        mLength = length;

        mDirection.normalize();
    }

    public PointLight getPointLight() {
        return mPointLight;
    }

    public void setPointLight(PointLight pointLight) {
        mPointLight = pointLight;
    }

    public Vector3 getDirection() {
        return mDirection;
    }

    public void setDirection(Vector3 direction) {
        mDirection = direction;
    }

    public float getLength() {
        return mLength;
    }

    public void setLength(float length) {
        mLength = length;
    }
}
