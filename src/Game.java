import graphics.*;
import graphics.shaders.PhongShader;
import graphics.shaders.Shader;
import input.Input;
import math.Maths;
import math.Vector2;
import math.Vector3;
import org.lwjgl.glfw.GLFW;
import utils.ContentLoader;

/**
 * Created by vesel on 30.10.2015.
 */

public class Game extends NexusGame {

    private Mesh mesh;
    private Shader shader;
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

        /*Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3(-1, -1, 0), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3( 0,  1, 0), new Vector2(0.5f, 0.0f)),
                new Vertex(new Vector3( 1, -1, 0), new Vector2(1.0f, 0.0f)),
                new Vertex(new Vector3( 0, -1, 1), new Vector2(0.0f, 0.5f))
        };*/

        Vertex[] vertices = new Vertex[] { new Vertex( new Vector3(-1.0f, -1.0f, 0.5773f),	new Vector2(0.0f, 0.0f)),
                        new Vertex( new Vector3(0.0f, -1.0f, -1.15475f),		new Vector2(0.5f, 0.0f)),
                        new Vertex( new Vector3(1.0f, -1.0f, 0.5773f),		new Vector2(1.0f, 0.0f)),
                        new Vertex( new Vector3(0.0f, 1.0f, 0.0f),      new Vector2(0.5f, 1.0f)) };
        //

        int indices[] = { 0, 3, 1,
                		1, 3, 2,
                		2, 3, 0,
                		1, 2, 0 };

        mesh = new Mesh();
        mesh.addVertices(vertices, indices, true);

        camera = new Camera();
        MatrixTransformation.setCamera(camera);

        material = new Material(ContentLoader.loadTexture("res/textures/checkerboard.png"), new Vector3(1, 1, 1));

        shader = PhongShader.getInstance();
        PhongShader.setAmbientLight(new Vector3(0.1f, 0.1f, 0.1f));
        PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3(1, 1, 1), 1f), new Vector3(1, 1, 1)));

        transform = new MatrixTransformation();
    }

    @Override
    public void update(Input inputStatus) {
        transform.mPosition.setZ(10f);
        //transform.mPosition.setX(Maths.sin(counter) * 5);
        //transform.mPosition.setY(Maths.sin(counter) * 5);
        //transform.mRotation.setX(Maths.sin(counter) * 360);
        //transform.mRotation.setY(Maths.sin(counter) * 360);

        transform.mPosition.setX(Maths.sin(counter) * 5);
        transform.mRotation.setY(Maths.sin(counter) * 180);
        camera.update(inputStatus);
        shader.updateUniforms(transform.createTransformationMatrix(), transform.getProjectionMatrix(), material);

        counter += 1f;

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

