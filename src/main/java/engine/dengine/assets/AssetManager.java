package engine.dengine.assets;

import engine.dengine.io.FileLoader;

import java.util.HashMap;

public class AssetManager
{
    private static final HashMap<String, Shader> shaders;
    private static final HashMap<String, Texture2D> texture2Ds;

    private AssetManager () {}

    static
    {
        shaders = new HashMap<>();
        texture2Ds = new HashMap<>();

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

    public static Shader getShader (String vertFilePath, String fragFilePath)
    {
        String key = vertFilePath.concat(fragFilePath);
        if (shaders.containsKey(key)) return shaders.get(key);
        Shader newShader = new Shader(FileLoader.readFile(fragFilePath), FileLoader.readFile(fragFilePath));
        shaders.put(key, newShader);
        return newShader;
    }

    public static Texture2D getTexture2D (String filePath)
    {
        if (texture2Ds.containsKey(filePath)) return texture2Ds.get(filePath);
        Texture2D newTexture = new Texture2D(filePath);
        texture2Ds.put(filePath, newTexture);
        return newTexture;
    }

    public static void disposeTexture2D (String filePath)
    {
        if (!texture2Ds.containsKey(filePath)) return;
        Texture2D tex = texture2Ds.get(filePath);
        texture2Ds.remove(filePath);
        tex.dispose();
    }

    public static void disposeTexture2D (Texture2D tex)
    {
        texture2Ds.forEach((key, value) -> {
            if (value == tex)
            {
                texture2Ds.remove(key);
            }
        });
    }

    public static void disposeShader (String vertFilePath, String fragFilePath)
    {
        String key = vertFilePath.concat(fragFilePath);
        if (!shaders.containsKey(key)) return;
        Shader shader = shaders.get(key);
        shaders.remove(key);
        shader.dispose();
    }

    public static void disposeShader (Shader shader)
    {
        shaders.forEach((key, value) -> {
            if (value == shader)
            {
                shaders.remove(key);
            }
        });
    }
}
