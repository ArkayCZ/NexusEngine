package engine.components;

import engine.GameObject;
import engine.MappedClass;
import engine.graphics.shaders.Shader;
import engine.input.Input;

/**
 * Created by vesel on 09.01.2016.
 */
public abstract class GameComponent extends MappedClass {

    private boolean mShouldBeRemoved;
    private boolean mInitialized;
    private GameObject mParentObject;

    public abstract void init();
    public abstract void render(Shader shader);
    public abstract void update(Input input);

    public void setParentObject(GameObject parent) {
        mParentObject = parent;
    }

    public boolean isInitialized() {
        return mInitialized;
    }

    public void setInitialized(boolean initialized) {
        mInitialized = initialized;
    }

    public GameObject getParentObject() {
        return mParentObject;
    }

    public boolean shouldBeRemoved() {
        return mShouldBeRemoved;
    }

    public void markForRemoval() {
        mShouldBeRemoved = true;
    }
}
