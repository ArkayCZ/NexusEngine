package engine.graphics.shaders.lighting;

import engine.math.Vector3;

/**
 * Created by vesel on 02.11.2015.
 */
public class SpotLight extends PointLight
{

    private Vector3 mDirection;
    private float mLength;

    public SpotLight(Vector3 color, float intensity, Attenuation attenuation, Vector3 position,
                     float range, Vector3 direction, float length)
    {
        super(color, intensity, attenuation, position, range);
        mDirection = direction.normalize();
        mLength = length;
    }

    public Vector3 getDirection()
    {
        return mDirection;
    }

    public void setDirection(Vector3 direction)
    {
        mDirection = direction;
    }

    public float getLength()
    {
        return mLength;
    }

    public void setLength(float length)
    {
        mLength = length;
    }

    @Override
    public void onMap()
    {
        super.onMap();
        addVector3("light-direction", mDirection);
        addFloat("length", mLength);
    }
}
