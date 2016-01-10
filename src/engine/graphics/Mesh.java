package engine.graphics;

import engine.math.Maths;
import engine.math.Vector3;
import engine.utils.Utils;

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

    public void addVertices(Vertex[] vertices, int[] indices, boolean calculateNormals) {
        if(calculateNormals)
            assignNormals(vertices, indices);

        mSize = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, mVBO);
        glBufferData(GL_ARRAY_BUFFER, Utils.createVertexBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createIndexBuffer(indices), GL_STATIC_DRAW);
    }

    public void addVertices(Vertex[] vertices, int[] indices) {
        addVertices(vertices, indices, false);
    }

    private void assignNormals(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            int firstIndex = indices[i];
            int secondIndex = indices[i + 1];
            int thirdIndex = indices[i + 2];

            Vector3 firstVertex = Maths.sub(vertices[secondIndex].getPosition(), vertices[firstIndex].getPosition());
            Vector3 secondVertex = Maths.sub(vertices[thirdIndex].getPosition(), vertices[firstIndex].getPosition());

            Vector3 normalVector = Maths.cross(firstVertex, secondVertex).normalize();

            vertices[firstIndex].setNormal(vertices[firstIndex].getNormal().add(normalVector));
            vertices[secondIndex].setNormal(vertices[secondIndex].getNormal().add(normalVector));
            vertices[thirdIndex].setNormal(vertices[thirdIndex].getNormal().add(normalVector));
        }

        //TODO: in case this doesn't work just rewrite it the way it was, but it should.
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].getNormal().normalize();
        }
                
    }

    public void render() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, mVBO);

        // 32 stands for Vertex.SIZE * 4 (SIZE = 8 or at least it was like this when i created this)

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIBO);
        glDrawElements(GL_TRIANGLES, mSize, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

}
