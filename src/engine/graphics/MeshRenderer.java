package engine.graphics;

import engine.GameComponent;
import engine.GameObject;
import engine.graphics.shaders.BasicShader;
import engine.graphics.shaders.Shader;
import engine.input.Input;

/**
 * Created by vesel on 09.01.2016.
 */
public class MeshRenderer extends GameComponent {

    private Mesh mMesh;
    private Material mMaterial;
    private Shader mShader;
    private MatrixTransformation mTransformation;

    private GameObject mParentObject;

    public MeshRenderer(Mesh mesh, Material material, MatrixTransformation mTransformation) {
        mMesh = mesh;
        mMaterial = material;
        mShader = BasicShader.getInstance();
        mTransformation = new MatrixTransformation();
    }

    public MeshRenderer(Mesh mesh, Material material) {
        mMesh = mesh;
        mMaterial = material;
        mShader = BasicShader.getInstance();
        mTransformation = new MatrixTransformation();
    }

    public MeshRenderer(Mesh mesh, Material material, Shader shader, MatrixTransformation mTransformation) {
        mMesh = mesh;
        mMaterial = material;
        mShader = shader;
        mTransformation = new MatrixTransformation();
    }

    @Override
    public void init() {
    }

    public void render(Shader shader) {
        mShader = shader;
        mShader.updateUniforms(mTransformation, mMaterial);
        mShader.bind();
        mMesh.render();
        mShader.unbind();
    }

    @Override
    public void update(Input input) {

    }

    public Mesh getMesh() {
        return mMesh;
    }

    public void setMesh(Mesh mesh) {
        mMesh = mesh;
    }

    public Material getMaterial() {
        return mMaterial;
    }

    public void setMaterial(Material material) {
        mMaterial = material;
    }

    public Shader getShader() {
        return mShader;
    }

    public void setShader(Shader shader) {
        mShader = shader;
    }

    public MatrixTransformation getTransformation() {
        return mTransformation;
    }

    public void setTransformation(MatrixTransformation transformation) {
        mTransformation = transformation;
    }

    public GameObject getParentObject() {
        return mParentObject;
    }

    @Override
    public void setParentObject(GameObject parentObject) {
        mParentObject = parentObject;
    }
}
