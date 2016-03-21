package engine.utils;

import engine.graphics.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vesel on 30.10.2015.
 */
public class Utils
{

    private Utils()
    {
    }

    public static void die(String message)
    {
        Log.e(message);
        System.exit(0);
    }

    public static FloatBuffer createFloatBuffer(float[] data)
    {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data).flip();
        return buffer;
    }

    public static IntBuffer createIntBuffer(int[] data)
    {
        IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data).flip();
        return buffer;
    }

    public static FloatBuffer createFloatUsingBufferUtils(int size)
    {
        return BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer createIntUsingBufferUtils(int size)
    {
        return BufferUtils.createIntBuffer(size);
    }

    public static IntBuffer createIndexBuffer(int[] indices)
    {
        IntBuffer buffer = createIntUsingBufferUtils(indices.length);
        buffer.put(indices).flip();
        return buffer;
    }

    public static FloatBuffer createVertexBuffer(Vertex[] vertices)
    {
        FloatBuffer buffer = createFloatUsingBufferUtils(vertices.length * Vertex.SIZE);

        for (Vertex vertex : vertices)
        {
            buffer.put(vertex.getPosition().getX());
            buffer.put(vertex.getPosition().getY());
            buffer.put(vertex.getPosition().getZ());
            buffer.put(vertex.getTextureCoordinate().getX());
            buffer.put(vertex.getTextureCoordinate().getY());
            buffer.put(vertex.getNormal().getX());
            buffer.put(vertex.getNormal().getY());
            buffer.put(vertex.getNormal().getZ());
        }

        buffer.flip();
        return buffer;
    }

    public static int[] createIntArray(Integer[] integers)
    {
        int[] result = new int[integers.length];
        for (int i = 0; i < integers.length; i++)
        {
            result[i] = integers[i];
        }

        return result;
    }

    public static Vertex[] createVertexArray(List<Vertex> vertices)
    {
        Vertex[] vertexArray = new Vertex[vertices.size()];
        vertices.toArray(vertexArray);

        return vertexArray;
    }

    public static int[] createIndexArray(List<Integer> indices)
    {
        int[] indexArray = new int[indices.size()];

        for (int i = 0; i < indices.size(); i++)
        {
            indexArray[i] = indices.get(i);
        }

        return indexArray;
    }

    public static String[] removeEmptyStrings(String[] strings)
    {
        List<String> result = new ArrayList<>();
        for (String s : strings)
        {
            if (!s.equals(""))
            {
                result.add(s);
            }
        }

        strings = new String[result.size()];
        result.toArray(strings);

        return strings;
    }
}
