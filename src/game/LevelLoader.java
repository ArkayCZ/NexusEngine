package game;

import engine.graphics.Bitmap;
import engine.graphics.Mesh;
import engine.graphics.Renderable;
import engine.graphics.Vertex;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.Log;
import engine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vesel on 02.11.2015.
 * Green bit represents the texture, 0;0 in the spritesheet is the upperleft corner, supports up to 16 texture
 * 0
 * x
 * F - bottom texture (0 - F);
 * F - top texture (0 - F);
 * F - x-- wall (0 - F);
 * F - x++ wall (0 - F);
 * F - y-- wall (0 - F);
 * F - y++ wall (0 - F);
 */
public class LevelLoader {

    private Bitmap mLevelBitmap;
    private List<Vertex> mLevelVertices;
    private List<Integer> mLevelIndices;

    float x1 = 0.25f;
    float y1 = 0.25f;
    float x0 = 0;
    float y0 = 0;

    public LevelLoader() {
        mLevelVertices = new ArrayList<>();
        mLevelIndices = new ArrayList<>();
    }

    public void setBitmap(Bitmap bitmap) {
        mLevelBitmap = bitmap.flipX();
    }

    public Level createLevel() {
        Mesh mMesh = generateLevelMesh();
        Level level = new Level(mMesh, mLevelBitmap);

        for(int x = 0; x < mLevelBitmap.getWidth(); x++) {
            for(int y = 0; y < mLevelBitmap.getHeight(); y++) {
                if(((mLevelBitmap.getPixel(x, y) & 0xFFFFFF) == 0xBBBBBB))
                    level.getDoors().add(createDoor(x, y, level));
            }
        }

        return level;
    }

