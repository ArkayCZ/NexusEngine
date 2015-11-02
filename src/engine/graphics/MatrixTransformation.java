package engine.graphics;

import engine.math.Matrix4;
import engine.math.Vector3;

/**
 * Created by vesel on 30.10.2015.
 */
public class MatrixTransformation {

    private static Camera sCamera;

    private static float sNear = 0.001f, sFar = 10000f, sWidth, sHeight, sFOV;

    public Vector3 mPosition;
    public Vector3 mRotation;
    public Vector3 mScale;

    public MatrixTransformation() {
        mPosition = new Vector3();
        mRotation = new Vector3();
        mScale = new Vector3(1.0f, 1.0f, 1.0f);
    }

    public Matrix4 createTransformationMatrix() {
        Matrix4 translationMatrix = new Matrix4(1.0f).setToTranslation(mPosition);
        Matrix4 rotationMatrix = new Matrix4(1.0f).setToRotation(mRotation);
        Matrix4 scaleMatrix = new Matrix4(1.0f).setToScale(mScale);

        return translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix));
    }

    public Matrix4 getProjectionMatrix() {
        Matrix4 projectionMatrix = new Matrix4().setToPerspective(sFOV, sWidth, sHeight, sNear, sFar);
        Matrix4 cameraMatrix = new Matrix4().setToCamera(sCamera.getForward(), sCamera.getUp());
        Matrix4 cameraTranslation = new Matrix4().setToTranslation(-sCamera.getPosition().getX(), -sCamera.getPosition().getY(), -sCamera.getPosition().getZ());

        Matrix4 transformationMatrix = createTransformationMatrix();

        return projectionMatrix.multiply(cameraMatrix.multiply(cameraTranslation.multiply(transformationMatrix)));
    }

    public static void setProjection(float fov, float width, float height, float near, float far) {
        sFOV = fov;
        sWidth = width;
        sHeight = height;
        sNear = near;
        sFar = far;
    }

    public void setTranslation(Vector3 position) {
        this.mPosition = position;
    }

    public void setTranslation(float x, float y, float z) {
        this.mPosition = new Vector3(x, y, z);
    }

    public void setRotation(Vector3 rotation) {
        this.mRotation = rotation;
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

    @Override
    public String toString() {
        return "Translation: X: " + mPosition.getX() + " Y: " + mPosition.getY() + " Z: " + mPosition.getZ();
    }
}
