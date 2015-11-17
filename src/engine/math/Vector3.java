package engine.math;

/**
 * Created by vesel on 30.10.2015.
 */
public class Vector3 {

    private float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(float x) {
        this.x = x;
        this.y = x;
        this.z = x;
    }

    public Vector3() {

    }

    public Vector3(Vector3 other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public float getLenght() {
        return (float)Math.sqrt(x*x + y*y + z*z);
    }

    public float dot(Vector3 other) {
        return x * other.getX() + y * other.getY() + y * other.getZ();
    }

    public Vector3 normalize() {
        float length = getLenght();

        x /= length;
        y /= length;
        z /= length;

        return this;
    }

    public Vector3 rotate(float angle, Vector3 axis) {
        float sinHalf = Maths.sin(angle / 2);
        float cosHalf = Maths.cos(angle / 2);

        float rX = axis.getX() * sinHalf;
        float rY = axis.getY() * sinHalf;
        float rZ = axis.getZ() * sinHalf;
        float rW = cosHalf;

        Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
        Quaternion rotationConjugate = rotation.getConjugate();

        Quaternion w = rotation.mul(this).mul(rotationConjugate);

        x = w.getX();
        y = w.getY();
        z = w.getZ();

        return this;
    }

    public Vector3 cross(Vector3 other) {
        /*float xx = y * other.getZ() - z * other.getY();
        float yy = z * other.getX() - x * other.getZ();
        float zz = x * other.getY() - y * other.getX();

        return new Vector3(xx, yy, zz);*/


        float xx = y * other.getZ() - z * other.getY();
        float yy = z * other.getX() - x * other.getZ();
        float zz = x * other.getY() - y * other.getX();

        this.x = xx;
        this.y = yy;
        this.z = zz;
        return this;


    }


    public Vector3 abs() {
        x = Maths.abs(x);
        y = Maths.abs(y);
        z = Maths.abs(z);

        return this;
    }

    public Vector3 add(Vector3 other) {
        x = x + other.getX();
        y = y + other.getY();
        z = z + other.getZ();

        return this;
    }


    public Vector3 add(float other) {
        x = x + other;
        y = y + other;
        z = z + other;

        return this;
    }

    public Vector3 sub(Vector3 other) {
        x = x - other.getX();
        y = y - other.getY();
        z = z - other.getZ();

        return this;
    }

    public Vector3 sub(float other) {
        x = x - other;
        y = y - other;
        z = z - other;

        return this;
    }

    public Vector3 mul(Vector3 other) {
        x = x * other.getX();
        y = y * other.getY();
        z = z * other.getZ();

        return this;
    }

    public Vector3 mul(float other) {
        x = x * other;
        y = y * other;
        z = z * other;

        return this;
    }
    public Vector3 div(Vector3 other) {
        x = x / other.getX();
        y = y / other.getY();
        z = z / other.getZ();

        return this;
    }

    public Vector3 div(float other) {
        x = x / other;
        y = y / other;
        z = z / other;

        return this;
    }

    public Vector2 getXY() {
        return new Vector2(x, y);
    }

    public Vector2 getXZ() {
        return new Vector2(x, z);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y + " Z: " + z;
    }
}
