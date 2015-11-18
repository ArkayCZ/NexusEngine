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
import engine.utils.Settings;

/**
 * Created by filip on 2.11.15.
 */
public class Player extends Entity {

    private Camera mCamera;
    private Level mLevel;

    private boolean mShooting = false;
    private boolean mSprinting = false;

    public Player(Vector3 position, Level level) {
        super(new Renderable(), level);
        mCamera = new Camera();
        mCamera.move(Maths.normalize(position), position.getLenght());
        mLevel = level;

        Renderable renderable = new Renderable();
        Mesh mesh = new Mesh();

        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3(-0.5F, -0.5F, 0), new Vector2(0, 0)),
                new Vertex(new Vector3(-0.5F,  0.5F, 0), new Vector2(1, 0)),
                new Vertex(new Vector3( 0.5F, -0.5F, 0), new Vector2(0, 1)),
                new Vertex(new Vector3( 0.5F,  0.5F, 0), new Vector2(1, 1))
        };

        int[] indices = new int[] {
                1, 3, 2,
                2, 0, 1,
        };

        mesh.addVertices(vertices, indices);
        setRenderable(new Renderable(new Material(ContentLoader.loadTexture("res/textures/PISGB0.png"), new Vector3(1))));
        getRenderable().setMesh(mesh);
        getRenderable().initTransformation();
        getRenderable().getTransformation().setRotation(new Vector3(0, 0, 270));
        getRenderable().getTransformation().mScale = new Vector3(0.5f);
    }

    public void update(Input input) {
        Vector3 movementVector = new Vector3();

        if(input.isKeyDown(Input.KEY_W)) {
            movementVector.add(mCamera.getForward());
        }
        if(input.isKeyDown(Input.KEY_S)) {
            movementVector.add(Maths.multiply(mCamera.getForward(), new Vector3(-1)));
        }
        if (input.isKeyDown(Input.KEY_A)) {
            movementVector.add(mCamera.getLeft());
        }
        if(input.isKeyDown(Input.KEY_D)) {
            movementVector.add(mCamera.getRight());
        }
        if(input.isKeyDown(Input.KEY_SHIFT)) {
            mSprinting = true;
        } else {
            mSprinting = false;
        }

        movementVector.setY(0.0f);

        Vector3 oldPosition = mCamera.getPosition();
        Vector3 newPosition = new Vector3(oldPosition);
        newPosition = newPosition.add(movementVector.mul(0.1f));

        if(Settings.COLLISIONS_ENABLED) {
            Vector3 collisionVector = mLevel.checkCollision(oldPosition, newPosition, 0.2f, 0.2f);
            movementVector = movementVector.mul(collisionVector);
        }

        mCamera.move(movementVector, mSprinting ? 1.5f : 1f);

        /*float xDelta = input.getMouseDeltaX();
        float yDelta = input.getMouseDeltaY();

        mCamera.rotateY(xDelta / 4);
        mCamera.rotateX(yDelta / 4);*/

        if(input.isKeyDown(Input.KEY_DOWN))
            mCamera.rotateX(-2f);
        if(input.isKeyDown(Input.KEY_UP))
            mCamera.rotateX(2f);
        if(input.isKeyDown(Input.KEY_LEFT))
            mCamera.rotateY(-2f);
        if(input.isKeyDown(Input.KEY_RIGHT))
            mCamera.rotateY(2f);

        getRenderable().getTransformation().setTranslation(new Vector3(mCamera.getPosition())
                .add(new Vector3(mCamera.getForward()).normalize()));
        getRenderable().getTransformation().mPosition.add(new Vector3(0, -0.1f, 0));

        Vector3 cameraVector = new Vector3(MatrixTransformation.getCamera().getPosition())
                .sub(getRenderable().getTransformation().mPosition);

        float cameraAngle =  Maths.atan2(cameraVector.getZ(), cameraVector.getX());
        if(cameraVector.getX() < 0)
            cameraAngle += 180f;

        getRenderable().getTransformation().mRotation.setX(cameraAngle + 90);
    }

    public Camera getCamera() {
        return mCamera;
    }

}
