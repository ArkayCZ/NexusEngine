package game;

import engine.NexusGame;
import engine.graphics.*;
import engine.graphics.shaders.BasicShader;
import engine.graphics.shaders.PhongShader;
import engine.graphics.shaders.Shader;
import engine.graphics.shaders.lighting.*;
import engine.input.Input;
import engine.math.Maths;
import engine.math.Vector2;
import engine.math.Vector3;
import org.lwjgl.glfw.GLFW;
import engine.utils.ContentLoader;

/**
 * Created by vesel on 30.10.2015.
 */

public class Game extends NexusGame {

    public Game() {
        super("TestGame");
        this.setWindowSize(1280, 800);
        this.start();
    }

    public void init() {
        this.getGameWindow().initProjection(70f, 0.1f, 1000f);
        this.initializeRenderingSystem();
        this.getRenderer().setClearColor(new Vector3(0));


    }

    @Override
    public void update(Input inputStatus) {

    }

    @Override
    public void render() {

    }

    @Override
    public void secondlyUpdate() {

    }
}

