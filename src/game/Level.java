package game;

import engine.graphics.*;
import engine.graphics.shaders.BasicShader;
import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.math.Vector3;
import engine.utils.ContentLoader;

/**
 * Created by vesel on 02.11.2015.
 */
public class Level {

    public static final float TILE_SIZE = 2f;
    public static final float LEVEL_HEIGHT = 2f;

    private Renderable mMeshData;
    private Player mPlayer;

    public Level(Mesh levelMesh) {
        mPlayer = new Player(new Vector3(0, 0, 0));
        mMeshData = new Renderable(levelMesh, BasicShader.getInstance(), new MatrixTransformation(),
                new Material(ContentLoader.loadTexture("res/textures/spritesheet.png"), new Vector3(1, 1, 1)));

        MatrixTransformation.setCamera(mPlayer.getCamera());
    }

    public void render(RenderingEngine engine) {
        engine.submit(mMeshData);
    }

    public void update(Input input) {
        mPlayer.update(input);
    }

}
