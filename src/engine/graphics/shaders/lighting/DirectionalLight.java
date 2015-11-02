package engine.graphics.shaders.lighting;

import engine.math.Vector3;

/**
 * Created by vesel on 01.11.2015.
 */
public class DirectionalLight {
    private BaseLight mBaseLight;
    private Vector3 mDirection;

    public DirectionalLight(BaseLight baseLight, Vector3 direction) {
        mBaseLight = baseLight;
        mDirection = direction.normalize();
    }

    public BaseLight getBaseLight() {
        return mBaseLight;
    }

    public void setBaseLight(BaseLight baseLight) {
        mBaseLight = baseLight;
    }

    public Vector3 getDirection() {
        return mDirection;
    }

    public void setDirection(Vector3 direction) {
        mDirection = direction;
    }
}
