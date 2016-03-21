package engine.graphics.font;

import engine.graphics.Texture;
import engine.graphics.Vertex;
import engine.math.Vector2;
import engine.math.Vector3;

/**
 * Created by vesel on 30.01.2016.
 */
public class FontSheet
{

    private Texture mSheet;

    public FontSheet(Texture texture)
    {
        mSheet = texture;
    }

    public Vertex[] getCharacterQuad(int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance)
    {
        Vertex[] vertices = new Vertex[]{
                new Vertex(new Vector3(0, 0, 0), new Vector2(toRelativeWidth(x), toRelativeHeight(y))),
                new Vertex(new Vector3(0, 0, 1), new Vector2(toRelativeWidth(x), toRelativeHeight(y) + toRelativeHeight(height))),
                new Vertex(new Vector3(1, 0, 1), new Vector2(toRelativeWidth(x) + toRelativeWidth(width), toRelativeHeight(y) + toRelativeHeight(height))),
                new Vertex(new Vector3(1, 0, 0), new Vector2(toRelativeWidth(x) + toRelativeWidth(width), toRelativeHeight(y)))
        };

        return vertices;
    }

    /**
     * Returns the texture coords for a letter quad.
     *
     * @param x      X position of the letter in pixels (from FontSpecification class)
     * @param y      Y position of the letter in pixels (from FontSpecification class)
     * @param width  Width of the letter in pixels (from FontSpecification class)
     * @param height Height of the letter in pixels (from FontSpecification class)
     * @return Array of Vector2s representing the texture coords of a quad.
     */
    public Vector2[] getTextureCoords(int x, int y, int width, int height)
    {
        return new Vector2[]{
                new Vector2(toRelativeWidth(x), toRelativeHeight(x)),
                new Vector2(toRelativeWidth(x), toRelativeHeight(x) + toRelativeHeight(height)),
                new Vector2(toRelativeWidth(x) + toRelativeWidth(width), toRelativeHeight(y) + toRelativeHeight(height)),
                new Vector2(toRelativeWidth(x) + toRelativeWidth(width), toRelativeHeight(y)),
        };
    }

    public float toRelativeWidth(int pixelCoord)
    {
        return pixelCoord * (1.0f / mSheet.getWidth());
    }

    public float toRelativeHeight(int pixelCoord)
    {
        return pixelCoord * (1.0f / mSheet.getHeight());
    }

    public void unbindTexture()
    {
        Texture.unbind();
    }

    public void bindTexture()
    {
        mSheet.bind();
    }

    public Texture getSheet()
    {
        return mSheet;
    }

    public void setSheet(Texture sheet)
    {
        mSheet = sheet;
    }
}
