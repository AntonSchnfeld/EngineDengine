package engine.dengine.graphics;

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
     * @throws IllegalArgumentException if the z-coordinate of all the <b>vertices</b> are not the same or the array
     * does not have length 9
     */
    public Triangle(float[] vertices) {
        super(vertices);
        validateVertices(vertices);
    }

    private void validateVertices (float[] vertices)
    {
        if (vertices.length != 9)
            throw new IllegalArgumentException("Tried to create triangle with insufficient amount of vertices: " + vertices.length + " (9 required");
        if (vertices[2] != vertices[5] || vertices[5] != vertices[8] || vertices[2] != vertices[8])
            throw new IllegalArgumentException("Tried to create triangle with vertices on different z-coordinates");
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
    public boolean collidesWith(Shape anotherShape) {
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
            if (isPointInTriangle(x, y, z, vertices)) return true; // Collision detected
        }
        // No collision detected
        return false;
    }

    private boolean isPointInTriangle (float x, float y, float z, float[] triangleVertices)
    {
        // Implementation of a point-in-triangle test using barycentric coordinates
        // This involves calculating barycentric coordinates for the point (x, y, z) with respect to the triangle
        // Check if all barycentric coordinates are within the range [0, 1]
        // If they are, the point is inside the triangle

        // Extract vertices of the triangle
        float x1 = triangleVertices[0];
        float y1 = triangleVertices[1];
        float z1 = triangleVertices[2];
        float x2 = triangleVertices[3];
        float y2 = triangleVertices[4];
        float z2 = triangleVertices[5];
        float x3 = triangleVertices[6];
        float y3 = triangleVertices[7];
        float z3 = triangleVertices[8];

        // Calculate barycentric coordinates
        float detT = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
        float alpha = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / detT;
        float beta = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / detT;
        float gamma = 1 - alpha - beta;

        // Check if the point is inside the triangle
        return alpha >= 0 && beta >= 0 && gamma >= 0 && alpha <= 1 && beta <= 1 && gamma <= 1;
    }
}
