package game.wolf3dgame;

import engine.deprecated.Entity;
import engine.graphics.*;
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

    private int mShootingCooldown = -1;

    private boolean mShooting = false;
    private boolean mSprinting = false;

    private Texture mNormalTexture;
    private Texture mShootingTexture;

    public Player(Vector3 position, Level level) {
        super(new Renderable(), level);
        mCamera = new Camera();
        mCamera.move(Maths.normalize(position), position.getLenght());
        mLevel = level;

        Renderable renderable = new Renderable();
        Mesh mesh = new Mesh();

        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3(-0.6F, -0.6F, 0), new Vector2(0, 0)),
                new Vertex(new Vector3(-0.6F,  0.4F, 0), new Vector2(1, 0)),
                new Vertex(new Vector3( 0.4F, -0.6F, 0), new Vector2(0, 1)),
                new Vertex(new Vector3( 0.4F,  0.4F, 0), new Vector2(1, 1))
        };

        int[] indices = new int[] {
                1, 3, 2,
                2, 0, 1,
        };

        mNormalTexture = ContentLoader.loadTexture("res/textures/PISGB0.png");
        mShootingTexture = ContentLoader.loadTexture("res/textures/PISFA0.png");

        mesh.addVertices(vertices, indices);
        setRenderable(new Renderable(new Material(ContentLoader.loadTexture("res/textures/PISGB0.png"), new Vector3(1))));
        getRenderable().setMesh(mesh);
        getRenderable().initTransformation();
        getRenderable().getTransformation().setRotation(new Vector3(0, 0, 270));
        getRenderable().getTransformation().setScale(new Vector3(0.5f));
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

        mSprinting = input.isKeyDown(Input.KEY_SHIFT);

        if((input.isLeftMouseButton() || input.isKeyDown(Input.KEY_SPACE)) && mShootingCooldown < 0) {
            mShootingCooldown = 8;
            shoot(mCamera.getForward());
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

        if(Settings.MOUSE_CONTROL) {
            float xDelta = input.getMouseDeltaX();
            float yDelta = input.getMouseDeltaY();

            mCamera.rotateY(xDelta / 4);
            mCamera.rotateX(yDelta / 4);
        } else {
            if(input.isKeyDown(Input.KEY_DOWN))
                mCamera.rotateX(-2f);
            if(input.isKeyDown(Input.KEY_UP))
                mCamera.rotateX(2f);
            if(input.isKeyDown(Input.KEY_LEFT))
                mCamera.rotateY(-2f);
            if(input.isKeyDown(Input.KEY_RIGHT))
                mCamera.rotateY(2f);
        }

        if(mShootingCooldown > 0) {
            getRenderable().getMaterial().setTexture(mShootingTexture);
        } else getRenderable().getMaterial().setTexture(mNormalTexture);

        mShootingCooldown--;

        getRenderable().getTransformation().setPosition(new Vector3(mCamera.getPosition())
                .add(new Vector3(mCamera.getForward()).normalize().mul(0.1f)));
        getRenderable().getTransformation().getPosition().add(new Vector3(0f, -0.0575f, 0));
        getRenderable().getTransformation().setScale(new Vector3(0.0625f));

        Vector3 cameraVector = new Vector3(Transform.getCamera().getPosition())
                .sub(getRenderable().getTransformation().getPosition());

        float cameraAngle =  Maths.atan2(cameraVector.getZ(), cameraVector.getX());
        getRenderable().getTransformation().getRotation().setX(cameraAngle + 90);
    }

    public void shoot(Vector3 direction) {
        Vector2 start = getRenderable().getTransformation().getPosition().getXZ();
        Vector2 shotDirection = direction.getXZ();

        for (Enemy e : mLevel.getEnemies()) {
            boolean hit = mLevel.checkEntityCollision(start, shotDirection, e);
            if (!hit)
                Log.i("Missed!");
            else {
                e.die();
            }
        }

    }

    public Camera getCamera() {
        return mCamera;
    }

}
