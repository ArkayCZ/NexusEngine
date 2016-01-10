package engine.graphics;

import engine.GameObject;
import engine.graphics.shaders.BasicShader;
import engine.graphics.shaders.Shader;
import engine.graphics.shaders.lighting.BaseLight;
import engine.graphics.shaders.lighting.DirectionalLight;
import engine.math.Vector3;
import engine.utils.Log;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created by vesel on 09.01.2016.
 */
public class ForwardRenderer {

    private Camera mMainCamera;

    private BaseLight mAmbientLight;
    private DirectionalLight mDirectionalLight;

    public void render(GameObject object) {
        object.render(BasicShader.getInstance());
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
    }

    public Vector3 getAmbient() {
        return mAmbientLight.getIntesifiedColor();
    }

    public void setClearColor(Vector3 color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

}
