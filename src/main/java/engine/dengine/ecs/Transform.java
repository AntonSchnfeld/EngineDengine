package engine.dengine.ecs;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform
{
    private Vector3f position;
    private Vector2f scale;

    public Transform (Vector3f position, Vector2f scale)
    {
        this.position = position;
        this.scale = scale;
    }

    public void setPosition (Vector3f position)
    {
        this.position = position;
    }

    public void setScale (Vector2f scale)
    {
        this.scale = scale;
    }

    public Vector3f getPosition ()
    {
        return position;
    }

    public Vector2f getScale ()
    {
        return scale;
    }
}
