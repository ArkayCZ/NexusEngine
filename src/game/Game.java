package game;

import engine.entities.Entity;
import engine.NexusGame;
import engine.entities.components.RenderComponent;
import engine.entities.components.TransformComponent;
import engine.graphics.*;
import engine.input.Input;
import engine.math.Vector3;
import engine.utils.ContentLoader;

public class Game extends NexusGame {

    private Entity monkey;
    private Camera mCamera;

    public Game() {
        super("TestGame");
        this.setWindowSize(1280, 800);
        this.start();
    }

    public void init() {
        this.getGameWindow().initProjection(90f, 0.1f, 1000f);
        this.initializeRenderingSystem();
        this.getRenderer().setClearColor(new Vector3(0));

        mCamera = new Camera();
        ForwardRenderer.setCamera(mCamera);

        monkey = new Entity();

        Mesh mesh = ContentLoader.loadMesh("res/models/monkey3.obj");
        Material material = new Material(ContentLoader.loadTexture("res/textures/brick.jpg"), new Vector3(1, 1, 1));

        monkey.addComponent(new RenderComponent(mesh, material));
        monkey.addComponent(new TransformComponent(new Transform()));

        getRootObject().addChild(monkey);
    }

    @Override
    public void update(Input inputStatus) {
        mCamera.update(inputStatus);
    }

    @Override
    public void render() {

    }

    @Override
    public void secondlyUpdate() {

    }
}

