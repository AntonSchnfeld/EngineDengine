package engine.dengine.assets;

import org.lwjgl.BufferUtils;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33C.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Texture2D}</h2>
 * <br>
 * The {@link Texture2D} class serves as an abstraction of <b>OpenGl 2D textures</b>. To obtain
 * an instance of {@link Texture2D}, use {@link AssetManager#getTexture2D(String)} or
 * {@link AssetManager#addTexture2D(String, boolean)}. This guarantees that no {@link Texture2D}
 * with an identical <b>source image</b> to another {@link Texture2D}
 * is instantiated. To dispose of a {@link Texture2D} instance from the {@link AssetManager} and delete it
 * from <b>OpenGL</b>, use {@link AssetManager#disposeTexture2D(Texture2D)} or
 * {@link AssetManager#disposeTexture2D(String)};
 */
public class Texture2D
{
    private final int id; // Texture id for OpenGL operations
    private final IntBuffer width, height; // Width and height of texture
    private final String filePath; // file path of source file

    /**
     * Creates a new {@link Texture2D} instance from the given image, by reading
     * the images <b>bytes</b> with {@link org.lwjgl.stb.STBImage#stbi_load(CharSequence, IntBuffer, IntBuffer, IntBuffer, int)}
     * and uploads it to <b>OpenGL</b>, then deletes the <b>bytes</b> from <b>stbi</b>.
     *
     * @param filepath the path to the image file
     * @param hasAlpha if the image file has a alpha channel
     * @throws FileNotFoundException if the image file could not be found or the file is not an image
     */
    protected Texture2D(String filepath, boolean hasAlpha)
            throws FileNotFoundException
    {
        this.filePath = filepath;

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        // Set texture parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT); // Repeat texture when stretched
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        // load and generate the texture
        width = BufferUtils.createIntBuffer(1);
        height = BufferUtils.createIntBuffer(1);
        IntBuffer nrChannels = BufferUtils.createIntBuffer(1);
        ByteBuffer data = stbi_load(filepath, width, height, nrChannels, hasAlpha ? 4 : 3);

        if (data == null)
        {
            throw new FileNotFoundException("Could not load texture from path " + filepath);
        }

        final int colorMode = hasAlpha ? GL_RGBA : GL_RGB;
        data.flip();
        glTexImage2D(
                GL_TEXTURE_2D, 0, colorMode, width.get(), height.get(),
                0, colorMode, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);
    }

    /**
     * Binds this {@link Texture2D} to the <b>OpenGL context</b> and binds it to
     * a <b>texture slot</b>.
     * @param slot the <b>texture slot</b> this {@link Texture2D} should be bound to
     */
    public void use (int slot)
    {
        if (slot >= 0 && slot <= 31)
        {
            glBindTexture(GL_TEXTURE_2D, id);
            glActiveTexture(GL_TEXTURE0 + slot);
            return;
        }
        throw new IllegalArgumentException(
                "Tried to bind Texture2D to texture slot that is either smaller than 0 or larger than 31");
    }

    /**
     * Returns the {@link Texture2D} height in <b>pixels</b>.
     * @return the height in <b>pixels</b>
     */
    public int getHeight ()
    {
        return height.get();
    }

    /**
     * Returns the {@link Texture2D} width in <b>pixels</b>.
     * @return the width in <b>pixels</b>
     */
    public int getWidth ()
    {
        return width.get();
    }

    /**
     * Returns the file path to this {@link Texture2D} instances <b>source image</b>.
     * @return file path to <b>source image</b>
     */
    public String getFilePath ()
    {
        return filePath;
    }

    /**
     * Deletes this {@link Texture2D} from <b>OpenGL</b>.
     */
    protected void dispose ()
    {
        glDeleteTextures(id);
    }

    /**
     * Indicates wether this {@link Texture2D} instance is "equal" to another {@link Object} instance.
     * The reference object is "equal" to this instance if it is of type {@link Texture2D} and has the same
     * <b>OpenGL texture id</b>, width and height as this instance.
     * @param obj the reference object
     * @return wether this instance and the reference object are "equal"
     */
    @Override
    public boolean equals (Object obj)
    {
        if (obj == null) return false;
        if (obj instanceof Texture2D tex)
            return tex.id == this.id
                    && this.width.get() == tex.width.get() && this.height.get() == tex.height.get()
                    && this.filePath.equals(tex.filePath);
        return false;
    }

    /**
     * Returns a {@link String} representation of this {@link Texture2D} instance.
     * @return a {@link String} representation of this {@link Texture2D} instance
     */
    @Override
    public String toString ()
    {
        return getClass().getName()
                + "[" + id + ", " + width.get() + ", " + height.get() + ", " + filePath +"]";
    }

    /**
     * Returns a hash code value which represents this {@link Texture2D} instance.
     * @return a hash code value which represents this {@link Texture2D} instance
     */
    @Override
    public int hashCode ()
    {
        return height.get() + width.get() + id + filePath.hashCode();
    }

    /**
     * Returns the <b>OpenGL identifier</b> which is used to perform operations like binding to a
     * <b>texture slot</b>
     * or deleting the <b>texture</b>.
     * @return the <b>OpenGL identifier</b>
     */
    public int getId()
    {
        return id;
    }
}
