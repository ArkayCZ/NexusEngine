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

    private Camera mCamera;
    private Mesh mLevelMesh;
    private Shader mShader;
    private MatrixTransformation mTransformation;
    private Material mLevelMaterial;
    private Mesh mesh;

    public WolfensteinClone() {
        super("Wolfenstein3D");
        setWindowSize(1280, 800);
        start();
    }

    @Override
    public void init() {
        getGameWindow().initProjection(70f, 0.1f, 1000f);



        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3(-10, -1.0f, -10), new Vector2(0, 0)),
                new Vertex(new Vector3(-10, -1.0f, 10 * 3), new Vector2(0, 1)),
                new Vertex(new Vector3(10 * 3, -1.0f, -10), new Vector2(1, 0)),
                new Vertex(new Vector3(10 * 3, -1.0f, 10 * 3), new Vector2(1, 1)),

        };

        int[] indices = new int[] {
                0, 1, 2,
                2, 1, 3
        };

        mesh = new Mesh();
        mesh.addVertices(vertices, indices, true);

        mShader = BasicShader.getInstance();
        mTransformation = new MatrixTransformation();

        mCamera = new Camera();
        mCamera.move(mCamera.getUp(), 10f);
        mCamera.rotateY(45f);
        MatrixTransformation.setCamera(mCamera);

        LevelLoader loader = new LevelLoader();
        loader.setBitmap(ContentLoader.loadBitmap("res/level/level1.png"));

        mLevelMaterial = new Material(ContentLoader.loadTexture("res/textures/spritesheet.png"), new Vector3(1, 1, 1));
        mLevelMesh = loader.generateLevelMesh();
    }

    @Override
    public void update(Input inputStatus) {
        mCamera.update(inputStatus);


        if(inputStatus.isKeyDown(Input.KEY_ESC))
            stop();
    }

    @Override
    public void render() {
        mShader.updateUniforms(mTransformation.createTransformationMatrix(), mTransformation.getProjectionMatrix(), mLevelMaterial);

        mShader.bind();
        mLevelMesh.render();
        mShader.unbind();
    }



    @Override
    public void secondlyUpdate() {

    }
}

//TODO: Create a level loader
