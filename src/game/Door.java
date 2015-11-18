package game;

import engine.Entity;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Renderable;
import engine.graphics.Vertex;
import engine.input.Input;
import engine.math.Maths;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.Log;

/**
 * Created by vesel on 03.11.2015.
 */
public class Door extends Entity {

    private static Mesh sMesh;

    public static final float DOOR_TH = 0.25f;
    public static final float DOOR_WI = 2.0f;
    public static final float DOOR_HE = 2.0f;
    public static final float DOOR_ST = 0.0f;

    private boolean mIsOpening = false;
    private float mOpeningFactor = 0.0f;
    private boolean mIsRotated = false;

    private Vector3 mOpenTranslation;
    private Vector3 mDefaultTranslation;

    public Door(Renderable renderable, Level level) {
        super(renderable, level);

        if (renderable.getMesh() == null)
            renderable.setMesh(getMesh());

        mOpenTranslation = new Vector3(1.8f, 0, 0);
    }

    @Override
    public void update(Input inputStatus) {
        int playerX = (int)getContext().getPlayerPosition().getX();
        int playerY = (int)getContext().getPlayerPosition().getY();

        int doorX = (int)getRenderable().getTransformation().mPosition.getX();
        int doorY = (int)getRenderable().getTransformation().mPosition.getZ();

        Vector2 playerPosition = new Vector2(playerX, playerY);
        Vector2 doorPosition = new Vector2(doorX, doorY);

        Vector2 resultVector = doorPosition.sub(playerPosition);

        float distance = resultVector.getLength();
        if(distance < 2.8f) {
            mOpeningFactor += 0.05f;
            mIsOpening = true;
        } else  {
            mIsOpening = false;
            mOpeningFactor -= 0.05f;
        }

        mOpeningFactor = mOpeningFactor < 0f ? 0f : mOpeningFactor;
        mOpeningFactor = mOpeningFactor > 1f ? 1f : mOpeningFactor;

        if(mOpeningFactor > 0) {
            Vector3 openingTranslation = new Vector3(mOpenTranslation).mul(mOpeningFactor);
            setTranslation(isRotated() ?  new Vector3(mDefaultTranslation).add(new Vector3(
                    openingTranslation.getZ(), 0, openingTranslation.getX()))
                    : new Vector3(mDefaultTranslation).add(openingTranslation));
        } else {
            setTranslation(new Vector3(mDefaultTranslation));
        }
    }


    private static Mesh getMesh() {
        if(sMesh != null)
            return sMesh;
        else {
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

    public void setDefaultTranslation(Vector3 defaultTranslation) {
        mDefaultTranslation = new Vector3(defaultTranslation);
    }

    public boolean isRotated() {
        return getRenderable().getTransformation().mRotation.getY() == 90;
    }
}
