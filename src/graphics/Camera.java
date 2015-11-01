package graphics;

import input.Input;
import math.Maths;
import math.Vector3;
import org.lwjgl.glfw.GLFW;
import utils.Log;

/**
 * Created by vesel on 31.10.2015.
 */
public class Camera {
    public static final Vector3 yAxis = new Vector3(0, 1, 0);

    private Vector3 mPosition;
    private Vector3 mForward;
    private Vector3 mUp;

    public Camera() {
        this(new Vector3(0, 0, 0), new Vector3(0, 0, 1), new Vector3(0, 1, 0));
    }

    public Camera(Vector3 position, Vector3 forward, Vector3 up) {
        mPosition = position;
        mForward = forward;
        mUp = up;

        mUp.normalize();
        mForward.normalize();
    }

    public void rotateY(float angle) {
        Vector3 axis = Maths.normalize(yAxis.cross(mForward));
        mForward.rotate(angle, yAxis).normalize();
        setUp(Maths.normalize(mForward.cross(axis)));
    }

    public void rotateX(float angle) {
        Vector3 axis = Maths.normalize(yAxis.cross(mForward));
        mForward.rotate(angle, axis).normalize();
        setUp(Maths.normalize(mForward.cross(axis)));
    }

    public Vector3 getLeft() {
        return Maths.cross(mForward, mUp).normalize();
    }

    public Vector3 getRight() {
        return Maths.cross(mUp, mForward).normalize();
    }

    public void move(Vector3 direction, float amount) {
        mPosition = mPosition.add(direction.mul(amount));
    }

    public void update(Input input) {
        if(input.isKeyDown(GLFW.GLFW_KEY_W))
            move(getForward(), 0.1f);
        if(input.isKeyDown(GLFW.GLFW_KEY_S)) {
            move(Maths.multiply(getForward(), new Vector3(-1f)), 0.1f);
        }
        if(input.isKeyDown(GLFW.GLFW_KEY_A))
            move(getLeft(), 0.1f);
        if(input.isKeyDown(GLFW.GLFW_KEY_D))
            move(getRight(), 0.1f);

        if(input.isKeyDown(GLFW.GLFW_KEY_UP))
            rotateX(-1);
        if(input.isKeyDown(GLFW.GLFW_KEY_DOWN))
            rotateX(1);
        if(input.isKeyDown(GLFW.GLFW_KEY_LEFT))
            rotateY(-1);
        if(input.isKeyDown(GLFW.GLFW_KEY_RIGHT))
            rotateY(1);

    }

    public Vector3 getPosition() {
        return mPosition;
    }

    public void setPosition(Vector3 position) {
        mPosition = position;
    }

    public Vector3 getForward() {
        return mForward;
    }

    public void setForward(Vector3 forward) {
        mForward = forward;
    }

    public Vector3 getUp() {
        return mUp;
    }

    public void setUp(Vector3 up) {
        mUp = up;
    }


}
