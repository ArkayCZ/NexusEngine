package engine.graphics.shaders;

import engine.graphics.*;
import engine.graphics.shaders.lighting.BaseLight;
import engine.graphics.shaders.lighting.DirectionalLight;
import engine.graphics.shaders.lighting.PointLight;
import engine.graphics.shaders.lighting.SpotLight;
import engine.math.Matrix4;
import engine.math.Vector3;
import engine.utils.ContentLoader;
import engine.utils.Log;

/**
 * Created by vesel on 01.11.2015.
 */
public class PhongShader extends Shader {

    //TODO: Make sure the shader file name always matches the class name.

    private static final int MAX_POINT_LIGHTS = 4;
    private static final int MAX_SPOT_LIGHTS = 4;

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

    private static PointLight[] sPointLights = new PointLight[MAX_POINT_LIGHTS];
    private static SpotLight[] sSpotLights = new SpotLight[MAX_SPOT_LIGHTS];

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

        for(int i = 0; i < MAX_POINT_LIGHTS; i++) {
            registerUniform("pointLights[" + i + "].baseLight.color");
            registerUniform("pointLights[" + i + "].baseLight.intensity");
            registerUniform("pointLights[" + i + "].attenuation.absolute");
            registerUniform("pointLights[" + i + "].attenuation.linear");
            registerUniform("pointLights[" + i + "].attenuation.quadratical");
            registerUniform("pointLights[" + i + "].position");
            registerUniform("pointLights[" + i + "].range");
        }

        for(int i = 0; i < MAX_POINT_LIGHTS; i++) {
            registerUniform("spotLights[" + i + "].pointLight.baseLight.color");
            registerUniform("spotLights[" + i + "].pointLight.baseLight.intensity");
            registerUniform("spotLights[" + i + "].pointLight.attenuation.absolute");
            registerUniform("spotLights[" + i + "].pointLight.attenuation.linear");
            registerUniform("spotLights[" + i + "].pointLight.attenuation.quadratical");
            registerUniform("spotLights[" + i + "].pointLight.position");
            registerUniform("spotLights[" + i + "].pointLight.range");

            registerUniform("spotLights[" + i + "].length");
            registerUniform("spotLights[" + i + "].direction");
        }
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

        for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
            setUniformPL("pointLights[" + i + "]", sPointLights[i]);
        }

        for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
            setUniformSL("spotLights[" + i + "]", sSpotLights[i]);
        }

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

    public void setUniformPL(String name, PointLight light) {
        if(light == null) return;
        setUniformBL(name + ".baseLight", light.getBaseLight());
        setUniform1f(name + ".attenuation.absolute", light.getAttenuation().getAbsolute());
        setUniform1f(name + ".attenuation.linear", light.getAttenuation().getLinear());
        setUniform1f(name + ".attenuation.quadratical", light.getAttenuation().getQuadratical());
        setUniform3f(name + ".position", light.getPosition());
        setUniform1f(name + ".range", light.getRange());
    }

    public void setUniformSL(String name, SpotLight light) {
        if(light == null) return;
        setUniformPL(name + ".pointLight", light.getPointLight());
        setUniform3f(name + ".direction", light.getDirection());
        setUniform1f(name + ".length", light.getLength());
    }

    public static Vector3 getAmbientLight() { return sAmbientLight; }
    public static void setAmbientLight(Vector3 ambientLight) { sAmbientLight = ambientLight; }

    public static DirectionalLight getDirectionalLight() {
        return sDirectionalLight;
    }
    public static void setDirectionalLight(DirectionalLight directionalLight) {
        sDirectionalLight = directionalLight;
    }

    public static void addPointLight(PointLight light) {
        for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
            if(sPointLights[i] == null) {
                sPointLights[i] = light;
                return;
            }
        }

        Log.e("Cannot add any more point lights! The maximum amount is 4!");
        System.exit(-1);
    }

    public static void addSpotLight(SpotLight spotLight) {
        for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
            if(sSpotLights[i] == null) {
                sSpotLights[i] = spotLight;
                return;
            }
        }

        Log.e("Cannot add any more spotlights! The maximum amount is 4!");
        System.exit(-1);
    }
}
