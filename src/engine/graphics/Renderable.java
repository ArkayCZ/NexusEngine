package engine.graphics;

import engine.graphics.shaders.Shader;

/**
 * Created by filip on 3.11.15.
 */
public class Renderable {

    private Mesh mMesh;
    private Shader mShader;
    private Transform mTransformation;
    private Material mMaterial;

    public Renderable(Mesh mesh, Shader shader, Transform transformation, Material material) {
        mMesh = mesh;
        mShader = shader;
        mTransformation = transformation;
        mMaterial = material;
    }

    public Renderable() {
        this(null, null, null, null);
    }

    public Renderable(Material mat) {
        this(null, null, null, mat);
    }

    public Renderable(Transform transform, Material material) {
        this(null, null, transform, material);
    }

    public Mesh getMesh() {
        return mMesh;
    }

    public void setMesh(Mesh mesh) {
        mMesh = mesh;
    }

    public Shader getShader() {
        return mShader;
    }

    public void setShader(Shader shader) {
        mShader = shader;
    }

    public Transform getTransformation() {
        return mTransformation;
    }

    public void setTransformation(Transform transformation) {
        mTransformation = transformation;
    }

    public Material getMaterial() {
        return mMaterial;
    }

    public void setMaterial(Material material) {
        mMaterial = material;
    }

    public void initTransformation() {
        mTransformation = new Transform();
    }

    public void render() {

    }


}
