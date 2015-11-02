package graphics.shaders;

import graphics.*;
import math.Matrix4;
import math.Vector3;
import utils.ContentLoader;

import javax.xml.crypto.dsig.Transform;

/**
 * Created by vesel on 01.11.2015.
 */
public class PhongShader extends Shader {

    //TODO: Make sure the shader file name always matches the class name.

    private static PhongShader sInstance;
    public static PhongShader getInstance() {
        if(sInstance != null)
            return sInstance;
        else {
            sInstance = new PhongShader();
            return sInstance;
        }
    }

    private static Vector3 sAmbientLight = new Vector3(0.1f);
    private static DirectionalLight sDirectionalLight = new DirectionalLight(new BaseLight(new Vector3(0), 0), new Vector3(0));

    public PhongShader() {
        super();

        attachProgram(ContentLoader.readFileAsString("shaders/phong_fragment.glsl"), Shader.FRAG);
        attachProgram(ContentLoader.readFileAsString("shaders/phong_vertex.glsl"), Shader.VERT);

        compile();

        registerUniform("projectionMatrix");
        registerUniform("worldMatrix");
        registerUniform("baseColor");
        registerUniform("ambientLight");

        registerUniform("directionalLight.baseLight.color");
        registerUniform("directionalLight.baseLight.intensity");
        registerUniform("directionalLight.direction");

        registerUniform("specularIntensity");
        registerUniform("specularExponent");
        registerUniform("cameraPosition");
    }


    @Override
    public void updateUniforms(Matrix4 worldMatrix, Matrix4 projectionMatrix, Material material) {
        if(material.getTexture() == null)
            Texture.unbind();
        else material.getTexture().bind();

        bind();
        setUniformMatrix("projectionMatrix", projectionMatrix);
        setUniformMatrix("worldMatrix", worldMatrix);
        setUniform3f("baseColor", material.getColor());
        setUniform3f("ambientLight", getAmbientLight());
        setUniformDL("directionalLight", getDirectionalLight());

        setUniform1f("specularIntensity", material.getSpecularIntensity());
        setUniform1f("specularExponent", material.getSpecularExponent());

        setUniform3f("cameraPosition", MatrixTransformation.getCamera().getPosition());
        unbind();
    }

    //Uniform directional light
    public void setUniformDL(String name, DirectionalLight light) {
        setUniformBL(name + ".baseLight", light.getBaseLight());
        setUniform3f(name + ".direction", light.getDirection());
    }

    //Uniform base light
    public void setUniformBL(String name, BaseLight light) {
        setUniform3f(name + ".color", light.getColor());
        setUniform1f(name + ".intensity", light.getIntensity());
    }

    public static Vector3 getAmbientLight() { return sAmbientLight; }
    public static void setAmbientLight(Vector3 ambientLight) { sAmbientLight = ambientLight; }

    public static DirectionalLight getDirectionalLight() {
        return sDirectionalLight;
    }
    public static void setDirectionalLight(DirectionalLight directionalLight) {
        sDirectionalLight = directionalLight;
    }
}
