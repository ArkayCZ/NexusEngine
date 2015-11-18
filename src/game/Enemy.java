package game;

import engine.Entity;
import engine.graphics.*;
import engine.graphics.shaders.BasicShader;
import engine.input.Input;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.utils.ContentLoader;

import java.util.List;

/**
 * Created by filip on 18.11.15.
 */
public class Enemy extends Entity {

    public static float START = 0.0F;
    public static float SIZEX = 1.0f;
    public static float SIZEY = 1.0f;

    public static float TXC_X = 1.0f;
    public static float TXC_Y = 1.0f;

    private static List<Texture> sTextures;
    private static Mesh sMesh;

    public Enemy(Level context) {
        super(new Renderable(), context);
        sTextures.add(ContentLoader.loadTexture("res/sswva1.png"));

        if(sMesh == null) {
            Vertex[] vertices = new Vertex[] {
                    new Vertex(new Vector3(START, START, START), new Vector2(START, START)),
                    new Vertex(new Vector3(START, SIZEY, START), new Vector2(START, TXC_Y)),
                    new Vertex(new Vector3(SIZEX, START, START), new Vector2(TXC_X, START)),
                    new Vertex(new Vector3(SIZEX, START, START), new Vector2(TXC_X, TXC_Y))
            };

            int[] indices = new int[] {
                    0, 1, 2,
                    2, 3, 0
            };

            sMesh = new Mesh();
            sMesh.addVertices(vertices, indices);
        }

            setRenderable(new Renderable(new Material(sTextures.get(0), new Vector3(1, 1, 1))));
    }

    @Override
    public void update(Input inputStatus) {

    }
}
