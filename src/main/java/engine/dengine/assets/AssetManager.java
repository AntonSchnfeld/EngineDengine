package engine.dengine.assets;

import engine.dengine.exceptions.ShaderAttachmentException;
import engine.dengine.exceptions.ShaderCompileException;
import engine.dengine.exceptions.ShaderLinkingException;
import engine.dengine.io.FileLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link AssetManager}</h2>
 * <br>
 * The {@link AssetManager} class is used to manage the lifecycles of <b>assets</b> like
 * <ul>
 *     <li>{@link Shader} instances</li>
 *     <li>{@link Texture2D} instances</li>
 * </ul>
 * The {@link AssetManager} also guarantees that there is always only one instance of an asset.
 * For example, using {@link AssetManager#getShader(String, String)}, you can never produce more
 * than one {@link Shader} instance with the same parameters.
 */
public final class AssetManager
{
    private static final HashMap<String, Shader> shaders;
    private static final HashMap<String, Texture2D> texture2Ds;

    // Private constructor to prevent instantiation
    private AssetManager () {}

    static
    {
        // Instantiate caches
        shaders = new HashMap<>();
        texture2Ds = new HashMap<>();

        // Ensure that all assets are disposed of in case of incompetence
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Shader shader : shaders.values())
            {
                shader.dispose();
            }
            for (Texture2D tex : texture2Ds.values())
            {
                tex.dispose();
            }
        }));
    }

    /**
     * Adds a {@link Shader} instance to the {@link AssetManager} cache and returns it if an identical
     * instance does not already exist in the cache. If an identical instance exists, that instance is returned.
     * @param vertFilePath the path to the <b>vertex shader</b>
     * @param fragFilePath the path to the <b>fragment shader</b>
     * @return a {@link Shader} instance with the given <b>vertex</b> and <b>fragment shaders</b>
     * @throws ShaderLinkingException if it gets thrown in {@link Shader#Shader(String, String)}
     * @throws ShaderAttachmentException if it gets thrown in {@link Shader#Shader(String, String)}
     * @throws ShaderCompileException if it gets thrown in {@link Shader#Shader(String, String)}
     * @throws IOException if it gets thrown in {@link FileLoader#readFile(String)} while reading the
     * contents of the <b>vertex</b> and <b>fragment shader</b> source files
     */
    public static Shader addShader (String vertFilePath, String fragFilePath)
            throws ShaderLinkingException, ShaderAttachmentException, ShaderCompileException, IOException
    {
        String key = vertFilePath.concat(fragFilePath);
        if (shaders.containsKey(key)) return shaders.get(key);
        Shader newShader = new Shader(FileLoader.readFile(fragFilePath), FileLoader.readFile(vertFilePath));
        shaders.put(key, newShader);
        return newShader;
    }

    /**
     * Adds a {@link Texture2D} instance to the {@link AssetManager} cache and returns it if an identical
     * instance does not already exist in the cache. If an identical instance exists, that instance is returned.
     * @param filePath the path to the <b>source image</b>
     * @param hasAlpha wether the <b>source image</b> has an alpha channel
     * @return a {@link Texture2D} instance which is constructed from the given <b>source image</b>
     * @throws FileNotFoundException if it gets thrown in {@link Texture2D#Texture2D(String, boolean)}
     */
    public static Texture2D addTexture2D (String filePath, boolean hasAlpha)
            throws FileNotFoundException
    {
        if (texture2Ds.containsKey(filePath)) return texture2Ds.get(filePath);
        Texture2D newTexture = new Texture2D(filePath, hasAlpha);
        texture2Ds.put(filePath, newTexture);
        return newTexture;
    }

    /**
     * Checks if a {@link Shader} instance with the same sources exists and returns that. Otherwise, <b>null</b> is returned.
     *
     * @param vertFilePath the path to the <b>vertex shader</b>
     * @param fragFilePath the path to the <b>fragment shader</b>
     * @return a {@link Shader} instance which is constructed from the given sources or null
     */
    public static Shader getShader (String vertFilePath, String fragFilePath)
    {
        String key = vertFilePath.concat(fragFilePath);
        if (shaders.containsKey(key)) return shaders.get(key);
        return null;
    }

    /**
     * Checks if a {@link Texture2D} instance with the same source exists and returns that. Otherwise, it
     * returns null.
     * @param filePath the path to the <b>source image</b>
     * @return a {@link Texture2D} instance which is constructed from the given source or null
     */
    public static Texture2D getTexture2D (String filePath)
    {
        if (texture2Ds.containsKey(filePath)) return texture2Ds.get(filePath);
        return null;
    }

    /**
     * Calls {@link Texture2D#dispose()} on the {@link Texture2D} instance which was instantiated from the file path and
     * removes it from the {@link AssetManager} cache.
     * @param filePath the file path to the source image of the {@link Texture2D} instance
     */
    public static void disposeTexture2D (String filePath)
    {
        if (!texture2Ds.containsKey(filePath)) return;
        Texture2D tex = texture2Ds.get(filePath);
        texture2Ds.remove(filePath);
        tex.dispose();
    }

    /**
     * Calls {@link Texture2D#dispose()} on the {@link Texture2D} instance and removes it from the {@link AssetManager} cache.
     * @param tex the texture which should be disposed of
     */
    public static void disposeTexture2D (Texture2D tex)
    {
        texture2Ds.values().remove(tex);
    }

    /**
     * Calls {@link Shader#dispose()} on the {@link Shader} instance which was instantiated with this <b>fragment</b> and
     * <b>vertex shader</b> and removes it from the {@link AssetManager} cache.
     * @param vertFilePath the file path to the </b>vertex shader</b> source of the {@link Shader} instance
     * @param fragFilePath the file path to the <b>fragment shader</b> source of the {@link Shader} instance
     */
    public static void disposeShader (String vertFilePath, String fragFilePath)
    {
        String key = vertFilePath.concat(fragFilePath);
        if (!shaders.containsKey(key)) return;
        Shader shader = shaders.get(key);
        shaders.remove(key);
        shader.dispose();
    }

    /**
     * Calls {@link Shader#dispose()} on the {@link Shader} instance and removes it from the {@link AssetManager} cache.
     * @param shader the shader which should be disposed of
     */
    public static void disposeShader (Shader shader)
    {
        shaders.values().remove(shader);
    }

    /**
     * Disposes of all cached <b>assets</b>. The {@link AssetManager} cache will be completely empty after
     * this method call.
     */
    public static void disposeAll ()
    {
        for (Shader shader : shaders.values())
            disposeShader(shader);
        for (Texture2D tex : texture2Ds.values())
            disposeTexture2D(tex);
    }
}
