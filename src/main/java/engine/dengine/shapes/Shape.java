package engine.dengine.shapes;

import engine.dengine.Constants;
import engine.dengine.ecs.Transform;
import engine.dengine.math.MathUtil;
import org.joml.Vector3f;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Shape}</h2>
 * The {@link Shape} class is used to store raw positional <b>vertices</b> of a shape.
 * Subclasses of {@link Shape} should be two-dimensional geometric shapes with three-dimensional
 * vertices, so a flat shape in 3-dimensional space. Also, all the <b>vertices</b> should be on the
 * same <b>plane</b>. It also implements a method to check if it is colliding with another {@link Shape}
 * instance ({@link Shape#collidesWith(Shape)}).
 */
public abstract class Shape
{
    protected float[] vertices;
    protected Transform transform;

    /**
     * Creates a new {@link Shape} instance with the given <b>vertices</b> with a default {@link Transform}
     * created with {@link Transform#Transform()}.
     * @param vertices the <b>vertices</b> of the new {@link Shape} instance
     * @throws IllegalArgumentException if the z-coordinate of all the <b>vertices</b> is not the same
     */
    public Shape (float[] vertices)
    {
        this(vertices, new Transform());
    }

    /**
     * Creates a new {@link Shape} instance.
     * @param vertices the vertices of the new {@link Shape} instance
     * @param transform the {@link Transform} of the new {@link Shape} instance
     * @throws IllegalArgumentException if the z-coordinate of all the <b>vertices</b> is not the same
     */
    public Shape (float[] vertices, Transform transform)
    {
        validateVertices(vertices);

        this.vertices = vertices;
        this.transform = transform;
    }

    private void validateVertices (float[] vertices)
    {
        final float z = vertices[2];
        for (int i = 2; i < vertices.length; i += 3)
            if (vertices[i] != z) throw new IllegalArgumentException("Tried to create " + getClass().getName() +
                    " with vertices on different z-coordinates");

        if (vertices.length % Constants.POSITION_SIZE != 0)
            throw new IllegalArgumentException("Tried to create " + getClass().getName() +
                    " with missing x, y or z coordinates");
    }

    /**
     * Returns the <b>vertices</b> of this {@link Shape} instance.
     * @return the <b>vertices float[]</b>
     */
    public float[] getVertices ()
    {
        return vertices;
    }

    /**
     * Returns the <b>transformed vertices</b> of this {@link Shape} instance
     * after applying <b>rotation</b>, <b>scaling</b>, and <b>translation</b>>.
     * The <b>transformation</b> is based on the {@link Shape} instances {@link Transform} instance.
     *
     * @return The <b>transformed vertices</b> as a float[].
     * @see Transform
     */
    public float[] getTransformedVertices ()
    {
        final int len = vertices.length;
        final float[] transformedVertices = new float[len];

        Vector3f rotatedVertex;
        for (int i = 0; i < len; i+= 3)
        {
            rotatedVertex = MathUtil.rotatePointOnZAxis(
                    transform.getRotation(),
                    vertices[i],
                    vertices[i + 1],
                    vertices[i + 2]);

            // Apply transformations on vertices
            transformedVertices[i] = rotatedVertex.x * transform.getScale().x + transform.getPosition().x;
            transformedVertices[i + 1] = rotatedVertex.y * transform.getScale().y + transform.getPosition().y;
            transformedVertices[i + 2] = rotatedVertex.z;
        }

        return transformedVertices;
    }

    /**
     * Returns this {@link Shape}s {@link Transform} instance.
     * @return this {@link Shape}s {@link Transform} instance
     */
    public Transform getTransform ()
    {
        return transform;
    }

    /**
     * Sets this {@link Shape}s {@link Transform} to another {@link Transform} using {@link Transform#set(Transform)}.
     * @param transform the other {@link Transform}
     */
    public void setTransform (Transform transform)
    {
        if (this.transform.equals(transform)) return;
        this.transform.set(transform);
    }

    /**
     * Returns the count of <b>vertices</b> of this {@link Shape} instance.
     * A <b>vertex</b> contains an x, y and z coordinate.
     * @return the <b>vertex</b> count
     */
    public int getVertexCount ()
    {
        return vertices.length / 3;
    }

    /**
     * Checks wether this {@link Shape} instance is <b>colliding</b> with another {@link Shape} instance.
     * <b>Collision</b> means that at least one <b>vertex</b> of the other instance has to be in the area of
     * this instance.
     * @param anotherShape the {@link Shape} instance to check for collision with this one
     * @return wether this instance is colliding with the other one
     */
    public abstract boolean collidesWith (Shape anotherShape);
}
