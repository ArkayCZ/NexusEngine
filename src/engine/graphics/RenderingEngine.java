package engine.graphics;

import engine.graphics.shaders.BasicShader;
import engine.graphics.shaders.Shader;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by filip on 3.11.15.
 */
public class RenderingEngine {

    private Queue<Renderable> mRenderables;

    public RenderingEngine() {
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
            MatrixTransformation transform = renderable.getTransformation();
            Material material = renderable.getMaterial();

            if(shader == null)
                shader = BasicShader.getInstance();

            shader.updateUniforms(
                    transform.createTransformationMatrix(),
                    transform.getProjectionMatrix(),
                    material);

            shader.bind();
            mesh.render();
            shader.unbind();
        }
    }
}
