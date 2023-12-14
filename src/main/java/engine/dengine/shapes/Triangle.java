package engine.dengine.shapes;

import engine.dengine.math.MathUtil;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Triangle}</h2>
 * <br>
 * The {@link Triangle} class is used to simulate a geometrical triangle in three-dimensional space.
 * @see Shape
 */
public class Triangle extends Shape
{

    /**
     * Creates a new {@link Triangle} instance with the given <b>vertices</b>.
     *
     * @param vertices the <b>vertices</b> of the new {@link Triangle} instance, must have length of 9
     * @throws IllegalArgumentException if less or more than 9 <b>vertices</b> are provided
     */
    public Triangle (float[] vertices)
    {
        super(vertices);
        if (vertices.length != 9) throw new IllegalArgumentException("Tried to create " + getClass().getName() +
                " instance with less or more than 9 vertices: " + vertices.length);
    }

    /**
     * Checks wether this {@link Shape} instance is <b>colliding</b> with another {@link Shape} instance.
     * <b>Collision</b> means that at least one <b>vertex</b> of the other instance has to be in the area of
     * this instance.
     *
     * @param anotherShape the {@link Shape} instance to check for collision with this one
     * @return wether this instance is colliding with the other one
     */
    @Override
    public boolean collidesWith (Shape anotherShape)
    {
        // Cant be colliding if they are on different z-coordinates
        if (anotherShape.getVertices()[2] != vertices[2]) return false;

        float[] anotherVertices = anotherShape.getVertices();

        // Loop through vertices of anotherShape
        for (int i = 0; i < anotherVertices.length; i += 3) {
            // Get the current vertex of anotherVertices
            float x = anotherVertices[i];
            float y = anotherVertices[i + 1];
            float z = anotherVertices[i + 2];
            // Check if the point (x, y, z) is inside the triangle
            if (MathUtil.isPointInTriangle(this, x, y, z)) return true; // Collision detected
        }
        // No collision detected
        return false;
    }
}
