package engine.graphics.shaders.lighting;

import engine.components.GameComponent;
import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.math.Vector3;

/**
 * Created by vesel on 01.11.2015.
 */
public class BaseLight extends GameComponent {

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

    public Vector3 getIntesifiedColor() {
        return new Vector3(mColor).mul(mIntensity);
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Shader shader) {

    }

    @Override
    public void update(Input input) {

    }

    @Override
    public void map() {
        addVector3("ambient", mColor);
        addFloat("intensity", mIntensity);
    }
}
