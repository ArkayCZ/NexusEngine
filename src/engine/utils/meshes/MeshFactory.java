package engine.utils.meshes;

import engine.graphics.Mesh;
import engine.graphics.Vertex;
import engine.math.Vector2;
import engine.math.Vector3;

/**
 * Created by vesel on 26.01.2016.
 */
public class MeshFactory
{

    public static Mesh generatePlaneMesh(float width, float height, boolean centered, boolean calculateNormals)
    {
        Mesh mesh = new Mesh();

        if (!centered)
        {
            Vertex[] vertices = new Vertex[]{
                    new Vertex(new Vector3(0, 0, 0), new Vector2(0)),
                    new Vertex(new Vector3(width, 0, 0), new Vector2(1, 0)),
                    new Vertex(new Vector3(width, 0, height), new Vector2(1, 1)),
                    new Vertex(new Vector3(0, 0, height), new Vector2(0, 1))
            };

            int[] indices = new int[]{
                    2, 1, 0,
                    0, 3, 2
            };

            mesh.addVertices(vertices, indices, true);
        }
        else
        {
            Vertex[] vertices = new Vertex[]{
                    new Vertex(new Vector3(-width / 2.0f, 0, -height / 2.0f), new Vector2(0, 0)),
                    new Vertex(new Vector3(width / 2.0f, 0, -height / 2.0f), new Vector2(1, 0)),
                    new Vertex(new Vector3(width / 2.0f, 0, height / 2.0f), new Vector2(1, 1)),
                    new Vertex(new Vector3(-width / 2.0f, 0, height / 2.0f), new Vector2(0, 1))
            };

            int[] indices = new int[]{
                    2, 1, 0,
                    0, 3, 2
            };

            mesh.addVertices(vertices, indices, true);
        }

        return mesh;
    }

}
