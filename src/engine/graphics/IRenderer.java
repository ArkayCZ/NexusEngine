package engine.graphics;

import engine.entities.Entity;
import engine.graphics.shaders.lighting.BaseLight;
import engine.graphics.shaders.lighting.DirectionalLight;
import engine.graphics.shaders.lighting.PointLight;
import engine.graphics.shaders.lighting.SpotLight;
import engine.math.Matrix;

/**
 * Created by vesel on 24.01.2016.
 */
public interface IRenderer {

    /* General functions */
    void setProjection(Matrix matrix);
    void clear();
    void submit(Entity e);
    void flush();
    void setAmbientLight(BaseLight ambientLight);
    void setDirectionalLight(DirectionalLight light);
    void addPointLight(PointLight light);
    void addSpotLight(SpotLight light);

}
