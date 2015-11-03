package game;

import engine.Entity;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Renderable;
import engine.graphics.Vertex;
import engine.input.Input;
import engine.math.Vector2;
import engine.math.Vector3;

/**
 * Created by vesel on 03.11.2015.
 */
public class Door extends Entity {

    private static Mesh sMesh;

    private static final float DOOR_TH = 0.25f;
    private static final float DOOR_WI = 2.0f;
    private static final float DOOR_HE = 2.0f;
    private static final float DOOR_ST = 0.0f;

    public Door(Renderable renderable) {
        super(renderable);

        if(renderable.getMesh() == null)
            renderable.setMesh(getMesh());
    }

    @Override
    public void update(Input inputStatus) {

    }


    private static Mesh getMesh() {
        if(sMesh != null)
            return sMesh;
        else {

            //TODO: create something that actually looks like a door
            Vertex[] vertices = new Vertex[] {
                    new Vertex(new Vector3(DOOR_ST, DOOR_ST, DOOR_ST), new Vector2(0.0f, 0.75f)),
                    new Vertex(new Vector3(DOOR_ST, DOOR_HE, DOOR_ST), new Vector2(0, 0.5f)),
                    new Vertex(new Vector3(DOOR_WI, DOOR_HE, DOOR_ST), new Vector2(0.25f, 0.5f)),
                    new Vertex(new Vector3(DOOR_WI, DOOR_ST, DOOR_ST), new Vector2(0.25f, 0.75f)),


                    new Vertex(new Vector3(DOOR_ST, DOOR_ST, DOOR_ST), new Vector2(0.1875f, 0.75f)),
                    new Vertex(new Vector3(DOOR_ST, DOOR_HE, DOOR_ST), new Vector2(0.1875f, 0.5f)),
                    new Vertex(new Vector3(DOOR_ST, DOOR_HE, DOOR_TH), new Vector2(0.25f, 0.5f)),
                    new Vertex(new Vector3(DOOR_ST, DOOR_ST, DOOR_TH), new Vector2(0.25f, 0.75f)),

                    new Vertex(new Vector3(DOOR_ST, DOOR_ST, DOOR_TH), new Vector2(0.0f, 0.75f)),
                    new Vertex(new Vector3(DOOR_ST, DOOR_HE, DOOR_TH), new Vector2(0, 0.5f)),
                    new Vertex(new Vector3(DOOR_WI, DOOR_HE, DOOR_TH), new Vector2(0.25f, 0.5f)),
                    new Vertex(new Vector3(DOOR_WI, DOOR_ST, DOOR_TH), new Vector2(0.25f, 0.75f)),


                    new Vertex(new Vector3(DOOR_WI, DOOR_ST, DOOR_ST), new Vector2(0.1875f, 0.75f)),
                    new Vertex(new Vector3(DOOR_WI, DOOR_HE, DOOR_ST), new Vector2(0.1875f, 0.5f)),
                    new Vertex(new Vector3(DOOR_WI, DOOR_HE, DOOR_TH), new Vector2(0.25f, 0.5f)),
                    new Vertex(new Vector3(DOOR_WI, DOOR_ST, DOOR_TH), new Vector2(0.25f, 0.75f)),
        };

            int[] indices = new int[] {
                    0, 1, 2,
                    0, 2, 3,
                    6, 5, 4,
                    7, 6, 4,

                    10, 9, 8,
                    11, 10, 8,
                    12, 13, 14,
                    12, 14, 15,
            };

            sMesh = new Mesh();
            sMesh.addVertices(vertices, indices);
        }

        return sMesh;
    }
}
