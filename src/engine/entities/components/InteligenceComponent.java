package engine.entities.components;

import engine.entities.IDManager;
import engine.graphics.IRenderer;
import engine.graphics.shaders.Shader;
import engine.input.Input;

/**
 * Created by vesel on 14.01.2016.
 * Component for AI. Useless currently.
 */
public class InteligenceComponent extends EntityComponent
{

    public static final int ID = IDManager.getComponentID();

    @Override
    public void onInit()
    {

    }

    @Override
    public void onRender(Shader shader, IRenderer renderer)
    {

    }

    @Override
    public void onUpdate(Input input)
    {

    }

    @Override
    public void onMap()
    {

    }

    @Override
    public void onDelete()
    {

    }
}
