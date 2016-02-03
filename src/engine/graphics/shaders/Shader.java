package engine.graphics.shaders;

import engine.MappedClass;
import engine.graphics.Material;
import engine.math.Matrix;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.ContentLoader;
import engine.utils.Log;
import engine.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by vesel on 30.10.2015.
 * Represents a GLSL shader within the engine.
 */
public class Shader {

    public static final int FRAG = GL_FRAGMENT_SHADER, VERT = GL_VERTEX_SHADER;

    private int mProgramID;

    private HashMap<String, Integer> mUniformLocations;
    private List<String> mSources;

    private String mShaderName;

    public Shader() {
        mUniformLocations = new HashMap<>();
        mSources = new ArrayList<>();
        mProgramID = glCreateProgram();

        if(mProgramID == -1)
            Log.e("Failed to create shader program!");
    }

    public Shader(String shaderName) {
        this();
        mShaderName = shaderName;
        attachProgram(ContentLoader.loadString("shaders/" + shaderName + "_vertex.glsl"), Shader.VERT);
        attachProgram(ContentLoader.loadString("shaders/" + shaderName + "_fragment.glsl"), Shader.FRAG);
        compile();
    }

    public int getUniformLocation(String name) {
        return glGetUniformLocation(mProgramID, name);
    }

    public boolean registerUniform(String name) {
        if(!mUniformLocations.containsKey(name)) {
            int location = getUniformLocation(name);
            if(location == -1) {
                Log.w("Unused uniform '" + name + "' in shader '" + mShaderName + "'.");
                return false;
            }
            mUniformLocations.put(name, location);
        }
        else
            Log.w("An uniform variable has been registered twice. " +
                    "Even though this wan't cause any errors you should get rid of the unnecessary code.");

        return true;
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

        mSources.add(source);

        int shaderProgram = glCreateShader(type);
        glShaderSource(shaderProgram, source);
        glCompileShader(shaderProgram);

        if(glGetShaderi(shaderProgram, GL_COMPILE_STATUS) == 0) {
            Log.e("Failed to compile shader! Type: " + (type == VERT ? "VERTEX" : "FRAGMENT"));
            Log.e(glGetShaderInfoLog(shaderProgram));
        }

        glAttachShader(mProgramID, shaderProgram);
        glDeleteShader(shaderProgram);
    }

    public void compile() {
        glLinkProgram(mProgramID);
        glValidateProgram(mProgramID);

        for(String source : mSources) {
            int attribCount = 0;
            for (String s : source.split("\n")) {
                if (s.startsWith("uniform")) {
                    s = s.replace("uniform", "").replace(";", "").trim().split(" ")[1];
                    if(registerUniform(s)) {
                        Log.r("Added an uniform: " + s + " to shader: " + mShaderName);
                    }
                } else if(s.startsWith("attribute")) {
                    glBindAttribLocation(mProgramID, attribCount,
                            s.replace("attribute", "").replace(";", "").trim().split(" ")[1]);
                    attribCount++;
                }
            }
        }
    }

    /**
     * Updates the shader uniforms with values from a MappedClass.
     * @param map Mapped class with values for the Shader.
     */
    public void updateUniforms(MappedClass map) {
        /* Assign floats */
        for(String s : map.getFloats().keySet())
            if(mUniformLocations.containsKey(s))
                setUniform1f(s, map.getFloat(s));

        for(String s : map.getVector2s().keySet())
            if(mUniformLocations.containsKey(s))
                setUniform2f(s, map.getVector2(s));

        for(String s : map.getVector3s().keySet())
            if(mUniformLocations.containsKey(s))
                setUniform3f(s, map.getVector3(s));

        for(String s : map.getMatrices().keySet())
            if(mUniformLocations.containsKey(s))
                setUniformMatrix(s, map.getMatrix(s));

        for(String s : map.getIntegers().keySet())
            if(mUniformLocations.containsKey(s))
                setUniform1i(s, map.getInteger(s));

        /* Assign materials */
        for(String s : map.getTextures().keySet())
            if(mUniformLocations.containsKey(s))
                setUniform1i(s, map.getTexture(s).getID());

        for(String s : map.getMaterials().keySet())
            if(mUniformLocations.containsKey(s)) {
                setUniform1i(s + "_texture", map.getMaterial(s).getTexture().getID());
                setUniform1f(s + "_specular_intensity", map.getMaterial(s).getSpecularIntensity());
                setUniform1f(s + "_specular_exponent", map.getMaterial(s).getSpecularExponent());
                setUniform3f(s + "_color", map.getMaterial(s).getColor());
            }
    }

    public void bind() {
        glUseProgram(mProgramID);
    }

    public void unbind() {
        glUseProgram(0);
    }
}
