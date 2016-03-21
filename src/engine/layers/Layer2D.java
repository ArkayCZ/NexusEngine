package engine.layers;

import engine.graphics.Camera;
import engine.graphics.IRenderer;
import engine.graphics.Transform;
import engine.graphics.window.Window;
import engine.math.Matrix;

import javax.xml.crypto.dsig.TransformService;

/**
 * Created by vesel on 26.01.2016.
 */
public class Layer2D extends Layer
{

    private Camera mCamera;

    public Layer2D(IRenderer renderer, Matrix projection, Window window)
    {
        super(renderer, projection, window);
        mCamera = new Camera();
    }

    public Layer2D(Matrix projection, Window window)
    {
        super(projection, window);
        mCamera = new Camera();
    }

    @Override
    public void onRender()
    {
        Transform.setProjection(getProjection());
        Transform.setCamera(mCamera);
        super.onRender();
    }
}
