package engine.entities.components;

import engine.graphics.IRenderer;
import engine.graphics.Mesh;
import engine.graphics.font.Font;
import engine.graphics.shaders.Shader;
import engine.input.Input;

/**
 * Created by vesel on 30.01.2016.
 * A graphical representation of a string with a font. Can be rendered.
 */
public class StringComponent extends EntityComponent {

    private Font mFont;
    private String mString;
    private Mesh mMesh;

    private int mFieldWidth;

    public StringComponent(Font font, String string) {
        mFont = font;
        mString = string;
    }

    @Override
    public void onInit() {
        mMesh = mFont.constructMesh(mString);
    }

    @Override
    public void onRender(Shader shader, IRenderer renderer) {
        mFont.enableRendering();
        mMesh.render();
        mFont.disableRendering();
    }

    @Override
    public void onUpdate(Input input) {

    }

    @Override
    public void onMap() {

    }

    @Override
    public void onDelete() {
        mMesh.delete();
    }
}
