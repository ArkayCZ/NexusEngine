package engine.graphics;

import engine.graphics.shaders.Shader;
import engine.math.Vector3;
import engine.utils.Log;
import org.lwjgl.opengl.GL;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created by filip on 3.11.15.
 * Simple Buffered renderer.
 * @deprecated Slow.
 */
public class BufferedRenderer {

    private Queue<Renderable> mRenderables;

    public BufferedRenderer() {
        mRenderables = new LinkedBlockingQueue<>();
    }

    public void submit(Renderable renderable) {
        mRenderables.add(renderable);
    }

    public void flush() {
        while(mRenderables.peek() != null) {
            Renderable renderable = mRenderables.poll();

            Shader shader = renderable.getShader();
            Mesh mesh = renderable.getMesh();
            Transform transform = renderable.getTransformation();
            Material material = renderable.getMaterial();

            if(shader == null)
                shader = new Shader("basic_120");

            /*shader.updateUniforms(
                    transform.createTransformationMatrix(),
                    transform.createWorldMatrix(),
                    material);*/

            shader.bind();
            mesh.render();
            shader.unbind();
        }
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

        //glEnable(GL_FRAMEBUFFER_SRGB);
    }

    public void setClearColor(Vector3 color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }
}
