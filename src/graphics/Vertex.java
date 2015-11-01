package graphics;

import math.Vector3;

/**
 * Created by vesel on 30.10.2015.
 */
public class Vertex {

    public static final int SIZE = 3;

    private Vector3 mPosition;

    public Vertex(Vector3 position) {
        mPosition = position;
    }

    public Vector3 getPosition() {
        return mPosition;
    }

    public void setPosition(Vector3 position) {
        mPosition = position;
    }

}
