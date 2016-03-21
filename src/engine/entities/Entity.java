package engine.entities;

import engine.MappedClass;
import engine.entities.components.EntityComponent;
import engine.entities.components.TransformComponent;
import engine.graphics.IRenderer;
import engine.graphics.shaders.Shader;
import engine.input.Input;
import engine.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vesel on 09.01.2016.
 * Basic component of a scene.
 */
public class Entity extends MappedClass
{

    /**
     * The list of all of the childern of this entity
     */
    private List<Entity> mChildren;
    /**
     * The list of all of the components of this entity
     */
    private List<EntityComponent> mComponents;

    /**
     * The parent of this entity
     */
    private Entity mParent;

    /**
     * Whether the entity is marked for removal and will be removed on next update
     */
    private boolean mShouldBeRemoved;
    /**
     * Whether the entity is already initialized
     */
    private boolean mInitialized;

    /**
     * ID of the entity assigned by the IDManager
     */
    private int mEntityID;

    private int mLastChildID = 0;

    /**
     * Initializes a new Entity and sets the next ID using 'IDMangaer'
     */
    public Entity()
    {
        super();
        mChildren = new ArrayList<>();
        mComponents = new ArrayList<>();
        mEntityID = IDManager.getEntityID();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Entity))
        {
            return false;
        }
        Entity ent = (Entity) obj;

        return ent.getID() == mEntityID;
    }

    @Override
    public int hashCode()
    {
        return mEntityID;
    }

    /**
     * Adds a child and initializes it in case it's not initialized already.
     *
     * @param child Entity to be added.
     */
    public void addChild(Entity child)
    {
        mChildren.add(child);
        child.setParent(this);

        if (!child.isInitialized())
        {
            child.onInit();
        }
    }

    /**
     * Adds a component and initializes it in case it's not initialized already.
     *
     * @param comp Component to be added.
     */
    public void addComponent(EntityComponent comp)
    {
        mComponents.add(comp);
        comp.setParentObject(this);

        if (!comp.isInitialized())
        {
            comp.onInit();
        }
    }

    /**
     * Marks the entity, all of it's children and components for removal.
     */
    public void markForRemoval()
    {
        for (Entity object : mChildren)
        {
            object.markForRemoval();
        }

        for (EntityComponent comp : mComponents)
        {
            comp.markForRemoval();
        }

        mShouldBeRemoved = true;
    }

    /**
     * Returns true if this entity should be removed.
     *
     * @return Boolean representing the above.
     */
    public boolean shouldBeRemoved()
    {
        return mShouldBeRemoved;
    }

    /**
     * Initializes the Entity AFTER it has been added to it's parent.
     */
    public void onInit()
    {
        for (Entity child : mChildren)
        {
            child.onInit();
        }

        for (EntityComponent comp : mComponents)
        {
            comp.onInit();
        }

        setInitialized(true);
    }

    /**
     * Updates all of the components and children. Removed any that are marked for removal.
     *
     * @param input The current input status.
     */
    public void onUpdate(Input input)
    {
        /* Update all children and check whether they should be removed in which case they get removed. */
        for (int i = 0; i < mChildren.size(); i++)
        {
            Entity child = mChildren.get(i);
            child.onUpdate(input);
            if (child.shouldBeRemoved())
            {
                child.onDelete();
                mChildren.remove(i);
            }
        }

        /* Update all components of this entity and checks whether they should be removed. */
        for (int i = 0; i < mComponents.size(); i++)
        {
            EntityComponent comp = mComponents.get(i);
            comp.onUpdate(input);
            if (comp.shouldBeRemoved())
            {
                comp.onDelete();
                mComponents.remove(i);
            }
        }
    }

    /**
     * Renders all of the components and Entities.
     *
     * @param shader   Shader to render the components and entities with.
     * @param renderer The renderer to render the components and entities with.
     */
    public void onRender(Shader shader, IRenderer renderer)
    {
        /* Renders all of the children. */
        for (Entity child : mChildren)
        {
            child.onRender(shader, renderer);
        }

        /* Renders all of the components. */
        for (EntityComponent comp : mComponents)
        {
            comp.onRender(shader, renderer);
        }
    }

    /**
     * Called upon the deletion of the Entity.
     */
    public void onDelete()
    {

    }

    @Override
    /**
     * Maps the entity, it's children and components into a HashMap.
     */
    public void onMap()
    {
        /* Map all of the children */
        mChildren.forEach(Entity::onMap);
        /* Map all of the components */
        mComponents.forEach(EntityComponent::onMap);
    }

    public EntityComponent getComponent(int id)
    {
        for (EntityComponent comp : mComponents)
        {
            if (comp.getComponentID() == id)
            {
                return comp;
            }
        }

        Log.e("Component with id '" + id + "' was not found!");
        return null;
    }

    public <T> T getComponent(Class<T> clazz)
    {
        if (!EntityComponent.class.isAssignableFrom(clazz))
        {
            return null;
        }
        for (EntityComponent comp : mComponents)
        {
            if (comp.getClass().equals(clazz))
            //noinspection unchecked
            {
                return (T) comp;
            }
        }

        return null;
    }

    public void setParent(Entity parent)
    {
        mParent = parent;
    }

    public boolean isInitialized()
    {
        return mInitialized;
    }

    public void setInitialized(boolean initialized)
    {
        mInitialized = initialized;
    }

    public int getID()
    {
        return mEntityID;
    }
}
