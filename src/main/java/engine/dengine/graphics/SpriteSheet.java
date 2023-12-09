package engine.dengine.graphics;

import engine.dengine.assets.Texture2D;
import org.joml.Vector4f;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>
 * {@link SpriteSheet}
 * </h2>
 * The {@link SpriteSheet} class is used to parse <b>spritesheets</b> into arrays of {@link Sprite} instances.
 */
public class SpriteSheet
{
    private final Texture2D texture;
    private final Sprite[] sprites;

    /**
     * Creates a new {@link SpriteSheet} instance with a spacing of 0.
     * @param texture2D the <b>spritesheet</b>
     * @param spriteHeight the height of one <b>sprite</b>
     * @param spriteWidth the width of one <b>sprite</b>
     * @param numSprites how many sprites are on the <b>spritesheet</b>
     */
    public SpriteSheet (Texture2D texture2D, int spriteHeight, int spriteWidth, int numSprites)
    {
        this(texture2D, spriteHeight, spriteWidth, numSprites, 0);
    }

    /**
     * Creates a new {@link SpriteSheet} instance.
     * @param texture2D the <b>spritesheet</b>
     * @param spriteHeight the height of one <b>sprite</b>
     * @param spriteWidth the width of one <b>sprite</b>
     * @param numSprites how many sprites are on the <b>spritesheet</b>
     * @param spriteSpacing the spacing between the <b>sprites</b>
     */
    public SpriteSheet (Texture2D texture2D, int spriteHeight, int spriteWidth, int numSprites, int spriteSpacing)
    {
        this.texture = texture2D;
        this.sprites = new Sprite[numSprites];

        int currX = 0, currY = texture.getHeight() - spriteHeight;

        for (int i = 0; i < numSprites; i++)
        {
            float topY = (currY + spriteHeight) / (float) texture.getHeight(),
                    bottomY = currY / (float) texture.getHeight(),
                    leftX = currX / (float) texture.getWidth(),
                    rightX = (currX + spriteWidth) / (float) texture.getWidth();

            float[] uvs =
                    {
                            leftX, topY,    // Top left
                            rightX, topY,   // Top right
                            leftX, bottomY, // Bottom left
                            rightX, bottomY // Bottom right
                    };

            Sprite sprite = new Sprite(texture, uvs, spriteWidth, spriteHeight);
            sprites[i] = sprite;

            currX += spriteWidth + spriteSpacing;
            if (currX >= texture.getWidth())
            {
                currX = 0;
                currY -= spriteHeight + spriteSpacing;
            }
        }
    }

    /**
     * Returns the {@link Sprite} instance associated with that index.
     * @param index the index
     * @return the {@link Sprite} instance
     */
    public Sprite getSprite (int index)
    {
        return sprites[index];
    }

    /**
     * Returns all {@link Sprite} instances.
     * @return an array of {@link Sprite} instances
     */
    public Sprite[] getSprites ()
    {
        return sprites;
    }

    /**
     * Returns the number of {@link Sprite} instances stored in this {@link SpriteSheet} instance.
     * @return the number of {@link Sprite} instances
     */
    public int getSpriteCount ()
    {
        return sprites.length;
    }
}
