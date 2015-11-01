package graphics;

import math.Vector3;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by vesel on 30.10.2015.
 */
public class DisplayList {

    private int mListID;

    public DisplayList(Vector3[] vertices, Vector3[] colors) {
        mListID = glGenLists(1);
        begin();
        for(int i = 0; i < vertices.length; i++) {
            setColor(colors[i].getX(), colors[i].getY(), colors[i].getZ(), 1.0f);
            addVertex(vertices[i]);
        }
        end();
    }

    public void begin() {
        glNewList(mListID, GL_COMPILE);
        glBegin(GL_TRIANGLES);
    }

    public void setColor(float r, float g, float b, float a) {
        glColor4f(r, g, b, a);
    }

    public void addVertex(Vector3 pos) {
        glVertex3f(pos.getX(), pos.getY(), pos.getZ());
    }

    public void end() {
        glEnd();
        glEndList();
    }

    public void render() {
        glCallList(mListID);
    }

}
