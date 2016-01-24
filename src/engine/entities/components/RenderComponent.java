package engine.entities.components;

import engine.graphics.ForwardRenderer;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.utils.Log;

public class RenderComponent extends EntityComponent {

    /** Contains the mesh required to render the component */
    private Mesh mMesh;
    /** Contains the material required to render the component */
    private Material mMaterial;

    public static final int ID = IDManager.getID();

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
        getMaterial().getTexture().bind();
        mMesh.render();
    }

    @Override
    public void onUpdate(Input input) {

    }

    @Override
    public void onMap() {
        addMaterial("material", getMaterial());
        addVector3("camera_position", ForwardRenderer.getCamera().getPosition());
        addVector3("camera_direction", ForwardRenderer.getCamera().getForward());
        addFloat("specular_intensity", getMaterial().getSpecularIntensity());
        addFloat("specular_exponent", getMaterial().getSpecularExponent());
        addVector3("material_color", getMaterial().getColor());
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
