package engine.math;

/**
 * Created by vesel on 30.10.2015.
 */
public class Quaternion {

    private float x, y, z, w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion() {

    }

    public float getLength() {
        return (float)Math.sqrt(x*x + y*y + z*z + w*w);
    }

    public Quaternion normalize() {
        float length = getLength();

        x /= length;
        y /= length;
        z /= length;
        w /= length;

        return this;
    }

    public Quaternion getConjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion mul(Quaternion other) {
        float xx = x * other.getW() + w * other.getX() + y * other.getZ() - z * other.getY();
        float yy = y * other.getW() + w * other.getY() + z * other.getX() - x * other.getZ();
        float zz = z * other.getW() + w * other.getZ() + x * other.getY() - y * other.getX();
        float ww = w * other.getW() - x * other.getX() - y * other.getY() - z * other.getZ();

        return new Quaternion(xx, yy, zz, ww);
    }

    public Quaternion mul(Vector3 other) {
        float xx =  w * other.getX() + y * other.getZ() - z * other.getY();
        float yy =  w * other.getY() + z * other.getX() - x * other.getZ();
        float zz =  w * other.getZ() + x * other.getY() - y * other.getX();
        float ww = -x * other.getX() - y * other.getY() - z * other.getZ();

        return new Quaternion(xx, yy, zz, ww);
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

    public float getW() {
        return w;
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

    public void setW(float w) {
        this.w = w;
    }
}
