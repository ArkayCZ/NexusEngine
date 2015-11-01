import graphics.*;
import input.Input;
import org.lwjgl.glfw.GLFW;
import utils.ContentLoader;

/**
 * Created by vesel on 30.10.2015.
 */

public class Main extends NexusGame {

    private Mesh mesh;
    private Shader shader;
    private MatrixTransformation transform;
    private Camera camera;

    private float counter = 0.0f;

    public Main() {
        super("TestGame");
        setWindowSize(800, 800);
        start();
    }

    public void init() {
        getGameWindow().initProjection(70f, 0.1f, 1000f);

        camera = new Camera();
        MatrixTransformation.setCamera(camera);

        mesh = ContentLoader.loadMesh("res/models/monkey3.obj");
        //mesh = new Mesh();
        //mesh.addVertices(vertices, indices);
        shader = new Shader();
        transform = new MatrixTransformation();

        shader.attachProgram(ContentLoader.readFileAsString("res/sprite_fragment.glsl"), Shader.FRAG);
        shader.attachProgram(ContentLoader.readFileAsString("res/sprite_vertex.glsl"), Shader.VERT);

        shader.compile();

    }

    @Override
    public void update(Input inputStatus) {
        //transform.mRotation.add(new Vector3(1.5f, 0.5f, 1.0f));
        /*if(inputStatus.isKeyDown(GLFW.GLFW_KEY_R))
            transform.mPosition.add(new Vector3(0f, 0.0f, 1.0f));
        if(inputStatus.isKeyDown(GLFW.GLFW_KEY_F))
            transform.mPosition.add(new Vector3(0f, 0.0f, -1.0f));
        transform.mScale = new Vector3(2.0f);*/

        transform.mPosition.setZ(20f);

        shader.bind();
        shader.setUniformMatrix("projectionMatrix", transform.getProjectionMatrix());
        shader.unbind();

        camera.update(inputStatus);

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
