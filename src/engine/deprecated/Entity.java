package engine.deprecated;

import engine.graphics.*;
import engine.input.Input;
import engine.math.Vector3;
import game.wolf3dgame.Level;

/**
 * Created by filip on 3.11.15.
 */
public abstract class Entity {

    private Renderable mRenderable;
    private Level mContext;

    public Entity(Renderable renderable, Level context) {
        mRenderable = renderable;
        if(mRenderable.getTransformation() == null)
            mRenderable.setTransformation(new Transform());

        mContext = context;
    }

    public void render(BufferedRenderer engine) {
        engine.submit(getRenderable());
    }

    public abstract void update(Input inputStatus);

    public void setTranslation(Vector3 translationVector) {
        getRenderable().getTransformation().setPosition(translationVector);
    }

    public void setTranslation(float x, float y, float z) {
        setTranslation(new Vector3(x, y, z));
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

    public Level getContext() {return mContext;}

    public Vector3 getTranslation() {
        return getRenderable().getTransformation().getPosition();
    }

}
