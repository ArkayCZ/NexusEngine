package game;

import engine.Entity;
import engine.graphics.*;
import engine.graphics.shaders.BasicShader;
import engine.input.Input;
import engine.math.Maths;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.ContentLoader;
import engine.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by filip on 18.11.15.
 */
public class Enemy extends Entity {

    public static float START = -0.5F;
    public static float SIZEX = 0.5f;
    public static float SIZEY = 0.8f;

    public static float TXC_S = 0.0f;
    public static float TXC_X = 1.0f;
    public static float TXC_Y = 1.0f;

    private static List<Texture> sTextures;
    private static Mesh sMesh;

    private static final int STATE_IDLE = 0;
    private static final int STATE_CHASING = 1;
    private static final int STATE_SHOOTING = 2;
    private static final int STATE_DYING = 3;
    private static final int STATE_DEAD = 4;

    private int state = STATE_IDLE;
    private boolean mAlive = true;

    private int mStateCounter;

    public Enemy(Level context) {
        super(new Renderable(), context);
        if(sTextures == null)
            loadTextures();

        if(sMesh == null) {
            Vertex[] vertices = new Vertex[] {
                    new Vertex(new Vector3(START, START, 0), new Vector2(TXC_S, TXC_Y)),
                    new Vertex(new Vector3(START, SIZEY, 0), new Vector2(TXC_S, TXC_S)),
                    new Vertex(new Vector3(SIZEX, START, 0), new Vector2(TXC_X, TXC_Y)),
                    new Vertex(new Vector3(SIZEX, SIZEY, 0), new Vector2(TXC_X, TXC_S))
            };

            int[] indices = new int[] {
                    0, 1, 2,
                    3, 2, 1
            };

            sMesh = new Mesh();
            sMesh.addVertices(vertices, indices);
        }

        setRenderable(new Renderable(new Material(sTextures.get(0), new Vector3(1, 1, 1))));
        getRenderable().setMesh(sMesh);
        getRenderable().initTransformation();
    }

    public static void loadTextures() {
        sTextures = new ArrayList<Texture>();
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVA1.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVB1.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVC1.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVD1.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVE0.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVF0.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVG0.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVH0.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVI0.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVJ0.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVK0.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVL0.png"));
        sTextures.add(ContentLoader.loadTexture("res/textures/SSWVM0.png"));
    }

    @Override
    public void update(Input inputStatus) {
        Vector3 cameraVector = new Vector3(MatrixTransformation.getCamera().getPosition())
                .sub(getRenderable().getTransformation().mPosition);

        float cameraAngle =  Maths.atan2(cameraVector.getZ(), cameraVector.getX());

        getRenderable().getTransformation().mRotation.setY(cameraAngle + 90);
        getRenderable().getTransformation().mPosition.setY(0.5f);

        Log.i(String.valueOf(state));

        if(state == STATE_IDLE && isPlayerVisible())
            state = STATE_CHASING;
        else if(state == STATE_CHASING)
            chasingUpdate();
        else if(state == STATE_SHOOTING)
            shootingUpdate();
        else if(state == STATE_DYING)
            dyingUpdate();
        else if(state == STATE_DEAD) {
            getRenderable().getMaterial().setTexture(sTextures.get(12));
            getRenderable().getTransformation().mRotation.setX(85);
            getRenderable().getTransformation().mPosition.setY(0.02f);
        }
    }

    private void chasingUpdate() {
        Vector3 cameraVector = new Vector3(MatrixTransformation.getCamera().getPosition())
                .sub(getRenderable().getTransformation().mPosition);

        float cameraAngle =  Maths.atan2(cameraVector.getZ(), cameraVector.getX());

        Vector2 directionVector = new Vector2(0.01f * Maths.cos(cameraAngle), 0.01f * Maths.sin(cameraAngle));

        Vector3 collisionVector = getContext().checkCollision(new Vector3(getTranslation()), new Vector3(getTranslation()).add(
                new Vector3(directionVector.getX(), 0, directionVector.getY())), 1, 1);

        getTranslation().add(new Vector3(directionVector.getX(), 0, directionVector.getY()).mul(collisionVector));

        if(new Random().nextInt(200) == 100) {
            state = STATE_SHOOTING;
            mStateCounter = 120;
        }
    }

    private void shootingUpdate() {
        if(mStateCounter < 0) {
            state = STATE_CHASING;
            return;
        }
        getRenderable().getMaterial().setTexture(sTextures.get(6));

        mStateCounter--;
    }

    private void dyingUpdate() {
        if(mStateCounter < 0) {
            state = STATE_DEAD;
            return;
        }

        getRenderable().getMaterial().setTexture(sTextures.get(10));

        mStateCounter--;
    }

    public void die() {
        mAlive = false;
        mStateCounter = 30;
        state = STATE_DYING;
        Log.i("Enemy killed!");
    }

    private boolean isPlayerVisible() {
        Vector3 cameraVector = new Vector3(MatrixTransformation.getCamera().getPosition())
                .sub(getRenderable().getTransformation().mPosition);

        float cameraAngle =  Maths.atan2(cameraVector.getZ(), cameraVector.getX());

        getRenderable().getTransformation().mRotation.setY(cameraAngle + 90);

        Vector2 directionVector = new Vector2(Maths.cos(cameraAngle), Maths.sin(cameraAngle));

        return getContext().checkEntityCollision(getTranslation().getXZ(), directionVector, getContext().getPlayer());
    }

}
