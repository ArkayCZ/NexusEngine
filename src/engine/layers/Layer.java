package engine.layers;

import engine.MappedClass;
import engine.entities.Entity;
import engine.entities.components.EntityComponent;
import engine.graphics.ForwardRenderer;
import engine.graphics.IRenderer;
import engine.graphics.window.Window;
import engine.input.Input;
import engine.math.Matrix;

public class Layer extends MappedClass {

    private IRenderer mRenderer;
    private Matrix mProjection;
    private Window mWindow;

    private Entity mRoot;

    public Layer(IRenderer renderer, Matrix projection, Window window) {
        this(projection, window);
        mRenderer = renderer;
    }

    public Layer(Matrix projection, Window window) {
        mProjection = projection;
        mWindow = window;
        mRoot = new Entity();
    }

    public void onInit() {
        if(mRenderer == null) {
            mRenderer = new ForwardRenderer();
            mRenderer.setProjection(mProjection);
        }

        mRoot.onInit();
    }

    public void onRender() {
        mRenderer.submit(mRoot);
        mRenderer.flush();
    }

    public void onUpdate(Input input) {
        mRoot.onUpdate(input);
    }

    public void onDelete() {
        mRoot.onDelete();
    }

    @Override
    public void onMap() {
        mRoot.onMap();
    }

    public Entity getRoot() {
        return mRoot;
    }

    public void add(Entity e) {
        getRoot().addChild(e);
    }

    public void add(EntityComponent component) {
        getRoot().addComponent(component);
    }

    public IRenderer getRenderer() {
        return mRenderer;
    }

    public void setRenderer(IRenderer renderer) {
        mRenderer = renderer;
    }

    public Matrix getProjection() {
        return mProjection;
    }

    public void setProjection(Matrix projection) {
        mProjection = projection;
    }

    public Window getWindow() {
        return mWindow;
    }

    public void setWindow(Window window) {
        mWindow = window;
    }

    public void setRoot(Entity root) {
        mRoot = root;
    }
}
