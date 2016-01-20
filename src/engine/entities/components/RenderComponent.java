package engine.entities.components;

import engine.graphics.ForwardRenderer;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.shaders.Shader;
import engine.input.Input;

public class RenderComponent extends EntityComponent {

    /** Contains the mesh required to render the component */
    private Mesh mMesh;
    /** Contains the material required to render the component */
    private Material mMaterial;

    public RenderComponent(Mesh mesh, Material material) {
        mMesh = mesh;
        mMaterial = material;
    }

    @Override
    public void onInit() {
        this.onMap();
    }

    public void onRender(Shader shader) {
        shader.updateUniforms(getParentObject());
        shader.bind();
        getMaterial().getTexture().bind();
        mMesh.render();
        shader.unbind();
    }

    @Override
    public void onUpdate(Input input) {

    }

    @Override
    public void onMap() {
        addMaterial("material", mMaterial);
        addVector3("camera_pos", ForwardRenderer.getCamera().getPosition());
        addVector3("camera_dir", ForwardRenderer.getCamera().getForward());
    }

    @Override
    public void onDelete() {

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

}
