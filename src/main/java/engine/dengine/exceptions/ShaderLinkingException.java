package engine.dengine.exceptions;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link ShaderLinkingException}</h2>
 * <br>
 * A {@link ShaderLinkingException} is thrown when a {@link engine.dengine.assets.Shader} fails to link
 * its <b>shaders</b> into a <b>program</b>.
 */
public class ShaderLinkingException extends Exception
{
    public ShaderLinkingException (String msg)
    {
        super(msg);
    }

    public ShaderLinkingException ()
    {
        super();
    }
}
