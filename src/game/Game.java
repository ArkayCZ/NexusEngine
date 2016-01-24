package game;

import engine.entities.Entity;
import engine.NexusGame;
import engine.entities.components.RenderComponent;
import engine.entities.components.TransformComponent;
import engine.graphics.*;
import engine.graphics.shaders.lighting.Attenuation;
import engine.graphics.shaders.lighting.PointLight;
import engine.graphics.shaders.lighting.SpotLight;
import engine.input.Input;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.ContentLoader;
import engine.utils.Log;

public class Game extends NexusGame {

    private Entity monkey;
    private Camera mCamera;
    private Transform mTransform;

    private SpotLight light;
    private PointLight mPointLight;

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
        Transform.setCamera(mCamera);
        ForwardRenderer.setCamera(mCamera);

        monkey = new Entity();

        Mesh mesh = new Mesh();
        mesh.addVertices(new Vertex[] {
                new Vertex(new Vector3(0, -1, 0), new Vector2(0, 0)),
                new Vertex(new Vector3(10, -1, 0), new Vector2(1, 0)),
                new Vertex(new Vector3(10, -1, 10), new Vector2(1, 1)),
                new Vertex(new Vector3(0, -1, 10), new Vector2(0, 1))
        }, new int[] {
                2, 1, 0,
                0, 3, 2
        }, true);

        Material material = new Material(ContentLoader.loadTexture("res/textures/spritesheet.png"), new Vector3(1, 1, 1));

        mTransform = new Transform();
        monkey.addComponent(new RenderComponent(mesh, material));
        monkey.addComponent(new TransformComponent(mTransform));

        getRootObject().addChild(monkey);
    }

    @Override
    public void update(Input inputStatus) {
        Transform.getCamera().update(inputStatus);
    }

    @Override
    public void render() {
    }

    @Override
    public void secondlyUpdate() {

    }
}

