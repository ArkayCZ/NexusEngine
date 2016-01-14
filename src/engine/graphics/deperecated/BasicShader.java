package engine.graphics.deperecated;

import engine.graphics.Material;
import engine.graphics.Texture;
import engine.graphics.shaders.Shader;
import engine.math.Matrix;
import engine.utils.ContentLoader;

/**
 * Created by vesel on 01.11.2015.
 */
public class BasicShader extends Shader {

    //TODO: Make sure the shader file name always matches the class name.

    private final String UNIFORM_WORLD_MATRIX = "worldMatrix";
    private final String UNIFORM_PROJECTION_MATRIX = "projectionMatrix";
    private final String UNIFORM_COLOR_VEC3 = "color";

    private static BasicShader sInstance;

    public static BasicShader getInstance() {
        if(sInstance != null)
            return sInstance;
        else {
            sInstance = new BasicShader();
            return sInstance;
        }
    }

    public BasicShader() {
        super();

        attachProgram(ContentLoader.readFileAsString("shaders/basic_fragment_120.glsl"), Shader.FRAG);
        attachProgram(ContentLoader.readFileAsString("shaders/basic_vertex_120.glsl"), Shader.VERT);

        compile();

        registerUniform(UNIFORM_PROJECTION_MATRIX);
        registerUniform(UNIFORM_COLOR_VEC3);
        //registerUniform(UNIFORM_WORLD_MATRIX);
    }

    public void updateUniforms(Matrix worldMatrix, Matrix projectionMatrix, Material material) {
        //super.updateUniforms(worldMatrix, projectionMatrix, material);

        if(material == null)
            return;
        if(material.getTexture() == null)
            Texture.unbind();
        else material.getTexture().bind();

        bind();
        setUniformMatrix(UNIFORM_PROJECTION_MATRIX, projectionMatrix);
        //setUniformMatrix(UNIFORM_WORLD_MATRIX, worldMatrix);
        setUniform3f(UNIFORM_COLOR_VEC3, material.getColor());
        unbind();
    }
}
