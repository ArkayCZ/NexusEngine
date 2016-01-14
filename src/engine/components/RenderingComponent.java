package engine.components;

import engine.graphics.ForwardRenderer;
import engine.graphics.Material;
import engine.graphics.MatrixTransformation;
import engine.graphics.Mesh;
import engine.graphics.shaders.Shader;
import engine.input.Input;

public class RenderingComponent extends GameComponent {

    private Mesh mMesh;
    private Material mMaterial;

    public RenderingComponent(Mesh mesh, Material material) {
        mMesh = mesh;
        mMaterial = material;
    }

    @Override
    public void init() {
    }

    public void render(Shader shader) {
        this.map();
        shader.updateUniforms(this);
        shader.bind();
        getMaterial().getTexture().bind();
        mMesh.render();
        shader.unbind();
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

    @Override
    public void map() {
        addMaterial("material", mMaterial);
        addMatrix("world_matrix", getParentObject().getTransform().createWorldMatrix());
        addMatrix("transform_matrix", getParentObject().getTransform().createTransformationMatrix());
        addVector3("camera_pos", ForwardRenderer.getCamera().getPosition());
        addVector3("camera_dir", ForwardRenderer.getCamera().getForward());
    }
}