    public Mesh generateLevelMesh() {

        for(int x = 0; x < mLevelBitmap.getWidth(); x++) {
            for (int y = 0; y < mLevelBitmap.getHeight(); y++) {


                int rgbComponents = mLevelBitmap.getPixel(x, y) & 0xFFFFFF;
                if(rgbComponents == 0)
                    continue;

                float floorTexture = (rgbComponents & 0xF00000) >> 20;
                float topTexture = (rgbComponents & 0xF0000) >> 16;
                float xmTexture = (rgbComponents & 0xF000) >> 12;
                float xpTexture = (rgbComponents & 0xF00) >> 8;
                float ymTexture = (rgbComponents & 0xF0) >> 4;
                float ypTexture = (rgbComponents & 0xF);

                float ts = Level.TILE_SIZE;

                //Floor
                remapTexture(floorTexture);
                addFaces(true);

                mLevelVertices.add(new Vertex(new Vector3(x * ts, 0, y * ts), new Vector2(x0, y0)));
                mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 0, y * ts), new Vector2(x1, y0)));
                mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 0, (y + 1) * ts), new Vector2(x1, y1)));
                mLevelVertices.add(new Vertex(new Vector3(x * ts, 0, (y + 1) * ts), new Vector2(x0, y1)));

                //Top
                remapTexture(topTexture);
                addFaces(false);

                mLevelVertices.add(new Vertex(new Vector3(x * ts, 2, y * ts), new Vector2(x0, y0)));
                mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 2, y * ts), new Vector2(x1, y0)));
                mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 2, (y + 1) * ts), new Vector2(x1, y1)));
                mLevelVertices.add(new Vertex(new Vector3(x * ts, 2, (y + 1) * ts), new Vector2(x0, y1)));

                //Walls
                if((mLevelBitmap.getPixel(x, y - 1) & 0xFFFFFF) == 0) {
                    remapTexture(ymTexture);
                    addFaces(false);

                    mLevelVertices.add(new Vertex(new Vector3(x * ts, 0, y * ts), new Vector2(x0, y0)));
                    mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 0, y * ts), new Vector2(x1, y0)));
                    mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 2, y * ts), new Vector2(x1, y1)));
                    mLevelVertices.add(new Vertex(new Vector3(x * ts, 2, y * ts), new Vector2(x0, y1)));
                }

                if((mLevelBitmap.getPixel(x, y + 1) & 0xFFFFFF) == 0) {
                    remapTexture(ypTexture);
                    addFaces(true);

                    mLevelVertices.add(new Vertex(new Vector3(x * ts, 0, (y + 1) * ts), new Vector2(x0, y0)));
                    mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 0, (y + 1) * ts), new Vector2(x1, y0)));
                    mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 2, (y + 1) * ts), new Vector2(x1, y1)));
                    mLevelVertices.add(new Vertex(new Vector3(x * ts, 2, (y + 1) * ts), new Vector2(x0, y1)));
                }

                if((mLevelBitmap.getPixel(x - 1, y) & 0xFFFFFF) == 0) {
                    remapTexture(xmTexture);
                    addFaces(true);

                    mLevelVertices.add(new Vertex(new Vector3(x * ts, 0, (y) * ts), new Vector2(x0, y0)));
                    mLevelVertices.add(new Vertex(new Vector3(x * ts, 0, (y + 1) * ts), new Vector2(x1, y0)));
                    mLevelVertices.add(new Vertex(new Vector3(x * ts, 2, (y + 1) * ts), new Vector2(x1, y1)));
                    mLevelVertices.add(new Vertex(new Vector3(x * ts, 2, (y) * ts), new Vector2(x0, y1)));
                }

                if((mLevelBitmap.getPixel(x + 1, y) & 0xFFFFFF) == 0) {
                    remapTexture(xpTexture);
                    addFaces(false);

                    mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 0, (y) * ts), new Vector2(x0, y0)));
                    mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 0, (y + 1) * ts), new Vector2(x1, y0)));
                    mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 2, (y + 1) * ts), new Vector2(x1, y1)));
                    mLevelVertices.add(new Vertex(new Vector3((x + 1) * ts, 2, (y) * ts), new Vector2(x0, y1)));
                }
            }
        }

        Vertex[] vertices = Utils.createVertexArray(mLevelVertices);
        int[] indices = Utils.createIndexArray(mLevelIndices);

        Mesh mesh = new Mesh();
        mesh.addVertices(vertices, indices);

        return mesh;
    }

    private void remapTexture(float t) {
        float tex = t-1;
        x0 = (float)((int)tex % 4) / 4;
        y0 = (float)((int)tex / 4) / 4;
        x1 = x0 + 0.25f;
        y1 = y0 + 0.25f;
    }

    private void addFaces(boolean flip) {
        if (!flip) {
            mLevelIndices.add(mLevelVertices.size());
            mLevelIndices.add(mLevelVertices.size() + 1);
            mLevelIndices.add(mLevelVertices.size() + 2);
            mLevelIndices.add(mLevelVertices.size());
            mLevelIndices.add(mLevelVertices.size() + 2);
            mLevelIndices.add(mLevelVertices.size() + 3);
        } else {
            mLevelIndices.add(mLevelVertices.size() + 2);
            mLevelIndices.add(mLevelVertices.size() + 1);
            mLevelIndices.add(mLevelVertices.size());
            mLevelIndices.add(mLevelVertices.size() + 3);
            mLevelIndices.add(mLevelVertices.size() + 2);
            mLevelIndices.add(mLevelVertices.size());
        }
    }

    private Door createDoor(int x, int y, Level level) {
        Door door = new Door(new Renderable(WolfensteinClone.sSpriteSheet), level);
        boolean rotate = false;
        if((mLevelBitmap.getPixel(x - 1, y) & 0xFFFFFF) != 0 && (mLevelBitmap.getPixel(x + 1, y) & 0xFFFFFF) != 0)
            rotate = true;


        if(rotate) {
            door.setRotation(new Vector3(0, 90, 0));
            door.setTranslation(x * 2 + 0.9f, 0, y * 2);
            door.setDefaultTranslation(new Vector3(door.getTranslation()));
            door.setIsRotated(true);
        } else {
            door.setTranslation(x * 2, 0f, y * 2 + 0.65f);
            door.setDefaultTranslation(new Vector3(door.getTranslation()));
        }


        return door;
    }

    public Bitmap getBitmap() {
        return mLevelBitmap;
    }

}
