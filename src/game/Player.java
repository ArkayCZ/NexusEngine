package game;

import engine.graphics.Camera;
import engine.input.Input;
import engine.math.Maths;
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

        float xDelta = input.getMouseDeltaX();
        float yDelta = input.getMouseDeltaY();

        mCamera.rotateY(xDelta / 4);
        mCamera.rotateX(yDelta / 4);
    }

    public Camera getCamera() {
        return mCamera;
    }

}
