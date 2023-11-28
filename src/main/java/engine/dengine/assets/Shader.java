package engine.dengine.assets;

import engine.dengine.exceptions.ShaderAttachmentException;
import engine.dengine.exceptions.ShaderCompileException;
import engine.dengine.exceptions.ShaderLinkingException;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL33C;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link Shader}</h2>
 * <br>
 * The {@link Shader} class serves as an abstraction of <b>OpenGL shader programs</b>. To obtain
 * an instance of {@link Shader}, use {@link AssetManager#getShader(String, String)} or
 * {@link AssetManager#addShader(String, String)}. This will guarantee that no {@link Shader}
 * with identical <b>vertex</b> and <b>fragment shaders</b> to another {@link Shader} is instantiated.
 * To dispose of a {@link Shader} instance from the {@link AssetManager} and delete it from <b>OpenGL</b>,
 * use {@link AssetManager#disposeShader(Shader)} or {@link AssetManager#disposeShader(String, String)}.
 */
public class Shader
{
    private final int id;

    /**
     * Creates a new {@link Shader} instance with the given <b>fragment</b> and <b>vertex shader</b>, by compiling
     * the aforementioned <b>shaders</b>, attaching them to the <b>program</b> and linking the <b>program</b>.
     * Then, the <b>vertex</b> and <b>fragment shaders</b> are deleted.
     *
     * @param fragSourceCode the source code of the fragment shader
     * @param vertSourceCode the source code of the vertex shader
     * @throws engine.dengine.exceptions.ShaderCompileException    if compiling of either the <b>fragment</b> or <b>vertex shader</b>
     *                                                             fails
     * @throws engine.dengine.exceptions.ShaderAttachmentException if attachment of the <b>vertex</b> and <b>fragment shaders</b>
     *                                                             fails
     * @throws engine.dengine.exceptions.ShaderLinkingException    if linking of the <b>shader program</b> fails
     */
    protected Shader(String fragSourceCode, String vertSourceCode)
            throws ShaderCompileException, ShaderAttachmentException, ShaderLinkingException
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
            throw new ShaderCompileException("Failed to compile fragment shader: " + glGetShaderInfoLog(fragId));

        // Compile vertex shader and check for errors
        GL33C.glCompileShader(vertId);
        if (GL33C.glGetShaderi(vertId, GL_COMPILE_STATUS) == GL_FALSE)
            throw new ShaderCompileException("Failed to compile vertex shader: " + glGetShaderInfoLog(vertId));

        // Attach shaders to program and check for errors
        glAttachShader(id, vertId);
        glAttachShader(id, fragId);
        if (GL33C.glGetProgrami(id, GL_ATTACHED_SHADERS) == GL_FALSE)
            throw new ShaderAttachmentException("Failed to attach vertex and fragment shader to shader program: " + glGetProgramInfoLog(id));

        // Link shaders to shader program and check for errors
        glLinkProgram(id);
        if (GL33.glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE)
            throw new ShaderLinkingException("Failed to link shader program: " + glGetProgramInfoLog(id));

        // Delete fragment and vertex shaders, since they are now linked to the shader program
        glDeleteShader(fragId);
        glDeleteShader(vertId);
    }

    /**
     * Binds this {@link Shader} to the <b>OpenGL context</b>.
     */
    public void use() {
        glUseProgram(id);
    }

    /**
     * Deletes this {@link Shader} from <b>OpenGL</b>.
     */
    protected void dispose() {
        glDeleteProgram(id);
    }

    /**
     * Gets the location of a <b>uniform variable</b>.
     *
     * @param uniformName the variable name of the <b>uniform</b>
     * @return the location of the uniform
     */
    public int getUniformLocation(String uniformName) {
        return glGetUniformLocation(id, uniformName);
    }

    /**
     * Uploads a <b>float</b> value into a <b>uniform variable</b> of type <b>float</b> of this {@link Shader}.
     *
     * @param uniform the variable name of the <b>uniform</b>
     * @param value   the <b>float</b> value which is to be uploaded
     */
    public void uploadUniform1f(String uniform, float value) {
        glUniform1f(GL33C.glGetUniformLocation(id, uniform), value);
    }

    /**
     * Uploads a <b>int</b> value into a <b>uniform variable</b> of type <b>int/uint/bool</b> of this {@link Shader}.
     *
     * @param uniform the variable name of the <b>uniform</b>
     * @param value   the <b>int</b> value which is to be uploaded
     */
    public void uploadUniform1i(String uniform, int value) {
        glUniform1i(GL33C.glGetUniformLocation(id, uniform), value);
    }

    /**
     * Uploads a {@link Texture2D} into a <b>uniform variable</b> of type <b>sampler2D</b> of this {@link Shader}.
     *
     * @param uniform the variable name of the <b>uniform</b>
     * @param slot    the <b>texture slot</b> the {@link Texture2D} is bound to
     */
    public void uploadUniformTexture2D(String uniform, int slot) {
        glUniform1i(GL33C.glGetUniformLocation(id, uniform), slot);
    }

    /**
     * Uploads a {@link Matrix4f} into a <b>uniform variable</b> of type <b>mat4</b> of this {@link Shader}.
     *
     * @param uniform the variable name of the <b>uniform</b>
     * @param value   alue the {@link Matrix4f} which is to be uploaded
     */
    public void uploadMat4f(String uniform, Matrix4f value) {
        glUniformMatrix4fv(GL33C.glGetUniformLocation(id, uniform)
                , false, value.get(new float[16]));
    }

    /**
     * Indicates wether this {@link Shader} instance is "equal" to another {@link Object} instance.
     * The reference object is "equal" to this instance if it is of type {@link Shader} and has the same
     * <b>OpenGL program id</b> as this instance.
     * @param obj the reference object
     * @return wether this instance and the reference object are "equal"
     */
    @Override
    public boolean equals (Object obj)
    {
        if (obj == null) return false;
        if (obj instanceof Shader shader)
            return shader.id == this.id;
        return false;
    }

    /**
     * Returns a {@link String} representation of this {@link Shader} instance.
     * @return a {@link String} representation of this {@link Shader} instance
     */
    @Override
    public String toString ()
    {
        return getClass().getName() + "[" + id + "]";
    }

    /**
     * Returns a hash code value which represents this {@link Shader} instance.
     * @return a hash code value which represents this {@link Shader} instance
     */
    @Override
    public int hashCode ()
    {
        return id;
    }
}
