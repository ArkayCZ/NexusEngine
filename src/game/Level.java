package game;

import engine.graphics.*;
import engine.graphics.shaders.BasicShader;
import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.math.Maths;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.ContentLoader;
import engine.utils.Log;

/**
 * Created by vesel on 02.11.2015.
 */
public class Level {

    public static final float TILE_SIZE = 2f;
    public static final float LEVEL_HEIGHT = 2f;

    private Renderable mMeshData;
    private Player mPlayer;
    private Bitmap mSource;

    public Level(Mesh levelMesh, Bitmap sourceBitmap) {
        mPlayer = new Player(new Vector3(11, 0.55f, 9), this);
        mMeshData = new Renderable(levelMesh, BasicShader.getInstance(), new MatrixTransformation(),
                new Material(ContentLoader.loadTexture("res/textures/spritesheet.png"), new Vector3(1, 1, 1)));

        mSource = sourceBitmap;

        MatrixTransformation.setCamera(mPlayer.getCamera());
    }

    public Vector3 checkCollision(Vector3 originalPostition, Vector3 newPostition, float objectWidth, float objectHeight) {
        Vector3 movementVector = Maths.sub(newPostition, originalPostition);
        Vector2 collisionVector = new Vector2(1, 1);

        if(movementVector.getLenght() <= 0) return new Vector3(1);

        Vector2 tileDimension = new Vector2(TILE_SIZE, TILE_SIZE);
        Vector2 objectDimension = new Vector2(objectWidth, objectHeight);

        Vector2 oldPositionVector2 = new Vector2(originalPostition.getX(), originalPostition.getZ());
        Vector2 newPositionVector2 = new Vector2(newPostition.getX(), newPostition.getZ());

        for (int x = 0; x < mSource.getWidth(); x++) {
            for(int y = 0; y < mSource.getHeight(); y++) {
                if(isSolid(x, y)) {
                    Vector2 colVec2 = check2DCollision(oldPositionVector2, newPositionVector2, objectDimension,
                            new Vector2(x, y).mul(tileDimension), tileDimension);
                    collisionVector = collisionVector.mul(colVec2);

                }

            }
        }

        return new Vector3(collisionVector.getX(), 0, collisionVector.getY());
    }

    public Vector2 check2DCollision(Vector2 oldPosition, Vector2 newPosition,
                                    Vector2 objectDimension, Vector2 blockPosition, Vector2 blockSize) {

        Vector2 collisionVector = new Vector2(0, 0);

        boolean cond1 = newPosition.getX() + objectDimension.getX() < blockPosition.getX();
        boolean cond2= newPosition.getX() - objectDimension.getX() > blockPosition.getX() + blockSize.getX() * (blockSize.getX() - 1);
        boolean cond3 = oldPosition.getY() + objectDimension.getY() < blockPosition.getY();
        boolean cond4 = oldPosition.getY() - objectDimension.getY() > blockPosition.getY() + blockSize.getY() * (blockSize.getY() - 1);

       // Log.i("C1: " + cond1 + " C2: " + cond2 + " C3: " + cond3 + " C4: " + cond4);
        if(cond1 || cond2 || cond3 || cond4) {
            collisionVector.setX(1);
        }


        cond1 = oldPosition.getX() + objectDimension.getX() < blockPosition.getX();
        cond2= oldPosition.getX() - objectDimension.getX() > blockPosition.getX() + blockSize.getX() * (blockSize.getX() - 1);
        cond3 = newPosition.getY() + objectDimension.getY() < blockPosition.getY();
        cond4 = newPosition.getY() - objectDimension.getY() > blockPosition.getY() + blockSize.getY() * (blockSize.getY() - 1);
       // Log.i("C1: " + cond1 + " C2: " + cond2 + " C3: " + cond3 + " C4: " + cond4);

        if(cond1 || cond2 || cond3 || cond4) {
            collisionVector.setY(1);
        }




        return collisionVector;
    }

    public void render(RenderingEngine engine) {
        engine.submit(mMeshData);
    }

    public void update(Input input) {
        mPlayer.update(input);
    }

    public boolean isSolid(int x, int y) {
        return (mSource.getPixel(x, y) & 0xFFFFFF) == 0;
    }
}
