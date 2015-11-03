package game;

import engine.NexusGame;
import engine.graphics.*;
import engine.graphics.shaders.BasicShader;
import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.ContentLoader;

/**
 * Created by vesel on 02.11.2015.
 */
public class WolfensteinClone extends NexusGame {

    private Level mLevel;

    public WolfensteinClone() {
        super("Wolfenstein3D");
        setWindowSize(1280, 800);
        start();
    }

    @Override
    public void init() {
        getGameWindow().initProjection(70f, 0.1f, 1000f);

        LevelLoader loader = new LevelLoader();
        loader.setBitmap(ContentLoader.loadBitmap("res/level/level1.png"));

        mLevel = new Level(loader.generateLevelMesh(), loader.getBitmap());
    }

    @Override
    public void update(Input inputStatus) {
        mLevel.update(inputStatus);

        if(inputStatus.isKeyDown(Input.KEY_ESC)) {
            stop();
        }

        inputStatus.update();
    }

    @Override
    public void render() {
        RenderingEngine engine = new RenderingEngine();

        mLevel.render(engine);

        engine.flush();
    }



    @Override
    public void secondlyUpdate() {

    }
}

//TODO: Create a level loader
