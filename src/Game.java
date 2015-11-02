import graphics.*;
import graphics.shaders.PhongShader;
import graphics.shaders.Shader;
import graphics.shaders.lighting.Attenuation;
import graphics.shaders.lighting.BaseLight;
import graphics.shaders.lighting.PointLight;
import graphics.shaders.lighting.SpotLight;
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

    PointLight light1;
    PointLight light2;
    SpotLight spotLight1;

    private float counter = 0.0f;

    public Game() {
        super("TestGame");
        setWindowSize(1280, 800);
        start();
    }

    public void init() {
        getGameWindow().initProjection(70f, 0.1f, 1000f);



        /*Vertex[] vertices = new Vertex[] { new Vertex( new Vector3(-1.0f, -1.0f, 0.5773f),	new Vector2(0.0f, 0.0f)),
                        new Vertex( new Vector3(0.0f, -1.0f, -1.15475f),		new Vector2(0.5f, 0.0f)),
                        new Vertex( new Vector3(1.0f, -1.0f, 0.5773f),		new Vector2(1.0f, 0.0f)),
                        new Vertex( new Vector3(0.0f, 1.0f, 0.0f),      new Vector2(0.5f, 1.0f)) };
        // Triangular mesh

        int indices[] = { 0, 3, 1,
                		1, 3, 2,
                		2, 3, 0,
                		1, 2, 0 };*/

        //Plane mesh
        float fieldWidth = 10.0f;
        float fieldLength = 10.0f;
        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3(-fieldWidth, -1.0f, -fieldLength), new Vector2(0, 0)),
                new Vertex(new Vector3(-fieldWidth, -1.0f, fieldLength * 3), new Vector2(0, 1)),
                new Vertex(new Vector3(fieldWidth * 3, -1.0f, -fieldLength), new Vector2(1, 0)),
                new Vertex(new Vector3(fieldWidth * 3, -1.0f, fieldLength * 3), new Vector2(1, 1)),

        };

        int[] indices = new int[] {
                0, 1, 2,
                2, 1, 3
        };

        mesh = new Mesh();
        mesh.addVertices(vertices, indices, true);

        camera = new Camera();
        MatrixTransformation.setCamera(camera);

        material = new Material(ContentLoader.loadTexture("res/textures/checkerboard.png"), new Vector3(1, 1, 1));

        shader = PhongShader.getInstance();
        PhongShader.setAmbientLight(new Vector3(0.1f, 0.1f, 0.1f));
        //PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3(1, 1, 1), 1f), new Vector3(1, 1, 1)));

        light1 = new PointLight(new BaseLight(new Vector3(1, 0, 0), 0.5f), new Attenuation(0, 0, 1f), new Vector3(-2, 0, 3), 5);
        light2 = new PointLight(new BaseLight(new Vector3(0, 0, 1), 0.5f), new Attenuation(0, 0, 1f), new Vector3(2, 0, 3), 5);
        spotLight1 = new SpotLight(new PointLight(new BaseLight(new Vector3(1, 1, 1), 10f),
                new Attenuation(0, 0, 0.1f), new Vector3(-2, 0, 3), 30f), new Vector3(1, 1, 1), 0.95f);

        PhongShader.addPointLight(light1);
        PhongShader.addPointLight(light2);
        PhongShader.addSpotLight(spotLight1);

        transform = new MatrixTransformation();
    }

    @Override
    public void update(Input inputStatus) {
        camera.update(inputStatus);
        shader.updateUniforms(transform.createTransformationMatrix(), transform.getProjectionMatrix(), material);

        light1.setPosition(new Vector3(-2 + Maths.sin(counter), -0.5f, 3 + Maths.sin(counter) * 5));
        light2.setPosition(new Vector3(2 + Maths.sin(counter), -0.5f, 3 + Maths.sin(counter) * 5));

        spotLight1.getPointLight().setPosition(camera.getPosition());
        spotLight1.setDirection(camera.getForward());

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

