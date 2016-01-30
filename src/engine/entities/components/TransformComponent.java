package engine.entities.components;

import engine.entities.IDManager;
import engine.graphics.IRenderer;
import engine.graphics.Transform;
import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.math.Vector3;

/**
 * Created by vesel on 19.01.2016.
 * Component representing transformation of an Entity.
 */
public class TransformComponent extends EntityComponent {

    private Transform mTransform;

    public static final int ID = IDManager.getComponentID();

    public TransformComponent() {
        this(new Transform());
    }

    public TransformComponent(Vector3 position, Vector3 rotation, Vector3 scale) {
        this(new Transform(position, rotation, scale));
    }

    public TransformComponent(Vector3 position) {
        this(position, new Vector3(0), new Vector3(1));
    }

    public TransformComponent(Transform transform) {
        mTransform = transform;
        mComponentID = TransformComponent.ID;
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onRender(Shader shader, IRenderer renderer) {

    }

    @Override
    public void onUpdate(Input input) {
    }

    @Override
    public void onMap() {
        addMatrix("transformation_matrix", mTransform.createTransformationMatrix());
        addMatrix("world_matrix", mTransform.createWorldMatrix());
    }

    @Override
    public void onDelete() {
    }

    public void setPosition(float x, float y, float z) {
        mTransform.setPosition(x, y, z);
    }

    public void setPosition(Vector3 position) {
        mTransform.setPosition(position);
    }

    public void setRotation(float x, float y, float z) {
        mTransform.setRotation(x, y, z);
    }

    public void setRotation(Vector3 rotation) {
        mTransform.setRotation(rotation);
    }

    public void setScale(float x, float y, float z) {
        mTransform.setScale(x, y, z);
    }

    public void setScale(Vector3 scale) {
        mTransform.setScale(scale);
    }

    public Vector3 getPosition() {
        return mTransform.getPosition();
    }

    public Vector3 getRotation() {
        return mTransform.getRotation();
    }

    public Vector3 getScale() {
        return mTransform.getScale();
    }

    public Transform getTransform() {
        return mTransform;
    }
}
