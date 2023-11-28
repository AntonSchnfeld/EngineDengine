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
    /**
     * Checks wether a three-dimensional point is within the boundaries of a {@link Rectangle} instance.
     * @param r the {@link Rectangle} instance
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     * @return wether the point lies within the boundaries of the {@link Rectangle} instance
     */
    public static boolean isPointInRectangle (Rectangle r, float x, float y, float z)
    {
        float[] rectangleVertices = r.getVertices();

        // If they are on different z indexes, they cant be colliding
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

        // Check if the point is inside the rectangle using the bounding box algorithm
        return x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && // Is x within the x bounds of the rectangle
                y >= Math.min(y1, y3) && y <= Math.max(y1, y3);  // Is y within the y bounds of the rectangle
    }

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
