package engine.entities;

import engine.MappedClass;
import engine.entities.components.EntityComponent;
import engine.graphics.shaders.Shader;
import engine.input.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vesel on 09.01.2016.
 */
public class Entity extends MappedClass {

    private List<Entity> mChildren;
    private List<EntityComponent> mComponents;

    private Entity mParent;

    private boolean mShouldBeRemoved;
    private boolean mInitialized;

    /**
     * Initializes a new Entity.
     */
    public Entity() {
        super();
        mChildren = new ArrayList<>();
        mComponents = new ArrayList<>();
    }

    /**
     * Adds a child and initializes it in case it's not initialized already.
     * @param child
     */
    public void addChild(Entity child) {
        mChildren.add(child);
        child.setParent(this);

        if(!child.isInitialized())
            child.onInit();
    }

    /**
     * Adds a component and initializes it in case it's not initialized already.
     * @param comp
     */
    public void addComponent(EntityComponent comp) {
        mComponents.add(comp);
        comp.setParentObject(this);

        if(!comp.isInitialized())
            comp.onInit();
    }

    public void markForRemoval() {
        for(Entity object : mChildren)
            object.markForRemoval();

        for(EntityComponent comp : mComponents)
            comp.markForRemoval();

        mShouldBeRemoved = true;
    }

    public boolean shouldBeRemoved() {
        return mShouldBeRemoved;
    }

    public void onInit() {
        for(Entity child : mChildren)
            child.onInit();

        for(EntityComponent comp : mComponents)
            comp.onInit();

        setInitialized(true);
    }

    public void onUpdate(Input input) {
        /* Update all children and check whether they should be removed in which case they get removed. */
        for (int i = 0; i < mChildren.size(); i++) {
            Entity child = mChildren.get(i);
            child.onUpdate(input);
            if(child.shouldBeRemoved()) {
                child.onDelete();
                mChildren.remove(i);
            }
        }

        /* Update all components of this entity and checks whether they should be removed. */
        for (int i = 0; i < mComponents.size(); i++) {
            EntityComponent comp = mComponents.get(i);
                comp.onUpdate(input);
            if(comp.shouldBeRemoved()) {
                comp.onDelete();
                mComponents.remove(i);
            }
        }
    }

    public void onRender(Shader shader) {
        /* Renders all of the children. */
        for(Entity child : mChildren)
            child.onRender(shader);

        /* Renders all of the components. */
        for(EntityComponent comp : mComponents)
            comp.onRender(shader);
    }

    public void onDelete() {

    }

    @Override
    public void onMap() {

    }

    public void setParent(Entity parent) {
        mParent = parent;
    }

    public boolean isInitialized() {
        return mInitialized;
    }

    public void setInitialized(boolean initialized) {
        mInitialized = initialized;
    }
}
