package game.wolf3dgame;

import engine.deprecated.Entity;
import engine.graphics.*;
import engine.graphics.deperecated.BasicShader;
import engine.input.Input;
import engine.math.Maths;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.ContentLoader;

import java.util.ArrayList;

/**
 * Created by vesel on 02.11.2015.
 */
public class Level {

    public static final float TILE_SIZE = 2f;
    public static final float LEVEL_HEIGHT = 2f;

    private Renderable mMeshData;
    private ArrayList<Door> mDoors;
    private ArrayList<Enemy> mEnemies;

    private Player mPlayer;
    private Bitmap mSource;
    //Scratchpad
    public Enemy enemy;

    public Level(Mesh levelMesh, Bitmap sourceBitmap) {
        mPlayer = new Player(new Vector3(11, 0.55f, 9), this);
        mMeshData = new Renderable(levelMesh, BasicShader.getInstance(), new Transform(),
                new Material(ContentLoader.loadTexture("res/textures/spritesheet.png"), new Vector3(1, 1, 1)));

        mSource = sourceBitmap;
        mDoors = new ArrayList<>();
        mEnemies = new ArrayList<>();


        Transform.setCamera(mPlayer.getCamera());
        Material doorMaterial = new Material(ContentLoader.loadTexture("res/textures/spritesheet.png"), new Vector3(1, 1, 1));
    }



    public void render(BufferedRenderer engine) {
        engine.submit(mMeshData);
        for(Door d : mDoors) {
            d.render(engine);
        }

        mPlayer.render(engine);

        for(Enemy e : mEnemies)
            if(e != null)
                e.render(engine);
    }

    public void update(Input input) {
        mPlayer.update(input);

        for(Door d : mDoors)
            d.update(input);

        for(Enemy e : mEnemies)
            if(e != null)
                e.update(input);
    }

    public boolean isSolid(int x, int y) {
        int pixel = mSource.getPixel(x, y) & 0xFFFFFF;
        return pixel == 0;
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

        for(Door d : mDoors) {
            Vector2 doorSize = d.isRotated() ? new Vector2(Door.DOOR_WI, Door.DOOR_TH) :
                    new Vector2(Door.DOOR_TH, Door.DOOR_WI);

            Vector2 doorPos = d.getTranslation().getXZ();
            collisionVector = collisionVector.mul(
                    check2DCollision(oldPositionVector2, newPositionVector2, objectDimension, doorPos, doorSize));
        }

        return new Vector3(collisionVector.getX(), 0, collisionVector.getY());
    }

    public boolean checkEntityCollision(Vector2 origin, Vector2 direction, Entity e) {
        Vector2 calcVector = new Vector2(origin);
        direction = direction.normalize();

        while(calcVector.getX() > 0 && calcVector.getX() < 150 && calcVector.getY() > 0  && calcVector.getY() < 150) {
            if (!check2DCollision(new Vector2(calcVector), new Vector2(calcVector).add(new Vector2(direction).mul(0.2f)), new Vector2(0.2f, 0.2f), e.getTranslation().getXZ().destoryDecPointData(), new Vector2(1, 1)).equals(new Vector2(1, 1)))
                return true;

            Vector2 blockPosition = new Vector2(calcVector).destoryDecPointData();

            if(isSolid((int)blockPosition.getX() / 2, (int)blockPosition.getY() / 2))
                return false;

            calcVector.add(new Vector2(direction).mul(0.2f));
        }

        return false;
    }

    public Vector2 check2DCollision(Vector2 oldPosition, Vector2 newPosition, Vector2 objectDimension, Vector2 blockPosition, Vector2 blockSize) {

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


    public ArrayList<Door> getDoors() {
        return mDoors;
    }

    public Vector2 getPlayerPosition() {
        return mPlayer.getCamera().getPosition().getXZ();
    }

    public ArrayList<Enemy> getEnemies() {
        return mEnemies;
    }

    public Player getPlayer() {
        return mPlayer;
    }
}
