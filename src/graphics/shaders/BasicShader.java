package graphics.shaders;

import graphics.Material;
import graphics.Texture;
import graphics.shaders.Shader;
import math.Matrix4;
import math.Vector3;
import utils.ContentLoader;

/**
 * Created by vesel on 01.11.2015.
 */
public class BasicShader extends Shader {

    //TODO: Make sure the shader file name always matches the class name.

    private final String UNIFORM_WORLD_MATRIX = "worldMatrix";
    private final String UNIFORM_PROJECTION_MATRIX = "projectionMatrix";
    private final String UNIFORM_COLOR_VEC3 = "color";

    public BasicShader() {
        super();

        attachProgram(ContentLoader.readFileAsString("shaders/sprite_fragment.glsl"), Shader.FRAG);
        attachProgram(ContentLoader.readFileAsString("shaders/sprite_vertex.glsl"), Shader.VERT);

        compile();

        registerUniform(UNIFORM_PROJECTION_MATRIX);
        registerUniform(UNIFORM_COLOR_VEC3);
        registerUniform(UNIFORM_WORLD_MATRIX);
    }

    @Override
    public void updateTransformations(Matrix4 worldMatrix, Matrix4 projectionMatrix, Material material) {
        //super.updateTransformations(worldMatrix, projectionMatrix, material);

        if(material.getTexture() == null)
            Texture.unbind();
        else material.getTexture().bind();

        bind();
        setUniformMatrix(UNIFORM_PROJECTION_MATRIX, projectionMatrix);
        setUniformMatrix(UNIFORM_WORLD_MATRIX, worldMatrix);
        setUniform3f(UNIFORM_COLOR_VEC3, material.getColor());
        unbind();
    }
}
