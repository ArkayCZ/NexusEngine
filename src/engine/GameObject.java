package engine;

import engine.components.GameComponent;
import engine.graphics.MatrixTransformation;
import engine.graphics.shaders.Shader;
import engine.input.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vesel on 09.01.2016.
 */
public class GameObject extends MappedClass {

    private List<GameObject> mChildren;
    private List<GameComponent> mComponents;

    private GameObject mParent;
    private MatrixTransformation mTransform;

    private boolean mShouldBeRemoved;
    private boolean mInitialized;

    public GameObject() {
        super();
        mChildren = new ArrayList<>();
        mComponents = new ArrayList<>();
        mTransform = new MatrixTransformation();
    }

    @Override
    public void map() {

    }

    public void addChild(GameObject child) {
        mChildren.add(child);
        child.setParent(this);

        if(!child.isInitialized())
            child.init();
    }

    public void addComponent(GameComponent comp) {
        mComponents.add(comp);
        comp.setParentObject(this);

        if(!comp.isInitialized())
            comp.init();
    }

    public void markForRemoval() {
        for(GameObject object : mChildren)
            object.markForRemoval();

        for(GameComponent comp : mComponents)
            comp.markForRemoval();

        mShouldBeRemoved = true;
    }

    public boolean shouldBeRemoved() {
        return mShouldBeRemoved;
    }

    public void init() {
        for(GameObject child : mChildren)
            child.init();

        for(GameComponent comp : mComponents)
            comp.init();

        setInitialized(true);
    }

    public void update(Input input) {


        for (int i = 0; i < mChildren.size(); i++) {
            GameObject child = mChildren.get(i);
            if(!child.shouldBeRemoved())
                child.update(input);
            else
                mChildren.remove(i);
        }

        for (int i = 0; i < mComponents.size(); i++) {
            GameComponent comp = mComponents.get(i);
            if(!comp.shouldBeRemoved())
                comp.update(input);
            else
                mComponents.remove(i);
        }
    }

    public void render(Shader shader) {
        for(GameObject child : mChildren)
            child.render(shader);

        for(GameComponent comp : mComponents)
            comp.render(shader);
    }

    public void onDelete() {

    }

    public MatrixTransformation getTransform() {
        return mTransform;
    }

    public void setParent(GameObject parent) {
        mParent = parent;
    }

    public boolean isInitialized() {
        return mInitialized;
    }

    public void setInitialized(boolean initialized) {
        mInitialized = initialized;
    }
}
