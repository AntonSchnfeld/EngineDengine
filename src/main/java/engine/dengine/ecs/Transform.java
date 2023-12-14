package engine.dengine.ecs;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Transform}</h2>
 * <br>
 * The {@link Transform} class is used to store the <b>transformations</b> which are applied on <b>vertices</b>.
 * A {@link Transform} object stores <b>transformations</b> on <b>position</b>, <b>scale</b> and <b>rotation</b>
 * in degrees.
 * <br>
 * A <b>translation</b> in is applied by adding the <b>x </b> and <b>y translation</b> to the <b>x</b> and <b>y
 * coordinates</b>, respectively. <b>Scaling</b> is applied by multiplying the <b>x</b> and <b>y coordinate</b>
 * with the <b>x</b> and <b>y scaling factors</b>. <b>Rotation</b> is applied by <b>rotating</b> each point around
 * the <b>z-axis</b> using a <b>rotation matrix</b>.
 * @see engine.dengine.math.MathUtil#rotatePointOnZAxis(float, float, float, float)
 */
public class Transform
{
    /** Positional transformations */
    private Vector3f position;
    /** Transformations regarding width and height */
    private Vector2f scale;
    /** Transformations on rotation */
    private float rotation;

    /**
     * Creates a new {@link Transform} instance with a <b>position</b> of 1, 1, 1, a <b>scale</b> of 1, 1
     * and a <b>rotation</b> of 0.
     */
    public Transform ()
    {
        this(new Vector3f(1, 1, 1), new Vector2f(1, 1), 0);
    }

    /**
     * Creates a new {@link Transform} instance with the copied data of another {@link Transform} instance.
     * @param transform the {@link Transform} instance which the data is copied from
     */
    public Transform (Transform transform)
    {
        this(new Vector3f(transform.position), new Vector2f(transform.scale), transform.rotation);
    }

    /**
     * Creates a new {@link Transform} instance with a <b>rotation</b> of 0.
     * @param position the <b>positional transformations</b>
     * @param scale the <b>scale transformations</b>
     */
    public Transform (Vector3f position, Vector2f scale)
    {
        this(position, scale, 0);
    }

    /**
     * Creates a new {@link Transform} instance.
     * @param position the <b>positional transformations</b>
     * @param scale the <b>scale transformations</b>
     * @param rotation the <b>rotational transformation</b>
     */
    public Transform (Vector3f position, Vector2f scale, float rotation)
    {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    /**
     * Sets the position.
     * @param position the new <b>position</b>
     */
    public void setPosition (Vector3f position)
    {
        this.position.set(position);
    }

    /**
     * Sets the scale.
     * @param scale the new <b>scale</b>
     */
    public void setScale (Vector2f scale)
    {
        this.scale.set(scale);
    }

    /**
     * Sets the <b>rotation</b>.
     * @param rotation the new <b>rotation</b>
     */
    public void setRotation (float rotation)
    {
        this.rotation = rotation;
    }

    /**
     * Returns the <b>position</b>.
     * @return the <b>position</b>
     */
    public Vector3f getPosition ()
    {
        return position;
    }

    /**
     * Returns the <b>scale</b>.
     * @return the <b>scale</b>
     */
    public Vector2f getScale ()
    {
        return scale;
    }

    /**
     * Returns the <b>rotation</b>.
     * @return the <b>rotation</b>
     */
    public float getRotation ()
    {
        return rotation;
    }

    /**
     * Sets all attributes of this {@link Transform} instance to that of another {@link Transform} instance
     * without sharing <b>references</b>.
     * @param transform the other {@link Transform} instance
     */
    public void set (Transform transform)
    {
        this.position.set(transform.position);
        this.scale.set(transform.scale);
        this.rotation = transform.rotation;
    }

    /**
     * Indicates wether this {@link Transform} instance is "equal" to another {@link Object} instance.
     * The reference object is "equal" to this instance if it is of type {@link Transform} and has "equal"
     * properties to this one.
     * @param obj the reference object
     * @return wether this instance and the reference object are "equal"
     */
    @Override
    public boolean equals (Object obj)
    {
        if (obj == null) return false;
        if (obj instanceof Transform transform)
            return transform.position.equals(position) && transform.scale.equals(scale)
                    && transform.rotation == rotation;
        return false;
    }
}
