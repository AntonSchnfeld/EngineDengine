package engine.dengine.window;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link KeyListener}</h2>
 * <br>
 * The {@link KeyListener} class is used to handle <b>GLFW key events</b>. Checking if a key is pressed
 * can be done via {@link KeyListener#isKeyPressed(int)}.
 */
public class KeyListener extends GLFWKeyCallback
{
    private static final boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static class SingletonHolder
    {
        private static final KeyListener theKeyListener = new KeyListener();
    }
    /**
     * Returns the {@link KeyListener} instance.
     * @return the {@link KeyListener} instance
     */
    protected static KeyListener getInstance()
    {
        return SingletonHolder.theKeyListener;
    }

    /**
     * Checks if a key is currently pressed. For the constants regarding <b>keyCode</b>,
     * see {@link org.lwjgl.glfw.GLFW}.
     * @param keyCode the <b>GLWF key code</b> of the key
     * @return wether the key is currently being pressed
     */
    public static boolean isKeyPressed (int keyCode)
    {
        return keys[keyCode];
    }

    @Override
    public void invoke (long window, int key, int scancode, int action, int mods)
    {
        keys[key] = action == GLFW_PRESS || action == GLFW_REPEAT;
    }
}
