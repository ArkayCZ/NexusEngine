package engine;

import engine.graphics.Material;
import engine.graphics.MatrixTransformation;
import engine.graphics.Mesh;
import engine.graphics.Renderable;

/**
 * Created by filip on 3.11.15.
 */
public class GameObject {

    private Renderable mRenderable;
    private Material mMaterial;
    //private

    public GameObject(Renderable renderable, Material material) {
        mMaterial = material;
    }

    public Renderable getRenderable() {
        return mRenderable;
    }

    public void setRenderable(Renderable renderable) {
        mRenderable = renderable;
    }

    public Material getMaterial() {
        return mMaterial;
    }

    public void setMaterial(Material material) {
        mMaterial = material;
    }
}
