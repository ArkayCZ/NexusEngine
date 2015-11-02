package math;

import java.util.Vector;

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

    public static float abs(float number) {
        return Math.abs(number);
    }

    public static Vector3 normalize(Vector3 vector) {
        return new Vector3(vector).normalize();
    }

    public static Vector2 normalize(Vector2 vector) {
        return new Vector2(vector).normalize();
    }

    public static Vector3 cross(Vector3 first, Vector3 second) {
        return new Vector3(first).cross(second);
    }

    public static Matrix4 multiply(Matrix4 first, Matrix4 second) {
        Matrix4 matrix = new Matrix4(first);
        return matrix.multiply(second);
    }

    public static Vector3 multiply(Vector3 first, Vector3 second) {
        Vector3 vector = new Vector3(first);
        return vector.mul(second);
    }

    public static Vector2 multiply(Vector2 first, Vector2 second) {
        Vector2 vector = new Vector2(first);
        return vector.mul(second);
    }

    public static Vector3 sub(Vector3 first, Vector3 second) {
        Vector3 vector = new Vector3(first);
        return vector.sub(second);
    }

}
