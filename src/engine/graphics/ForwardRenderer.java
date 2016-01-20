package engine.graphics;

import engine.entities.Entity;
import engine.MappedClass;
import engine.graphics.shaders.Shader;
import engine.graphics.shaders.lighting.BaseLight;
import engine.graphics.shaders.lighting.DirectionalLight;
import engine.graphics.shaders.lighting.PointLight;
import engine.math.Vector3;
import engine.utils.ContentLoader;
import engine.utils.Log;
import org.lwjgl.opengl.GL;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created by vesel on 09.01.2016.
 */
public class ForwardRenderer extends MappedClass {

    private static Camera sMainCamera;

    private BaseLight mAmbientLight;
    private DirectionalLight mDirectionalLight;
    private List<PointLight> mPointLights;
    private List<DirectionalLight> mDirectionalLights;

    private Shader mAmbientShader;
    private Shader mDirectionalShader;
    private Shader mPointShader;
    private Shader mSpotShader;

    public ForwardRenderer() {
        /* Initialize super-class */
        super();
        mAmbientLight = new BaseLight(new Vector3(1, 1, 1), 0.2f);
    }

    @Override
    public void onMap() {
        addVector3("ambient", mAmbientLight.getIntesifiedColor());
        addVector3("camera_pos", getCamera().getPosition());
        addVector3("camera_dir", getCamera().getForward());
    }

    public void render(Entity object) {
        this.onMap();
        object.onMap();
        Transform.setCamera(sMainCamera);
        mAmbientShader.bind();
        mAmbientShader.updateUniforms(this);
        object.onRender(mAmbientShader);
        mAmbientShader.unbind();

        mDirectionalShader.bind();
        mDirectionalShader.updateUniforms(this);
        object.onRender(mDirectionalShader);
        mDirectionalShader.unbind();
    }

    public void initOpenGL() {
        GL.createCapabilities();

        Log.i(glGetString(GL_VERSION));

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_BLEND);

        /* Initialize ambient shader */
        mAmbientShader = new Shader();
        mAmbientShader.attachProgram(ContentLoader.readFileAsString("shaders/forward/ambient_fragment.glsl"), Shader.FRAG);
        mAmbientShader.attachProgram(ContentLoader.readFileAsString("shaders/forward/ambient_vertex.glsl"), Shader.VERT);
        mAmbientShader.compile();

        /* Initialize directional shader */
        mDirectionalShader = new Shader();
        mDirectionalShader.attachProgram(ContentLoader.readFileAsString("shaders/forward/directional_fragment.glsl"), Shader.FRAG);
        mDirectionalShader.attachProgram(ContentLoader.readFileAsString("shaders/forward/directional_vertex.glsl"), Shader.VERT);
        mDirectionalShader.compile();

        /* Initialize lights to their basic values. */

        /* Ambient light */
    }

    public Vector3 getAmbient() {
        return mAmbientLight.getIntesifiedColor();
    }

    public void setClearColor(Vector3 color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    public static Camera getCamera() {
        return sMainCamera;
    }

    public static void setCamera(Camera camera) {
        sMainCamera = camera;
    }

}
