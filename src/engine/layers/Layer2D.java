package engine.layers;

import engine.graphics.IRenderer;
import engine.graphics.window.Window;
import engine.math.Matrix;

/**
 * Created by vesel on 26.01.2016.
 */
public class Layer2D extends Layer {
    public Layer2D(IRenderer renderer, Matrix projection, Window window) {
        super(renderer, projection, window);
    }

    public Layer2D(Matrix projection, Window window) {
        super(projection, window);
    }
}
