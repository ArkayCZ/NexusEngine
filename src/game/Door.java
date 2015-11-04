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

    private static final float DOOR_TH = 0.25f;
    private static final float DOOR_WI = 2.0f;
    private static final float DOOR_HE = 2.0f;
    private static final float DOOR_ST = 0.0f;

    private boolean mIsOpenning = false;
    private float mOpeningFactor = 0.0f;
    private boolean mIsRotated = false;

    private Vector3 mOpenTransaltion;
    private Vector3 mDefaultTranslation;

    public Door(Renderable renderable, Level context) {
        super(renderable, context);

        if(renderable.getMesh() == null)
            renderable.setMesh(getMesh());

        mDefaultTranslation = new Vector3(getTranslation());
        mOpenTransaltion = new Vector3(0);
        mOpenTransaltion.setX(1.8f);
    }

    @Override
    public void update(Input inputStatus) {
        int playerX = (int)getContext().getPlayerPosition().getX();
        int playerY = (int)getContext().getPlayerPosition().getY();

        int doorX = (int)getTranslation().getX();
        int doorY = (int)getTranslation().getZ();

        if(playerX == doorX && playerY == doorY)
            Log.i("PlayerX: " + playerX + " DoorX: " + doorX);

        if(playerX == doorX - 1 && playerY == doorY ||
                playerX == doorX && playerY == doorY ||
                playerX == doorX + 1 && playerY == doorY
                ) mIsOpenning = true;
        else mIsOpenning = false;

        float distance = Maths.abs(getContext().getPlayerPosition().getLength() -
                mDefaultTranslation.getLenght());
        Log.i("" + distance);

        if(distance < 1.5f) {
            mOpeningFactor += 0.02f;
        }
        else mOpeningFactor -= 0.02f;

        mOpeningFactor = mOpeningFactor > 1.0f ? 1.0f : mOpeningFactor;
        mOpeningFactor = mOpeningFactor < 0.0f ? 0.0f : mOpeningFactor;

        Vector3 oTranslation = Maths.interpolate(new Vector3(0), mOpenTransaltion, mOpeningFactor);
        setTranslation(Maths.add(new Vector3(mDefaultTranslation), oTranslation));
    }


    public void setDefaultTranslation(Vector3 translation) {
        mDefaultTranslation = translation;
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

    public void setIsRotated(boolean b) {
        mIsRotated = true;
        mOpenTransaltion = new Vector3(0, 0, 2);
    }
}
