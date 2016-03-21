package engine.graphics.font;

import engine.graphics.Mesh;
import engine.graphics.Vertex;
import engine.math.Vector2;
import engine.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Font
{

    private FontSheet mSheet;
    private FontSpecification mSpec;

    private Vector2 mCursor;
    private Vector2 mScreenDimensions;

    public Font(FontSheet sheet, FontSpecification spec)
    {
        mSheet = sheet;
        mSpec = spec;
    }

    public Mesh constructMesh(String s)
    {
        List<Vertex> vertices = new ArrayList<>();

        for (int i = 0; i < s.length(); i++)
        {
            char character = s.toCharArray()[i];

            Vector2[] textureCoords = mSheet.getTextureCoords(
                    getSpec().getX(character),
                    getSpec().getY(character),
                    getSpec().getWidth(character),
                    getSpec().getHeight(character)
            );

           /* vertices.add(new Vertex(new Vector3()))
            vertices.add(new Vertex())
            vertices.add(new Vertex())
            vertices.add(new Vertex())*/
        }
        return null;
    }

    public void enableRendering()
    {
        mSheet.bindTexture();
    }

    public void disableRendering()
    {
        mSheet.unbindTexture();
    }

    public float toRelativeWidth(int pixels)
    {
        return pixels * (1.0f / mScreenDimensions.getY());
    }

    public float toRelativeHeight(int pixels)
    {
        return pixels * (1.0f / mScreenDimensions.getX());
    }

    public FontSpecification getSpec()
    {
        return mSpec;
    }

    public FontSheet getSheet()
    {
        return mSheet;
    }
}
