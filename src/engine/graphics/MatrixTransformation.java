package engine.graphics;

import engine.math.Matrix;
import engine.math.Vector3;

/**
 * Created by vesel on 30.10.2015.
 */
public class MatrixTransformation {

    private static Camera sCamera;

    private static float sNear = 0.001f, sFar = 10000f, sWidth, sHeight, sFOV;

    private Vector3 mPosition;
    private Vector3 mRotation;
    private Vector3 mScale;

    public MatrixTransformation() {
        mPosition = new Vector3(0);
        mRotation = new Vector3(0);
        mScale = new Vector3(1);
    }

    public Matrix createTransformationMatrix() {
        Matrix translationMatrix = new Matrix(1.0f).setToTranslation(mPosition);
        Matrix rotationMatrix = new Matrix(1.0f).setToRotation(mRotation);
        Matrix scaleMatrix = new Matrix(1.0f).setToScale(mScale);

        return translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix));
    }

    /**
     * Assembles the final matrix for determining the position of the vertices using camera projection.
     * @return
     */
    public Matrix createWorldMatrix() {
        Matrix projectionMatrix = new Matrix().setToPerspective(sFOV, sWidth, sHeight, sNear, sFar);
        Matrix cameraMatrix = new Matrix().setToCamera(sCamera.getForward(), sCamera.getUp());
        Matrix cameraTranslation = new Matrix().setToTranslation(-sCamera.getPosition().getX(), -sCamera.getPosition().getY(), -sCamera.getPosition().getZ());

        Matrix transformationMatrix = createTransformationMatrix();

        return projectionMatrix.multiply(cameraMatrix.multiply(cameraTranslation.multiply(transformationMatrix)));
    }

    public static void setProjection(float fov, float width, float height, float near, float far) {
        sFOV = fov;
        sWidth = width;
        sHeight = height;
        sNear = near;
        sFar = far;
    }

    public void setPosition(Vector3 position) {
        this.mPosition = position;
    }

    public void setPosition(float x, float y, float z) {
        this.mPosition = new Vector3(x, y, z);
    }

    public void setRotation(Vector3 rotation) {
        this.mRotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        setRotation(new Vector3(x, y, z));
    }

    public void setScale(float x, float y, float z) {
       mScale = new Vector3(x, y, z);
    }

    public void setScale(Vector3 scale) {
        mScale = scale;
    }

    public static Camera getCamera() {
        return sCamera;
    }

    public static void setCamera(Camera cam) {
        sCamera = cam;
    }

    public Vector3 getPosition() {
        return mPosition;
    }

    public Vector3 getRotation() {
        return mRotation;
    }

    public Vector3 getScale() {
        return mScale;
    }

    @Override
    public String toString() {
        return "Translation: X: " + mPosition.getX() + " Y: " + mPosition.getY() + " Z: " + mPosition.getZ();
    }
}
