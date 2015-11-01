package math;

/**
 * Created by vesel on 31.10.2015.
 */
public class Maths {

    public static float sin(float angle) {
        return (float)Math.sin(Math.toRadians(angle));
    }

    public static float cos(float angle) {
        return (float)Math.cos(Math.toRadians(angle));
    }

    public static float tan(float angle) {
        return (float)Math.tan(Math.toRadians(angle));
    }

    public static float toRad(float angle) {
        return (float)Math.toRadians(angle);
    }

    public static float sqrt(float number) {
        return (float)Math.sqrt(number);
    }

    public static float pow(float number, float power) {
        return (float)Math.pow(number, power);
    }

    public static Matrix4 multiply(Matrix4 first, Matrix4 second) {
        Matrix4 matrix = new Matrix4(first);
        return matrix.multiply(second);
    }

    public static Vector3 multiply(Vector3 first, Vector3 second) {
        Vector3 vector = new Vector3(first);
        return vector.mul(second);
    }

}
