package engine;

import engine.graphics.*;
import engine.input.Input;
import engine.math.Vector3;

/**
 * Created by filip on 3.11.15.
 */
public abstract class Entity {

    private Renderable mRenderable;
    //private

    public Entity(Renderable renderable) {
        mRenderable = renderable;
        if(mRenderable.getTransformation() == null)
            mRenderable.setTransformation(new MatrixTransformation());


    }

    public void render(RenderingEngine engine) {
        engine.submit(getRenderable());
    }

    public abstract void update(Input inputStatus);

    public void setTranslation(Vector3 translationVector) {
        getRenderable().getTransformation().setTranslation(translationVector);
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


}
