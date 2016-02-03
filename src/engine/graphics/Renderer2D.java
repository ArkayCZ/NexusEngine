package engine.graphics;

import engine.MappedClass;
import engine.entities.Entity;
import engine.graphics.font.Font;
import engine.graphics.shaders.Shader;
import engine.graphics.shaders.lighting.BaseLight;
import engine.graphics.shaders.lighting.DirectionalLight;
import engine.graphics.shaders.lighting.PointLight;
import engine.graphics.shaders.lighting.SpotLight;
import engine.math.Matrix;
import engine.math.Vector2;
import engine.utils.Log;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by vesel on 31.01.2016.
 */
public class Renderer2D extends MappedClass implements IRenderer2D {

    private Queue<Entity> mEntityStack;
    private Shader mShader;

    public Renderer2D() {
        mEntityStack = new LinkedBlockingQueue<>();
        mShader = new Shader("basic");
    }

    @Override
    public void clear() {

    }

    @Override
    public void submit(Entity e) {
        mEntityStack.add(e);
    }

    @Override
    public void flush() {
        while(mEntityStack.peek() != null) {
            Entity e = mEntityStack.poll();

            this.onMap();
            e.onMap();

            mShader.bind();
            mShader.updateUniforms(this);
            e.onRender(mShader, this);
            mShader.unbind();
        }
    }

    @Override
    public void onMap() {

    }

    @Override
    public void drawRectangle(float x, float y, float width, float height, int color) {

    }

    @Override
    public void drawRectangle(Vector2 start, Vector2 size, int color) {

    }

    @Override
    public void drawString(float x, float y, Font font, String s) {

    }

    @Override
    public void drawLine(Vector2 origin, Vector2 target, int color) {

    }

    @Override
    public void setProjection(Matrix matrix) {

    }

    @Override
    public void setAmbientLight(BaseLight ambientLight) {

    }

    @Override
    public void setDirectionalLight(DirectionalLight light) {

    }

    @Override
    public void addPointLight(PointLight light) {

    }

    @Override
    public void addSpotLight(SpotLight light) {

    }
}
