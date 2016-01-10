package engine;

import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.math.Matrix;
import engine.math.Vector2;
import engine.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vesel on 09.01.2016.
 */
public class GameObject extends MappedClass {

    private List<GameObject> mChildren;
    private List<GameComponent> mComponents;

    private HashMap<String, Float> mFloats;
    private HashMap<String, Integer> mIntegers;
    private HashMap<String, Vector2> mVector2s;
    private HashMap<String, Vector3> mVector3s;
    private HashMap<String, Matrix> mMatrices;

    private GameObject mParent;
    private boolean mShouldBeRemoved;
    private boolean mInitialized;

    public GameObject() {
        mChildren = new ArrayList<>();
        mComponents = new ArrayList<>();

        mFloats = new HashMap<>();
        mIntegers = new HashMap<>();
        mVector2s = new HashMap<>();
        mVector3s = new HashMap<>();
        mMatrices = new HashMap<>();
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

    public void setParent(GameObject parent) {
        mParent = parent;
    }

    public boolean isInitialized() {
        return mInitialized;
    }

    public void setInitialized(boolean initialized) {
        mInitialized = initialized;
    }

    public void addFloat(String name, float value) {
        mFloats.put(name, value);
    }

    public void addInteger(String name, int value) {
        mIntegers.put(name, value);
    }

    public void addVector2(String name, Vector2 value) {
        mVector2s.put(name, value);
    }

    public void addVector3(String name, Vector3 value) {
        mVector3s.put(name, value);
    }

    public void addMatrix(String name, Matrix value) {
        mMatrices.put(name, value);
    }

    public float getFloat(String name) {
        return mFloats.get(name);
    }

    public int getInteger(String name) {
        return mIntegers.get(name);
    }

    public Vector2 getVector2(String name) {
        return mVector2s.get(name);
    }

    public Vector3 getVector3(String name) {
        return mVector3s.get(name);
    }

    public Matrix getMatrix(String name) {
        return mMatrices.get(name);
    }

    @Override
    public void map() {

    }
}
