package engine.dengine.exceptions;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link ShaderAttachmentException}</h2>
 * <br>
 * A {@link ShaderAttachmentException} is thrown when a {@link engine.dengine.assets.Shader} fails to attach either its
 * <b>vertex</b> or <b>fragment shader</b> to itself.
 */
public class ShaderAttachmentException extends Exception
{
    public ShaderAttachmentException (String msg)
    {
        super(msg);
    }

    public ShaderAttachmentException ()
    {
        super();
    }
}
