package engine.dengine.graphics;

import org.joml.Vector4f;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Vertex}</h2>
 * <br>
 * The {@link Vertex} class is used to represent a <b>vertex</b> for <b>rendering</b>.
 * It stores x, y and z coordinates, the r, g, b and a values of its color, UV coordinates
 * and the <b>texture identifier</b>.
 */
public class Vertex
{
    private float x, y, z;
    private Vector4f color;
    private float u, v;
    private float textureId;
    private boolean dirty;

    /**
     * Creates a new {@link Vertex} instance with default values.
     */
    public Vertex ()
    {
        this(0, 0, 0, null, 0, 0, 0);
    }

    /**
     * Creates a new {@link Vertex} instance.
     * @param x the x coordinate of the new {@link Vertex} instance
     * @param y the y coordinate of the new {@link Vertex} instance
     * @param z the z coordinate of the new {@link Vertex} instance
     * @param color the color of the new {@link Vertex} instance
     * @param u the U UV coordinate of the new {@link Vertex} instance
     * @param v the V UV coordinate of the new {@link Vertex} instance
     * @param textureId the texture id of the new {@link Vertex} instance
     */
    public Vertex (float x, float y, float z, Vector4f color, float u, float v, float textureId)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
        this.u = u;
        this.v = v;
        this.textureId = textureId;

        this.dirty = false;
    }

    /**
     * Sets the x-coordinate of the vertex. Makes this {@link Vertex} instance <b>dirty</b>.
     * @param x The new x-coordinate.
     */
    public void setX (float x)
    {
        this.x = x;
        this.dirty = true;
    }

    /**
     * Gets the y-coordinate of the vertex.
     * @return The y-coordinate.
     */
    public float getY ()
    {
        return y;
    }

    /**
     * Sets the y-coordinate of the vertex. Makes this {@link Vertex} instance <b>dirty</b>.
     * @param y The new y-coordinate.
     */
    public void setY (float y)
    {
        this.y = y;
        this.dirty = true;
    }

    /**
     * Gets the z-coordinate of the vertex.
     * @return The z-coordinate.
     */
    public float getZ ()
    {
        return z;
    }

    /**
     * Sets the z-coordinate of the vertex. Makes this {@link Vertex} instance <b>dirty</b>.
     * @param z The new z-coordinate.
     */
    public void setZ (float z)
    {
        this.z = z;
        this.dirty = true;
    }

    /**
     * Gets the color of the vertex.
     * @return The color as a {@link Vector4f} instance.
     */
    public Vector4f getColor ()
    {
        return color;
    }

    /**
     * Sets the color of the vertex. Makes this {@link Vertex} instance <b>dirty</b>.
     * @param color The new color as a {@link Vector4f} instance.
     */
    public void setColor (Vector4f color)
    {
        this.color = color;
        this.dirty = true;
    }

    /**
     * Gets the U coordinate of the vertex's UV coordinates.
     * @return The U coordinate.
     */
    public float getU ()
    {
        return u;
    }

    /**
     * Sets the U coordinate of the vertex's UV coordinates. Makes this {@link Vertex} instance <b>dirty</b>.
     * @param u The new U coordinate.
     */
    public void setU (float u)
    {
        this.u = u;
        this.dirty = true;
    }

    /**
     * Gets the V coordinate of the vertex's UV coordinates.
     * @return The V coordinate.
     */
    public float getV ()
    {
        return v;
    }

    /**
     * Sets the V coordinate of the vertex's UV coordinates. Makes this {@link Vertex} instance <b>dirty</b>.
     * @param v The new V coordinate.
     */
    public void setV (float v)
    {
        this.v = v;
        this.dirty = true;
    }

    /**
     * Gets the texture identifier of the vertex.
     * @return The texture identifier.
     */
    public float getTextureId ()
    {
        return textureId;
    }

    /**
     * Sets the texture identifier of the vertex. Makes this {@link Vertex} instance <b>dirty</b>.
     * @param textureId The new texture identifier.
     */
    public void setTextureId (float textureId)
    {
        this.textureId = textureId;
        this.dirty = true;
    }

    /**
     * Returns wether this {@link Vertex} instance is <b>dirty</b>.
     * @return wether this {@link Vertex} instance is <b>dirty</b>
     */
    public boolean getDirty ()
    {
        return dirty;
    }

    /**
     * <b>Cleans</b> this {@link Vertex} instance.
     */
    public void clean ()
    {
        dirty = false;
    }
}
