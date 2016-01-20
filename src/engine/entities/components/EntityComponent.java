package engine.entities.components;

import engine.entities.Entity;
import engine.graphics.Material;
import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.math.Matrix;
import engine.math.Quaternion;
import engine.math.Vector2;
import engine.math.Vector3;

/**
 * Created by vesel on 09.01.2016.
 */
public abstract class EntityComponent {

    private boolean mShouldBeRemoved;
    private boolean mInitialized;
    private Entity mParentObject;

    public abstract void onInit();
    public abstract void onRender(Shader shader);
    public abstract void onUpdate(Input input);
    public abstract void onMap();
    public abstract void onDelete();

    public void setParentObject(Entity parent) {
        mParentObject = parent;
    }

    public boolean isInitialized() {
        return mInitialized;
    }

    public void setInitialized(boolean initialized) {
        mInitialized = initialized;
    }

    public Entity getParentObject() {
        return mParentObject;
    }

    public boolean shouldBeRemoved() {
        return mShouldBeRemoved;
    }

    public void markForRemoval() {
        mShouldBeRemoved = true;
    }

    public void addFloat(String name, float value) {
        getParentObject().addFloat(name, value);
    }

    public void addVector2(String name, Vector2 vector) {
        getParentObject().addVector2(name, vector);
    }

    public void addVector3(String name, Vector3 vector) {
        getParentObject().addVector3(name, vector);
    }

    public void addMatrix(String name, Matrix matrix) {
        getParentObject().addMatrix(name, matrix);
    }

    public void addMaterial(String name, Material material) {
        getParentObject().addMaterial(name, material);
    }

    public void addQuaternion(String name, Quaternion quat) {
        getParentObject().addQuaternion(name, quat);
    }

    public void addInteger(String name, int value) {
        getParentObject().addInteger(name, value);
    }

    public float getFloat(String name) {
        return getParentObject().getFloat(name);
    }

    public Vector2 getVector2(String name) {
        return getParentObject().getVector2(name);
    }

    public Vector3 getVector3(String name) {
        return getParentObject().getVector3(name);
    }

    public Matrix getMatrix(String name) {
        return getParentObject().getMatrix(name);
    }

    public Material getMaterial(String name) {
        return getParentObject().getMaterial(name);
    }

    public Quaternion getQuaternion(String name) {
        return getParentObject().getQuaternion(name);
    }

    public int getInteger(String name) {
        return getParentObject().getInteger(name);
    }
}
