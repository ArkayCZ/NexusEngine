package math;

import utils.Log;

/**
 * Created by vesel on 30.10.2015.
 */
public class Matrix4 {

    public static final int SIZE = 16; //4*4
    private float[] mData = new float[16];

    public Matrix4(float diagonal) {
        if(diagonal == 1.0f) {
            setToIdentity();
            return;
        }
        resetElements();
        setDiagonal(diagonal);
    }

    public Matrix4() {
        resetElements();
    }

    public Matrix4(Matrix4 other) {
        for(int i = 0; i < SIZE; i++) {
            mData[i] = other.getData()[i];
        }
    }

    public Matrix4 setToIdentity() {
        resetElements();
        setDiagonal(1.0f);

        return this;
    }

    public Matrix4 setToTranslation(Vector3 vector) {
        setToTranslation(vector.getX(), vector.getY(), vector.getZ());
        return this;
    }

    public Matrix4 setToTranslation(float x, float y, float z) {
        setToIdentity();

        set(0, 3, x);
        set(1, 3, y);
        set(2, 3, z);

        return this;
    }

    public Matrix4 setToRotation(Vector3 vector) {
        setToRotation(vector.getX(), vector.getY(), vector.getZ());
        return this;
    }

    public Matrix4 setToRotation(float x, float y, float z) {
        Matrix4 xRotationMatrix = new Matrix4(1.0f);
        Matrix4 yRotationMatrix = new Matrix4(1.0f);
        Matrix4 zRotationMatrix = new Matrix4(1.0f);

        float cos = Maths.cos(x);
        float sin = Maths.sin(x);

        xRotationMatrix.setToIdentity();
        xRotationMatrix.set(1, 1, cos);
        xRotationMatrix.set(1, 2, -sin);
        xRotationMatrix.set(2, 1, sin);
        xRotationMatrix.set(2, 2, cos);

        cos = Maths.cos(y);
        sin = Maths.sin(y);

        yRotationMatrix.set(0, 0, cos);
        yRotationMatrix.set(0, 2, -sin);
        yRotationMatrix.set(2, 0, sin);
        yRotationMatrix.set(2, 2, cos);

        cos = Maths.cos(z);
        sin = Maths.sin(z);

        zRotationMatrix.set(0, 0, cos);
        zRotationMatrix.set(0, 1, -sin);
        zRotationMatrix.set(1, 0, sin);
        zRotationMatrix.set(1, 1, cos);

        Matrix4 rotationMatrix;

        rotationMatrix = zRotationMatrix.multiply(yRotationMatrix.multiply(xRotationMatrix));
        mData = rotationMatrix.getData();

        return this;
    }

    public Matrix4 setToScale(float x, float y, float z) {
        setToIdentity();
        set(0, 0, x);
        set(1, 1, y);
        set(2, 2, z);

        return this;
    }

    public Matrix4 setToScale(Vector3 vector) {
        setToScale(vector.getX(), vector.getY(), vector.getZ());

        return this;
    }

    public Matrix4 setToPerspective(float fov, float width, float height, float near, float far) {
        float tan = Maths.tan(fov / 2);
        float ar = width / height;
        float range = near - far;

        set(0, 0, 1.0f / (tan * ar));
        set(1, 1, 1.0f / tan);
        set(2, 2, (-near - far) / range);
        set(2, 3, 2.0f * far * near / range);
        set(3, 2, 1.0f);

        return this;
    }

    /*public Matrix4 createPerspective(float fov, float width, float height, float near, float far) {
        float tan = Maths.tan(fov / 2);
        float ar = width / height;
        float range = near - far;

        set(0, 0, 1.0f / (tan * ar));    set(0, 1, 0.0f);    set(0, 2, 0.0f);    set(0, 3, 0.0f);
        set(1, 0, 0.0f);    set(1, 1, 1.0f / tan);    set(1, 2, 0.0f);    set(1, 3, 0.0f);
        set(2, 0, 0.0f);    set(2, 1, 0.0f);    set(2, 2, (-near - far) / range);    set(2, 3, 2.0f * far * near / range);
        set(3, 0, 0.0f);    set(3, 1, 0.0f);    set(3, 2, 1.0f);    set(3, 3, 0.0f);

        return this;
    }*/

    public Matrix4 setToCamera(Vector3 fw, Vector3 up) {
        setToIdentity();
        fw.normalize();

        Vector3 r = up;
        r.normalize();
        r = Maths.cross(r, fw);

        Vector3 u = Maths.cross(fw, r);

        set(0, 0, r.getX());
        set(0, 1, r.getY());
        set(0, 2, r.getZ());
        set(1, 0, u.getX());
        set(1, 1, u.getY());
        set(1, 2, u.getZ());
        set(2, 0, fw.getX());
        set(2, 1, fw.getY());
        set(2, 2, fw.getZ());
        set(3, 3, 1.0f);

        return this;
    }

    public Matrix4 setToOrthogonal(float left, float right, float bottom, float top, float near, float far) {
        setToIdentity();

        set(0, 0, 2.0f / (right - left));
        set(1, 1, 2.0f / (top - bottom));
        set(2, 2, -2.0f / (far - near));

        set(0, 3, -(right + left) / (right - left));
        set(1, 3, -(top + bottom) / (top - bottom));
        set(2, 3, -(far + near) / (far - near));

        return this;
    }

    public Matrix4 multiply(Matrix4 other) {
        Matrix4 result = new Matrix4(1.0f);

        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                float sum = 0.0f;
                for(int i = 0; i < 4; i++) {
                    sum += get(x, i) * other.get(i, y);
                }
                result.set(x, y, sum);
            }
        }
        mData = result.getData();
        return this;
    }

    private void resetElements() {
        for(int i = 0; i < SIZE; i++)
            mData[i] = 0.0f;
    }

    private void setDiagonal(float diagonal) {
        set(0, 0, diagonal);
        set(1, 1, diagonal);
        set(2, 2, diagonal);
        set(3, 3, diagonal);
    }

    public Matrix4 transpose() {
        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                set(x, y, get(y, x));
            }
        }

        return this;
    }

    public float get(int y, int x) {
        if((x + y * 4) > 15) {
            Log.e("Failed to access element [" + x + ";" + y + "]");
            return 0.0f;
        }
        return mData[x + y * 4];
    }

    public float[] getData() {
        return mData;
    }

    public float[] getDataCopy() {
        float[] data = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            data[i] = mData[i];
        }

        return data;
    }

    public void set(int y, int x, float value) {
        if((x + y * 4) > 15) {
            Log.e("Failed to access element [" + x + ";" + y + "]");
            return;
        }
        mData[x + y * 4] = value;
    }

    public String toString() {
        String result = "STRING";
        for(int y = 0; y < 4; y++) {
            String line = "";
            for(int x = 0; x < 4; x++) {
                line += " " + get(x, y);
            }
            result += "\n" + line;
        }

        return result;
    }

    /*
        set(0, 0, 1.0f);    set(0, 1, 0.0f);    set(0, 2, 0.0f);    set(0, 3, 0.0f);
        set(1, 0, 0.0f);    set(1, 1, 1.0f);    set(1, 2, 0.0f);    set(1, 3, 0.0f);
        set(2, 0, 0.0f);    set(2, 1, 0.0f);    set(2, 2, 1.0f);    set(2, 3, 0.0f);
        set(3, 0, 0.0f);    set(3, 1, 0.0f);    set(3, 2, 0.0f);    set(3, 3, 1.0f);
     */

}

