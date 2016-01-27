package engine.layers;

import engine.graphics.Camera;
import engine.graphics.ForwardRenderer;
import engine.graphics.IRenderer;
import engine.graphics.Transform;
import engine.graphics.window.Window;
import engine.math.Matrix;

public class Scene extends Layer {

    private Camera mCamera;

    public Scene(IRenderer renderer, Matrix projection, Window window) {
        super(renderer, projection, window);
    }

    public Scene(Matrix projection, Window window) {
        super(projection, window);
        mCamera = new Camera();
    }

    @Override
    public void onRender() {
        Transform.setCamera(mCamera);
        Transform.setProjection(getProjection());
        super.onRender();
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
    }
}
