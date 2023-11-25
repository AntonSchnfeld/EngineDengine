package engine.dengine.assets;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL33C;
import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Anton Schoenfeld
 *
 * <br>
 * <h2>{@link Shader}</h2>
 * <br>
 * <p>
 * The <b>{@link Shader}</b> class is an abstraction of <b>OpenGL Shader Programs</b> and is able to perform
 * all operations that would be possible with a <i>raw <b>Shader Program</b></i>. The {@link Shader} class
 * should not be instantiated twice with the same <b>Vertex</b> and <b>Fragment Shaders</b>, as that
 * would be a waste of memory.
 * </p>
 */
public class Shader
{
    /**
     * This field holds the OpenGL Identifier for this shader program. Using this Identifier, it is possible
     * to perform actions on this program.
     */
    private final int id;

    /**
     * Creates a new {@link Shader} object.
     *
     * @param fragSourceCode the source code of the fragment shader
     * @param vertSourceCode the source code of the vertex shader
     * @throws AssertionError if assertions are enabled and the Compiling of the Fragment or Vertex Shader,
     * the Linking of the Shaders to the Program or the Attaching of the Shaders to the Program goes wrong
     */
    protected Shader (String fragSourceCode, String vertSourceCode)
    {
        // Create Shader program
        this.id = GL33C.glCreateProgram();

        // Create vertex and fragment shaders
        int fragId = GL33C.glCreateShader(GL_FRAGMENT_SHADER);
        int vertId = GL33C.glCreateShader(GL_VERTEX_SHADER);

        // Provide vertex and fragment shaders with source code
        GL33C.glShaderSource(fragId, fragSourceCode);
        GL33C.glShaderSource(vertId, vertSourceCode);

        // Compile fragment shader and check for errors
        GL33C.glCompileShader(fragId);
        if (GL33C.glGetShaderi(fragId, GL_COMPILE_STATUS) == GL_FALSE)
        {
            assert false : "Error performing fragment shader compilation: " + GL33C.glGetShaderInfoLog(fragId);
        }

        // Compile vertex shader and check for errors
        GL33C.glCompileShader(vertId);
        if (GL33C.glGetShaderi(vertId, GL_COMPILE_STATUS) == GL_FALSE)
        {
            assert false : "Error performing vertex shader compilation: " + GL33C.glGetShaderInfoLog(vertId);
        }

        // Attach shaders to program and check for errors
        glAttachShader(id, vertId);
        glAttachShader(id, fragId);
        if (GL33C.glGetProgrami(id, GL_ATTACHED_SHADERS) == GL_FALSE)
        {
            String msg = GL33.glGetProgramInfoLog(id);
            assert false : "Error attaching fragment and vertex shader to shader program: " + id;
        }

        // Link shaders to shader program and check for errors
        glLinkProgram(id);
        if (GL33.glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE)
        {
            assert false : "Error linking shader program: " + GL33C.glGetProgramInfoLog(id);
        }

        // Delete fragment and vertex shaders, since they are now linked to the shader program
        glDeleteShader(fragId);
        glDeleteShader(vertId);
    }

    public void use ()
    {
        GL33C.glUseProgram(id);
    }
    protected void dispose ()
    {
        GL33C.glDeleteProgram(id);
    }
    public int getUniformLocation (String uniformName)
    {
        return glGetUniformLocation(id, uniformName);
    }
    public void uploadUniform1f (String uniform, float value)
    {
        glUniform1f(GL33C.glGetUniformLocation(id, uniform), value);
    }
    public void uploadUniform1i (String uniform, int value)
    {
        glUniform1i(GL33C.glGetUniformLocation(id, uniform), value);
    }
    public void uploadUniformTexture2D (String uniform, int slot)
    {
        glUniform1i(GL33C.glGetUniformLocation(id, uniform), slot);
    }

    public void uploadMat4f(String uniform, Matrix4f value)
    {
        glUniformMatrix4fv(GL33C.glGetUniformLocation(id, uniform)
                , false, value.get(new float[16]));
    }
}
