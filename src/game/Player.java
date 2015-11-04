package game;

import engine.graphics.Camera;
import engine.input.Input;
import engine.math.Maths;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.Log;
import engine.utils.Settings;

/**
 * Created by filip on 2.11.15.
 */
public class Player {

    private Camera mCamera;
    private Level mLevel;

    private boolean mSprinting = false;

    public Player(Vector3 position, Level level) {
        mCamera = new Camera();
        mCamera.move(Maths.normalize(position), position.getLenght());
        mLevel = level;
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

        if(Settings.MOUSE_CAMERA) {
            float xDelta = input.getMouseDeltaX();
            float yDelta = input.getMouseDeltaY();

            mCamera.rotateY(xDelta / 4);
            mCamera.rotateX(yDelta / 4);
        } else if(Settings.KEY_CAMERA) {
            if(input.isKeyDown(Input.KEY_LEFT))
                mCamera.rotateY(-2);
            if(input.isKeyDown(Input.KEY_RIGHT))
                mCamera.rotateY(2);
            if(input.isKeyDown(Input.KEY_UP))
                mCamera.rotateX(-2);
            if(input.isKeyDown(Input.KEY_DOWN))
                mCamera.rotateX(2);
        }
    }

    public Vector3 getPosition() {
        return mCamera.getPosition();
    }

    public void setPosition(float x, float y, float z) {
        mCamera.setPosition(new Vector3(x, y, z));
    }

    public void setPosition(Vector3 pos) {
        mCamera.setPosition(pos);
    }

    public Camera getCamera() {
        return mCamera;
    }

}
