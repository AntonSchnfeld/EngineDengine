package engine.dengine.ecs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Entity}</h2>
 * <br>
 * The {@link Entity} class is used to represent <b>entites</b> in a <b>entity component system</b>.
 * Its behaviour is completely defined by its <b>components</b>, which are contained in the <b>entity</b>.
 * All in all, an <b>entity</b> is basically only a collection of <b>components</b>.
 * @see <a href="https://en.wikipedia.org/wiki/Entity_component_system">Entity Component System</a>
 */
public class Entity
{
    private List<Component> components;

    /**
     * Creates a new {@link Entity} instance.
     */
    public Entity ()
    {
        components = new ArrayList<>();
    }

    /**
     * Adds a {@link Component} instance to this <b>entity</b> if it is not already added.
     * @param component the {@link Component} instance
     */
    public void addComponent (Component component)
    {
        for (Component comp : components)
        {
            if (comp.getClass().equals(component.getClass()))
                return;
        }
        components.add(component);
    }

    /**
     * Removes all {@link T} from this <b>entity</b>
     * @param clazz the {@link Class} instance of {@link T}
     * @param <T> the type of the {@link T} instance
     */
    public <T extends Component> void removeComponent (Class<T> clazz)
    {
        for (Component component : components)
            if (component.getClass().equals(clazz))
                components.remove(component);
    }

    /**
     * Gets the {@link T} instance of this entity, if one was added.
     * @param clazz the {@link Class} instance of {@link T}
     * @return null if no {@link T} instance was added, otherwise the {@link T} instance of this <b>entity</b>
     * @param <T> the type of the {@link T} instance
     */
    public <T extends Component> T getComponent (Class<T> clazz)
    {
        for (Component component : components)
            if (component.getClass().isAssignableFrom(clazz)) return clazz.cast(component);
        return null;
    }

    /**
     * Updates this <b>entity</b> by calling {@link Component#update(float)} on all added {@link Component}
     * instances.
     * @param deltaTime the delta time since the last update
     */
    public void update (float deltaTime)
    {
        for (Component component : components)
            component.update(deltaTime);
    }

    /**
     * Initializes this <b>entity</b> by calling {@link Component#init()} on all added {@link Component}
     * instances.
     */
    public void init ()
    {
        for (Component component : components)
            component.init();
    }

    /**
     * Disposes of this <b>entity</b> by calling {@link Component#dispose()} on all added {@link Component}
     * instances.
     */
    public void dispose ()
    {
        for (Component component : components)
            component.dispose();
    }
}
