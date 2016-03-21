package engine.utils.meshes;

public class IndexOBJ
{

    private int mVertexIndex;
    private int mTextureCoordinateIndex;
    private int mNormalIndex;

    @Override
    public boolean equals(Object obj)
    {
        IndexOBJ index = (IndexOBJ) obj;

        return mVertexIndex == index.getVertexIndex() &&
                mTextureCoordinateIndex == index.getTextureCoordinateIndex() &&
                mNormalIndex == index.getNormalIndex();
    }

    @Override
    public int hashCode()
    {
        final int BASE = 97;
        final int MULTIPLIER = 47;

        int result = BASE;
        result = MULTIPLIER * result + mVertexIndex;
        result = MULTIPLIER * result + mTextureCoordinateIndex;
        result = MULTIPLIER * result + mVertexIndex;

        return result;
    }

    public int getVertexIndex()
    {
        return mVertexIndex;
    }

    public void setVertexIndex(int vertexIndex)
    {
        mVertexIndex = vertexIndex;
    }

    public int getTextureCoordinateIndex()
    {
        return mTextureCoordinateIndex;
    }

    public void setTextureCoordinateIndex(int textureCoordinateIndex)
    {
        mTextureCoordinateIndex = textureCoordinateIndex;
    }

    public int getNormalIndex()
    {
        return mNormalIndex;
    }

    public void setNormalIndex(int normalIndex)
    {
        mNormalIndex = normalIndex;
    }
}
