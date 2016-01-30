package engine.graphics;

import engine.math.Maths;
import engine.math.Matrix;
import engine.math.Vector3;
import engine.utils.Log;

/**
 * Created by vesel on 30.10.2015.
 * Represents a combination if transformation matrices (scale, translation and rotation)
 */
public class Transform {

    private static Camera sCamera;
    private static Matrix sProjection;

    private Vector3 mPosition;
    private Vector3 mRotation;
    private Vector3 mScale;

    private boolean mHasTransformChanged = true;
    private Matrix mLastTransformMatrix;

    public Transform() {
        this(new Vector3(0), new Vector3(0), new Vector3(1));
    }

    public Transform(Vector3 position) {
        this(position, new Vector3(0), new Vector3(1));
    }

    public Transform(Vector3 position, Vector3 rotation) {
        this(position, rotation, new Vector3(1));
    }

    public Transform(Vector3 position, Vector3 rotation, Vector3 scale) {
        mPosition = position;
        mRotation = rotation;
        mScale = scale;
    }

    public Matrix createTransformationMatrix() {
        if(!mHasTransformChanged) return mLastTransformMatrix;
        Matrix translationMatrix = new Matrix(1.0f).setToTranslation(mPosition);
        Matrix rotationMatrix = new Matrix(1.0f).setToRotation(mRotation);
        Matrix scaleMatrix = new Matrix(1.0f).setToScale(mScale);

        mHasTransformChanged = false;
        mLastTransformMatrix = translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix));
        return mLastTransformMatrix;
    }

    /**
     * Assembles the final matrix for determining the position of the vertices using camera projection.
     * @return Final world matrix.
     */
    public Matrix createWorldMatrix() {
        Matrix projectionMatrix = new Matrix(sProjection);
        Matrix cameraMatrix = new Matrix().setToCamera(sCamera.getForward(), sCamera.getUp());
        Matrix cameraTranslation = new Matrix(1.0f).setToTranslation(-sCamera.getPosition().getX(), -sCamera.getPosition().getY(), -sCamera.getPosition().getZ());

        Matrix transformationMatrix = createTransformationMatrix();

        return projectionMatrix.multiply(cameraMatrix.multiply(cameraTranslation.multiply(transformationMatrix)));
    }

    public Matrix createWorldMatrix(Matrix projection, Camera camera) {
        Matrix cameraMatrix = new Matrix().setToCamera(camera.getForward(), camera.getUp());
        Matrix cameraTranslation = new Matrix(1.0f);/*.setToTranslation(Maths.multiply(camera.getPosition(), new Vector3(-1)));*/
        Matrix transformationMatrix = this.createTransformationMatrix();

        return projection.multiply(cameraMatrix.multiply(cameraTranslation.multiply(transformationMatrix)));
    }

    public static void setProjection(Matrix matrix) {
        sProjection = matrix;
    }

    public static Matrix getProjection() {
        return sProjection;
    }

    public void setPosition(Vector3 position) {
        mHasTransformChanged = true;
        this.mPosition = position;
    }

    public void setPosition(float x, float y, float z) {
        mHasTransformChanged = true;
        this.mPosition = new Vector3(x, y, z);
    }

    public void setRotation(Vector3 rotation) {
        mHasTransformChanged = true;
        this.mRotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        mHasTransformChanged = true;
        setRotation(new Vector3(x, y, z));
    }

    public void setScale(float x, float y, float z) {
        mHasTransformChanged = true;
       mScale = new Vector3(x, y, z);
    }

    public void setScale(Vector3 scale) {
        mHasTransformChanged = true;
        mScale = scale;
    }

    public static Camera getCamera() {
        return sCamera;
    }

    public static void setCamera(Camera cam) {
        sCamera = cam;
    }

    public Vector3 getPosition() {
        mHasTransformChanged = true;
        return mPosition;
    }

    public Vector3 getRotation() {
        mHasTransformChanged = true;
        return mRotation;
    }

    public Vector3 getScale() {
        mHasTransformChanged = true;
        return mScale;
    }

    @Override
    public String toString() {
        return "Translation: X: " + mPosition.getX() + " Y: " + mPosition.getY() + " Z: " + mPosition.getZ();
    }
}
