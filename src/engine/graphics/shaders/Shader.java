package engine.graphics.shaders;

import engine.graphics.Material;
import engine.graphics.MatrixTransformation;
import engine.math.Matrix;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.Log;
import engine.utils.Utils;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by vesel on 30.10.2015.
 */
public class Shader {

    public static final int FRAG = GL_FRAGMENT_SHADER, VERT = GL_VERTEX_SHADER;

    private int mProgramID;

    private HashMap<String, Integer> uniformLocations;

    public Shader() {
        uniformLocations = new HashMap<>();
        mProgramID = glCreateProgram();

        if(mProgramID == -1)
            Log.e("Failed to create shader program!");
    }

    public int getUniformLocation(String name) {
        if(uniformLocations.containsKey(name))
            return uniformLocations.get(name);
        else {
            int location = glGetUniformLocation(mProgramID, name);
            if(location == -1) {
                Log.w("Failed to find uniform variable '" + name + "'! Check your uniform names, there is possibly a typo.");
            }
            uniformLocations.put(name, location);

            return location;
        }
    }

    public void registerUniform(String name) {
        if(!uniformLocations.containsKey(name))
            uniformLocations.put(name, getUniformLocation(name));
        else
            Log.w("An uniform variable has been registered twice. " +
                    "Even thought this wan't cause any errors you should get rid of the unnecessary code.");
    }

    public void setAttributeLocation(String attrib, int location) {
        glBindAttribLocation(mProgramID, location, attrib);
    }

    public void setUniform1i(String name, int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    public void setUniform1f(String name, float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform2f(String name, Vector2 value) {
        glUniform2f(getUniformLocation(name), value.getX(), value.getY());
    }

    public void setUniform3f(String name, Vector3 value)  {
        glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }

    public void setUniformMatrix(String name, Matrix matrix) {
        glUniformMatrix4fv(getUniformLocation(name), true, Utils.createFloatBuffer(matrix.getData()));
    }

    /**
     * Adds a shader to the OpenGL program.
     * @param source Source code of the shader program! NOT the path. You can use ContentLoader to load a file as a String.
     * @param type Shader type, must be a static constant in the Shader class.
     */
    public void attachProgram(String source, int type) {
        if(type != FRAG && type != VERT) {
            Log.e("Unknown program type!");
            System.exit(-1);
        }

        int shaderProgram = glCreateShader(type);
        glShaderSource(shaderProgram, source);
        glCompileShader(shaderProgram);

        if(glGetShaderi(shaderProgram, GL_COMPILE_STATUS) == 0) {
            Log.e("Failed to compile shader! Type: " + type);
            Log.e(glGetShaderInfoLog(shaderProgram));
            System.exit(-1);
        }

        glAttachShader(mProgramID, shaderProgram);
        glDeleteShader(shaderProgram);
    }

    public void compile() {
        glLinkProgram(mProgramID);
        glValidateProgram(mProgramID);
    }

    public void updateUniforms(MatrixTransformation transform, Material material) {

    }

    public void bind() {
        glUseProgram(mProgramID);
    }

    public void unbind() {
        glUseProgram(0);
    }

}
