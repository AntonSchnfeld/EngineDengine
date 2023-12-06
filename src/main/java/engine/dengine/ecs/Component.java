package engine.dengine.ecs;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Component}</h2>
 * <br>
 * The {@link Component} class is used to represent <b>components</b> in a <b>entity component system</b>.
 * It provides overridable methods for <b>updating</b>, <b>initialising</b> and <b>disposing</b> of the
 * <b>component</b>.
 * @see <a href="https://en.wikipedia.org/wiki/Entity_component_system">Entity Component System</a>
 */
public abstract class Component
{
    /**
     * The entity which this component is bound to
     */
    protected Entity entity;

    /**
     * Creates a new {@link Component} instance.
     */
    public Component ()
    {

    }

    /**
     * Initializes the component.
     */
    public void init ()
    {

    }

    /**
     * Updates the component.
     * @param deltaTime the delta time since the last update
     */
    public void update (float deltaTime)
    {

    }

    /**
     * Disposes of the {@link Component} instance and cleans up any allocated resources.
     */
    public void dispose ()
    {

    }
}
