package graphics;

import math.Matrix4;
import math.Vector2;
import math.Vector3;
import utils.Log;
import utils.Utils;

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
            uniformLocations.put(name, location);

            return location;
        }
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

    public void setUniformMatrix(String name, Matrix4 matrix) {
        glUniformMatrix4fv(getUniformLocation(name), true, Utils.createFloatBuffer(matrix.getData()));
    }

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

    public void bind() {
        glUseProgram(mProgramID);
    }

    public void unbind() {
        glUseProgram(0);
    }

}
