package engine.graphics.shaders.lighting;

import engine.math.Vector3;

/**
 * Created by vesel on 01.11.2015.
 */
public class DirectionalLight extends BaseLight {

    private Vector3 mDirection;

    public DirectionalLight(Vector3 color, float intensity, Vector3 direction) {
        super(color, intensity);
        mDirection = direction.normalize();
    }

    public Vector3 getDirection() {
        return mDirection;
    }

    public void setDirection(Vector3 direction) {
        mDirection = direction;
    }

    @Override
    public void map() {
        super.map();
        addVector3("direction", mDirection);
    }
}
