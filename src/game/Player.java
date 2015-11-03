package game;

import engine.graphics.Camera;
import engine.input.Input;
import engine.math.Maths;
import engine.math.Vector3;
import engine.utils.Log;

/**
 * Created by filip on 2.11.15.
 */
public class Player {

    private Vector3 mPosition;
    private Camera mCamera;

    public Player(Vector3 position) {
        mPosition = position;
        mCamera = new Camera();
        //mCamera.move(Maths.normalize(position), position.getLenght());
    }

    public void update(Input input) {
        Vector3 movementVector = new Vector3();

        if(input.isKeyDown(Input.KEY_W)) {
            movementVector.add(mCamera.getForward());
        } else if(input.isKeyDown(Input.KEY_S)) {
            movementVector.add(Maths.multiply(mCamera.getForward(), new Vector3(-1)));
        } else if (input.isKeyDown(Input.KEY_A)) {
            movementVector.add(mCamera.getLeft());
        } else if(input.isKeyDown(Input.KEY_D)) {
            movementVector.add(mCamera.getRight());
        }

        mCamera.move(movementVector, 0.5f);

        float xDelta = input.getMouseDeltaX();
        float yDelta = input.getMouseDeltaY();

        //Log.i("MouseDeltaX:" + xDelta);
        //Log.i("MouseDeltaY:" + yDelta);

        mCamera.rotateY(xDelta / 4);
        mCamera.rotateX(yDelta / 4);
    }

    public Camera getCamera() {
        return mCamera;
    }

}
