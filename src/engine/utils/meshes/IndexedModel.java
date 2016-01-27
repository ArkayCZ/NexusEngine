package engine.utils.meshes;

import engine.math.Vector2;
import engine.math.Vector3;

import java.util.List;

/**
 * Created by vesel on 26.01.2016.
 */
public class IndexedModel {

    private List<Vector3> mPositions;
    private List<Vector2> mTextureCoordinates;
    private List<Vector3> mNormals;
    private List<Integer> mIndices;

    public IndexedModel(List<Vector3> positions, List<Vector2> textureCoordinates, List<Vector3> normals, List<Integer> indices) {
        mPositions = positions;
        mTextureCoordinates = textureCoordinates;
        mNormals = normals;
        mIndices = indices;
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
