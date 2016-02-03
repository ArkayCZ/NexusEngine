package engine.graphics;

import engine.graphics.font.Font;
import engine.math.Matrix;
import engine.math.Vector2;

/**
 * Created by vesel on 26.01.2016.
 */
public interface IRenderer2D extends IRenderer {

    void drawRectangle(float x, float y, float width, float height, int color);
    void drawRectangle(Vector2 start, Vector2 size, int color);
    /* TODO: Add Rectangle class and the possibility to add border */

    void drawString(float x, float y, Font font, String s);

    void drawLine(Vector2 origin, Vector2 target, int color);

}
