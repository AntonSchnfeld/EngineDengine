package engine.dengine.shapes;

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

    /**
     * Creates a new {@link Shape} instance with the given <b>vertices</b>.
     * @param vertices the <b>vertices</b> of the new {@link Shape} instance
     */
    public Shape (float[] vertices)
    {
        this.vertices = vertices;
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
