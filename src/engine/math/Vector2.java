package engine.math;

/**
 * Created by vesel on 30.10.2015.
 */
public class Vector2 {

    private float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vector) {
        this(vector.x, vector.y);
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

    public Vector2 abs() {
        x = Maths.abs(x);
        y = Maths.abs(y);

        return this;
    }

    public float distance(Vector2 other) {
        float deltaX = Maths.abs(x - other.getX());
        float deltaY = Maths.abs(y - other.getY());

        return Maths.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public Vector2 add(Vector2 other) {
        x += other.getX();
        y += other.getY();
        return this;
    }

    public Vector2 add(float other) {
        x += other;
        y += other;
        return this;
    }

    public Vector2 sub(Vector2 other) {
        x -= other.getX();
        y -= other.getY();
        return this;
    }

    public Vector2 sub(float other) {
        x -= other;
        y -= other;
        return this;
    }

    public Vector2 mul(Vector2 other) {
        x *= other.getX();
        y *= other.getY();
        return this;
    }

    public Vector2 mul(float other) {
        x *= other;
        y *= other;
        return this;
    }

    public Vector2 div(Vector2 other) {
        x /= other.getX();
        y /= other.getY();
        return this;
    }

    public Vector2 div(float other) {
        x /= other;
        y /= other;
        return this;
    }

    public Vector2 rotate(float rotation) {
        float rad = (float)Math.toRadians(rotation);
        float sin = (float)Math.sin(rad), cos = (float)Math.cos(rad);

        return new Vector2(x * cos - y * sin, x * sin + y * cos);
    }

    public Vector2 destoryDecPointData() {
        x = (int) x;
        y = (int) y;

        return this;
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

    public boolean equals(Vector2 obj) {
        return obj.getX() == x && obj.getY() == y;
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}
