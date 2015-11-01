package graphics;

import utils.Utils;

import static graphics.Vertex.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by vesel on 30.10.2015.
 */
public class Mesh {

    private int mVBO, mIBO;
    private int mSize;

    public Mesh() {
        mVBO = glGenBuffers();
        mIBO = glGenBuffers();
        mSize = 0;
    }

    public void addVertices(Vertex[] vertices, int[] indices) {
        mSize = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, mVBO);
        glBufferData(GL_ARRAY_BUFFER, Utils.createVertexBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createIndexBuffer(indices), GL_STATIC_DRAW);
    }

    public void render() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, mVBO);

        // 20 stands for Vertex.SIZE * 4

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 20, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 20, 12);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIBO);
        glDrawElements(GL_TRIANGLES, mSize, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

}
