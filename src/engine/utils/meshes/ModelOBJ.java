package engine.utils.meshes;

import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.Log;
import engine.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vesel on 26.01.2016.
 * Represents an OBJ wavefront model.
 */
public class ModelOBJ {

    private List<Vector3> mPositions;
    private List<Vector2> mTextureCoordinates;
    private List<Vector3> mNormals;
    private List<IndexOBJ> mIndices;

    private boolean mContainsTextureCoords;
    private boolean mContainsNormalVectors;

    public ModelOBJ(String path) {
        mPositions = new ArrayList<>();
        mTextureCoordinates = new ArrayList<>();
        mNormals = new ArrayList<>();
        mIndices = new ArrayList<>();

        mContainsTextureCoords = false;
        mContainsNormalVectors = false;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;

            while((line = reader.readLine()) != null) {
                if(line.equals(""))
                    continue;
                String[] data = line.split(" ");
                Utils.removeEmptyStrings(data);

                if(data.length == 0 || data[0].equals("#")) continue;

                if(data[0].equals("v"))
                    mPositions.add(new Vector3(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3])));
                else if(data[0].equals("vt"))
                    mTextureCoordinates.add(new Vector2(Float.parseFloat(data[1]), 1.0f - Float.parseFloat(data[2])));
                else if(data[0].equals("vn"))
                    mNormals.add(new Vector3(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3])));
                else if(data[0].equals("f")) {
                    for (int i = 0; i < data.length - 3; i++) {
                        mIndices.add(parseIndex(data[1]));
                        mIndices.add(parseIndex(data[2 + i]));
                        mIndices.add(parseIndex(data[3 + i]));
                    }
                }
            }
            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }

    private IndexOBJ parseIndex(String data) {
        if(data == null)
            return null;
        String[] values = data.split("/");

        IndexOBJ result = new IndexOBJ();
        result.setVertexIndex(Integer.parseInt(values[0]) - 1);

        if(values.length > 1) {
            if(!values[1].isEmpty()) {
                mContainsTextureCoords = true;
                result.setTextureCoordinateIndex(Integer.parseInt(values[1]) - 1);
            }

            if(values.length > 2) {
                mContainsNormalVectors = true;
                result.setNormalIndex(Integer.parseInt(values[2]) - 1);
            }
        }

        return result;
    }

    public IndexedModel createIndexedModel() {
        IndexedModel result = new IndexedModel();
        IndexedModel normalModel = new IndexedModel();
        HashMap<IndexOBJ, Integer> resultIndexMap = new HashMap<>();
        HashMap<Integer, Integer> normalIndexMap = new HashMap<>();
        HashMap<Integer, Integer> indexMap = new HashMap<>();

        for (int i = 0; i < mIndices.size(); i++) {
            IndexOBJ index = mIndices.get(i);

            Vector3 pos = mPositions.get(index.getVertexIndex());
            Vector2 textureCoord = mContainsTextureCoords ? mTextureCoordinates.get(index.getTextureCoordinateIndex()): new Vector2(0);
            Vector3 normal = mContainsNormalVectors ? mNormals.get(index.getNormalIndex()): new Vector3(0);

            Integer modelVertexIndex = resultIndexMap.get(index);

            if(modelVertexIndex == null) {
                modelVertexIndex = result.getPositions().size();
                resultIndexMap.put(index, modelVertexIndex);


                result.getPositions().add(pos);
                result.getTextureCoordinates().add(textureCoord);
                if(mContainsNormalVectors)
                    result.getNormals().add(normal);
            }

            Integer normalModelIndex = normalIndexMap.get(index.getVertexIndex());

            if(normalModelIndex == null) {
                normalModelIndex = normalModel.getPositions().size();
                normalIndexMap.put(index.getVertexIndex(), normalModelIndex);

                normalModel.getPositions().add(pos);
                normalModel.getTextureCoordinates().add(textureCoord);
                normalModel.getNormals().add(normal);
            }

            result.getIndices().add(modelVertexIndex);
            normalModel.getIndices().add(normalModelIndex);
            indexMap.put(modelVertexIndex, normalModelIndex);
        }

        if(!mContainsNormalVectors) {
            normalModel.calculateNormals();

            for(int i = 0; i < result.getPositions().size(); i++)
                result.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
        }

        return result;
    }

    public boolean containsTextureCoords() {
        return mContainsTextureCoords;
    }

    public void setContainsTextureCoords(boolean containsTextureCoords) {
        mContainsTextureCoords = containsTextureCoords;
    }

    public boolean containsNormals() {
        return mContainsNormalVectors;
    }

    public void setContainsNormalVectors(boolean containsNormalVectors) {
        mContainsNormalVectors = containsNormalVectors;
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

    public List<IndexOBJ> getIndices() {
        return mIndices;
    }

    public void setIndices(List<IndexOBJ> indices) {
        mIndices = indices;
    }

}
