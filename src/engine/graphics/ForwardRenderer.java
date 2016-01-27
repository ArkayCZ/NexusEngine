package engine.graphics;

import engine.entities.Entity;
import engine.MappedClass;
import engine.graphics.shaders.Shader;
import engine.graphics.shaders.lighting.*;
import engine.math.Maths;
import engine.math.Matrix;
import engine.math.Vector3;
import engine.utils.ContentLoader;
import engine.utils.Log;
import engine.utils.Settings;
import org.lwjgl.opengl.GL;

import java.lang.invoke.VolatileCallSite;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created by vesel on 09.01.2016.
 * Renderer based on ForwardRendering mechanics. All shaders are in forward/ folder.
 */
public class ForwardRenderer extends MappedClass implements IRenderer {

    private BaseLight mAmbientLight;
    private DirectionalLight mDirectionalLight;
    private List<PointLight> mPointLights;
    private List<SpotLight> mSpotLights;

    private Shader mAmbientShader;
    private Shader mDirectionalShader;
    private Shader mPointShader;
    private Shader mSpotShader;

    private Queue<Entity> mEntityStack;

    public ForwardRenderer() {
        /* Initialize super-class */
        super();
        mAmbientLight = new BaseLight(new Vector3(1, 1, 1), 0.2f);
        mDirectionalLight = new DirectionalLight(new Vector3(1f, 1f, 1f), 0.6f, new Vector3(-1, 1, -1));
        mPointLights = new ArrayList<>();
        mSpotLights = new ArrayList<>();

        mEntityStack = new LinkedBlockingQueue<>();
        this.initOpenGL();
    }

    @Override
    public void onMap() {
        addVector3("ambient", mAmbientLight.getIntesifiedColor());
        addVector3("camera_position", Transform.getCamera().getPosition());
        addVector3("camera_direction", Transform.getCamera().getForward());
    }

    @Override
    public void setProjection(Matrix matrix) {
        Transform.setProjection(matrix);
    }

    @Override
    public void clear() {
        mSpotLights.clear();
        mPointLights.clear();
    }

    @Override
    public void submit(Entity e) {
        mEntityStack.add(e);
    }

    public void flush() {
        if(mEntityStack.isEmpty()) return;
        while(mEntityStack.peek() != null)
            render(mEntityStack.poll());
    }

    public void render(Entity object) {
        this.onMap();
        object.onMap();
        mAmbientShader.bind();
        mAmbientShader.updateUniforms(this);
        object.onRender(mAmbientShader, this);
        mAmbientShader.unbind();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        if(mDirectionalLight != null) {
            mDirectionalShader.bind();
            mDirectionalShader.updateUniforms(this);

            mDirectionalShader.setUniform3f("light_color", mDirectionalLight.getColor());
            mDirectionalShader.setUniform3f("light_direction", mDirectionalLight.getDirection());
            mDirectionalShader.setUniform1f("light_intensity", mDirectionalLight.getIntensity());

            object.onRender(mDirectionalShader, this);
            mDirectionalShader.unbind();
        }

        mPointShader.bind();
        for(PointLight light : mPointLights) {
            mPointShader.setUniform3f("light_color", light.getColor());
            mPointShader.setUniform3f("light_attenuation", light.getAttenuation().toVector3());
            mPointShader.setUniform3f("light_position", light.getPosition());
            mPointShader.setUniform1f("light_range", light.getRange());
            mPointShader.setUniform1f("light_intensity", light.getIntensity());

            mPointShader.updateUniforms(this);
            object.onRender(mPointShader, this);
        }
        mPointShader.unbind();

        mSpotShader.bind();
        for(SpotLight light : mSpotLights) {
            mSpotShader.setUniform3f("light_color", light.getColor());
            mSpotShader.setUniform3f("light_attenuation", light.getAttenuation().toVector3());
            mSpotShader.setUniform3f("light_position", light.getPosition());
            mSpotShader.setUniform1f("light_range", light.getRange());
            mSpotShader.setUniform1f("light_intensity", light.getIntensity());
            mSpotShader.setUniform3f("light_direction", light.getDirection());
            mSpotShader.setUniform1f("light_length", light.getLength());

            mSpotShader.updateUniforms(this);
            object.onRender(mSpotShader, this);
        }
        mSpotShader.unbind();


        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public void initOpenGL() {
        /* Link LWJGL to GLFW context */
        GL.createCapabilities();

        /* Print the OpenGL version to console. */
        Log.i(glGetString(GL_VERSION));

        /* Basic OpenGL settings. */
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_BLEND);

        /* Initialize shaders */
        mAmbientShader = new Shader("forward/ambient");
        mDirectionalShader = new Shader("forward/directional");
        mPointShader = new Shader("forward/point");
        mSpotShader = new Shader("forward/spot");
    }

    public Vector3 getAmbient() {
        return mAmbientLight.getIntesifiedColor();
    }

    public void setClearColor(Vector3 color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    public List<PointLight> getPointLights() {
        return mPointLights;
    }

    public void setPointLights(List<PointLight> pointLights) {
        mPointLights = pointLights;
    }

    public List<SpotLight> getSpotLights() {
        return mSpotLights;
    }

    public void setSpotLights(List<SpotLight> spotLights) {
        mSpotLights = spotLights;
    }

    public DirectionalLight getDirectionalLight() {
        return mDirectionalLight;
    }

    @Override
    public void setAmbientLight(BaseLight ambientLight) {
        mAmbientLight = ambientLight;
    }

    public void setDirectionalLight(DirectionalLight directionalLight) {
        mDirectionalLight = directionalLight;
    }

    @Override
    public void addPointLight(PointLight light) {
        mPointLights.add(light);
    }

    @Override
    public void addSpotLight(SpotLight light) {
        mSpotLights.add(light);
    }

    public BaseLight getAmbientLight() {
        return mAmbientLight;
    }

}
