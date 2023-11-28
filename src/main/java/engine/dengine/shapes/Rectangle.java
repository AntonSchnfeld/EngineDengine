package engine.dengine.shapes;

import engine.dengine.math.MathUtil;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Rectangle}</h2>
 * <br>
 * The {@link Rectangle} class is used to simulate a geometrical rectangle in three-dimensional space.
 * @see Shape
 */
public class Rectangle extends Shape
{
    /**
     * Creates a new {@link Rectangle} instance with the given measurements.
     * @param x the x-coordinate of the top left corner
     * @param y the y-coordinate of the top right corner
     * @param z the z coordinate of the {@link Rectangle} instance
     * @param width the width of the {@link Rectangle} instance
     * @param height the height of the {@link Rectangle} instance
     */
    public Rectangle (float x, float y, float z, float width, float height)
    {
        super(new float[]
                {
                        x, y, z, // Top left
                        x + width, y, z, // Top right
                        x, y - height, z, // Bottom left
                        x + width, y - height, z // Bottom right
                });
    }

    /**
     * Checks wether this {@link Shape} instance is <b>colliding</b> with another {@link Shape} instance.
     * <b>Collision</b> means that at least one <b>vertex</b> of the other instance has to be in the area of
     * this instance.
     * @param anotherShape the {@link Shape} instance to check for collision with this one
     * @return wether this instance is colliding with the other one
     */
    @Override
    public boolean collidesWith(Shape anotherShape)
    {
        float[] anotherVertices = anotherShape.getVertices();

        // Loop through vertices of anotherShape
        for (int i = 0; i < anotherVertices.length; i += 3) {
            float x = anotherVertices[i];
            float y = anotherVertices[i + 1];
            float z = anotherVertices[i + 2];

            // Check if the point (x, y, z) is inside the rectangle
            if (MathUtil.isPointInRectangle(this, x, y, z))
            {
                return true; // Collision detected
            }
        }
        // No collision detected
        return false;
    }
}
