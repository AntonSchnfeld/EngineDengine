package engine.dengine.ecs;

import engine.dengine.graphics.RenderBatch;
import engine.dengine.graphics.Sprite;
import engine.dengine.shapes.Shape;
import org.joml.Vector4f;

public class RenderComponent extends Component
{
    private Vector4f color;
    private Sprite sprite;
    private Shape shape;
    private Transform lastTransform;
    private boolean dirty;

    public RenderComponent (Shape shape, Sprite sprite)
    {
        this(shape, sprite, new Vector4f(1, 1, 1, 1));
    }

    public RenderComponent (Shape shape, Sprite sprite, Vector4f color)
    {
        this.shape = shape;
        this.sprite = sprite;
        this.color = color;
        dirty = false;
    }

    @Override
    public void init ()
    {
        this.lastTransform = entity.getTransform();
        dirty = true;
    }

    @Override
    public void update (float deltaTime)
    {
        if (!entity.getTransform().equals(lastTransform))
        {
            lastTransform = entity.getTransform();
            dirty = true;
        }
    }

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

    public void setSprite (Sprite sprite)
    {
        this.sprite = sprite;
        dirty = true;
    }

    public void setColor (Vector4f color)
    {
        this.color = color;
        dirty = true;
    }

    public boolean isDirty ()
    {
        return dirty;
    }

    public void clean ()
    {
        this.dirty = false;
    }
}
