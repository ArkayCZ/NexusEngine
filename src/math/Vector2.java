package math;

/**
 * Created by vesel on 30.10.2015.
 */
public class Vector2 {

    private float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getLength() {
        return (float)Math.sqrt(x*x + y*y);
    }

    public float dot(Vector2 other) {
        return x * other.getX() + y * other.getY();
    }

    public Vector2 normalize() {
        float length = getLength();

        x /= length;
        y /= length;

        return this;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.getX(), y + other.getY());
    }

    public Vector2 add(float other) {
        return new Vector2(x + other, y + other);
    }

    public Vector2 sub(Vector2 other) {
        return new Vector2(x - other.getX(), y - other.getY());
    }

    public Vector2 sub(float other) {
        return new Vector2(x - other, y - other);
    }

    public Vector2 mul(Vector2 other) {
        return new Vector2(x * other.getX(), y * other.getY());
    }

    public Vector2 mul(float other) {
        return new Vector2(x * other, y * other);
    }

    public Vector2 div(Vector2 other) {
        return new Vector2(x / other.getX(), y / other.getY());
    }

    public Vector2 div(float other) {
        return new Vector2(x / other, y / other);
    }

    public Vector2 rotate(float rotation) {
        float rad = (float)Math.toRadians(rotation);
        float sin = (float)Math.sin(rad), cos = (float)Math.cos(rad);

        return new Vector2(x * cos - y * sin, x * sin + y * cos);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
