package graphics.shaders.lighting;

import math.Vector3;

/**
 * Created by vesel on 02.11.2015.
 */
public class PointLight {

    private Attenuation mAttenuation;
    private BaseLight mBaseLight;
    private Vector3 mPosition;
    private float mRange;



    public PointLight( BaseLight baseLight, Attenuation attenuation, Vector3 position, float range) {
        mAttenuation = attenuation;
        mBaseLight = baseLight;
        mPosition = position;
        mRange = range;


    }

    public float getRange() {
        return mRange;
    }

    public void setRange(float range) {
        mRange = range;
    }

    public Attenuation getAttenuation() {
        return mAttenuation;
    }

    public void setAttenuation(Attenuation attenuation) {
        mAttenuation = attenuation;
    }

    public BaseLight getBaseLight() {
        return mBaseLight;
    }

    public void setBaseLight(BaseLight baseLight) {
        mBaseLight = baseLight;
    }

    public Vector3 getPosition() {
        return mPosition;
    }

    public void setPosition(Vector3 position) {
        mPosition = position;
    }
}
