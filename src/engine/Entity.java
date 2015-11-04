package engine;

import engine.graphics.*;
import engine.input.Input;
import engine.math.Vector3;
import game.Level;

/**
 * Created by filip on 3.11.15.
 */
public abstract class Entity {

    private Renderable mRenderable;
    private Level mContext;

    public Entity(Renderable renderable, Level context) {
        mContext = context;
        mRenderable = renderable;
        if(mRenderable.getTransformation() == null)
            mRenderable.setTransformation(new MatrixTransformation());
    }

    public void render(RenderingEngine engine) {
        engine.submit(getRenderable());
    }

    public abstract void update(Input inputStatus);

    public Level getContext() {
        return mContext;
    }

    public void setTranslation(Vector3 translationVector) {
        getRenderable().getTransformation().setTranslation(translationVector);
    }

    public void setTranslation(float x, float y, float z) {
        setTranslation(new Vector3(x, y, z));
    }

    public Vector3 getTranslation() {
        return getRenderable().getTransformation().mPosition;
    }

    public Vector3 getRotation() {
        return getRenderable().getTransformation().mRotation;
    }

    public void setRotation(Vector3 rotation) {
        getRenderable().getTransformation().setRotation(rotation);
    }

    public void setRotation(float x, float y, float z) {
        setRotation(new Vector3(x, y, z));
    }

    public void setScale(Vector3 scale) {
        getRenderable().getTransformation().setScale(scale);
    }

    public void setScale(float x, float y, float z) {
        setScale(new Vector3(x, y, z));
    }

    public Renderable getRenderable() {
        return mRenderable;
    }

    public void setRenderable(Renderable renderable) {
        mRenderable = renderable;
    }


}
