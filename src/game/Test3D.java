package game;

import engine.entities.Entity;
import engine.entities.components.RenderComponent;
import engine.entities.components.TransformComponent;
import engine.graphics.*;
import engine.graphics.window.Window;
import engine.input.Input;
import engine.layers.Scene;
import engine.math.Matrix;
import engine.math.Vector3;
import engine.utils.meshes.MeshFactory;

/**
 * Created by vesel on 26.01.2016.
 */
public class Test3D extends Scene {

    Entity monkey;

    public Test3D(Window window) {
        super(new ForwardRenderer(), new Matrix().setToPerspective(
                90, window.getWidth(), window.getHeight(), 0.001f, 1000f), window);
        setCamera(new Camera());
    }

    @Override
    public void onInit() {
        super.onInit();

        Entity plane = new Entity();
        plane.addComponent(new RenderComponent(MeshFactory.generatePlaneMesh(32, 32, true, true), new Material("res/textures/brick.jpg", new Vector3(1))));
        Transform planeTransform = new Transform();
        planeTransform.getPosition().setY(-1f);
        plane.addComponent(new TransformComponent(planeTransform));



        for(int i = 0; i < 90; i++) {
            monkey = new Entity();
            monkey.addComponent(new RenderComponent(new Mesh("res/models/donut.obj"), new Material("res/textures/black.png", new Vector3(1))));
            Transform monkeyTransform = new Transform();
            monkeyTransform.getPosition().setY(i * 1f);
            monkey.addComponent(new TransformComponent(monkeyTransform));
            add(monkey);
        }
        add(plane);
    }

    @Override
    public void onUpdate(Input input) {
        super.onUpdate(input);
        getCamera().update(input);

        TransformComponent transform = monkey.getComponent(TransformComponent.class);
        transform.getRotation().setY(transform.getRotation().getY() + 1f);
    }
}
