package game;

import engine.entities.Entity;
import engine.graphics.Renderer2D;
import engine.graphics.window.Window;
import engine.input.Input;
import engine.layers.Layer2D;
import engine.math.Matrix;

/**
 * Created by vesel on 30.01.2016.
 */
public class Test2D extends Layer2D
{

    private Entity stringEntity;

    public Test2D(Window window)
    {
        super(new Renderer2D(), new Matrix().setToOrthogonal(-1, 1, -1, 1, -1, 1), window);
    }

    @Override
    public void onInit()
    {
        super.onInit();

        /*stringEntity = new Entity();
        stringEntity.addComponent(new StringComponent(new Font(ContentLoader.loadFontSheet("res/fonts/roboto-sheet.png"),
                ContentLoader.loadFontSpecification("res/fonts/roboto-spec.fnt")), "TestString"));

        add(stringEntity);*/
    }

    @Override
    public void onUpdate(Input input)
    {
        super.onUpdate(input);
    }

    @Override
    public void onRender()
    {
        super.onRender();
    }
}
