package engine.utils.meshes;

import engine.graphics.Vertex;
import engine.math.Maths;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vesel on 26.01.2016.
 */
public class IndexedModel {

    private List<Vector3> mPositions;
    private List<Vector2> mTextureCoordinates;
    private List<Vector3> mNormals;
    private List<Integer> mIndices;

    private boolean mContainsNormals;
    private boolean mContainsTextureCoords;

    public IndexedModel(List<Vector3> positions, List<Vector2> textureCoordinates, List<Vector3> normals, List<Integer> indices) {
        mPositions = positions;
        mTextureCoordinates = textureCoordinates;
        mNormals = normals;
        mIndices = indices;
    }

    public IndexedModel() {
        mPositions = new ArrayList<>();
        mTextureCoordinates = new ArrayList<>();
        mNormals = new ArrayList<>();
        mIndices = new ArrayList<>();
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

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].getNormal().normalize();
        }

    }

    public void calculateNormals() {
        Integer[] integerData = new Integer[mIndices.size()];
        mIndices.toArray(integerData);
        int[] indices = Utils.createIntArray(integerData);
        Vertex[] vertices = new Vertex[mPositions.size()];
        for(int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(mPositions.get(i), mTextureCoordinates.get(i), mNormals.get(i));
        }

        assignNormals(vertices, indices);
    }

    public boolean containsNormals() {
        return mContainsNormals;
    }

    public void setContainsNormals(boolean containsNormals) {
        mContainsNormals = containsNormals;
    }

    public boolean containsTextureCoords() {
        return mContainsTextureCoords;
    }

    public void setContainsTextureCoords(boolean containsTextureCoords) {
        mContainsTextureCoords = containsTextureCoords;
    }

    public List<Vector3> getPositions() {
        return mPositions;
    }

    public void setPositions(List<Vector3> positions) {
        mPositions = positions;
    }

    public List<Vector2> getTextureCoordinates() {
        return mTextureCoordinates;
    }

    public void setTextureCoordinates(List<Vector2> textureCoordinates) {
        mTextureCoordinates = textureCoordinates;
    }

    public List<Vector3> getNormals() {
        return mNormals;
    }

    public void setNormals(List<Vector3> normals) {
        mNormals = normals;
    }

    public List<Integer> getIndices() {
        return mIndices;
    }

    public void setIndices(List<Integer> indices) {
        mIndices = indices;
    }
}
