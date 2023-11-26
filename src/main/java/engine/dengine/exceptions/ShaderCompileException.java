package engine.dengine.exceptions;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link ShaderCompileException}</h2>
 * <br>
 * A {@link ShaderCompileException} is thrown when a {@link engine.dengine.assets.Shader} fails to compile either its
 * <b>vertex</b> or <b>fragment shader</b>.
 */
public class ShaderCompileException extends Exception
{
    public ShaderCompileException (String msg)
    {
        super(msg);
    }

    public ShaderCompileException ()
    {
        super();
    }
}
