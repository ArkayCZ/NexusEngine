package game;

import engine.GameObject;
import engine.NexusGame;
import engine.components.RenderingComponent;
import engine.graphics.Camera;
import engine.graphics.ForwardRenderer;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.input.Input;
import engine.math.Maths;
import engine.math.Vector3;
import engine.utils.ContentLoader;

public class Game extends NexusGame {

    private GameObject monkey;
    private Camera cam;

    public Game() {
        super("TestGame");
        this.setWindowSize(1280, 800);
        this.start();
    }

    public void init() {
        this.getGameWindow().initProjection(90f, 0.1f, 1000f);
        this.initializeRenderingSystem();
        this.getRenderer().setClearColor(new Vector3(0));

        cam = new Camera();
        ForwardRenderer.setCamera(cam);

        monkey = new GameObject();

        Mesh mesh = ContentLoader.loadMesh("res/models/monkey3.obj");
        Material material = new Material(ContentLoader.loadTexture("res/textures/brick.jpg"), new Vector3(1, 1, 1));

        monkey.addComponent(new RenderingComponent(mesh, material));
        monkey.getTransform().setPosition(0, 0, -2f);
        monkey.getTransform().setScale(new Vector3(2));

        getRootObject().addChild(monkey);
    }

    @Override
    public void update(Input inputStatus) {
        cam.update(inputStatus);
    }

    @Override
    public void render() {

    }

    @Override
    public void secondlyUpdate() {

    }
}

