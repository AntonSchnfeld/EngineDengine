package engine.dengine.graphics;

import engine.dengine.assets.Texture2D;


/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Sprite}</h2>
 * <br>
 * The {@link Sprite} class is used to store a part of a {@link Texture2D} instance, not the entire image.
 * It is a storage of <b>UVs</b> combined with <b>textures</b>.
 */
public class Sprite
{
    private Texture2D texture;
    private float[] uvs;

    /**
     * Creates a new {@link Sprite} instance which contains the whole texture.
     * @param texture the texture which should be used
     */
    public Sprite (Texture2D texture)
    {
        this(texture, new float[]
                {
                        0, 1,
                        1, 1,
                        0, 0,
                        1, 0
                });
    }

    /**
     * Creates a new {@link Sprite} instance with the specified <b>texture</b> and <b>UVs</b>.
     * @param texture the <b>texture</b> which should be used
     * @param uvs the <b>UVs</b> which will be used
     */
    public Sprite (Texture2D texture, float[] uvs)
    {
        this.texture = texture;
        this.uvs = uvs;
    }

    /**
     * Returns the stored {@link Texture2D} instance.
     * @return the stored <b>texture</b>
     */
    public Texture2D getTexture ()
    {
        return texture;
    }

    /**
     * Returns the stored <b>UVs</b>.
     * @return the <b>UVs</b>
     */
    public float[] getUvs ()
    {
        return uvs;
    }

    /**
     * Returns the <b>OpenGL identifier</b> of the stored {@link Texture2D} instance.
     * @return the <b>texture identifier</b>
     * @see Texture2D#getId()
     */
    public int getTextureId ()
    {
        return texture.getId();
    }
}
