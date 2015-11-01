package graphics.deperecated;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by vesel on 30.10.2015.
 * Represents a wrapper for the OpenGL immediate rendering mode and can be used in case VBOs are not supported or if you need to draw a
 * simple mesh like a rectangle. Usage should be avoided if possible.
 */
public class ImmediateRenderer {

    /**
     * Variables to replace OpenGL rendering modes so that GL11 does not have to be imported
     */
    public static final int TRIANGLES = GL_TRIANGLES;
    public static final int QUADS = GL_QUADS;

    /**
     * Wrapper for glBegin(mode);
     * @param mode Rendering mode, one of the constants in this class.
     */
    public void begin(int mode) {
        glBegin(mode);
    }

    /**
     * Ends a mesh. Wrapper for glEnd();
     */
    public void end() {
        glEnd();
    }

    /**
     * Sets a color for the Vertex, wrapper for glColor4f(r, g, b, a);
     * @param r Red color element.
     * @param g Green color element.
     * @param b Blue color element.
     * @param a Alpha transparency element.
     */
    public void setColor(float r, float g, float b, float a) {
        glColor4f(r, g, b, a);
    }


    /**
     * Adds a vertex to the mesh. Wrapper for glVertex3f();
     * @param x X coordinate of the vertex.
     * @param y Y coordinate of the vertex.
     * @param z Z coordinate of the vertex.
     */
    public void addVertex(float x, float y, float z) {
        glVertex3f(x, y, z);
    }

}
