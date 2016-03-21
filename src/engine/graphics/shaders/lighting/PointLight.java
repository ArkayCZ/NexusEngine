package engine.graphics.shaders.lighting;

import engine.math.Vector3;

/**
 * Created by vesel on 02.11.2015.
 */
public class PointLight extends BaseLight
{

    private Attenuation mAttenuation;
    private Vector3 mPosition;
    private float mRange;

    public PointLight(Vector3 color, float intensity, Attenuation attenuation, Vector3 position, float range)
    {
        super(color, intensity);
        mAttenuation = attenuation;
        mPosition = position;
        mRange = range;
    }

    public float getRange()
    {
        return mRange;
    }

    public void setRange(float range)
    {
        mRange = range;
    }

    public Attenuation getAttenuation()
    {
        return mAttenuation;
    }

    public void setAttenuation(Attenuation attenuation)
    {
        mAttenuation = attenuation;
    }

    public Vector3 getPosition()
    {
        return mPosition;
    }

    public void setPosition(Vector3 position)
    {
        mPosition = position;
    }

}
