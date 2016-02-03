package engine.entities.components;

import engine.entities.IDManager;
import engine.graphics.*;
import engine.graphics.shaders.Shader;
import engine.input.Input;

public class RenderComponent extends EntityComponent {

    /** Contains the mesh required to render the component */
    private Mesh mMesh;
    /** Contains the material required to render the component */
    private Material mMaterial;

    public static final int ID = IDManager.getComponentID();

    public RenderComponent(Mesh mesh, Material material) {
        mMesh = mesh;
        mMaterial = material;
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onRender(Shader shader, IRenderer renderer) {
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

        if(Transform.getCamera() != null) {
            addVector3("camera_position", Transform.getCamera().getPosition());
            addVector3("camera_direction", Transform.getCamera().getForward());
        }
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
