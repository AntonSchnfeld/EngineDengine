package engine.dengine.shapes;

import engine.dengine.Constants;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Polygon}</h2>
 * <br>
 * The {@link Polygon} class is used to represent a {@link Shape} which is made up of triangles.
 * A {@link Polygon} instance can be easily built using {@link Polygon.Builder}.
 */
public class Polygon extends Shape
{
    /**
     * Creates a new {@link Polygon} instance.
     * @param vertices the <b>vertices</b> of the new {@link Polygon} instance
     */
    public Polygon (float[] vertices)
    {
        super(vertices);
    }

    /**
     * Checks wether this {@link Polygon} instance is <b>colliding</b> with another {@link Shape} instance.
     * <b>Collision</b> means that at least one <b>vertex</b> of the other instance has to be in the area of
     * this instance.
     * @param anotherShape the {@link Shape} instance to check for collision with this one
     * @return wether this instance is colliding with the other one
     */
    @Override
    public boolean collidesWith(Shape anotherShape)
    {
        for (int i = 0; i < vertices.length; i += Constants.TRIANGLE_SIZE)
        {
            float[] temp = new float[Constants.TRIANGLE_SIZE];
            System.arraycopy(vertices, i, temp, 0, Constants.TRIANGLE_SIZE);
            Triangle triangle = new Triangle(temp);

            if (triangle.collidesWith(anotherShape)) return true;
        }

        return false;
    }

    /**
     * @author Anton Schoenfeld
     * @version 1.0
     * @since 1.0
     * <br>
     * <h2>{@link Polygon.Builder}</h2>
     * <br>
     * The {@link Polygon.Builder} class is used to easily create {@link Polygon} instances
     * using {@link Polygon.Builder#addVertex(float, float)} to add a vertex or
     * {@link Polygon.Builder#addTriangle(Triangle)} /
     * {@link Polygon.Builder#addTriangle(float, float, float, float, float, float)}
     * to add a Triangle.
     * @see Polygon
     */
    public static class Builder
    {
        private float[] vertices;
        private int index;
        private float z;

        /**
         * Creates a new {@link Polygon.Builder} instance.
         * @param size the vertex capacity of the {@link Polygon.Builder} instance
         * @param z the z coordinate of the {@link Polygon} instance which will be produced
         */
        public Builder (int size, float z)
        {
            vertices = new float[size * Constants.POSITION_SIZE];
            index = 0;
            this.z = z;
        }

        /**
         * Adds a vertex to this {@link Polygon.Builder} instance.
         * @param x the x coordinate of the vertex
         * @param y the y coordinate of the vertex
         * @return this {@link Polygon.Builder} instance
         */
        public Builder addVertex (float x, float y)
        {
            vertices[index] = x;
            vertices[index + 1] = y;
            vertices[index + 2] = z;

            index += 3;

            return this;
        }

        /**
         * Adds a {@link Triangle} instances vertices to this {@link Polygon.Builder} instance.
         * @param triangle the {@link Triangle} instance which will provide the vertices
         * @return this {@link Polygon.Builder} instance
         */
        public Builder addTriangle (Triangle triangle)
        {
            float[] triangleVertices = triangle.vertices;

            if (triangleVertices[2] != z) throw new IllegalArgumentException("Tried to add " + Triangle.class.getName()
                    + " to " + getClass().getName() + " with different z coordinate");

            System.arraycopy(triangleVertices, 0, vertices, index, Constants.TRIANGLE_SIZE);

            index += Constants.TRIANGLE_SIZE;

            return this;
        }

        /**
         * Adds a triangle to this {@link Polygon.Builder} instance.
         * @param x1 the x coordinate of the first triangle vertex
         * @param y1 the y coordinate of the first triangle vertex
         * @param x2 the x coordinate of the second triangle vertex
         * @param y2 the y coordinate of the second triangle vertex
         * @param x3 the x coordinate of the third triangle vertex
         * @param y3 the y coordinate of the third triangle vertex
         * @return this {@link Polygon.Builder} instance
         */
        public Builder addTriangle (float x1, float y1, float x2, float y2, float x3, float y3)
        {
            vertices[index] = x1;
            vertices[index + 1] = y1;
            vertices[index + 2] = z;
            vertices[index + 3] = x2;
            vertices[index + 4] = y2;
            vertices[index + 5] = z;
            vertices[index + 6] = x3;
            vertices[index + 7] = y3;
            vertices[index + 8] = z;

            index += 9;

            return this;
        }

        /**
         * Returns the {@link Polygon} instance with all the added vertices.
         * @return the {@link Polygon} instance
         */
        public Polygon toPolygon ()
        {
            return new Polygon(vertices);
        }
    }
}
