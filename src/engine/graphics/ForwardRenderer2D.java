package engine.graphics;

import engine.entities.Entity;
import engine.graphics.font.Font;
import engine.graphics.shaders.lighting.BaseLight;
import engine.graphics.shaders.lighting.DirectionalLight;
import engine.graphics.shaders.lighting.PointLight;
import engine.graphics.shaders.lighting.SpotLight;
import engine.math.Matrix;
import engine.math.Vector2;

/**
 * Created by vesel on 26.01.2016.
 */
public class ForwardRenderer2D implements IRenderer2D
{


    @Override
    public void drawRectangle(float x, float y, float width, float height, int color)
    {

    }

    @Override
    public void drawRectangle(Vector2 start, Vector2 size, int color)
    {

    }

    @Override
    public void drawString(float x, float y, Font font, String s)
    {

    }

    @Override
    public void drawLine(Vector2 origin, Vector2 target, int color)
    {

    }

    @Override
    public void setProjection(Matrix matrix)
    {

    }

    @Override
    public void clear()
    {

    }

    @Override
    public void submit(Entity e)
    {

    }

    @Override
    public void flush()
    {

    }

    @Override
    public void setAmbientLight(BaseLight ambientLight)
    {

    }

    @Override
    public void setDirectionalLight(DirectionalLight light)
    {

    }

    @Override
    public void addPointLight(PointLight light)
    {

    }

    @Override
    public void addSpotLight(SpotLight light)
    {

    }
}
