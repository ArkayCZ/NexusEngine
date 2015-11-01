import graphics.*;
import input.Input;
import graphics.shaders.BasicShader;
import math.Vector2;
import math.Vector3;
import org.lwjgl.glfw.GLFW;
import utils.ContentLoader;

/**
 * Created by vesel on 30.10.2015.
 */

public class Game extends NexusGame {

    private Mesh mesh;
    private BasicShader shader;
    private MatrixTransformation transform;
    private Camera camera;
    private Material material;

    private float counter = 0.0f;

    public Game() {
        super("TestGame");
        setWindowSize(1280, 800);
        start();
    }

    public void init() {
        getGameWindow().initProjection(70f, 0.1f, 1000f);

        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3(-1, -1, 0), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3( 0,  1, 0), new Vector2(0.5f, 0.0f)),
                new Vertex(new Vector3( 1, -1, 0), new Vector2(1.0f, 0.0f)),
                new Vertex(new Vector3( 0, -1, 1), new Vector2(0.0f, 0.5f))
        };

        int[] indices = new int[] {
                3, 1, 0,
                2, 1, 3,
                0, 1, 2,
                0, 2, 3
        };

        camera = new Camera();
        MatrixTransformation.setCamera(camera);

        //mesh = ContentLoader.loadMesh("res/models/cube.obj");
        mesh = new Mesh();
        mesh.addVertices(vertices, indices);


        material = new Material(ContentLoader.loadTexture("res/textures/checkerboard.png"), new Vector3(0, 1, 1));
        shader = new BasicShader();

        transform = new MatrixTransformation();
    }

    @Override
    public void update(Input inputStatus) {
        transform.mPosition.setZ(20f);

        camera.update(inputStatus);

        shader.updateTransformations(transform.createTransformationMatrix(), transform.getProjectionMatrix(), material);

        if(inputStatus.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
            stop();
    }

    @Override
    public void render() {
        shader.bind();
        mesh.render();
        shader.unbind();
    }

    @Override
    public void secondlyUpdate() {

    }
}

