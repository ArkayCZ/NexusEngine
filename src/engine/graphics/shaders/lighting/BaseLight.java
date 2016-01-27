package engine.graphics.shaders.lighting;

import engine.entities.IDManager;
import engine.entities.components.EntityComponent;
import engine.graphics.IRenderer;
import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.math.Vector3;

/**
 * Created by vesel on 01.11.2015.
 */
public class BaseLight extends EntityComponent {

    private Vector3 mColor;
    private float mIntensity;

    public static final int ID = IDManager.getComponentID();

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
    public void onInit() {

    }

    @Override
    public void onRender(Shader shader, IRenderer renderer) {
        renderer.setAmbientLight(this);
    }

    @Override
    public void onUpdate(Input input) {

    }

    @Override
    public void onMap() {

    }

    @Override
    public void onDelete() {

    }
}
