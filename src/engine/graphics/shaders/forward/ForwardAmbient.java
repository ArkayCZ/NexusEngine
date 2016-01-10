package engine.graphics.shaders.forward;

import engine.graphics.Material;
import engine.graphics.MatrixTransformation;
import engine.graphics.shaders.Shader;
import engine.graphics.shaders.lighting.BaseLight;
import engine.math.Matrix;
import engine.utils.ContentLoader;

/**
 * Created by vesel on 10.01.2016.
 */
public class ForwardAmbient extends Shader {

    private static ForwardAmbient sInstance = new ForwardAmbient();

    public ForwardAmbient() {
        super();

        this.attachProgram(ContentLoader.readFileAsString("res/shaders/forward/ambient_vertex.glsl"), Shader.VERT);
        this.attachProgram(ContentLoader.readFileAsString("res/shaders/forward/ambient_fragment.glsl"), Shader.FRAG);

        this.setAttributeLocation("position", 0);
        this.setAttributeLocation("texture_coordinate", 1);

        this.compile();

        this.registerUniform("modelview_projected");
        this.registerUniform("ambient");
        this.registerUniform("texture");
    }

    public void updateUniforms(MatrixTransformation transform, Material material, BaseLight ambientLight) {
        super.updateUniforms(transform, material);

        Matrix worldMatrix = transform.createTransformationMatrix();
        Matrix projected = transform.getProjectionMatrix();
        material.getTexture().bind();

        setUniformMatrix("modelview_projected", projected);
        setUniform3f("ambient", material.getColor());
    }

    public ForwardAmbient getInstance() {
        if(sInstance == null)
            sInstance = new ForwardAmbient();

        return sInstance;
    }
}
