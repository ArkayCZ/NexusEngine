package utils;

import graphics.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.*;

/**
 * Created by vesel on 30.10.2015.
 */
public class Utils {

    private Utils() {}

    public static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data).flip();
        return buffer;
    }

    public static IntBuffer createIntBuffer(int[] data) {
        IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data).flip();
        return buffer;
    }

    public static FloatBuffer createFloatUsingBufferUtils(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer createIntUsingBufferUtils(int size) {
        return BufferUtils.createIntBuffer(size);
    }

    public static IntBuffer createIndexBuffer(int[] indices) {
        IntBuffer buffer = createIntUsingBufferUtils(indices.length);
        buffer.put(indices).flip();
        return buffer;
    }

    public static FloatBuffer createVertexBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatUsingBufferUtils(vertices.length * Vertex.SIZE);

        for(int i = 0; i < vertices.length; i++) {
            buffer.put(vertices[i].getPosition().getX());
            buffer.put(vertices[i].getPosition().getY());
            buffer.put(vertices[i].getPosition().getZ());
        }

        buffer.flip();
        return buffer;
    }

    public static int[] createIntArray(Integer[] integers) {
        int[] result = new int[integers.length];
        for(int i = 0; i < integers.length; i++) {
            result[i] = integers[i];
        }

        return result;
    }
}
