package engine.dengine.math;

import engine.dengine.shapes.Rectangle;
import engine.dengine.shapes.Triangle;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link MathUtil}</h2>
 * <br>
 * The {@link MathUtil} class is used to perform useful mathematical calculations and checks.
 */
public class MathUtil
{
    // Prevent instantiation of utility class
    private MathUtil () {}

    /**
     * Checks wether a three-dimensional point is within the boundaries of a {@link Rectangle} instance.
     * Uses <b>barycentric</b> coordinates to check for <b>collision</b>, where the rectangle is divided
     * into two triangles which each get checked separately.
     * @param r the {@link Rectangle} instance
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     * @return wether the point lies within the boundaries of the {@link Rectangle} instance
     */
    public static boolean isPointInRectangle(Rectangle r, float x, float y, float z)
    {
        float[] rectangleVertices = r.getVertices();

        // If they are on different z indexes, they can't be colliding
        if (z != rectangleVertices[2]) return false;

        // Extract vertices of the rectangle
        float x1 = rectangleVertices[0];
        float y1 = rectangleVertices[1];
        float x2 = rectangleVertices[3];
        float y2 = rectangleVertices[4];
        float x3 = rectangleVertices[6];
        float y3 = rectangleVertices[7];
        float x4 = rectangleVertices[9];
        float y4 = rectangleVertices[10];

        // First triangle
        // Calculate barycentric coordinates for the first triangle
        float detT1 = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
        float alpha1 = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / detT1;
        float beta1 = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / detT1;
        float gamma1 = 1 - alpha1 - beta1;

        // Check if the point is inside the first triangle
        if (alpha1 >= 0 && beta1 >= 0 && gamma1 >= 0) return true;

        // Second Triangle
        // Calculate barycentric coordinates for the second triangle
        float detT2 = (y4 - y1) * (x3 - x1) + (x1 - x4) * (y3 - y1);
        float alpha2 = ((y4 - y1) * (x - x1) + (x1 - x4) * (y - y1)) / detT2;
        float beta2 = ((y1 - y3) * (x - x1) + (x3 - x1) * (y - y1)) / detT2;
        float gamma2 = 1 - alpha2 - beta2;

        // Check if the point is inside the second triangle
        return alpha2 >= 0 && beta2 >= 0 && gamma2 >= 0;
    }

    /**
     * Checks wether a three-dimensional point is within the boundaries of a {@link Triangle} instance.
     * Uses <b>barycentric</b> coordinates to check for <b>collision</b>.
     * @param t the {@link Triangle} instance
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     * @return wether the point lies within the boundaries of the {@link Triangle} instance
     */
    public static boolean isPointInTriangle (Triangle t, float x, float y, float z)
    {
        // Implementation of a point-in-triangle test using barycentric coordinates
        // This involves calculating barycentric coordinates for the point (x, y, z) with respect to the triangle
        // Check if all barycentric coordinates are within the range [0, 1]
        // If they are, the point is inside the triangle

        float[] triangleVertices = t.getVertices();
        // If z is not the same, collision is impossible
        if (triangleVertices[2] != z) return false;

        // Extract vertices of the triangle
        float x1 = triangleVertices[0];
        float y1 = triangleVertices[1];
        float x2 = triangleVertices[3];
        float y2 = triangleVertices[4];
        float x3 = triangleVertices[6];
        float y3 = triangleVertices[7];

        // Calculate barycentric coordinates
        float detT = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
        float alpha = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / detT;
        float beta = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / detT;
        float gamma = 1 - alpha - beta;

        // Check if the point is inside the triangle
        return alpha >= 0 && beta >= 0 && gamma >= 0 && alpha <= 1 && beta <= 1 && gamma <= 1;
    }
}
