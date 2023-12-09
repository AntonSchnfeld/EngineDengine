package engine.dengine.ecs;

import engine.dengine.graphics.RenderBatch;
import engine.dengine.graphics.Sprite;
import engine.dengine.shapes.Shape;
import org.joml.Vector4f;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link RenderComponent}</h2>
 * <br>
 * The {@link RenderComponent} class is used to represent a {@link Component} which is used to render its
 * {@link Entity}. The {@link RenderComponent} class contains all information which is necessary for <b>rendering</b>.
 * A {@link RenderComponent} will automatically <b>render</b> its {@link Entity}, once it is added to a <b>scene</b>.
 * If relevant <b>rendering</b> data of a {@link RenderComponent} instance is changed, it will become <b>dirty</b> to
 * signal that its <b>vertices</b> have changed and need to be re-uploaded to <b>OpenGL</b>.
 */
public class RenderComponent extends Component
{
    /** The color of the vertices */
    private Vector4f color;
    /** The sprite ( can be null ) */
    private Sprite sprite;
    /** The vertices */
    private Shape shape;
    /** The {@link engine.dengine.ecs.RenderComponent#entity}s {@link engine.dengine.ecs.Transform} */
    private Transform lastTransform;
    /** Wether this {@link engine.dengine.ecs.RenderComponent} is dirty */
    private boolean dirty;

    /**
     * Creates a new {@link RenderComponent} instance with a color of 1, 1, 1, 1.
     * @param shape the {@link Shape}
     * @param sprite the {@link Sprite}
     */
    public RenderComponent (Shape shape, Sprite sprite)
    {
        this(shape, sprite, new Vector4f(1, 1, 1, 1));
    }

    /**
     * Creates a new {@link RenderComponent} instance.
     * @param shape the {@link Shape}
     * @param sprite the {@link Sprite}
     * @param color the <b>color</b> of the {@link RenderComponent}
     */
    public RenderComponent (Shape shape, Sprite sprite, Vector4f color)
    {
        this.shape = shape;
        this.sprite = sprite;
        this.color = color;
        dirty = false;
    }

    /**
     * Initializes the {@link RenderComponent} by assgning its {@link Transform} to its {@link Entity}s
     * {@link Transform}.
     */
    @Override
    public void init ()
    {
        super.init();
        this.lastTransform = entity.getTransform();
        dirty = true;
    }

    /**
     * Updates the {@link RenderComponent} by updating its {@link Transform} to its {@link Entity}s {@link Transform}
     * if it is different from its own.
     */
    @Override
    public void update (float deltaTime)
    {
        super.update(deltaTime);
        Transform entityTransform = entity.getTransform();
        if (!entityTransform.equals(lastTransform))
        {
            lastTransform.set(entityTransform);
            shape.setTransform(lastTransform);
            dirty = true;
        }
    }

    /**
     * Returns a float[] of valid <b>OpenGL vertices</b> which can be used for <b>rendering</b>.
     * @return the float[] of <b>vertices</b>
     */
    public float[] getRawOpenGLVertices ()
    {
        // Create float[] of vertices
        final float[] result = new float[shape.getVertexCount() * RenderBatch.VERTEX_SIZE];

        for (int i = 0; i < shape.getVertexCount(); i++)
        {
            int index = i * RenderBatch.VERTEX_SIZE;

            // Position
            result[index] = shape.getVertices()[i * RenderBatch.POSITION_SIZE];         // x
            result[index + 1] = shape.getVertices()[i * RenderBatch.POSITION_SIZE + 1]; // y
            result[index + 2] = shape.getVertices()[i * RenderBatch.POSITION_SIZE + 2]; // z

            // Color
            result[index + 3] = color.x;        // r
            result[index + 4] = color.y;        // g
            result[index + 5] = color.y;        // b
            result[index + 6] = color.z;        // a

            // UVs
            result[index + 7] = sprite.getUvs()[i + RenderBatch.UV_SIZE];       // U
            result[index + 8] = sprite.getUvs()[i + RenderBatch.UV_SIZE + 1];   // V

            // Texture Id
            result[index + 9] = sprite.getTextureId();      // Id
        }

        return result;
    }

    /**
     * Sets the {@link Sprite} and makes this {@link RenderComponent} instance <b>dirty</b>.
     * @param sprite the new {@link Sprite}
     */
    public void setSprite (Sprite sprite)
    {
        this.sprite = sprite;
        dirty = true;
    }

    /**
     * Sets the <b>color</b> and makes this {@link RenderComponent} instance <b>dirty</b>.
     * @param color the new <b>color</b>
     */
    public void setColor (Vector4f color)
    {
        this.color = color;
        dirty = true;
    }

    /**
     * Wether this {@link RenderComponent} instance is <b>dirty</b>.
     * @return if this {@link RenderComponent} instance is <b>dirty</b>
     */
    public boolean isDirty ()
    {
        return dirty;
    }

    /**
     * <b>Cleans</b> this {@link RenderComponent} instance.
     */
    public void clean ()
    {
        this.dirty = false;
    }
}
