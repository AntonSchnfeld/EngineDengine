package engine.dengine.graphics;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link RenderBatch}</h2>
 * <br>
 * The {@link RenderBatch} class is used to <b>batch vertices</b> together, so they can be rendered in
 * a single <b>OpenGL</b> call.
 */
public class RenderBatch
{
    // Vertex is made up like this:
    // Position             Color                   Texture Mapping     Texture Identifier
    // float: x, y, z   |   float: r, g, b, a   |   float: u, v     |   float: texture_id

    private static final int POSITION_POS = 0;
    private static final int COLOR_POS = 1;
    private static final int UV_POS = 2;
    private static final int TEXTURE_ID_POS = 3;

    public static final int POSITION_SIZE = 3;
    public static final int COLOR_SIZE = 4;
    public static final int UV_SIZE = 2;
    public static final int TEXTURE_ID_SIZE = 1;
    public static final int VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE + UV_SIZE + TEXTURE_ID_SIZE;

    private static final int POSITION_STRIDE = POSITION_SIZE * Float.BYTES;
    private static final int COLOR_STRIDE = COLOR_SIZE * Float.BYTES;
    private static final int UV_STRIDE = UV_SIZE * Float.BYTES;
    private static final int TEXTURE_ID_STRIDE = TEXTURE_ID_SIZE * Float.BYTES;
    private static final int VERTEX_STRIDE = POSITION_STRIDE + COLOR_STRIDE + UV_STRIDE + TEXTURE_ID_STRIDE;

    private static final int POSITION_POINTER = 0;
    private static final int COLOR_POINTER = POSITION_POINTER + POSITION_SIZE;
    private static final int UV_POINTER = COLOR_POINTER + COLOR_STRIDE;
    private static final int TEXTURE_ID_POINTER = UV_POINTER + UV_STRIDE;

    private static final int MAX_BATCH_SIZE = 1000;

    private int VBO, EBO, VAO;
}
