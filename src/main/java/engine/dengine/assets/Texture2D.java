package engine.dengine.assets;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL33C.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture2D
{
    private final int id; // Texture id for OpenGL operations
    private final IntBuffer width, height; // Width and height of image

    protected Texture2D(String filepath)
    {
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
        ByteBuffer data = stbi_load(filepath, width, height, nrChannels, 4);

        if (data != null)
        {
            final int colorMode = GL_RGBA;

            data.flip();
            glTexImage2D(GL_TEXTURE_2D, 0, colorMode, width.get(), height.get(), 0, colorMode, GL_UNSIGNED_BYTE, data);
            stbi_image_free(data);
        }
        else
        {
            assert false : "Could not load texture from path " + filepath;
        }
    }
    public void use (int slot)
    {
        if (slot >= 0 && slot <= 31)
        {
            glBindTexture(GL_TEXTURE_2D, id);
            glActiveTexture(GL_TEXTURE0+slot);
            return;
        }
        throw new IllegalArgumentException("slot is either smaller than 0 or larger than 31");
    }
    public int getId ()
    {
        return id;
    }
    public int getHeight ()
    {
        return height.get();
    }
    public int getWidth ()
    {
        return width.get();
    }
    protected void dispose ()
    {
        glDeleteTextures(id);
    }
}
